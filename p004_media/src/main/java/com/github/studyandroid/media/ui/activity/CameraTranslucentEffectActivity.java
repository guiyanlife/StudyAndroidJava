package com.github.studyandroid.media.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.studyandroid.media.R;
import com.github.studyandroid.media.camera.CameraRenderManager;

public class CameraTranslucentEffectActivity extends Activity implements View.OnClickListener {
    private ImageView ivToolbarBack;
    private TextView tvToolbarContent;
    private GLSurfaceView gvCameraDisplay;
    private Button btnStart, btnStop, btnAlphaInc, getBtnAlphaDec;

    private CameraRenderManager mCamera;
    private float mCameraViewAlpha = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_translucent_effect);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivToolbarBack = findViewById(R.id.iv_toolbar_back);
        tvToolbarContent = findViewById(R.id.tv_toolbar_content);
        gvCameraDisplay = findViewById(R.id.gv_camera_display);
        btnStart = findViewById(R.id.btn_camera_trans_start);
        btnStop = findViewById(R.id.btn_camera_trans_stop);
        btnAlphaInc = findViewById(R.id.btn_camera_trans_alpha_inc);
        getBtnAlphaDec = findViewById(R.id.btn_camera_trans_alpha_dec);
    }

    private void setListener() {
        ivToolbarBack.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnAlphaInc.setOnClickListener(this);
        getBtnAlphaDec.setOnClickListener(this);
        gvCameraDisplay.setOnTouchListener(gvCameraDisplayTouchListener);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        ivToolbarBack.setVisibility(View.VISIBLE);
        tvToolbarContent.setText(intent.getStringExtra("title"));

        mCamera = new CameraRenderManager(gvCameraDisplay);
//        mCamera.initCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
        mCamera.initCamera(0);
        mCamera.onStartPreview();
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
            case R.id.btn_camera_trans_start:
                mCamera.onStartPreview();
                break;
            case R.id.btn_camera_trans_stop:
                mCamera.onStopPreview();
                break;
            case R.id.btn_camera_trans_alpha_inc:
                mCameraViewAlpha += 0.1f;
                mCameraViewAlpha = mCameraViewAlpha > 1 ? 1.0f : mCameraViewAlpha;
                mCamera.setViewAlpha(mCameraViewAlpha);
                break;
            case R.id.btn_camera_trans_alpha_dec:
                mCameraViewAlpha -= 0.1f;
                mCameraViewAlpha = mCameraViewAlpha < 0 ? 0 : mCameraViewAlpha;
                mCamera.setViewAlpha(mCameraViewAlpha);
                break;
            default:
                break;
        }
    }

    private View.OnTouchListener gvCameraDisplayTouchListener = new View.OnTouchListener() {
        private int lastPosX, lastPosY;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    //将点下的点的坐标保存
                    lastPosX = (int) event.getRawX();
                    lastPosY = (int) event.getRawY();
                    break;

                case MotionEvent.ACTION_MOVE:
                    //计算出需要移动的距离
                    int dx = (int) event.getRawX() - lastPosX;
                    int dy = (int) event.getRawY() - lastPosY;
                    //将移动距离加上，现在本身距离边框的位置
                    int left = view.getLeft() + dx;
                    int top = view.getTop() + dy;
                    //获取到layoutParams然后改变属性，在设置回去
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.height = view.getHeight();
                    layoutParams.width = view.getWidth();
                    layoutParams.leftMargin = left;
                    layoutParams.topMargin = top;
                    view.setLayoutParams(layoutParams);
                    //记录最后一次移动的位置
                    lastPosX = (int) event.getRawX();
                    lastPosY = (int) event.getRawY();
                    break;
            }
            //刷新界面
            view.invalidate();
            return true;
        }
    };
}
