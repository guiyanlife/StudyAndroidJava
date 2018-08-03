package com.github.studyandroid.glide38.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {
    public static final String DIR_PROJECT = "/studyandroid/glide38/app/";
    public static final String DIR_CACHE = DIR_PROJECT + "cache/";   // 网页缓存路径
    public static final String IMG_CACHE = DIR_PROJECT + "image/";   // image缓存路径
    public static final String VIDEO_CACHE = DIR_PROJECT + "video/"; // video缓存路径
    public static final String MUSIC_CACHE = DIR_PROJECT + "music/"; // music缓存路径

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
