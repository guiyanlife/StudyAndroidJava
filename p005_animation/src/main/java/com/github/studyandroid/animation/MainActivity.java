package com.github.studyandroid.animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.animation.ui.activities.TagCloudEasyhomeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvOriginal3dtagcloud, tvEasyhomeDeviceCloud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivBack = findViewById(R.id.iv_back);
        tvOriginal3dtagcloud = findViewById(R.id.tv_original_3d_tag_cloud);
        tvEasyhomeDeviceCloud = findViewById(R.id.tv_easyhome_device_cloud);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvOriginal3dtagcloud.setOnClickListener(this);
        tvEasyhomeDeviceCloud.setOnClickListener(this);
    }

    private void doNetWork() {
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_original_3d_tag_cloud:
                intent = new Intent("hs.act.github.phone.DemoTagCloudMainActivity");
                startActivity(intent);
                break;
            case R.id.tv_easyhome_device_cloud:
                intent = new Intent(this, TagCloudEasyhomeActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
