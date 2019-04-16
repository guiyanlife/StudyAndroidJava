package com.github.studyandroid.media.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.media.R;
import com.github.studyandroid.media.util.MediaUtil;

import java.io.IOException;

public class VideoOrgEffectActivity extends Activity implements View.OnClickListener {
    private ImageView ivToolbarBack;
    private TextView tvToolbarContent;
    private Button btnPlay, btnPause, btnStop;
    private SurfaceView svVideoDisplay;

    private MediaUtil mVideoPlayer;

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
        svVideoDisplay = findViewById(R.id.sv_video_display);
        btnPlay = findViewById(R.id.btn_video_play);
        btnPause = findViewById(R.id.btn_video_pause);
        btnStop = findViewById(R.id.btn_video_stop);
    }

    private void setListener() {
        ivToolbarBack.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        ivToolbarBack.setVisibility(View.VISIBLE);
        tvToolbarContent.setText(intent.getStringExtra("title"));

        // Set surface background is transparent
        svVideoDisplay.setZOrderOnTop(true);
        svVideoDisplay.getHolder().setFormat(PixelFormat.TRANSPARENT);

        mVideoPlayer = new MediaUtil(svVideoDisplay, null, null);
        try {
            AssetManager assetMg = this.getApplicationContext().getAssets();
            AssetFileDescriptor fileDescriptor = assetMg.openFd("video_720x480_1mb.mp4");
            mVideoPlayer.initVideo(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        mVideoPlayer.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            case R.id.btn_video_play:
                mVideoPlayer.onStartPlay();
                break;
            case R.id.btn_video_pause:
                if (mVideoPlayer.isPlaying())
                    mVideoPlayer.onPause();
                break;
            case R.id.btn_video_stop:
                mVideoPlayer.onStop();
                break;
            default:
                break;
        }
    }
}
