package com.github.studyandroid.app.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Copyright (c) 2019 GitHub, Inc.
 * Description: Application
 * Author(s): Gui Yan (guiyanwork@163.com)
 */
public class MyApplicaiton extends MultiDexApplication {
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
