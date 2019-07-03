package com.github.studyandroid.opengl.opengles20;

import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BitmapRenderManager {
    private String TAG = BitmapRenderManager.class.getSimpleName();
    private static final int MSG_BITMAP_RENDER = 0;
    private static final long RENDER_INTERVAL_TIME = 300; //0.3s

    private BitmapTexture mBitmapTexture;
    private GLSurfaceView mGLSurfaceView;
    private float mViewAlpha = 1.0f;

    private HandlerThread mThread;
    private Handler mHandler;

    /**
     * 构造函数
     *
     * @param glSurfaceView 显示Bitmap图像的载体，必须传
     */
    public BitmapRenderManager(GLSurfaceView glSurfaceView) {
        this.mGLSurfaceView = glSurfaceView;
    }

    /**
     * 初始化Bitmap渲染
     */
    public void initBitmapRender() {
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

        // init bitmap render thread
        mThread = new HandlerThread("UPDATE_PERSON_FOR_SM");
        mThread.start();
        mHandler = new BitmapRenderHandler(mThread.getLooper());

    }

    /**
     * 用于渲染bitmap的Handler
     */
    private class BitmapRenderHandler extends Handler {
        public BitmapRenderHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mGLSurfaceView.requestRender(); //调用该函数，让GLSurfaceView进行渲染bitmap
            sendEmptyMessageDelayed(MSG_BITMAP_RENDER, RENDER_INTERVAL_TIME);
        }
    }

    /**
     * GLSurfaceView准备好之后触发该接口，用于渲染Bitmap
     */
    private GLSurfaceView.Renderer render = new GLSurfaceView.Renderer() {
        BitmapTranslucentTriangle bmpTranTriangle = null;

        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
            // 创建OpenGL的2D纹理，用于渲染Bitmap图像
            BitmapTranslucentTriangle.createTextureObject();
            // 初始化Bitmaps用于渲染
            mBitmapTexture = new BitmapTexture();
            //new一个控制GLES渲染的类
            bmpTranTriangle = new BitmapTranslucentTriangle(mGLSurfaceView.getContext(), 1280, 720);
        }

        @Override
        public void onSurfaceChanged(GL10 gl10, int width, int height) {
            // 渲染窗口大小发生改变的处理
            bmpTranTriangle.onChange(width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl10) {
            // 执行Bitmap图像的渲染工作
            BitmapTranslucentTriangle.updateTexImage(mBitmapTexture.getNextBitmap());
            bmpTranTriangle.setTextureAlpha(mViewAlpha);
            bmpTranTriangle.draw();
        }
    };

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
        mHandler.sendEmptyMessage(MSG_BITMAP_RENDER);
    }

    /**
     * 停止预览
     */
    public void onStopPreview() {
        mHandler.removeMessages(MSG_BITMAP_RENDER);
    }

    /**
     * 释放资源
     */
    public void onDestroy() {
        Log.v(TAG, "onDestroy");
        mHandler.removeMessages(MSG_BITMAP_RENDER);
        mThread.quitSafely();
        mThread = null;
    }
}