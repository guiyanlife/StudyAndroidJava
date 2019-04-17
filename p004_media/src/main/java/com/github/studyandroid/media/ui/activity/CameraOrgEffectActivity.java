package com.github.studyandroid.media.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.media.R;
import com.github.studyandroid.media.helper.CameraHelper;
import com.github.studyandroid.media.helper.MediaPlayerHelper;

import java.io.IOException;

public class CameraOrgEffectActivity extends Activity implements View.OnClickListener {
    private ImageView ivToolbarBack;
    private TextView tvToolbarContent;
    private Button btnStart, btnStop;
    private SurfaceView svCameraDisplay;

    private CameraHelper mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_org_effect);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivToolbarBack = findViewById(R.id.iv_toolbar_back);
        tvToolbarContent = findViewById(R.id.tv_toolbar_content);
        svCameraDisplay = findViewById(R.id.sv_camera_display);
        btnStart = findViewById(R.id.btn_camera_start);
        btnStop = findViewById(R.id.btn_camera_stop);
    }

    private void setListener() {
        ivToolbarBack.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        ivToolbarBack.setVisibility(View.VISIBLE);
        tvToolbarContent.setText(intent.getStringExtra("title"));

        // Set surface background is transparent
        svCameraDisplay.setZOrderOnTop(true);
        svCameraDisplay.getHolder().setFormat(PixelFormat.TRANSPARENT);

        mCamera = new CameraHelper(svCameraDisplay);
        mCamera.initCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            case R.id.btn_camera_start:
                mCamera.onStartPreview();
                break;
            case R.id.btn_camera_stop:
                mCamera.onStopPreview();
                break;
            default:
                break;
        }
    }
}
