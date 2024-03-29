package com.github.studyandroid.map.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.github.studyandroid.map.R;
import com.github.studyandroid.map.database.DBHelper;
import com.github.studyandroid.map.utils.FileUtil;

public class MyApplication extends MultiDexApplication {
    public static final String DIR_PROJECT = "/studyandroid/map/app/";
    public static final String DIR_CACHE = DIR_PROJECT + "cache/";   // 网页缓存路径
    public static final String IMG_CACHE = DIR_PROJECT + "image/";   // image缓存路径
    public static final String VIDEO_CACHE = DIR_PROJECT + "video/"; // video缓存路径
    public static final String MUSIC_CACHE = DIR_PROJECT + "music/"; // music缓存路径
    public static Context mContext;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initResource();
    }

    private void initResource() {
        FileUtil.copyDbFromRaw(R.raw.wmap, DBHelper.DB_NAME);
    }
}
