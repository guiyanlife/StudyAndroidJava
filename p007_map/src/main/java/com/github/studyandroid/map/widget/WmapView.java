package com.github.studyandroid.map.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.github.studyandroid.map.R;

import java.util.List;

public class WmapView extends View {
    private List<WmapDataBean> mWmapData;
    private Bitmap mBmPointRed, mBmPointBlue, mBmPointGreen, mBmBackground;
    private int mIntPointSize, mIntBgWidth, mIntBgHeight;
    private boolean isSetRefPointA = false, isSetRefPointB = false;
    private int mRefaX, mRefaY, mRefbX, mRefbY;
    private double mRefaLatitude, mRefaLongitude, mRefbLatitude, mRefbLongitude;


    //画水质文字的画笔
    private Paint paintText = new Paint();

    public WmapView(Context context) {
        super(context);
        initData();
    }

    public WmapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        mBmPointRed = BitmapFactory.decodeResource(getResources(), R.drawable.widget_wmap_pos_red, options);
        mBmPointBlue = BitmapFactory.decodeResource(getResources(), R.drawable.widget_wmap_pos_blue);
        mBmPointGreen = BitmapFactory.decodeResource(getResources(), R.drawable.widget_wmap_pos_green);
        mIntPointSize = options.outWidth;
        mBmBackground = BitmapFactory.decodeResource(getResources(), R.drawable.widget_wmap_bg, options);
        mIntBgWidth = options.outWidth;
        mIntBgHeight = options.outHeight;

        //水质文字画笔的属性
        paintText.setColor(getResources().getColor(R.color.white));
        paintText.setAntiAlias(true);
        paintText.setTypeface(Typeface.DEFAULT); //文字样式为默认，其他有加粗，斜体等
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
        //以地图尺寸作为默认值
        int width = fixWmapSize(mIntBgWidth, widthMeasureSpec);
        int height = fixWmapSize(mIntBgHeight, heightMeasureSpec);
        //保证地图显示比例
        if (width < height * mIntBgWidth / mIntBgHeight)
            height = width * mIntBgHeight / mIntBgWidth;
        else
            width = height * mIntBgWidth / mIntBgHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String strWQualit;
        int viewWidth, viewHeight, pointDrawSize, pointX, pointY, textWidth, textHeight, wQuality;

        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        pointDrawSize = viewWidth * mIntPointSize / mIntBgWidth;
        paintText.setTextSize((int) (pointDrawSize / 3.6)); //文字大小

        //绘制背景地图
        canvas.drawBitmap(mBmBackground, null, new Rect(0, 0, viewWidth, viewHeight), null);

        //绘制设置参考点提示界面
        if (isSetRefPointA == false || isSetRefPointB == false) {
            paintText.setTextSize(viewHeight / 16); //文字大小
            Rect bounds = new Rect();
            String str;
            if (!isSetRefPointA)
                str = "请调用函数setRefPointA()设置参考点A";
            else
                str = "请调用函数setRefPointB()设置参考点B";
            paintText.getTextBounds(str, 0, str.length(), bounds);
            textWidth = bounds.width();
            textHeight = bounds.height();
            canvas.drawText(str, (viewWidth - textWidth) / 2, (viewHeight + textHeight) / 2, paintText);
            return;
        }

        //绘制水质地图数据
        if (mWmapData == null || mWmapData.size() <= 0)
            return;
        for (WmapDataBean data : mWmapData) {
            wQuality = data.getQuality();
            pointX = latitude2PosX(data.getLatitude());
            pointY = longitude2PosY(data.getLongitude());

            //绘制水质坐标点
            if (wQuality < 300)      //wQuality < 300
                canvas.drawBitmap(mBmPointGreen, null, new Rect(pointX - pointDrawSize / 2, pointY - pointDrawSize / 2, pointX + pointDrawSize / 2, pointY + pointDrawSize / 2), null);
            else if (wQuality < 700) //700 <= wQuality < 300
                canvas.drawBitmap(mBmPointBlue, null, new Rect(pointX - pointDrawSize / 2, pointY - pointDrawSize / 2, pointX + pointDrawSize / 2, pointY + pointDrawSize / 2), null);
            else                     //wQuality > 700
                canvas.drawBitmap(mBmPointRed, null, new Rect(pointX - pointDrawSize / 2, pointY - pointDrawSize / 2, pointX + pointDrawSize / 2, pointY + pointDrawSize / 2), null);

            //绘制水质值的文字
            Rect bounds = new Rect();
            strWQualit = String.valueOf(wQuality);
            paintText.getTextBounds(strWQualit, 0, strWQualit.length(), bounds);
            textWidth = bounds.width();
            textHeight = bounds.height();
            canvas.drawText(strWQualit, pointX - textWidth / 2, pointY + textHeight / 2, paintText);
        }
    }

    /**
     * 纬度转换为实际绘图横坐标位置
     *
     * @param latitude 纬度
     * @return 横坐标位置
     */
    private int latitude2PosX(double latitude) {
        return Math.abs((int) ((latitude * (mRefaX - mRefbX) + mRefbX * mRefaLatitude - mRefaX * mRefbLatitude) / (mRefaLatitude - mRefbLatitude)) * getMeasuredWidth() / mIntBgWidth);
    }

    /**
     * 经度转换为实际绘图纵坐标位置
     *
     * @param longitude 经度
     * @return 纵坐标位置
     */
    private int longitude2PosY(double longitude) {
        return Math.abs((int) ((longitude * (mRefaY - mRefbY) + mRefbY * mRefaLongitude - mRefaY * mRefbLongitude) / (mRefaLongitude - mRefbLongitude)) * getMeasuredHeight() / mIntBgHeight);
    }

    /**
     * 设置水质地图数据
     *
     * @param datas 水质地图数据
     */
    public void setWmapDates(List<WmapDataBean> datas) {
        mWmapData = datas;
        invalidate();
    }

    /**
     * 设置参考点A，用于水质数据到经纬度的转换
     *
     * @param x         背景图片像素点横坐标（左上角为原点）
     * @param y         背景图片像素点纵坐标
     * @param latitude  该像素点所对应的纬度
     * @param longitude 该像素点所对应的经度
     */
    public void setRefPointA(int x, int y, double latitude, double longitude) {
        mRefaX = x;
        mRefaY = y;
        mRefaLatitude = latitude;
        mRefaLongitude = longitude;
        isSetRefPointA = true;
    }

    /**
     * 设置参考点B，用于水质数据到经纬度的转换
     *
     * @param x         背景图片像素点横坐标（左上角为原点）
     * @param y         背景图片像素点纵坐标
     * @param latitude  该像素点所对应的纬度
     * @param longitude 该像素点所对应的经度
     */
    public void setRefPointB(int x, int y, double latitude, double longitude) {
        mRefbX = x;
        mRefbY = y;
        mRefbLatitude = latitude;
        mRefbLongitude = longitude;
        isSetRefPointB = true;
    }

    /**
     * 清除水质地图数据
     */
    public void clearWmapDatas() {
        mWmapData.clear();
        invalidate();
    }

    /**
     * 获取水质地图数据
     *
     * @return 水质地图数据
     */
    public List<WmapDataBean> getWmapDates() {
        return mWmapData;
    }
}
