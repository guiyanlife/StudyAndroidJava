package com.github.studyandroid.media.helper;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.studyandroid.media.util.ScreenUtil;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Hashtable;

public class MediaPlayerHelper {
    private String TAG = "MediaPlayerHelper";
    private SurfaceHolder mHolder;
    private MediaPlayer mPlayer;

    private SurfaceView mSurface;
    private ImageView mThumbnail;
    private ImageView mPlay;

    private OnMediaListener listener;

    /**
     * 构造函数
     *
     * @param surface   播放视频的载体，必须传
     * @param thumbnail 视频缩略图（可选）
     * @param play      视频播放按钮（可选）
     */
    public MediaPlayerHelper(SurfaceView surface, ImageView thumbnail, ImageView play) {
        this.mSurface = surface;
        this.mThumbnail = thumbnail;
        this.mPlay = play;
    }

    /**
     * 初始化MediaPlayer
     *
     * @param fd     播放源文件的文件描述符
     * @param offset 文件的offset
     * @param length 文件的长度
     */
    public void initVideo(FileDescriptor fd, long offset, long length) {
        if (fd == null) {
            return;
        }
        try {
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(fd, offset, length);
            mPlayer.prepare();
            mPlayer.setOnCompletionListener(completionListener);
            mPlayer.setOnPreparedListener(preparedListener);
            mPlayer.setOnVideoSizeChangedListener(sizeChangedListener);
            mHolder = mSurface.getHolder();
            mHolder.addCallback(callback);
            mHolder.setFormat(PixelFormat.RGBA_8888);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * SurfaceView 准备好之后触发该接口，一般用来播放
     */
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            onAdaptiveSize();
            mPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    /**
     * 是否显示缩略图和播放按钮
     *
     * @param visible 设置是否显示播放按钮
     */
    public void isVisible(boolean visible) {
        if (mThumbnail != null && mPlay != null) {
            if (visible) {
                mThumbnail.setVisibility(View.VISIBLE);
                mPlay.setVisibility(View.VISIBLE);
            } else {
                mThumbnail.setVisibility(View.GONE);
                mPlay.setVisibility(View.GONE);
            }
        } else {
            Log.d(TAG, "不需要缩略图和播放按钮");
        }

    }


    /**
     * 开始播放
     */
    public void onStartPlay() {
        if (mPlayer != null) {
            isVisible(false);
            mPlayer.start();
        }
    }

    /**
     * 判断视频是否在播放
     *
     * @return
     */
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    /**
     * 暂停播放
     */
    public void onPause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    /**
     * 停止播放
     */
    public void onStop() {
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 释放MediaPlayer
     */
    public void onDestroy() {
        Log.v(TAG, "onDestroy");
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (listener != null) {
                listener.onCompletion(mp);
            }
        }
    };

    private MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (listener != null) {
                listener.onPrepared(mp);
            }

        }
    };

    MediaPlayer.OnVideoSizeChangedListener sizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            if (listener != null) {
                listener.onVideoSizeChanged(mp, width, height);
            }

        }
    };

    public void setOnMediaListener(OnMediaListener listener) {
        this.listener = listener;
    }

    public interface OnMediaListener {
        void onPrepared(MediaPlayer mp);

        void onVideoSizeChanged(MediaPlayer mp, int width, int height);

        void onCompletion(MediaPlayer mp);
    }

    /**
     * 适应控件的高度或宽度显示视频的大小
     */
    public void onAdaptiveSize() {
        if (mPlayer != null && mSurface != null) {
            int videoWidth = mPlayer.getVideoWidth();
            int videoHeight = mPlayer.getVideoHeight();

            int surfaceViewWidth = mSurface.getWidth();
            int surfaceViewHeight = mSurface.getHeight();

            Log.d(TAG, "Video Width: " + videoWidth + ", " + "Video Height: " + videoHeight);
            Log.d(TAG, "Surface Width: " + surfaceViewWidth + ", " + "Surface Height: " + surfaceViewHeight);
//        mHolder.setFixedSize(mSurfaceViewWidth, mSurfaceViewHeight);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int marginHorizontal, marginVertical;
            if (videoWidth / videoHeight > surfaceViewWidth / surfaceViewHeight) { //适应控件的宽度显示视频的大小
                marginHorizontal = 0;
                marginVertical = (surfaceViewHeight - surfaceViewWidth * videoHeight / videoWidth) / 2;
            } else {                                                               //适应控件的高度显示视频的大小
                marginHorizontal = (surfaceViewWidth - surfaceViewHeight * videoWidth / videoHeight) / 2;
                marginVertical = 0;
            }
            lp.setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mSurface.setLayoutParams(lp);
        }
    }

    /**
     * 全屏控件的高度显示视频的大小
     */
    public void onFullScreenSize() {
        if (mPlayer != null && mSurface != null) {
            int videoWidth = mPlayer.getVideoWidth();
            int videoHeight = mPlayer.getVideoHeight();
            int mSurfaceViewWidth = ScreenUtil.getScreenWidth(mSurface.getContext());
            int mSurfaceViewHeight = ScreenUtil.getScreenHeight(mSurface.getContext());
            Log.d(TAG, "Screen Width: " + mSurfaceViewWidth + ", " + "Screen Height: " + mSurfaceViewHeight);
//            mHolder.setFixedSize(mSurfaceViewWidth, mSurfaceViewHeight);
            int w = mSurfaceViewHeight * videoWidth / videoHeight;
            int margin = (mSurfaceViewWidth - w) / 2;
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(margin, 0, margin, 0);
            mSurface.setLayoutParams(lp);
        }

    }

    /**
     * 获取视频缩略图
     *
     * @param filePath
     * @param kind
     * @return 缩略图的Bitmap
     */
    public Bitmap getVideoThumbnail(String filePath, int kind) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (filePath.startsWith("http://") || filePath.startsWith("https://") || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }
        if (bitmap == null) {
            return null;
        }
        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }
        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 96, 96, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }
}
