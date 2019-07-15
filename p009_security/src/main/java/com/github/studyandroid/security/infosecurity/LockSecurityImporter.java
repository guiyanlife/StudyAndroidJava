package com.github.studyandroid.security.infosecurity;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class LockSecurityImporter {
    private static String TAG = LockSecurityImporter.class.getName();
    private static boolean debug = true;

    public static final long LIMITE_SIZE = 100 * 1024 * 1024; //可用磁盘空间下限100M
    private static LockSecurityImporter sInstance;

    private String tempLockSecurityFile;
    private String lockSecurityFile;

    private LockSecurityImporter() {
        tempLockSecurityFile = getApp().getFilesDir() + "/templocksecurity.dat";
        lockSecurityFile = getApp().getFilesDir() + "/locksecurity.dat";
    }

    public static LockSecurityImporter getInstance() {
        if (sInstance == null) {
            sInstance = new LockSecurityImporter();
        }

        return sInstance;
    }

    /**
     * 导入蓝牙锁安全配置
     * 实现方法：
     * 1. 检查磁盘空余时间
     * 2. 将导入的蓝牙锁安全配置信息存储为templocksecurity.dat文件，保存成功下一步，保存失败返回错误
     * 3. 检查蓝牙锁配置文件是否有必要导入
     * 4. 导入成功并且本设备的sn对应的蓝牙锁list有更新，则发送状态改变广播
     *
     * @param content  蓝牙锁安全配置信息
     * @param password 导入密码
     * @return 导入结果
     */
    public boolean importLockSecurityConfig(String content, String password) {
        if (checkStorageSpace() == false) {
            if (debug)
                Log.e(TAG, "Not enough disk storage space");
            return false;
        }

        boolean saved = saveTempLockSecurity(content);
        if (saved == false) {
            if (debug)
                Log.e(TAG, "Save lock security config info to temp .dat file error");
            return false;
        }

        int state = needImport(tempLockSecurityFile, password);
        if (state == -1)  //不能导入，反馈导入错误
            return false;
        else if (state == 1) //没必要导入，反馈导入正确
            return true;
        else if (state == 0) { //需要导入，并且mac list有更新
            try {
                new File(lockSecurityFile).delete();
                FileUtils.moveFile(new File(tempLockSecurityFile), new File(lockSecurityFile));
            } catch (Exception e) {
                if (debug)
                    Log.e(TAG, "Store new lock security file error");
                e.printStackTrace();
                return false;
            }
        } else {
            if(debug)
                Log.e(TAG, "Import state is error");
            return false;
        }

        //如果导入成功并且本设备的sn对应的蓝牙锁list有更新，则发送状态改变广播
        getApp().sendBroadcast(new Intent(LockSecurity.ACTION_LOCK_SECURITY_CONFIG_CHANGED));
        return true;
    }

    /**
     * 从当前蓝牙锁配置.dat文件获取Mac地址列表
     *
     * @return 蓝牙锁MAC地址列表
     */
    public List<String> getPairedMacList() {
        LockSecurity orgLKS = new LockSecurity();
        boolean orgLksParsed = orgLKS.parse(lockSecurityFile);
        if (orgLksParsed) {
            return orgLKS.getMacs();
        }
        return null;
    }

    /**
     * 测试蓝牙锁配置文件是否有必要导入
     * 实现方法：
     * 1. 当new(temp)文件解析失败
     *    返回-1
     * 2. 当new(temp)文件解析成功，old文件解析失败时
     *    如果new文件的password检查通过，时间未过期，返回0（后续会new文件替换old文件）
     *    其他情况，返回-1
     * 3. 当new(temp)文件解析成功，old文件解析成功时
     *    如果new文件的password检查通过，时间未过期，maclist内容与old文件不一致，返回0（后续会new文件替换old文件）
     *    如果new文件的password检查通过，时间未过期，maclist内容与old文件一致，返回1（后续会new文件不替换old文件）
     *    其他情况，返回-1
     *
     * @return 返回需要导入结果。-1:不能导入(后续会反馈导入错误)。0:需要导入(后续会反馈导入成功，发change广播)，1:没必要导入(后续会反馈导入成功，不发change广播)
     */
    private int needImport(String tempDatPath, String password) {
        LockSecurity orgLKS = new LockSecurity();
        boolean orgLksParsed = orgLKS.parse(lockSecurityFile);
        if (tempDatPath != null) {
            LockSecurity newLKS = new LockSecurity();
            boolean newLksParsed = newLKS.parse(tempDatPath);
            if (newLksParsed) {
                if (orgLksParsed) { //new文件解析成功，old文件解析成功
                    if (password != null && password.equals(newLKS.getPassword()) && LockSecurity.checkDeadline(newLKS.getDeadline())) {
                        if (isListEqual(newLKS.getMacs(), orgLKS.getMacs()))
                            return 1;
                        return 0;
                    }
                } else { //new文件解析成功，old文件解析失败
                    if (password != null && password.equals(newLKS.getPassword()) && LockSecurity.checkDeadline(newLKS.getDeadline()))
                        return 0;
                }
            }
        }
        return -1;
    }

    /**
     * 将导入的蓝牙锁的配置信息保存为templocksecurity.dat文件
     * @param lockSecurityContent 蓝牙锁配置信息内容
     * @return 保存结果
     */
    private boolean saveTempLockSecurity(String lockSecurityContent) {
        try {
            File tempFile = new File(tempLockSecurityFile);
            tempFile.delete();
        } catch (Exception e) {
            return false;
        }

        try (FileOutputStream tempLockSecurityFos = new FileOutputStream(new File(tempLockSecurityFile), false)) {
            tempLockSecurityFos.write(lockSecurityContent.getBytes());
            tempLockSecurityFos.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 比对两个List是否相同
     * @param list1 List 1
     * @param list2 List 2
     * @param <E> List里面元素的类型
     * @return 比对结果
     */
    private <E> boolean isListEqual(List<E> list1, List<E> list2) {
        if (list1 == list2)
            return true;

        if (list1 == null && list2 == null)
            return true;

        if (list1 == null || list2 == null)
            return false;

        if (list1.size() != list2.size())
            return false;

        if (!list1.containsAll(list2))
            return false;
        return true;
    }

    /**
     * 检测磁盘可用空间是否有100M
     *
     * @return 返回检查结果
     */
    private static boolean checkStorageSpace() {
        return checkDataStorageSpace(LIMITE_SIZE);
    }

    private static boolean checkDataStorageSpace(long minSpace) {
        boolean spaceEnough;
        if (getTotalMemorySize() - getUsedMemorySize() < minSpace) {
            spaceEnough = false;
        } else {
            spaceEnough = true;
        }
        return spaceEnough;
    }

    private static long getTotalMemorySize() {
        File file = Environment.getDataDirectory();
        return getTotalMemorySize(file);
    }

    private static long getTotalMemorySize(File file) {
        StatFs statFs = new StatFs(file.getPath());
        long blockSizeLong = statFs.getBlockSizeLong();
        long blockCountLong = statFs.getBlockCountLong();
        long size = blockCountLong * blockSizeLong;
        return size;
    }

    public static long getUsedMemorySize() {
        File file = Environment.getDataDirectory();
        return getUsedMemorySize(file);
    }


    public static long getUsedMemorySize(File file) {
        StatFs statFs = new StatFs(file.getPath());
        long availableBlocksLong = statFs.getAvailableBlocksLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        long blockCountLong = statFs.getBlockCountLong();
        long size = (blockCountLong - availableBlocksLong)
                * blockSizeLong;
        return size;
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