package com.github.studyandroid.okhttp.application;

/**
 * Copyright (c) 2019 GitHub, Inc.
 * Description: Crash handler
 * Author(s): Gui Yan (guiyanwork@163.com)
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler mCrashHandler;

    private CrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CrashHandler getInstance() {
        if (mCrashHandler == null) {
            mCrashHandler = new CrashHandler();
        }
        return mCrashHandler;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
