package com.github.studyandroid.okhttp.application;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/**
 * Copyright (c) 2019 GitHub, Inc.
 * Description: Application
 * Author(s): Gui Yan (guiyanwork@163.com)
 */
public class MyApplication extends MultiDexApplication {
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance();
    }
}
