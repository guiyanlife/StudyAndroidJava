package com.github.baselibrary.ringutil;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.github.baselibrary.R;
import com.github.baselibrary.widget.SwitchButton;


public class RingActivity extends AppCompatActivity {
    private static final String TAG = RingActivity.class.getSimpleName();
    private FrameLayout mFlRingBg;
    private SeekBar mNoticeSeekBar;    //通知音效进度条
    private SeekBar mMediaSeekBar;     //多媒体音效进度条
    private SeekBar mAlarmSeekBar;     //闹铃音效进度条
    private SwitchButton toggle_quiet; //静音
    private RadioGroup mRgRingEffect;  //音效选择
    private ImageView mIvExit;         //退出图标

    private RingActivityControl mRingActivityControl;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mRingActivityControl = new RingActivityControl(this, handler);
        findView();
        setListener();
    }

    private int getLayoutId() {
        return R.layout.activity_ring;
    }


    private void findView() {
        mFlRingBg = findViewById(R.id.tv_ring_bg);
        mIvExit = findViewById(R.id.iv_ring_exit);
        toggle_quiet = findViewById(R.id.sb_mute_mode);
        mNoticeSeekBar = findViewById(R.id.sb_vol_notification);
        mMediaSeekBar = findViewById(R.id.sb_vol_media);
        mAlarmSeekBar = findViewById(R.id.sb_vol_alarm);
        mRgRingEffect = findViewById(R.id.rg_ring_effect);
        expandViewTouchDelegate(mIvExit, 10, 10, 10,10);
        //公共控制类的使用
        mRingActivityControl.ringRegisterVolSeekbar(RingActivityControl.SOUND_NOTIFICATION, mNoticeSeekBar);
        mRingActivityControl.ringRegisterVolSeekbar(RingActivityControl.SOUND_MUSIC, mMediaSeekBar);
        mRingActivityControl.ringRegisterVolSeekbar(RingActivityControl.SOUND_ALARM, mAlarmSeekBar);
        mRingActivityControl.ringRegisterMuteSwitchbutton(toggle_quiet);
        mRingActivityControl.ringRegisterRingEffect(mRgRingEffect, R.id.rb_ring1, R.id.rb_ring2, R.id.rb_ring3);
    }

    private void setListener() {
        //设置背景按键事件监听
        mFlRingBg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //设置退出按键事件监听
        mIvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //公共控制类的使用
        mRingActivityControl.ringSetListener();
    }

    private void doNetWork() {
        //公共控制类的使用
        mRingActivityControl.ringUpdateState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doNetWork();
        //公共控制类的使用
        mRingActivityControl.ringStartListenSystemVol();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //公共控制类的使用
        mRingActivityControl.ringFinishListenSystemVol();
    }

    /**
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    private void expandViewTouchDelegate(final View view, final int top, final int bottom, final int left, final int right) {
        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }
}
