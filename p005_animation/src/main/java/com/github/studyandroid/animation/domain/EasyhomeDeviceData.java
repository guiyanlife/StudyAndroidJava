package com.github.studyandroid.animation.domain;

import com.github.studyandroid.animation.R;

import java.util.ArrayList;
import java.util.List;

public class EasyhomeDeviceData {
    private int deviceIndex;
    private int deviceImageId;
    private String deviceStateInfo;
    private String deviceAction;

    public static List<EasyhomeDeviceData> get() {
        int[] deviceIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

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
                R.drawable.tagcloud_yyj
        };

        String[] deviceStateInfos = new String[]{
                "3            -3            -8\n冷藏      变温      冷冻",
                "电热水器\n当前温度36℃",
                "净水机\n水质良好",
                "空调\n制冷，设置温度26℃",
                "烤箱\n剩余时间30分钟",
                "破壁机\n立即购买",
                "燃气热水器\n当前温度28℃",
                "燃气灶\n立即购买",
                "消毒柜\n剩余时间11分钟",
                "洗碗机\n不在线",
                "洗衣机\n剩余时间23分钟",
                "烟机\n不在线"
        };

        String[] deviceActions = new String[]{
                "hs.act.github.phone.TagCloudDeviceBxActivity",
                "hs.act.github.phone.TagCloudDeviceDrsqActivity",
                "hs.act.github.phone.TagCloudDeviceJsjActivity",
                "hs.act.github.phone.TagCloudDeviceKtActivity",
                "hs.act.github.phone.TagCloudDeviceKxActivity",
                "hs.act.github.phone.TagCloudDevicePbjActivity",
                "hs.act.github.phone.TagCloudDeviceRqesqActivity",
                "hs.act.github.phone.TagCloudDeviceRqzActivity",
                "hs.act.github.phone.TagCloudDeviceXdgActivity",
                "hs.act.github.phone.TagCloudDeviceXwjActivity",
                "hs.act.github.phone.TagCloudDeviceXyjActivity",
                "hs.act.github.phone.TagCloudDeviceYyjActivity"
        };

        List<EasyhomeDeviceData> deviceDataList = new ArrayList<>();
        for (int index : deviceIndexs) {
            EasyhomeDeviceData data = new EasyhomeDeviceData();
            data.deviceIndex = index;
            data.deviceImageId = deviceImageIds[index];
            data.deviceStateInfo = deviceStateInfos[index];
            data.deviceAction = deviceActions[index];
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

    public String getDeviceAction() {
        return deviceAction;
    }

    public void setDeviceAction(String deviceAction) {
        this.deviceAction = deviceAction;
    }
}
