package com.github.studyandroid.map.database;

import com.github.studyandroid.map.application.MyApplication;

/**
 * Created by hanho on 2016/8/15.
 */
public class DBOperation {
    public static DBOperation mInstance;
    DBHelper mDbHelper;

    public static DBOperation getInstance() {
        if (mInstance == null) {
            synchronized (DBOperation.class) {
                if (mInstance == null) {
                    mInstance = new DBOperation();
                }
            }
        }
        return mInstance;
    }

    public DBOperation() {
        createDBData();
    }

    public void createDBData() {
        if (mDbHelper == null) {
            mDbHelper = new DBHelper(MyApplication.mContext);
        }
    }

    public DBHelper getDbHelper() {
        return mDbHelper;
    }

}
