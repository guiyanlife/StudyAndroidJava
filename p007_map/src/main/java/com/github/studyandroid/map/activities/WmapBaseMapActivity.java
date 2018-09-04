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

import java.util.ArrayList;
import java.util.List;

public class WmapBaseMapActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTitle;
    private ImageView mIvBack;
    private WmapView mWmMap;

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
    }

    private void setListener() {
        mIvBack.setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        mTvTitle.setText(intent.getStringExtra("title"));

        mWmMap.setRefPointA(684, 286, 122.7238674733, 37.4285460040);
        mWmMap.setRefPointB(181, 473, 89.0167236328, 27.2497461568);
        mWmMap.setWmapDates(getWmapDatas());
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
        return list;
    }
}
