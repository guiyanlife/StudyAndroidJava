package com.github.studyandroid.media.camera;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.github.studyandroid.media.R;
import com.github.studyandroid.media.util.FileUtil;
import com.github.studyandroid.media.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glClearColor;

public class CameraTranslucentTriangle {
    private Context mContext;

    public int uPosHandle;
    public int aTexHandle;
    public int mMVPMatrixHandle;
    public int mTexAlphaHandle;

    private FloatBuffer mPosBuffer;
    private FloatBuffer mTexBuffer;
    private float[] mPosCoordinate;
    private float[] mTexCoordinate;

    private float[] mProjectMatrix = new float[16];
    private float[] mCameraMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];
    private float mTexAlphaValue = 1.0f;

    /**
     * 创建图形的坐标点（圆形）
     *
     * @param density 密度，表示圆形是由density个扇形拼成的
     * @return 返回坐标点的数据点
     */
    private float[] getCirclePosCoordinate(int density) {
        float[] data = new float[(density + 2) * 2]; //数据点
        double delta = 2 * Math.PI / density;
        data[0] = 0.5f; //O.x
        data[1] = 0.5f; //O.y
        for (int i = 0; i < density; i++) {
            data[(i + 1) * 2] = (float) Math.sin(delta * i) / 2 + 0.5f;
            data[(i + 1) * 2 + 1] = (float) Math.cos(delta * i) / 2 + 0.5f;
        }
        data[(density + 1) * 2] = 0.5f;
        data[(density + 1) * 2 + 1] = 1.0f;
        return data;
    }

    /**
     * 创建图形坐标点对应的纹理点（圆形）
     *
     * @param density 密度，表示圆形是由density个扇形拼成的
     * @param rotate  旋转度数
     * @param isFlipY 纹理关于Y轴镜像
     * @param width   Camera原始预览图像的宽
     * @param height  Camera原始预览图像的高
     * @return 返回纹理数据点
     */
    private float[] getCircleTexCoordinate(int density, float rotate, boolean isFlipY, int width, int height) {
        return getCircleTexCoordinate(density, rotate, isFlipY, height / (double) width);
    }

    /**
     * 创建图形坐标点对应的纹理点（圆形）
     *
     * @param density 密度，表示圆形是由density个扇形拼成的
     * @param rotate  旋转度数
     * @param isFlipY 纹理关于Y轴镜像
     * @param ratio   Camera原始预览图像高宽比例
     * @return 返回纹理数据点
     */
    private float[] getCircleTexCoordinate(int density, float rotate, boolean isFlipY, double ratio) {
        float[] data = new float[(density + 2) * 2]; //数据点
        double delta = 2 * Math.PI / density;
        double rotAngle = rotate * Math.PI / 180;
        data[0] = 0.5f; //O.x
        data[1] = 0.5f; //O.y
        for (int i = 0; i < density + 1; i++) {
            data[(i + 1) * 2] = (float) (Math.sin(delta * i + rotAngle) * ratio / 2) + 0.5f;
            if (isFlipY)
                data[(i + 1) * 2 + 1] = -(float) Math.cos(delta * i + rotAngle) / 2 + 0.5f;
            else
                data[(i + 1) * 2 + 1] = (float) Math.cos(delta * i + rotAngle) / 2 + 0.5f;
        }
        return data;
    }

    /**
     * 构造函数
     *
     * @param context APP的内容上下文
     */
    public CameraTranslucentTriangle(Context context) {
        this(context, 1280, 720);
    }

    /**
     * 构造函数
     *
     * @param context       APP的内容上下文
     * @param previewWidth  Camera预览图像的宽
     * @param previewHeight Camera预览图像的高
     */
    public CameraTranslucentTriangle(Context context, int previewWidth, int previewHeight) {
        this.mContext = context;

        // enable blend for texture color's alpha
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        // clear surface color for transparent
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        int shaderProgram = getShaderProgram();
        uPosHandle = GLES20.glGetAttribLocation(shaderProgram, "a_Position");
        aTexHandle = GLES20.glGetAttribLocation(shaderProgram, "inputTextureCoordinate");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "u_matViewProjection");
        mTexAlphaHandle = GLES20.glGetUniformLocation(shaderProgram, "u_valInputTextureAlpha");


        //正方形
        //mPosCoordinate = {0, 0, 0, 1, 1, 1, 1, 0}; //正方形坐标
        //mTexCoordinate = {0, 1, 1, 1, 1, 0, 0, 0}; //正方形纹理坐标，适用手机前置摄像头，逆时针90度旋转
        //mTexCoordinate = {1, 0, 0, 0, 0, 1, 1, 1}; //正方形纹理坐标，适用蜂盒前置摄像头，顺时针90度旋转
        //mTexCoordinate = {1, 1, 0, 1, 0, 0, 1, 0}; //正方形纹理坐标，适用手机后置摄像头，顺时针90度旋转并沿Y轴旋转
        // 圆形
        mPosCoordinate = getCirclePosCoordinate(360); //圆形坐标
        //mTexCoordinate = getCircleTexCoordinate(360, 90, false, previewWidth, previewHeight); //圆形纹理坐标，适用手机前置摄像头，逆时针90度旋转
        //mTexCoordinate = getCircleTexCoordinate(360, -90, false, previewWidth, previewHeight); //圆形纹理坐标，适用蜂盒前置摄像头，顺时针90度旋转
        mTexCoordinate = getCircleTexCoordinate(360, -90, true, previewWidth, previewHeight);  //圆形纹理坐标，适用手机后置摄像头，顺时针90度旋转并沿Y轴旋转

        mPosBuffer = convertToFloatBuffer(mPosCoordinate);
        mTexBuffer = convertToFloatBuffer(mTexCoordinate);

        GLES20.glVertexAttribPointer(uPosHandle, 2, GLES20.GL_FLOAT, false, 0, mPosBuffer);
        GLES20.glVertexAttribPointer(aTexHandle, 2, GLES20.GL_FLOAT, false, 0, mTexBuffer);

        GLES20.glEnableVertexAttribArray(uPosHandle);
        GLES20.glEnableVertexAttribArray(aTexHandle);
    }

    /**
     * 编译并链接vertex shader与fragment shader，生成shader program
     *
     * @return shader program的索引
     */
    private int getShaderProgram() {
        int shaderProgram;
        String vertexShaderSource = FileUtil.TextFile2String(mContext, R.raw.cam_tran_vertex_shader);
        String fragmentShaderSource = FileUtil.TextFile2String(mContext, R.raw.cam_tran_fragment_shader);
        shaderProgram = ShaderUtil.buildProgram(vertexShaderSource, fragmentShaderSource);
        GLES20.glUseProgram(shaderProgram);
        return shaderProgram;
    }

    /**
     * 绘制三维图形
     */
    public void draw() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glUniform1f(mTexAlphaHandle, mTexAlphaValue);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, mPosCoordinate.length / 2);
    }

    /**
     * 当GLSurface尺寸变化时，OpenGL渲染保持原始比例渲染
     *
     * @param width  GLSurface的宽
     * @param height GLSurface的高
     */
    public void onChange(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Matrix.scaleM(mMVPMatrix, 0, 1, -1, 1);
        float ratio = (float) height / width;
        if (ratio > 1)
            Matrix.orthoM(mProjectMatrix, 0, -0.5f, 0.5f, -0.5f * ratio, 0.5f * ratio, 1, 7);// 3和7代表远近视点与眼睛的距离，非坐标点
        else
            Matrix.orthoM(mProjectMatrix, 0, -0.5f / ratio, 0.5f / ratio, -0.5f, 0.5f, 1, 7);// 3和7代表远近视点与眼睛的距离，非坐标点
        Matrix.setLookAtM(mCameraMatrix, 0, 0.5f, 0.5f, 3, 0.5f, 0.5f, 0f, 0f, 1.0f, 0.0f);// 3代表眼睛的坐标点
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mCameraMatrix, 0);
    }

    /**
     * 设置绘制视频纹理的透明度
     *
     * @param alpha 透明度值，范围0~1
     */
    public void setTextureAlpha(float alpha) {
        if (alpha < 0)
            mTexAlphaValue = 0f;
        else if (alpha > 1)
            mTexAlphaValue = 1.0f;
        else
            mTexAlphaValue = alpha;
    }

    /**
     * 将Buffer的float数组形式转换成FloatBuffer形式
     *
     * @param buffer float数组
     * @return FloatBuffer
     */
    private FloatBuffer convertToFloatBuffer(float[] buffer) {
        FloatBuffer fb = ByteBuffer.allocateDirect(buffer.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        fb.put(buffer);
        fb.position(0);
        return fb;
    }

    /**
     * 创建纹理为外部SurfaceTexture使用
     *
     * @return 返回该纹理的索引
     */
    public static int createOESTextureObject() {
        int[] tex = new int[1];
        //生成一个纹理
        GLES20.glGenTextures(1, tex, 0);
        //将此纹理绑定到外部纹理上
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, tex[0]);
        //设置纹理过滤参数
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        //解除纹理绑定
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        return tex[0];
    }
}
