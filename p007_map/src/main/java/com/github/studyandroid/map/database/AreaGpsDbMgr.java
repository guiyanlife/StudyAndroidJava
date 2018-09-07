package com.github.studyandroid.map.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tingting on 2016/9/28.
 */
public class AreaGpsDbMgr {
    public static final String TABLE_NAME = "area_gps";
    public static final String SQL_CREATE_AREA_GPS_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "`id` int(11) NOT NULL," +
                    "`code` varchar(100) DEFAULT NULL," +
                    "`parentCode` varchar(100) DEFAULT NULL," +
                    "`level` tinyint(4) DEFAULT NULL," +
                    "`name` varchar(100) DEFAULT NULL," +
                    "`latitude` double DEFAULT NULL," +
                    "`longitude` double DEFAULT NULL," +
                    "PRIMARY KEY (`id`)" +
                    ");";

    private static SQLiteDatabase sdb;
    private static AreaGpsDbMgr mInstance;
    private static Object mDBLock = Object.class;

    public static AreaGpsDbMgr getInstance() {
        sdb = DBOperation.getInstance().getDbHelper().getWritableDatabase();
        if (mInstance == null) {
            synchronized (mDBLock) {
                if (mInstance == null) {
                    mInstance = new AreaGpsDbMgr();
                }
            }
        }
        return mInstance;
    }

    private AreaGpsDbMgr() {
    }

    private void add(List<AreaGpsInfoEntry> entries) {
        synchronized (mDBLock) {
            sdb.beginTransaction();
            try {
                for (AreaGpsInfoEntry entry : entries) {
                    sdb.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(null, ?, ?, ?, ?, ?, ?)", new Object[]{entry.code, entry.parentCode, entry.level, entry.name, entry.latitude, entry.longitude});
                }
            } finally {
                sdb.endTransaction();
            }
        }
    }

    public long insert(AreaGpsInfoEntry entry) {
        synchronized (mDBLock) {
            if (entry == null) return -1;
            ContentValues values = new ContentValues();
            //values.put("id", entry.id);
            values.put("code", entry.code);
            values.put("parentCode", entry.parentCode);
            values.put("level", entry.level);
            values.put("name", entry.name);
            values.put("latitude", entry.latitude);
            values.put("longitude", entry.longitude);
            long newRowId = sdb.insert(TABLE_NAME, null, values);
            return newRowId;
        }
    }

    public void updateValue(AreaGpsInfoEntry entry) {
        synchronized (mDBLock) {
            if (entry == null) return;
            ContentValues values = new ContentValues();
            values.put("parentCode", entry.parentCode);
            values.put("level", entry.level);
            values.put("name", entry.name);
            values.put("latitude", entry.latitude);
            values.put("longitude", entry.longitude);
            sdb.update(TABLE_NAME, values, "code = ?", new String[]{entry.code});
        }
    }

    public void updateEntry(AreaGpsInfoEntry entry) {
        synchronized (mDBLock) {
            if (entry == null) return;
            ContentValues values = new ContentValues();
            values.put("parentCode", entry.parentCode);
            values.put("level", entry.level);
            values.put("name", entry.name);
            values.put("latitude", entry.latitude);
            values.put("longitude", entry.longitude);
            sdb.update(TABLE_NAME, values, "code = ?", new String[]{entry.code});
        }
    }

    public void updateAll(List<AreaGpsInfoEntry> entries) {
        synchronized (mDBLock) {
            for (AreaGpsInfoEntry entry : entries) {
                ContentValues values = new ContentValues();
                values.put("parentCode", entry.parentCode);
                values.put("level", entry.level);
                values.put("name", entry.name);
                values.put("latitude", entry.latitude);
                values.put("longitude", entry.longitude);
                sdb.update(TABLE_NAME, values, "code = ?", new String[]{entry.code});
            }
        }
    }

    public void deleteEntry(AreaGpsInfoEntry entry) {
        synchronized (mDBLock) {
            if (entry == null) return;
            sdb.delete(TABLE_NAME, "code = ?", new String[]{entry.code});
        }
    }

    public AreaGpsInfoEntry queryCode(String code) {
        synchronized (mDBLock) {
            AreaGpsInfoEntry entry = null;
            Cursor c = sdb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE code = " + code, null);
            if (c != null && c.getCount() >= 0) {
                if(c.moveToNext()) {
                    entry = new AreaGpsInfoEntry();
                    entry.id = c.getInt(c.getColumnIndex("id"));
                    entry.code = c.getString(c.getColumnIndex("code"));
                    entry.parentCode = c.getString(c.getColumnIndex("parentCode"));
                    entry.level = c.getInt(c.getColumnIndex("level"));
                    entry.name = c.getString(c.getColumnIndex("name"));
                    entry.latitude = c.getDouble(c.getColumnIndex("latitude"));
                    entry.longitude = c.getDouble(c.getColumnIndex("longitude"));
                }
                c.close();
            }
            return entry;
        }
    }

    public List<AreaGpsInfoEntry> queryAll() {
        synchronized (mDBLock) {
            ArrayList<AreaGpsInfoEntry> entries = new ArrayList<AreaGpsInfoEntry>();
            Cursor c = queryTheCursor();
            while (c.moveToNext()) {
                AreaGpsInfoEntry entry = new AreaGpsInfoEntry();
                entry.id = c.getInt(c.getColumnIndex("id"));
                entry.code = c.getString(c.getColumnIndex("code"));
                entry.parentCode = c.getString(c.getColumnIndex("parentCode"));
                entry.level = c.getInt(c.getColumnIndex("level"));
                entry.name = c.getString(c.getColumnIndex("name"));
                entry.latitude = c.getDouble(c.getColumnIndex("latitude"));
                entry.longitude = c.getDouble(c.getColumnIndex("longitude"));
                entries.add(entry);
            }
            c.close();
            return entries;
        }
    }

    /**
     * query all entrys, return cursor
     *
     * @return Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = sdb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return c;
    }

}
