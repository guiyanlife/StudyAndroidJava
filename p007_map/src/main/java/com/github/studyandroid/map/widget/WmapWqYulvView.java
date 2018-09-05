package com.github.studyandroid.map.widget;

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
    private double mDoubleYulvValue = -1;

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
        String tdsState;

        //画余氯渐变尺图像
        canvas.drawBitmap(mBmScale, null, new Rect(drawPosX(mIntPointerWidth / 2 + 1), drawPosY(mIntPointerHeight + 3), drawPosX(mIntViewDefaultWidth - mIntPointerWidth / 2 - 1), getMeasuredHeight()), null);

        if (mDoubleYulvValue > -0.00001 && mDoubleYulvValue <= 0.40001) {
            //画水质状态文字
            mPaintTextWstate.setTextSize(drawPosY(14)); //文字大小
            Rect bounds = new Rect();
            tdsState = tdsData2State(mDoubleYulvValue);
            mPaintTextWstate.getTextBounds(tdsState, 0, tdsState.length(), bounds);
            textWidth = bounds.width();
            textHeight = bounds.height();
            canvas.drawText(tdsState, drawPosX(yulvData2PosX(mDoubleYulvValue)) - textWidth / 2, drawPosY(mIntPointerHeight / 3) + textHeight / 2, mPaintTextWstate);


            //画水质值指针的图片
            canvas.drawBitmap(mBmPointer, null, new Rect(drawPosX(yulvData2PosX(mDoubleYulvValue) - mIntPointerWidth / 2), 0, drawPosX(yulvData2PosX(mDoubleYulvValue) + mIntPointerWidth / 2), drawPosY(mIntPointerHeight)), null);
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
     * 余氯数据转换为余氯状态
     *
     * @param yulv 余氯数据
     * @return 余氯状态
     */
    private String tdsData2State(double yulv) {
        String ret;
        if (yulv < 0) {
            ret = "异常";
        } else if (yulv < 0.05) { //0<=yulv<0.05
            ret = "很少";
        } else if (yulv < 0.1) { //0.05<=yulv<0.1
            ret = "较少";
        } else if (yulv < 0.2) { //0.1<=yulv<0.2
            ret = "正常";
        } else { //0.2<=yulv
            ret = "较多";
        }
        return ret;
    }

    /**
     * 余氯数据转换为View默认尺寸的横坐标位置
     *
     * @param yulv 余氯数据
     * @return View默认尺寸的横坐标位置
     */
    private int yulvData2PosX(double yulv) {
        int ret;
        if (yulv < 0) {
            ret = -mIntPointerWidth;
        } else if (yulv < 0.1) { //0<=yulv<0.1
            ret = mIntPointerWidth / 2 + 1 + (int)(mIntScaleWidth * yulv * 5);
        } else if (yulv < 0.2) { //0.1<=tds<0.2
            ret = mIntPointerWidth / 2 + 1 + mIntScaleWidth / 2 + (int)(mIntScaleWidth *  (yulv - 0.1) * 2.5);
        } else { //0.2<=tds<0.4
            ret = mIntPointerWidth / 2 + 1 + mIntScaleWidth * 3 / 4 +(int)(mIntScaleWidth * (yulv - 0.2) * 1.25);
        }
        return ret;
    }

    /**
     * 清除水质余氯数据
     */
    public void setYulvData(double value) {
        mDoubleYulvValue = value;
        invalidate();
    }

    /**
     * 清除水质余氯数据
     */
    public void clearData() {
        mDoubleYulvValue = -1;
        invalidate();
    }
}
