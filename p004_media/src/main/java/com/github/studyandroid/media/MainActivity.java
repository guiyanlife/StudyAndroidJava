package com.github.studyandroid.media;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.media.ui.activity.CameraOrgEffectActivity;
import com.github.studyandroid.media.ui.activity.CameraTranslucentEffectActivity;
import com.github.studyandroid.media.ui.activity.VideoMultiDecEffectActivity;
import com.github.studyandroid.media.ui.activity.VideoOrgEffectActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvOrginalEffect;
    private TextView tvVideoMultiDecEffect;
    private TextView tvCameraTranslucentEffect;
    private TextView tvCameraOrgEffect;

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
        tvVideoMultiDecEffect  = findViewById(R.id.tv_video_multi_decode_effect);
        tvCameraTranslucentEffect = findViewById(R.id.tv_camera_translucent_effect);
        tvCameraOrgEffect = findViewById(R.id.tv_camera_original_effect);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvOrginalEffect.setOnClickListener(this);
        tvVideoMultiDecEffect.setOnClickListener(this);
        tvCameraTranslucentEffect.setOnClickListener(this);
        tvCameraOrgEffect.setOnClickListener(this);
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
            case R.id.tv_video_multi_decode_effect:
                Intent intentVideoMultiDecEffect = new Intent(this, VideoMultiDecEffectActivity.class);
                intentVideoMultiDecEffect.putExtra("title", tvVideoMultiDecEffect.getText());
                startActivity(intentVideoMultiDecEffect);
                break;
            case R.id.tv_camera_original_effect:
                Intent intentCameraOrgEffect = new Intent(this, CameraOrgEffectActivity.class);
                intentCameraOrgEffect.putExtra("title", tvCameraOrgEffect.getText());
                startActivity(intentCameraOrgEffect);
                break;
            case R.id.tv_camera_translucent_effect:
                Intent intentCameraTransEffect = new Intent(this, CameraTranslucentEffectActivity.class);
                intentCameraTransEffect.putExtra("title", tvCameraTranslucentEffect.getText());
                startActivity(intentCameraTransEffect);
                break;
            default:
                break;
        }
    }
}
