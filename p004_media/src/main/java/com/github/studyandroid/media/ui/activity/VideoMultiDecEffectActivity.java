package com.github.studyandroid.media.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.studyandroid.media.R;
import com.github.studyandroid.media.media.MediaPlayerHelper;

import java.io.IOException;

public class VideoMultiDecEffectActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rlBackground;
    private ImageView ivToolbarBack;
    private TextView tvToolbarContent;
    private SurfaceView svVideoDisplay1, svVideoDisplay2, svVideoDisplay3, svVideoDisplay4;
    private Button btnMultiPlay, btnMultiPause, btnMultiStop;

    private MediaPlayerHelper mVideoPlayer1, mVideoPlayer2, mVideoPlayer3, mVideoPlayer4;
    private MediaPlayerHelper.OnMediaListener mMediaListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_multi_dec_effect);
        //Set Activity orientation is landscape
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        rlBackground = findViewById(R.id.rlToolbarTop);
        ivToolbarBack = findViewById(R.id.iv_toolbar_back);
        tvToolbarContent = findViewById(R.id.tv_toolbar_content);
        svVideoDisplay1 = findViewById(R.id.sv_video_display1);
        svVideoDisplay2 = findViewById(R.id.sv_video_display2);
        svVideoDisplay3 = findViewById(R.id.sv_video_display3);
        svVideoDisplay4 = findViewById(R.id.sv_video_display4);
        btnMultiPlay = findViewById(R.id.btn_video_multi_play);
        btnMultiPause = findViewById(R.id.btn_video_multi_pause);
        btnMultiStop = findViewById(R.id.btn_video_multi_stop);
    }

    private void setListener() {
        ivToolbarBack.setOnClickListener(this);
        btnMultiPlay.setOnClickListener(this);
        btnMultiPause.setOnClickListener(this);
        btnMultiStop.setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        ivToolbarBack.setVisibility(View.VISIBLE);
        tvToolbarContent.setText(intent.getStringExtra("title"));
        rlBackground.setBackgroundColor(getResources().getColor(R.color.colorPrimaryHalfHalfTrans));
        btnMultiPlay.setAlpha(0.7f);
        btnMultiPause.setAlpha(0.7f);
        btnMultiStop.setAlpha(0.7f);

        mVideoPlayer1 = new MediaPlayerHelper(svVideoDisplay1, null, null);
        mVideoPlayer2 = new MediaPlayerHelper(svVideoDisplay2, null, null);
        mVideoPlayer3 = new MediaPlayerHelper(svVideoDisplay3, null, null);
        mVideoPlayer4 = new MediaPlayerHelper(svVideoDisplay4, null, null);
        try {
            AssetManager assetMg = this.getApplicationContext().getAssets();
            AssetFileDescriptor assetFd1mb = assetMg.openFd("video_1920x1080_6mb.mp4");
            AssetFileDescriptor assetFd3mb = assetMg.openFd("video_1920x1080_3mb.mp4");
            AssetFileDescriptor assetFd6mb = assetMg.openFd("video_1280x720_1mb.mp4");
            AssetFileDescriptor assetFd7mb = assetMg.openFd("video_1920x1080_7mb.mp4");
            mVideoPlayer1.initVideo(assetFd1mb.getFileDescriptor(), assetFd1mb.getStartOffset(), assetFd1mb.getLength());
            mVideoPlayer2.initVideo(assetFd3mb.getFileDescriptor(), assetFd3mb.getStartOffset(), assetFd3mb.getLength());
            mVideoPlayer3.initVideo(assetFd6mb.getFileDescriptor(), assetFd6mb.getStartOffset(), assetFd6mb.getLength());
            mVideoPlayer4.initVideo(assetFd7mb.getFileDescriptor(), assetFd7mb.getStartOffset(), assetFd7mb.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置自动循环播放
        mMediaListener = new MediaPlayerHelper.OnMediaListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }

            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

            }

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        };
        mVideoPlayer1.setOnMediaListener(mMediaListener);
        mVideoPlayer2.setOnMediaListener(mMediaListener);
        mVideoPlayer3.setOnMediaListener(mMediaListener);
        mVideoPlayer4.setOnMediaListener(mMediaListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoPlayer1.onDestroy();
        mVideoPlayer2.onDestroy();
        mVideoPlayer3.onDestroy();
        mVideoPlayer4.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            case R.id.btn_video_multi_play:
                mVideoPlayer1.onStartPlay();
                mVideoPlayer2.onStartPlay();
                mVideoPlayer3.onStartPlay();
                mVideoPlayer4.onStartPlay();
                break;
            case R.id.btn_video_multi_pause:
                if (mVideoPlayer1.isPlaying())
                    mVideoPlayer1.onPause();
                if (mVideoPlayer2.isPlaying())
                    mVideoPlayer2.onPause();
                if (mVideoPlayer3.isPlaying())
                    mVideoPlayer3.onPause();
                if (mVideoPlayer4.isPlaying())
                    mVideoPlayer4.onPause();
                break;
            case R.id.btn_video_multi_stop:
                mVideoPlayer1.onStop();
                mVideoPlayer2.onStop();
                mVideoPlayer3.onStop();
                mVideoPlayer4.onStop();
                break;
            default:
                break;
        }
    }
}
