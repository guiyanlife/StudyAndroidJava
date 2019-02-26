package com.github.studyandroid.base.application;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

public class MyApplicaiton extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        //该方法在onCreate之前调用
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        //程序创建时执行
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        //程序终止时执行
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        //低内存时执行
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        //程序在内存清理的时候执行（回收内存）
        //HOME键退出应用程序、长按MENU键，打开Recent TASK都会执行
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //屏幕方向改变或系统一些配置改变会触发
        super.onConfigurationChanged(newConfig);
    }
}
