package com.github.studyandroid.network.application;

import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.github.commonlibs.libwebview.hios2.HiosHelper;
import com.github.studyandroid.network.retrofit.RetrofitNetNew;

public class MyApplication extends MultiDexApplication {
    public static final String DIR_PROJECT = "/studyandroid/network/app/";
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
        configRetrofit();
        configHios();
    }

    private void configHios() {
        HiosHelper.config("ad.github.web.page", "github.web.page");
    }

    private void configRetrofit() {
        String cacheDir = Environment.getExternalStorageDirectory() + DIR_CACHE;
        RetrofitNetNew.config();
    }


}
