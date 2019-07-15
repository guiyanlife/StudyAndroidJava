package com.github.studyandroid.security.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    /**
     * 字符串内容转文件
     * @param content 字符串内容
     * @param path 生成的文件路径
     * @return 转换结果
     */
    public static boolean string2File(String content, String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsolutePath(), false);
            fw.write(content);
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 把文件中的内容读出，转成字符串
     *
     * @param filePath 文件路径
     * @return 文件内容String形式
     */
    public static String file2String(String filePath) {
        try {
            StringBuffer fileData = new StringBuffer();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            char[] buf = new char[1024];
            int numRead;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
            return fileData.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
