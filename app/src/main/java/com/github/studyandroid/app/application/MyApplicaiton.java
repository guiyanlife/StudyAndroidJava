package com.github.studyandroid.app.application;

import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

public class MyApplicaiton extends MultiDexApplication {
    public static final String DIR_PROJECT = "/studyandroid/retrofit/app/";
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
        configMyRetrofit();
    }

    private void configMyRetrofit() {
        String cacheDir = Environment.getExternalStorageDirectory() + DIR_CACHE;
    }


}
