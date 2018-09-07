package com.github.studyandroid.map.utils;

import com.github.studyandroid.map.application.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    /**
     * 复制raw目录下的数据库文件到应用数据库目录下
     *
     * @param dbRawId
     * @param dbFileName
     * @return
     */
    public static File copyDbFromRaw(int dbRawId, String dbFileName) {
        String filePath = MyApplication.mContext.getDatabasePath(dbFileName).getAbsolutePath();
        File file = new File(filePath);
        if (!file.exists()) {
            copyFileFromRaw(dbRawId, filePath);
        }
        return file;
    }

    /**
     * 复制raw资源文件到指定目录
     *
     * @param srcRawId    res/raw下的资源文件id
     * @param dstFilePath 复制到目标目录
     */
    public static void copyFileFromRaw(int srcRawId, String dstFilePath) {
        InputStream inputStream = MyApplication.mContext.getResources().openRawResource(srcRawId);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dstFilePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制assert文件
     *
     * @param oldPath 源文件路径
     * @param newPath 目的文件路径
     * @return 复制是否成功
     */
    public static boolean copyFileFromAssert(String oldPath, String newPath) {
        boolean flag = false;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            int byteread = 0;
            File newfile = new File(newPath);
            File parent = newfile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!newfile.exists()) {
                newfile.createNewFile();
            }
            is = MyApplication.mContext.getAssets().open(oldPath);
            fos = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            while ((byteread = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteread);
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}