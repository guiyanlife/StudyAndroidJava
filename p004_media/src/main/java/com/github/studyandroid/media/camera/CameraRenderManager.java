package com.github.studyandroid.media.camera;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CameraRenderManager {
    private String TAG = "CameraRenderManager";

    private GLSurfaceView mGLSurfaceView;
    private SurfaceTexture mSurfaceTexture;
    private Camera mCamera = null;

    private float mViewAlpha = 1.0f;

    /**
     * 构造函数
     *
     * @param glSurfaceView 显示Camera图像的载体，必须传
     */
    public CameraRenderManager(GLSurfaceView glSurfaceView) {
        this.mGLSurfaceView = glSurfaceView;
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
            // set GLSurface view background to transparent
            mGLSurfaceView.setZOrderOnTop(true);
            mGLSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
            mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            // set render version for GLES
            mGLSurfaceView.setEGLContextClientVersion(2);
            // set render callback
            mGLSurfaceView.setRenderer(render);
            // set render mode: when execute GLSurfaceView.requestRender() or GLSurfaceView.onResume() process rendering
            mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

            requestCameraPermission();
            mCamera = Camera.open(cameraId);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.set("orientation", "portrait");
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            parameters.setPreviewSize(1280, 720);
            //parameters.setPreviewFpsRange(15000, 30000);
            mCamera.setDisplayOrientation(90);
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * SurfaceTexture准备好之后触发该接口，用于保存每帧Camera的图像数据
     */
    private SurfaceTexture.OnFrameAvailableListener surfaceTextureListener = new SurfaceTexture.OnFrameAvailableListener() {
        @Override
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            mGLSurfaceView.requestRender(); //当Camera一帧预览图像存储到SurfaceTexture完成时，调用该函数，让GLSurfaceView进行渲染
        }
    };

    /**
     * GLSurfaceView准备好之后触发该接口，用于渲染视频
     */
    private GLSurfaceView.Renderer render = new GLSurfaceView.Renderer() {
        CameraTranslucentTriangle camTranTriangle = null;

        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
            // 基于OpenGL的纹理创建SurfaceTexture，用于渲染Camera的预览图像
            int mOESTextureId = CameraTranslucentTriangle.createOESTextureObject();
            mSurfaceTexture = new SurfaceTexture(mOESTextureId);
            //new一个控制GLES渲染的类
            camTranTriangle = new CameraTranslucentTriangle(mGLSurfaceView.getContext(), 1280, 720);
            try {
                mCamera.setPreviewTexture(mSurfaceTexture);
                //mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //添加帧可用监听，通知GLSurface渲染
            mSurfaceTexture.setOnFrameAvailableListener(surfaceTextureListener);
        }

        @Override
        public void onSurfaceChanged(GL10 gl10, int width, int height) {
            // 渲染窗口大小发生改变的处理
            camTranTriangle.onChange(width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl10) {
            // 执行Camera每帧图像的渲染工作
            mSurfaceTexture.updateTexImage();
            camTranTriangle.setTextureAlpha(mViewAlpha);
            camTranTriangle.draw();
        }
    };

    /**
     * 请求Camera的权限
     */
    private void requestCameraPermission() {
        if (ActivityCompat.checkSelfPermission(mGLSurfaceView.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mGLSurfaceView.getContext(), new String[]{Manifest.permission.CAMERA}, 0);
        }
    }

    /**
     * 设置视图的透明度
     *
     * @param alpha 透明度值，范围0~1
     */
    public void setViewAlpha(float alpha) {
        mViewAlpha = alpha;
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