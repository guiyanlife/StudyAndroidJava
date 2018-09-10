package com.github.studyandroid.map.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.github.studyandroid.map.R;

public class WmapWqYulvView extends View {
    private int mIntScaleWidth, mIntScaleHeight, mIntPointerWidth, mIntPointerHeight, mIntViewDefaultWidth, mIntViewDefaultHeight;
    private Bitmap mBmScale, mBmPointer;
    private int mIntYulvValue10000 = -1;

    //画水质状态文字的画笔
    private Paint mPaintTextWstate = new Paint();

    public WmapWqYulvView(Context context) {
        super(context);
        initData();
    }

    public WmapWqYulvView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        mBmScale = BitmapFactory.decodeResource(getResources(), R.drawable.widget_wmap_yulv_scale, options);
        mIntScaleWidth = options.outWidth;
        mIntScaleHeight = options.outHeight;
        mBmPointer = BitmapFactory.decodeResource(getResources(), R.drawable.widget_wmap_tds_pointer, options);
        mIntPointerWidth = options.outWidth;
        mIntPointerHeight = options.outHeight;
        mIntViewDefaultWidth = mIntScaleWidth + mIntPointerWidth + 2;
        mIntViewDefaultHeight = mIntScaleHeight + 3 + mIntPointerHeight; //3 is interval

        //水质文字画笔的属性
        mPaintTextWstate.setColor(getResources().getColor(R.color.white));
        mPaintTextWstate.setAntiAlias(true);
        mPaintTextWstate.setTypeface(Typeface.DEFAULT); //文字样式为默认，其他有加粗，斜体等
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
        //以View的原始尺寸作为默认值
        int width = fixWmapSize(mIntViewDefaultWidth, widthMeasureSpec);
        int height = fixWmapSize(mIntViewDefaultHeight, heightMeasureSpec);
        //保证View的显示比例
        if (width < height * mIntViewDefaultWidth / mIntViewDefaultHeight)
            height = width * mIntViewDefaultHeight / mIntViewDefaultWidth;
        else
            width = height * mIntViewDefaultWidth / mIntViewDefaultHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int textWidth, textHeight;
        String yulvState;

        //画余氯渐变尺图像
        canvas.drawBitmap(mBmScale, null, new Rect(drawPosX(mIntPointerWidth / 2 + 1), drawPosY(mIntPointerHeight + 3), drawPosX(mIntViewDefaultWidth - mIntPointerWidth / 2 - 1), getMeasuredHeight()), null);

        if (mIntYulvValue10000 >= 0 && mIntYulvValue10000 <= 4000) {
            //画水质状态文字
            mPaintTextWstate.setTextSize(drawPosY(14)); //文字大小
            Rect bounds = new Rect();
            yulvState = yulv10000Data2State(mIntYulvValue10000);
            mPaintTextWstate.getTextBounds(yulvState, 0, yulvState.length(), bounds);
            textWidth = bounds.width();
            textHeight = bounds.height();
            canvas.drawText(yulvState, drawPosX(yulv10000Data2PosX(mIntYulvValue10000)) - textWidth / 2, drawPosY(mIntPointerHeight / 3) + textHeight / 2, mPaintTextWstate);

            //画水质值指针的图片
            canvas.drawBitmap(mBmPointer, null, new Rect(drawPosX(yulv10000Data2PosX(mIntYulvValue10000) - mIntPointerWidth / 2), 0, drawPosX(yulv10000Data2PosX(mIntYulvValue10000) + mIntPointerWidth / 2), drawPosY(mIntPointerHeight)), null);
        }
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
     * 余氯10000倍数据转换为余氯状态
     *
     * @param yulv10000 余氯数据
     * @return 余氯状态
     */
    private String yulv10000Data2State(int yulv10000) {
        String ret;
        if (yulv10000 < 0) {
            ret = "异常";
        } else if (yulv10000 < 500) { //0<=yulv<0.05
            ret = "很少";
        } else if (yulv10000 < 1000) { //0.05<=yulv<0.1
            ret = "较少";
        } else if (yulv10000 < 2000) { //0.1<=yulv<0.2
            ret = "正常";
        } else { //0.2<=yulv
            ret = "较多";
        }
        return ret;
    }

    /**
     * 余氯10000倍数据转换为View默认尺寸的横坐标位置
     *
     * @param yulv10000 余氯数据
     * @return View默认尺寸的横坐标位置
     */
    private int yulv10000Data2PosX(int yulv10000) {
        int ret;
        if (yulv10000 < 0) {
            ret = -mIntPointerWidth;
        } else if (yulv10000 < 1000) { //0<=yulv<0.1
            ret = mIntPointerWidth / 2 + 1 + mIntScaleWidth * yulv10000 / 2000;
        } else if (yulv10000 < 2000) { //0.1<=tds<0.2
            ret = mIntPointerWidth / 2 + 1 + mIntScaleWidth / 2 + mIntScaleWidth * (yulv10000 - 1000) / 4000;
        } else { //0.2<=tds<0.4
            ret = mIntPointerWidth / 2 + 1 + mIntScaleWidth * 3 / 4 + mIntScaleWidth * (yulv10000 - 2000) / 8000;
        }
        return ret;
    }

    /**
     * 设置水质余氯10000倍数据
     */
    public void setYulv10000Data(int value) {
        mIntYulvValue10000 = value;
        invalidate();
    }

    /**
     * 获取水质余氯10000倍数据
     */
    public int getYulv10000Data() {
        return mIntYulvValue10000;
    }

    /**
     * 设置水质余氯数据
     */
    public void setYulvData(double value) {
        mIntYulvValue10000 = (int) (value * 10000);
        invalidate();
    }

    /**
     * 设置水质余氯数据，带动画
     */
    public void setYulvDataAnim(double value, int ms) {
        ObjectAnimator anim = ObjectAnimator.ofInt(this, "Yulv10000Data", getYulv10000Data(), (int)(value * 10000));
        anim.setDuration(ms);
        anim.start();
    }

    /**
     * 获取水质余氯数据
     */
    public double getYulvData() {
        return mIntYulvValue10000 / 10000.0;
    }

    /**
     * 清除水质余氯数据
     */
    public void clearData() {
        mIntYulvValue10000 = -1;
        invalidate();
    }
}
