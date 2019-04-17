package com.github.studyandroid.media.helper;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

public class CameraHelper {
    private String TAG = "CameraHelper";


    private SurfaceHolder mHolder;
    private SurfaceView mSurface;
    private Camera mCamera = null;

    /**
     * 构造函数
     *
     * @param surfaceView 显示Camera图像的载体，必须传
     */
    public CameraHelper(SurfaceView surfaceView) {
        this.mSurface = surfaceView;
    }

    /**
     * 初始化Camera
     *
     * @param cameraId
     */
    public void initCamera(int cameraId) {
        if (cameraId < 0) {
            return;
        }

        try {
            mCamera = Camera.open(cameraId);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.set("orientation", "portrait");
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            parameters.setPreviewSize(1280, 720);
            mCamera.setDisplayOrientation(90);
            mCamera.setParameters(parameters);

            mHolder = mSurface.getHolder();
            mHolder.addCallback(callback);
            mHolder.setFormat(PixelFormat.RGBA_8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * SurfaceView 准备好之后触发该接口，一般用来播放
     */
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            onAdaptiveSize(true);
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    /**
     * 适应控件的高度或宽度显示视频的大小
     */
    public void onAdaptiveSize(boolean isCameraRotate90) {
        if (mCamera != null && mSurface != null) {
            int cameraWidth = mCamera.getParameters().getPreviewSize().width;
            int cameraHeight = mCamera.getParameters().getPreviewSize().height;

            // 如果摄像头被设置旋转90度，则尺寸对应反转
            if (isCameraRotate90) {
                int temp = cameraWidth;
                cameraWidth = cameraHeight;
                cameraHeight = temp;
            }

            int surfaceViewWidth = mSurface.getWidth();
            int surfaceViewHeight = mSurface.getHeight();

            Log.e(TAG, "Camera Preview Width: " + cameraWidth + ", " + "Camera Preview Height: " + cameraHeight);
            Log.e(TAG, "Surface Width: " + surfaceViewWidth + ", " + "Surface Height: " + surfaceViewHeight);
//        mHolder.setFixedSize(mSurfaceViewWidth, mSurfaceViewHeight);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int marginHorizontal, marginVertical;
            if (cameraWidth / cameraHeight > surfaceViewWidth / surfaceViewHeight) { //适应控件的宽度显示Camera预览的大小
                marginHorizontal = 0;
                marginVertical = (surfaceViewHeight - surfaceViewWidth * cameraHeight / cameraWidth) / 2;
            } else {                                                                 //适应控件的高度显示Camera预览的大小
                marginHorizontal = (surfaceViewWidth - surfaceViewHeight * cameraWidth / cameraHeight) / 2;
                marginVertical = 0;
            }
            lp.setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mSurface.setLayoutParams(lp);
        }
    }

    /**
     * 开始预览
     */
    public void onStartPreview() {
        if (mCamera != null) {
            mCamera.startPreview();
        }
    }

    /**
     * 停止预览
     */
    public void onStopPreview() {
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    /**
     * 释放Camera
     */
    public void onDestroy() {
        Log.v(TAG, "onDestroy");
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
