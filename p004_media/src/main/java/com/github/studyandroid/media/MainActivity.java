package com.github.studyandroid.media;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.media.ui.activity.VideoOrgEffectActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvOrginalEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivBack = findViewById(R.id.iv_back);
        tvOrginalEffect = findViewById(R.id.tv_video_original_effect);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvOrginalEffect.setOnClickListener(this);
    }

    private void doNetWork() {
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_video_original_effect:
                Intent intentVideoOrgEffect = new Intent(this, VideoOrgEffectActivity.class);
                intentVideoOrgEffect.putExtra("title", tvOrginalEffect.getText());
                startActivity(intentVideoOrgEffect);
                break;
            default:
                break;
        }
    }
}
