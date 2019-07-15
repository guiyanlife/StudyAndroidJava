package com.github.studyandroid.security.infosecurity;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LockSecurity {
    private static String TAG = LockSecurity.class.getName();
    private static boolean debug = true;
    private static String NEWLINE = System.getProperty("line.separator");
    public static final String LOCKSECURITY_DAT_GEN_PATH =  getApp().getFilesDir() + "/genlocksecurity.dat";
    public static final String ACTION_LOCK_SECURITY_CONFIG_CHANGED = "com.github.studyandroid.security.ACTION_LOCK_SECURITY_CONFIG_CHANGED";
    private static final String TEST_PRIVATE_KEY_CONTENT = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDHh0clHuoo94HH5FhOaYBpHxyeQL7ukP52dYTQEIMtuvrwlg4WSKLClnm1s2TzbMKDHIY9var5QQAAL7TCzAKOYp8swUuMvXWP3uR2IV3/5x8x1X6wyo2RxCDh+GFPTGC0db609JyslzomhdTogfLsDO9eMCFH/xEUkNfXHNb1E9Gz0/w1PEj59WDjCR7zJMo92+VRSAeW2DMqbh1QkFg7ldKuUnKblOrq7CQpuyN7fnv06h9tYgE5zkI9HZGMvra/UuRDDOYwE+mj9ata6mIpLCdHqoY52qMcpvGHQP7epiTDeSQlQ5AA+HsLtFQ778Oc4GCssxs8jsUwzuZfWT37AgMBAAECggEAQfQOaU/JuRbvOELbibfH8XeOPyVJt5QH+OIpr5j1s4UN8762E1mZK1cQDiapZOl7dllUak4qmM+Kj4U1a/qBkc05rlKKhZAOryrPO9kVuEQOhKt8gDXEC/3XnLo8eDjX8m7FRg1hSI+IcjlkwiJKSQqRiQHBzomafMDQB9QJneKEp/bAOs0yHZ17NNOxjjJUuEoLUyHrCwhi8DAMqSfiqtOeHF9HmcOIt9pc5MW5+iv/5k9Q9SAxhCefJQgKoii9JhgDloAv1huCjfl7gFmgCvrW0zUj6Fo+cn4r6Y42GyOL/ItBSZuUd85KEtMWsrHqChWp3rNS9SlJWFhiCxydCQKBgQDs0vWsmRwLqc1LU5/r3sesNxrYoebcb1GGu9feqykecADb19fww/nThQCwoziqtnkURChGevqQhdYcxWozOGdSFNo20/935Oisql8PHbcws0CrCp+JltHWCkXerpGjlq9EDQyNUZ9SO+KLURhvHl+HsUBeN7UXcHbybAKIwPzU/wKBgQDXrzsHI555Sd/ey3fTLsnrwnzkNVy5IQiC8jiL5NgkmSdb1EyuANXIW9++RM86h1Wq+ZjWhOP85nysvsq2NoWPY0IGnQ9phBPL5obE9rg3hAsEMmhd0TP+98hjYpenpxkxqR1tLxMBNokTG903Hm5AGpEz9Ko7zk/U7IIHnh3rBQKBgHNfBm4yHNaVvzx+Hb9zxYRhSAPXQ7eJx4bCxhRk/+AjIlf3W+ygHHnuJnrm2TlZMb0sww4q5Rw5Byj3B5iyVqtHG1NwjdkjvDI5wZuEl3TzLoLk5vnJbqo+UPF9y9eNFg8p6D6EUABoyiGivSuM+W8Ka9g6fU9Q67GH6ugiVEmTAoGACCiRtAht4yqfND9ADhXz/14A+WTnV/1e5A0jhG8omeHNMlUKfX58l7KZ+QmDSTR7UhdU57BpK1TQnHwvEMtpK+5WPgfM4HHlic3zhRnMCBWPkR4TApF6RQe8Zb4B/I5+jnlWKp4gOuiEMNvZ907IXtZibbOwiMiaDnTPylf5y70CgYBbzTRZ4nzS+sXARcv2//c9s7ZJSoU5TUn/E+uT9ythRPORtX+hxTE6j/shBIySR/+47ojmyxUJ4QMmcHm0UKYoIDl8eAvhO0d42/zgdCXv2ZZgP8mf9mHgBhJWCiDmNXfMwp/d2ti75eAi6RNgSwPk+TraPayEUSYMjX9ILt6mpw==";
    private static final String TEST_CERTIFICATION_CONTENT = "MIIDXTCCAkUCCQCcK1c5SmJ7CjANBgkqhkiG9w0BAQsFADCBmDELMAkGA1UEBhMCQ04xEDAOBgNVBAgMB2JlaWppbmcxEDAOBgNVBAcMB2JlaWppbmcxETAPBgNVBAoMCGJlZWJveGVzMREwDwYDVQQLDAhwbGF0Zm9ybTEZMBcGA1UEAwwQd3d3LmJlZWJveGVzLmNvbTEkMCIGCSqGSIb3DQEJARYVcGxhdGZvcm1AYmVlYm94ZXMuY29tMB4XDTE5MDcwOTA3NDM0NVoXDTI5MDcwNjA3NDM0NVowSDELMAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JlaWppbmcxEDAOBgNVBAcMB0JlaWppbmcxFTATBgNVBAoMDEJlZWJveGVzIEx0ZDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMeHRyUe6ij3gcfkWE5pgGkfHJ5Avu6Q/nZ1hNAQgy26+vCWDhZIosKWebWzZPNswoMchj29qvlBAAAvtMLMAo5inyzBS4y9dY/e5HYhXf/nHzHVfrDKjZHEIOH4YU9MYLR1vrT0nKyXOiaF1OiB8uwM714wIUf/ERSQ19cc1vUT0bPT/DU8SPn1YOMJHvMkyj3b5VFIB5bYMypuHVCQWDuV0q5ScpuU6ursJCm7I3t+e/TqH21iATnOQj0dkYy+tr9S5EMM5jAT6aP1q1rqYiksJ0eqhjnaoxym8YdA/t6mJMN5JCVDkAD4ewu0VDvvw5zgYKyzGzyOxTDO5l9ZPfsCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAcBmpleH3PGSsKsnrvNiV3VHSqYJEqvXkIHw9pvCrkjE7Q1V8QkAwyV7pnwJW46kO2RIaT8p0BYWo7QG5CJx3kEMwPAWOeqpe8zMuRr9OAVGtoutiU1mY1SOeIglRHYkp12NYi3dE3cY7BjF5nEMiPvUSXjEUs4OicqlpjDDu8OUy6NXbj0MJA+rAcg+yGShsee++bZLH7SShRr77tcD/NyIc8ygsnGxQtQ7dY7SytNTdj8V8bL5mBHPfTqE0C7kCCh4xz+qcOw9/1lwz4G0E4ep98ahgLtwBUVjL6t16UNQmJY6fKUMjsuilMoltE8765nKKdKpnoNZ8BRa3DfbHKA==";

    private static String magicStringLK = "LK";
    private StringBuffer rawMagic = new StringBuffer("");          //dat文件的幻数
    private StringBuffer rawEncryptInfo = new StringBuffer("");    //dat文件中的明文加密后（密文）信息
    private StringBuffer rawPassword = new StringBuffer("");  //蓝牙锁配置明文的密码
    private StringBuffer rawDeadline = new StringBuffer("");  //蓝牙锁配置明文的有效期截止时间
    private StringBuffer rawMACs = new StringBuffer("");      //蓝牙锁配置明文的SN与MAC的键值对
    private StringBuffer rawMd5 = new StringBuffer("");       //蓝牙锁配置明文的Md5信息完整性校验信息
    private StringBuffer rawLockConfig = new StringBuffer("");//蓝牙锁配置明文中的配置信息（密码，deadline，macs的信息）

    private String btlockPassword; //蓝牙锁配置文件的验证密码
    private String btlockDeadline; //蓝牙锁配置文件的有效截止日期
    private List<String> btlockListMacs = new ArrayList<>(); //本设备SN对应的蓝牙锁的MAC地址列表

    private boolean parsed = false;

    /**
     * 构造函数
     */
    public LockSecurity() {
    }

    /**
     * 解析蓝牙锁配置文件(.dat)
     *
     * @param filename 配置文件路径
     * @return 解析结果
     */
    public synchronized boolean parse(String filename) {
        File lf = new File(filename);
        if (!lf.exists()) {
            return false;
        }

        if (breakDown(filename) == false) {
            return false;
        }

        if (checkMagic() == false) {
            return false;
        }

        if (checkLockConfigMd5() == false) {
            return false;
        }

        if (parsePassword() == false) {
            return false;
        }

        if (parseDeadline() == false) {
            return false;
        }

        if (parseMacs() == false) {
            return false;
        }

        parsed = true;
        return true;
    }

    /**
     * 获取蓝牙锁配置文件中的密码信息
     *
     * @return 配置文件中的密码
     */
    public synchronized String getPassword() {
        if (parsed) {
            return btlockPassword;
        } else {
            return null;
        }
    }

    /**
     * 获取蓝牙锁配置文件中的有效期截止时间
     *
     * @return 配置文件中的deadline时间
     */
    public synchronized String getDeadline() {
        if (parsed) {
            return btlockDeadline;
        } else {
            return null;
        }
    }

    /**
     * 获取当前终端所支持的蓝牙锁MAC地址列表
     *
     * @return MAC地址列表
     */
    public synchronized List<String> getMacs() {
        if (parsed) {
            return btlockListMacs;
        } else {
            return null;
        }
    }

    /**
     * 将蓝牙锁配置.dat文件信息进行拆分
     * 内容格式：
     *     第一行，幻数。第二行，加密信息。
     *
     * @param filename 蓝牙锁配置文件的path
     * @return 拆分结果。true: 正确，false: 错误
     */
    private boolean breakDown(String filename) {
        File lf = new File(filename);
        if (!checkFileRows(lf)) {
            return false;
        }

        try (BufferedReader brFile = new BufferedReader(new FileReader(filename))) {
            String tempLine;

            if ((tempLine = brFile.readLine()) == null)
                return false;
            rawMagic.append(tempLine);

            if ((tempLine = brFile.readLine()) == null)
                return false;
            rawEncryptInfo.append(tempLine);

            //String plaintext = LockSecurityRSA.decryptByPublicKey(rawEncryptInfo.toString(), LockSecurityRSA.getPublicKeyByPlatform());
            //LockSecurityRSA.getPublicKeyByPlatform(); //for test
            String plaintext = LockSecurityRSA.decryptByPublicKey(rawEncryptInfo.toString(), LockSecurityRSA.getPublicKeyByCertificate(TEST_CERTIFICATION_CONTENT)); //for test
            if (plaintext == null || plaintext.isEmpty())
                return false;

            if (!breakDownPlaintext(plaintext))
                return false;

            if (debug) {
                Log.e(TAG, "====Lock .dat file section====");
                Log.e(TAG, rawMagic.toString());
                Log.e(TAG, "==============================");
                Log.e(TAG, rawEncryptInfo.toString());
                Log.e(TAG, "====Lock plaintext section====");
                Log.e(TAG, rawPassword.toString());
                Log.e(TAG, "==============================");
                Log.e(TAG, rawDeadline.toString());
                Log.e(TAG, "==============================");
                Log.e(TAG, rawMACs.toString());
                Log.e(TAG, "==============================");
                Log.e(TAG, rawMd5.toString());
                Log.e(TAG, "==============================");
            }
            return true;
        } catch (Exception e) {
            if (debug) {
                Log.e(TAG, "" + e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * 将蓝牙锁配置明文信息进行拆分
     * 内容格式：
     *     第一行，密码。第二行，有效期截止时间。第三行到倒数第二行，sn与mac的映射列表。最后一行，md5完整性校验。
     *     内容至少是4行
     *
     * @param plaintext 蓝牙锁配置文件的path
     * @return 拆分结果。true: 正确，false: 错误
     */
    private boolean breakDownPlaintext(String plaintext) {
        String lines[] = plaintext.split(NEWLINE);
        if (lines.length < 4)
            return false;
        rawPassword.append(lines[0]);
        rawDeadline.append(lines[1]);
        for (int i = 2; i < lines.length - 1; i++) {
            rawMACs.append(lines[i]).append(NEWLINE);
        }
        rawMACs.deleteCharAt(rawMACs.length() - NEWLINE.length());
        rawMd5.append(lines[lines.length - 1]);
        rawLockConfig.append(rawPassword).append(NEWLINE).append(rawDeadline).append(NEWLINE).append(rawMACs);

        return true;
    }

    /**
     * 检查幻数的正确性
     *
     * @return 检查结果
     */
    private boolean checkMagic() {
        if (rawMagic.toString().equals(magicStringLK)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查明文中的蓝牙配置信息的完整性
     *
     * @return 检查结果
     */
    private boolean checkLockConfigMd5() {
        if (rawMd5.toString().equals(LockSecurityRSA.md5Encrypt(rawLockConfig.toString())))
            return true;
        if (debug)
            Log.e(TAG, "Bluetooth lock config info md5 check error");
        return false;
    }

    /**
     * 解析蓝牙锁配置文件中的密码信息
     *
     * @return 检查结果
     */
    private boolean parsePassword() {
        if (rawPassword.toString().trim().length() == 0) {
            if (debug) {
                Log.e(TAG, "Bluetooth lock password parse failed");
            }
            return false;
        } else {
            btlockPassword = rawPassword.toString().trim();
            return true;
        }
    }

    /**
     * 解析蓝牙锁配置文件中的过期时间信息
     *
     * @return 检查结果
     */
    private boolean parseDeadline() {
        if (rawDeadline.toString().trim().length() == 0) {
            if (debug) {
                Log.e(TAG, "Bluetooth lock deadline parse failed");
            }
            return false;
        } else {
            btlockDeadline = rawDeadline.toString().trim();
            return true;
        }
    }

    /**
     * 解析.dat文件中的当前终端所支持的蓝牙锁MAC地址列表
     *
     * @return 检查结果
     */
    private boolean parseMacs() {
        String deviceSN = Build.SERIAL;
        String mapMacs[] = rawMACs.toString().split(NEWLINE);
        for (String mapMac : mapMacs) {
            String snMac[] = mapMac.trim().split(" "); //snMac[0]: SN, snMac[1]: MAC
            if (snMac.length == 2) {
                if (deviceSN.equals(snMac[0]))
                    btlockListMacs.add(snMac[1]);
            } else {
                if (debug)
                    Log.e(TAG, "Bluetooth lock Macs parse failed: " + mapMac);
                return false;
            }
        }
        return true;
    }

    /**
     * 获取蓝牙锁配置文件总行数
     *     配置文件行数必须为2行：第一行，幻数。第二行，加密信息。
     *
     * @param file 文件描述符
     * @return 获取结果。正确或错误
     */
    private boolean checkFileRows(File file) {
        int fileRows = -1;
        try {
            FileReader in = new FileReader(file);
            LineNumberReader reader = new LineNumberReader(in);
            reader.skip(Long.MAX_VALUE);
            fileRows = reader.getLineNumber() + 1;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileRows != 2)
            return false;
        return true;
    }

    /**
     * 检查配置文件的有效日期
     *
     * @return 检查结果
     */
    public static boolean checkDeadline(String lockConfigDeadlineDate) {
        try {
            long nowTime = System.currentTimeMillis();
            long deadline = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lockConfigDeadlineDate).getTime();
            if (nowTime > deadline) {
                if(debug)
                    Log.e(TAG, "Current time over deadline time");
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(debug)
                Log.e(TAG, "Deadline date format parse error");
            return false;
        }
    }

    /**
     * 基于设备SN号、对应蓝牙锁MAC地址和密码生成的蓝牙锁配置dat文件
     * 文件格式：
     * 第1行，密码
     * 第2行，有效日期
     * 第3行至倒数第二行，sn与mac的对应列表
     * 最后一行，用于校验内容完整性的md5值。
     *
     * @param sn       终端设备的sn号
     * @param mac      蓝牙锁的mac地址
     * @param password 验证密码
     * @return 生成结果。生成成功返回true，文件保存在LOCKSECURITY_DAT_GEN_PATH位置。失败返回true，不会生成文件。
     */
    public synchronized static boolean genLockSecurityFile(String sn, String mac, String password) {
        StringBuffer sbPlaintext = new StringBuffer();
        sbPlaintext.append(password).append(NEWLINE);
        Date date = new Date();
        date.setTime(System.currentTimeMillis() + 48 * 60 * 60 * 1000); //蓝牙锁配置文件有效期是48小时
        sbPlaintext.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)).append(NEWLINE);
        sbPlaintext.append(sn + " " + mac);
        String md5info = LockSecurityRSA.md5Encrypt(sbPlaintext.toString());
        sbPlaintext.append(NEWLINE).append(md5info);
        if (debug) {
            Log.e(TAG, "====Lock config plaintext====");
            Log.e(TAG, sbPlaintext.toString());
            Log.e(TAG, "=============================");
        }

        String encryptInfo = LockSecurityRSA.encryptByPrivateKey(sbPlaintext.toString(), TEST_PRIVATE_KEY_CONTENT);
        if (encryptInfo == null)
            return false;
        StringBuffer datFileContent = new StringBuffer();
        datFileContent.append(magicStringLK).append(NEWLINE);
        datFileContent.append(encryptInfo);
        if (debug) {
            Log.e(TAG, "====Lock dat file content====");
            Log.e(TAG, datFileContent.toString());
            Log.e(TAG, "=============================");
        }

        File file = new File(LOCKSECURITY_DAT_GEN_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file.getAbsolutePath(), false);
            fileWritter.write(datFileContent.toString());
            fileWritter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过反射机制，获取当前APP的Application
     */
    private static Application sInstanceApp;

    private static Application getApp() {
        if (sInstanceApp == null) {
            Application app = null;
            try {
                app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
                if (app == null) {
                    throw new IllegalStateException("Static initialization of Applications must be on main thread.");
                }
            } catch (final Exception e) {
                try {
                    app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
                } catch (final Exception ex) {
                    e.printStackTrace();
                }
            } finally {
                sInstanceApp = app;
            }
        }
        return sInstanceApp;
    }
}
