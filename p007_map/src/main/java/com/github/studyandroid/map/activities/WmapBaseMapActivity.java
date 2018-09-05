package com.github.studyandroid.map.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.map.R;
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

        mWmMap.setRefPointA(634, 335, 122.7115353451, 37.4031411382);
        mWmMap.setRefPointB(213, 485, 88.9782714844, 27.2204411301);
        mWmMap.setWmapDates(getWmapDatas());

        mWmapWqTds.setTdsData(578);
        mWmapWqYulv.setYulvData(0.3);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_toolbar_back) {
            onBackPressed();
        }
    }

    private List<WmapDataBean> getWmapDatas() {
        List<WmapDataBean> list = new ArrayList<>();
        WmapDataBean data = new WmapDataBean();
        data.setLatitude(116.4065867503); //北京
        data.setLongitude(39.9098071731);
        data.setQuality(520);
        list.add(data);
        WmapDataBean data1 = new WmapDataBean();
        data1.setLatitude(121.4703369141); //上海
        data1.setLongitude(31.1799095987);
        data1.setQuality(770);
        list.add(data1);
        WmapDataBean data2 = new WmapDataBean();
        data2.setLatitude(113.2127380371); //广州
        data2.setLongitude(23.0948913850);
        data2.setQuality(20);
        list.add(data2);
        WmapDataBean data3 = new WmapDataBean();
        data3.setLatitude(125.9538617943); //通化市
        data3.setLongitude(41.7400595853);
        data3.setQuality(356);
        list.add(data3);
        WmapDataBean data4 = new WmapDataBean();
        data4.setLatitude(87.6113430216); //乌鲁木齐
        data4.setLongitude(43.8328524160);
        data4.setQuality(89);
        list.add(data4);
        return list;
    }
}
