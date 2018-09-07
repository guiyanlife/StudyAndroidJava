package com.github.studyandroid.map.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hanho on 2016/8/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "wmap.db"; // 创建数据库的文件
    private static final int VERSION = 1;            // 数据库版本,版本是更新的依据

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    public DBHelper(Context context, String name) {
        super(context, name, null, VERSION);
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public DBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AreaGpsDbMgr.SQL_CREATE_AREA_GPS_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table " + AreaGpsDbMgr.TABLE_NAME + " if exists");
        onCreate(db);
    }
}
