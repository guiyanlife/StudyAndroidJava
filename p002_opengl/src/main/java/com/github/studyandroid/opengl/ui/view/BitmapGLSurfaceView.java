package com.github.studyandroid.opengl.ui.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BitmapGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {
    public BitmapGLSurfaceView(Context context) {
        super(context);
    }

    public BitmapGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }
}
