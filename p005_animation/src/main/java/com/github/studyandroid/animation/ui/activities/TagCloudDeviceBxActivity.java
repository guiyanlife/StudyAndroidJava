package com.github.studyandroid.animation.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.animation.R;

public class TagCloudDeviceBxActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mTvTitle, mTvDeviceContent;
    private ImageView mIvBack, mIvDeviceBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_cloud_device);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        mTvTitle = findViewById(R.id.tv_toolbar_content);
        mIvBack = findViewById(R.id.iv_toolbar_back);
        mIvDeviceBg = findViewById(R.id.iv_device_bg);
        mTvDeviceContent = findViewById(R.id.tv_device_content);
    }

    private void setListener() {
        mIvBack.setOnClickListener(this);
    }

    private void doNetWork() {
        mTvTitle.setText("TagCloudDeviceBxActivity");
        mIvDeviceBg.setImageResource(R.drawable.tagcloud_bx);
        mTvDeviceContent.setText("3          -8\n冷藏      冷冻");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_toolbar_back) {
            onBackPressed();
        }
    }

}
