package com.github.studyandroid.animation.domain;

import com.github.studyandroid.animation.R;

import java.util.ArrayList;
import java.util.List;

public class EasyhomeDeviceData {
    private int deviceIndex;
    private String deviceName;
    private int deviceImageId;
    private String deviceStateInfo;

    public static List<EasyhomeDeviceData> get() {
        int[] deviceIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};

        String[] deviceNames = new String[]{
                "冰箱",
                "电热水器",
                "净水机",
                "空调",
                "烤箱",
                "破壁机",
                "燃气热水器",
                "燃气灶", //上述设备在冰箱附近
                "消毒柜",
                "洗碗机",
                "洗衣机",
                "烟机",
                "酒柜",
                "冰吧",
                "电视",
                "电压力锅",
                "空气净化器",
                "门口机",
                "燃气传感器",
                "扫地机器人",
                "微波炉",
                "蒸箱"
        };

        int[] deviceImageIds = {
                R.drawable.tagcloud_bx,
                R.drawable.tagcloud_drsq,
                R.drawable.tagcloud_jsj,
                R.drawable.tagcloud_kt,
                R.drawable.tagcloud_kx,
                R.drawable.tagcloud_pbj,
                R.drawable.tagcloud_rqesq,
                R.drawable.tagcloud_rqz,
                R.drawable.tagcloud_xdg,
                R.drawable.tagcloud_xwj,
                R.drawable.tagcloud_xyj,
                R.drawable.tagcloud_yyj,
                R.drawable.tagcloud_jg,
                R.drawable.tagcloud_bb,
                R.drawable.tagcloud_ds,
                R.drawable.tagcloud_dylg,
                R.drawable.tagcloud_kqjhq,
                R.drawable.tagcloud_mkj,
                R.drawable.tagcloud_rqcgq,
                R.drawable.tagcloud_sdjqr,
                R.drawable.tagcloud_wbl,
                R.drawable.tagcloud_zx
        };

        String[] deviceStateInfos = new String[]{
                "3          -8\n冷藏      冷冻",
                "电热水器\n立即购买",
                "净水机\n工作中",
                "空调\n立即购买",
                "烤箱\n已离线",
                "破壁机\n已离线",
                "燃气热水器\n立即购买",
                "燃气灶\n立即购买",
                "消毒柜\n已离线",
                "洗碗机\n已离线",
                "洗衣机\n立即购买",
                "烟机\n立即购买",
                "酒柜\n立即购买",
                "冰吧\n立即购买",
                "电视\n立即购买",
                "电压力锅\n立即购买",
                "空气净化器\n立即购买",
                "门口机\n立即购买",
                "燃气传感器\n立即购买",
                "扫地机器人\n立即购买",
                "微波炉\n立即购买",
                "蒸箱\n立即购买"
        };

        List<EasyhomeDeviceData> deviceDataList = new ArrayList<>();
        for (int index : deviceIndexs) {
            EasyhomeDeviceData data = new EasyhomeDeviceData();
            data.deviceName = deviceNames[index];
            data.deviceIndex = index;
            data.deviceImageId = deviceImageIds[index];
            data.deviceStateInfo = deviceStateInfos[index];
            deviceDataList.add(data);
        }
        return deviceDataList;
    }

    public int getDeviceIndex() {
        return deviceIndex;
    }

    public void setDeviceIndex(int deviceIndex) {
        this.deviceIndex = deviceIndex;
    }

    public int getDeviceImageId() {
        return deviceImageId;
    }

    public void setDeviceImageId(int deviceImageId) {
        this.deviceImageId = deviceImageId;
    }

    public String getDeviceStateInfo() {
        return deviceStateInfo;
    }

    public void setDeviceStateInfo(String deviceStateInfo) {
        this.deviceStateInfo = deviceStateInfo;
    }
}
