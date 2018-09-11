package com.github.studyandroid.map.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.map.R;
import com.github.studyandroid.map.database.AreaGpsDbMgr;
import com.github.studyandroid.map.database.AreaGpsInfoEntry;
import com.github.studyandroid.map.widget.WmapDataBean;
import com.github.studyandroid.map.widget.WmapView;
import com.github.studyandroid.map.widget.WmapWqTdsView;
import com.github.studyandroid.map.widget.WmapWqYulvView;

import java.util.ArrayList;
import java.util.List;

public class WmapBaseMapActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTitle;
    private ImageView mIvBack;
    private WmapView mWmMap;
    private WmapWqTdsView mWmapWqTds;
    private WmapWqYulvView mWmapWqYulv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wmap_base_map);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        mTvTitle = findViewById(R.id.tv_toolbar_content);
        mIvBack = findViewById(R.id.iv_toolbar_back);
        mWmMap = findViewById(R.id.wv_water_map);
        mWmapWqTds = findViewById(R.id.wv_tds_scale);
        mWmapWqYulv = findViewById(R.id.wv_yulv_scale);
    }

    private void setListener() {
        mIvBack.setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        mTvTitle.setText(intent.getStringExtra("title"));

        mWmMap.setRefPointA(634, 335, 37.4031411382,122.7115353451);
        mWmMap.setRefPointB(213, 485, 27.2204411301,88.9782714844);
        mWmMap.setWmapDates(getWmapDatas());

        //mWmapWqTds.setTdsData(578);
        mWmapWqTds.setTdsDataAnim(578, 5000);

        //mWmapWqYulv.setYulvData(0.3);
        mWmapWqYulv.setYulvDataAnim(0.3, 5000);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_toolbar_back) {
            onBackPressed();
        }
    }

    private List<WmapDataBean> getWmapDatas() {
        AreaGpsInfoEntry areaGpsInfoEntry;
        List<WmapDataBean> list = new ArrayList<>();

        WmapDataBean data0 = new WmapDataBean();
        areaGpsInfoEntry = AreaGpsDbMgr.getInstance().queryCode("110000"); //北京
        data0.setLatitude(areaGpsInfoEntry.latitude);
        data0.setLongitude(areaGpsInfoEntry.longitude);
        data0.setQuality(520);
        list.add(data0);

        WmapDataBean data1 = new WmapDataBean();
        areaGpsInfoEntry = AreaGpsDbMgr.getInstance().queryCode("310000"); //上海
        data1.setLatitude(areaGpsInfoEntry.latitude);
        data1.setLongitude(areaGpsInfoEntry.longitude);
        data1.setQuality(770);
        list.add(data1);

        WmapDataBean data2 = new WmapDataBean();
        areaGpsInfoEntry = AreaGpsDbMgr.getInstance().queryCode("440100"); //广州
        data2.setLatitude(areaGpsInfoEntry.latitude);
        data2.setLongitude(areaGpsInfoEntry.longitude);
        data2.setQuality(20);
        list.add(data2);

        WmapDataBean data3 = new WmapDataBean();
        areaGpsInfoEntry = AreaGpsDbMgr.getInstance().queryCode("370213"); //青岛李沧区
        data3.setLatitude(areaGpsInfoEntry.latitude);
        data3.setLongitude(areaGpsInfoEntry.longitude);
        data3.setQuality(356);
        list.add(data3);

        WmapDataBean data4 = new WmapDataBean();
        areaGpsInfoEntry = AreaGpsDbMgr.getInstance().queryCode("650100"); //乌鲁木齐
        data4.setLatitude(areaGpsInfoEntry.latitude);
        data4.setLongitude(areaGpsInfoEntry.longitude);
        data4.setQuality(89);
        list.add(data4);
        return list;
    }
}
