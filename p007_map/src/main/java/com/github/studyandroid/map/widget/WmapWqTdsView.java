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

public class WmapWqTdsView extends View {
    private int mIntScaleWidth, mIntScaleHeight, mIntPointerWidth, mIntPointerHeight, mIntViewDefaultWidth, mIntViewDefaultHeight, mIntTdsValue = -1;
    private Bitmap mBmScale, mBmPointer;

    //画水质状态文字的画笔
    private Paint mPaintTextWstate = new Paint();

    public WmapWqTdsView(Context context) {
        super(context);
        initData();
    }

    public WmapWqTdsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        mBmScale = BitmapFactory.decodeResource(getResources(), R.drawable.widget_wmap_tds_scale, options);
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

        //画水质渐变尺图像
        canvas.drawBitmap(mBmScale, null, new Rect(drawPosX(mIntPointerWidth / 2 + 1), drawPosY(mIntPointerHeight + 3), drawPosX(mIntViewDefaultWidth - mIntPointerWidth / 2 - 1), getMeasuredHeight()), null);

        if (mIntTdsValue >= 0 && mIntTdsValue <= 2000) {
            //画水质状态文字
            mPaintTextWstate.setTextSize(drawPosY(14)); //文字大小
            Rect bounds = new Rect();
            tdsState = tdsData2State(mIntTdsValue);
            mPaintTextWstate.getTextBounds(tdsState, 0, tdsState.length(), bounds);
            textWidth = bounds.width();
            textHeight = bounds.height();
            canvas.drawText(tdsState, drawPosX(tdsData2PosX(mIntTdsValue)) - textWidth / 2, drawPosY(mIntPointerHeight / 3) + textHeight / 2, mPaintTextWstate);


            //画水质值指针的图片
            canvas.drawBitmap(mBmPointer, null, new Rect(drawPosX(tdsData2PosX(mIntTdsValue) - mIntPointerWidth / 2), 0, drawPosX(tdsData2PosX(mIntTdsValue) + mIntPointerWidth / 2), drawPosY(mIntPointerHeight)), null);

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
     * tds数据转换为水质状态
     *
     * @param tds tds数据转换
     * @return 水质状态
     */
    private String tdsData2State(int tds) {
        String ret;
        if (tds < 0) {
            ret = "异常";
        } else if (tds <= 50) { //0<=tds<=50
            ret = "很少";
        } else if (tds <= 100) { //50<=tds<=100
            ret = "较少";
        } else if (tds <= 300) { //100<tds<=300
            ret = "正常";
        } else if (tds <= 600) { //300<tds<=600
            ret = "较多";
        } else if (tds <= 1000) { //600<tds<=1000
            ret = "很多";
        } else { //1000<tds
            ret = "极多";
        }
        return ret;
    }

    /**
     * tds数据转换为View默认尺寸的横坐标位置
     *
     * @param tds tds数据
     * @return View默认尺寸的横坐标位置
     */
    private int tdsData2PosX(int tds) {
        int ret;
        if (tds < 0) {
            ret = -mIntPointerWidth;
        } else if (tds <= 100) { //0<=tds<=100
            ret = mIntPointerWidth / 2 + 1 + mIntScaleWidth * tds / 300;
        } else if (tds <= 300) { //100<tds<=300
            ret = mIntPointerWidth / 2 + 1 + mIntScaleWidth / 3 + mIntScaleWidth * (tds - 100) / 1200;
        } else if (tds <= 600) { //300<tds<=600
            ret = mIntPointerWidth / 2 + 1 + mIntScaleWidth / 2 + mIntScaleWidth * (tds - 300) / 1800;
        } else if (tds <= 1000) { //600<tds<=1000
            ret = mIntPointerWidth / 2 + 1 + mIntScaleWidth * 2 / 3 + mIntScaleWidth * (tds - 600) / 2400;
        } else { //1000<tds<=2000
            ret = mIntPointerWidth / 2 + 1 + mIntScaleWidth * 5 / 6 + mIntScaleWidth * (tds - 1000) / 6000;
        }
        return ret;
    }

    /**
     * 设置水质TDS数据
     */
    public void setTdsData(int value) {
        mIntTdsValue = value;
        invalidate();
    }

    /**
     * 清除水质TDS数据
     */
    public void clearTdsData() {
        mIntTdsValue = -1;
        invalidate();
    }
}
