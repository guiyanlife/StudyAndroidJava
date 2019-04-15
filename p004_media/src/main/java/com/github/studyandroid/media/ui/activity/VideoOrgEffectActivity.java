package com.github.studyandroid.media.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.media.R;

public class VideoOrgEffectActivity extends Activity implements View.OnClickListener {
    private ImageView ivToolbarBack;
    private TextView tvToolbarContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_org_effect);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivToolbarBack = findViewById(R.id.iv_toolbar_back);
        tvToolbarContent = findViewById(R.id.tv_toolbar_content);
    }

    private void setListener() {
        ivToolbarBack.setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        ivToolbarBack.setVisibility(View.VISIBLE);
        tvToolbarContent.setText(intent.getStringExtra("title"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            default:
                break;
        }
    }
}
