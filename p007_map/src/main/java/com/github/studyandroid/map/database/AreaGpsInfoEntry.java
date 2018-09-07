package com.github.studyandroid.map.database;

public class AreaGpsInfoEntry {
    public int id;
    public String code;
    public String parentCode;
    public int level;
    public String name;
    public double latitude;
    public double longitude;

    public AreaGpsInfoEntry() {
    }

    public AreaGpsInfoEntry(int id, String code, String parentCode, int level, String name, double latitude, double longitude) {
        this.id = id;
        this.code = code;
        this.parentCode = parentCode;
        this.level = level;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
