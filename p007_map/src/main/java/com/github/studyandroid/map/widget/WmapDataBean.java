package com.github.studyandroid.map.widget;

/**
 * 水质地图的水质数据封装类
 */
public class WmapDataBean {
    int quality;     //水质值
    double longitude; //精度
    double latitude;  //纬度

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
