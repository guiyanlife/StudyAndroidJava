package com.github.studyandroid.map.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.github.studyandroid.map.R;

/**
 * TODO: document your custom view class.
 */
public class WmapWqTdsYbpView extends View {
    private int mIntBgWidth, mIntBgHeight, mIntPointSize, mIntViewDefaultWidth, mIntViewDefaultHeight, mIntTdsValue = -1;
    private Bitmap mBmBackground, mBmPointer;

    public WmapWqTdsYbpView(Context context) {
        super(context);
        init(null, 0);
    }

    public WmapWqTdsYbpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public WmapWqTdsYbpView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        mBmPointer = BitmapFactory.decodeResource(getResources(), R.drawable.widget_wmap_dashboard_pointer, options);
        mIntPointSize = options.outWidth;
        mBmBackground = BitmapFactory.decodeResource(getResources(), R.drawable.widget_wmap_dashboard_bg, options);
        mIntBgWidth = options.outWidth;
        mIntBgHeight = options.outHeight;
        mIntViewDefaultWidth = mIntBgWidth;
        mIntViewDefaultHeight = mIntBgHeight;
    }

    private int fixWmapSize(int defaultSize, int measureSpec) {
        int ret = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                ret = defaultSize;
                break;
            case MeasureSpec.AT_MOST: //wrap_content
                ret = defaultSize;
                break;
            case MeasureSpec.EXACTLY: //match_parent or 固定尺寸
                ret = size;
                break;
        }
        return ret;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //以仪表盘背景尺寸作为默认值
        int width = fixWmapSize(mIntBgWidth, widthMeasureSpec);
        int height = fixWmapSize(mIntBgHeight, heightMeasureSpec);
        //保证仪表盘显示比例
        if (width < height * mIntBgWidth / mIntBgHeight)
            height = width * mIntBgHeight / mIntBgWidth;
        else
            width = height * mIntBgWidth / mIntBgHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制仪表盘
        canvas.drawBitmap(mBmBackground, null, new Rect(0, 0, drawPosX(mIntBgWidth), drawPosY(mIntBgHeight)), null);

        //绘制仪表盘的指针
        if (mIntTdsValue >= 0 && mIntTdsValue <= 1000) {
            Matrix matrix = new Matrix();
            matrix.postScale(getMeasuredWidth() / mIntViewDefaultWidth, getMeasuredHeight() / mIntViewDefaultWidth);
//            matrix.postTranslate(-drawPosX(mIntPointSize - 100) / 2, -drawPosX(mIntPointSize) / 2);
//            matrix.postRotate(tdsData2Angle(mIntTdsValue)); //根据Tds的值，进行相应的旋转
//            matrix.postTranslate(drawPosX(mIntViewDefaultWidth) / 2, drawPosY(mIntViewDefaultHeight) / 2);
            canvas.drawBitmap(mBmPointer, matrix, null);
        }
    }

    /**
     * tds数据转换为旋转角度
     *
     * @param tds tds数据转换
     * @return 旋转角度
     */
    private float tdsData2Angle(int tds) {
        return tds;
    }

    /**
     * View默认尺寸横坐标，转换为当前View尺寸的横坐标
     *
     * @param defaultPosX View默认尺寸横坐标
     * @return 当前View尺寸的横坐标
     */
    private int drawPosX(int defaultPosX) {
        return defaultPosX * getMeasuredWidth() / mIntViewDefaultWidth;
    }

    /**
     * View默认尺寸纵坐标，转换为当前View尺寸的纵坐标
     *
     * @param defaultPosY View默认尺寸纵坐标
     * @return 当前View尺寸的纵坐标
     */
    private int drawPosY(int defaultPosY) {
        return defaultPosY * getMeasuredHeight() / mIntViewDefaultHeight;
    }


    /**
     * 设置水质TDS数据
     */
    public void setTdsData(int value) {
        mIntTdsValue = value;
        invalidate();
    }

    /**
     * 设置水质TDS数据，带动画
     */
    public void setTdsDataAnim(int value, int ms) {
        ObjectAnimator anim = ObjectAnimator.ofInt(this, "TdsData", getTdsData(), value);
        anim.setDuration(ms);
        anim.start();
    }

    /**
     * 获取水质TDS数据
     */
    public int getTdsData() {
        return mIntTdsValue;
    }
}
