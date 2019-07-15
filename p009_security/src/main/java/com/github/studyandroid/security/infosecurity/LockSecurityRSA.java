package com.github.studyandroid.security.infosecurity;

import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import javax.crypto.Cipher;

public class LockSecurityRSA {
    private static final String TAG = LockSecurityRSA.class.getName();
    private static boolean debug = true;
    private static final String CA_DN = "CN=www.beeboxes.com,OU=platform,O=beeboxes,L=beijing,ST=beijing,C=CN";
    private static final String KEY_ALGORITHM = "RSA"; //加密算法RSA
    private static final String CIPHER_TRANSFORMAT = "RSA/ECB/PKCS1Padding"; //填充方式（该项一定要选，保证各系统生成的兼容性一致）
    private static final int MAX_ENCRYPT_BLOCK = 117; //RSA最大加密明文大小
    private static final int MAX_DECRYPT_BLOCK = 256; //RSA最大解密密文大小

    /**
     * 根据证书信息获取公钥
     *
     * @param certificate 证书(Base64编码)
     * @return 证书内的公钥(Base64编码)
     */
    public static String getPublicKeyByCertificate(String certificate) {
        try {
            byte[] certByte = Base64.decode(certificate, Base64.DEFAULT);
            CertificateFactory f = CertificateFactory.getInstance("X.509");
            InputStream certIn = new ByteArrayInputStream(certByte);
            X509Certificate cert = (X509Certificate) f.generateCertificate(certIn);
            PublicKey pk = cert.getPublicKey();
            if (pk != null)
                return Base64.encodeToString(pk.getEncoded(), Base64.DEFAULT).replaceAll("[\n\r]", "");
        } catch (Exception e) {
            if (debug) {
                Log.e(TAG, "" + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    /**
     * 获取当前终端设备上的公钥
     *
     * @return 当前终端设备上的公钥(Base64编码)
     */
    public static String getPublicKeyByPlatform() {
        PublicKey pubkey = null;
        String aliasCA = null;

        try {
            boolean found = false;
            KeyStore ks = KeyStore.getInstance("AndroidCAStore");
            ks.load(null, null); //Load default system keystore
            Enumeration<String> aliases = ks.aliases();
            while (aliases.hasMoreElements()) {
                aliasCA = (String) aliases.nextElement();
                Certificate tempCert = ks.getCertificate(aliasCA);
                if (tempCert instanceof X509Certificate) {
                    X509Certificate tempX509Cert = (X509Certificate) tempCert;
                    Principal principal = tempX509Cert.getIssuerDN();
                    String issuerDn = principal.getName();
                    if (issuerDn.contains(CA_DN)) {
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
                X509Certificate cert = (X509Certificate) ks.getCertificate(aliasCA);
                pubkey = cert.getPublicKey();
                if (debug) {
                    Log.e(TAG, "Platform cert: " + Base64.encodeToString(cert.getEncoded(), Base64.DEFAULT).replaceAll("[\n\r]", ""));
                    Log.e(TAG, "Platform pubkey: " + Base64.encodeToString(pubkey.getEncoded(), Base64.DEFAULT).replaceAll("[\n\r]", ""));
                }
            } else {
                return null;
            }

            if (pubkey != null)
                return Base64.encodeToString(pubkey.getEncoded(), Base64.DEFAULT).replaceAll("[\n\r]", "");
        } catch (Exception e) {
            if (debug) {
                Log.e(TAG, "" + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    /**
     * 使用公钥对密文解密
     *
     * @param ciphertext 密文(Base64编码)
     * @param privateKey 公钥(Base64编码)
     * @return 明文信息
     */
    public static String decryptByPublicKey(String ciphertext, String privateKey) {
        byte[] keyBytes = Base64.decode(ciphertext, Base64.DEFAULT);
        try {
            byte[] decryptedData = decryptByPublicKey(keyBytes, privateKey);
            String plaintext = new String(decryptedData, StandardCharsets.UTF_8);
            return plaintext;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey, Base64.DEFAULT);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMAT);
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 使用私钥对明文进行加密
     *
     * @param plaintext  明文信息
     * @param privateKey 私钥(Base64编码)
     * @return 加密后的密文信息(Base64编码)
     */
    public static String encryptByPrivateKey(String plaintext, String privateKey) {
        try {
            byte[] data = plaintext.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedData = encryptByPrivateKey(data, privateKey);
            return Base64.encodeToString(encryptedData, Base64.DEFAULT).replaceAll("[\n\r]", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey, Base64.DEFAULT);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMAT);
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 获取md5加密
     *
     * @param inStr 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5Encrypt(String inStr) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");

            byte[] byteArray = digest.digest(inStr.getBytes());
            String hexString = "";
            for (byte b : byteArray) {
                int temp = b & 255;
                if (temp < 16 && temp >= 0) {
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (Exception e) {
            if (debug) {
                Log.e(TAG, "" + e.getMessage());
                e.printStackTrace();
            }
        }
        return "";
    }
}
