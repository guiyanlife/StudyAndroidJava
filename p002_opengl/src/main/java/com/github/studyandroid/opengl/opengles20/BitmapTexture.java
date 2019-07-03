package com.github.studyandroid.opengl.opengles20;

import android.graphics.Bitmap;

import com.github.studyandroid.opengl.util.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

public class BitmapTexture {
    private List<Bitmap> mBitmapList;
    private int mBitmapIndex;

    public BitmapTexture() {
        mBitmapList = new ArrayList<>();
        mBitmapList.add(BitmapUtil.getBitmapFromAssets("image_render_1.jpg"));
        mBitmapList.add(BitmapUtil.getBitmapFromAssets("image_render_2.jpg"));
        mBitmapList.add(BitmapUtil.getBitmapFromAssets("image_render_3.jpg"));
        mBitmapIndex = 0;
    }

    public BitmapTexture(List<Bitmap> mBitmapList) {
        this.mBitmapList = mBitmapList;
        mBitmapIndex = 0;
    }

    public Bitmap getBitmap(int i) {
        if (mBitmapList != null && i > -1 && i < mBitmapList.size())
            return mBitmapList.get(i);
        return null;
    }

    public Bitmap getNextBitmap() {
        if (mBitmapList == null || mBitmapList.isEmpty()) {
            mBitmapIndex = 0;
            return null;
        }
        if (mBitmapIndex + 1 < mBitmapList.size())
            mBitmapIndex++;
        else
            mBitmapIndex = 0;
        return mBitmapList.get(mBitmapIndex);
    }
}
