package com.github.studyandroid.map.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.map.R;

public class AmapBaseMapActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTitle;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap_base_map);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        mTvTitle = findViewById(R.id.tv_toolbar_content);
        mIvBack = findViewById(R.id.iv_toolbar_back);
    }

    private void setListener() {
        mIvBack.setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        mTvTitle.setText(intent.getStringExtra("title"));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_toolbar_back) {
            onBackPressed();
        }
    }

}
