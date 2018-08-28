package com.github.studyandroid.animation.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.animation.R;

public class DevicePbjDialog extends Dialog {
    private Context mContext;
    private TextView mTvDeviceState, mTvDeviceLock, mTvDeviceMode, mTvDeviceSpeed, mTvDeviceTemp, mTvDeviceYypb;
    private ImageView mIvDeviceClose, mIvDeviceYypb;
    private String mStrDeviceState, mStrDeviceLock, mStrDeviceMode, mStrDeviceSpeed, mStrDeviceTemp, mStrDeviceYypb;
    OnClickListenerInterface mYypbButtonListener;

    public DevicePbjDialog(Context context) {
        super(context, R.style.TagCloudDialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tagcloud_dialog_device_pbj);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        mTvDeviceState = findViewById(R.id.iv_tagcloud_dialog_device_state);
        mTvDeviceLock = findViewById(R.id.tv_tagcloud_dialog_device_lock);
        mTvDeviceMode = findViewById(R.id.tv_tagcloud_dialog_device_mode);
        mTvDeviceSpeed = findViewById(R.id.tv_tagcloud_dialog_device_speed);
        mTvDeviceTemp = findViewById(R.id.tv_tagcloud_dialog_device_temp);
        mTvDeviceYypb = findViewById(R.id.tv_tagcloud_dialog_device_yypb);
        mIvDeviceYypb = findViewById(R.id.iv_tagcloud_dialog_device_yypb);
        mIvDeviceClose = findViewById(R.id.iv_tagcloud_dialog_device_close);
    }

    private void setListener() {
        mIvDeviceClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (mYypbButtonListener != null) {
            mIvDeviceYypb.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mYypbButtonListener.onClick();
                }
            });
        }
    }

    private void doNetWork() {
        setCanceledOnTouchOutside(false); //触摸dialog外围区域不消失
        if (mStrDeviceState != null && mStrDeviceState.length() > 0)
            mTvDeviceState.setText(mStrDeviceState);
        if (mStrDeviceLock != null && mStrDeviceLock.length() > 0)
            mTvDeviceLock.setText(mStrDeviceLock);
        if (mStrDeviceMode != null && mStrDeviceMode.length() > 0)
            mTvDeviceMode.setText(mStrDeviceMode);
        if (mStrDeviceSpeed != null && mStrDeviceSpeed.length() > 0)
            mTvDeviceSpeed.setText(mStrDeviceSpeed);
        if (mStrDeviceTemp != null && mStrDeviceTemp.length() > 0)
            mTvDeviceTemp.setText(mStrDeviceTemp);
        if (mStrDeviceYypb != null && mStrDeviceYypb.length() > 0)
            mTvDeviceYypb.setText(mStrDeviceYypb);
    }

    public DevicePbjDialog setDeviceState(String state) {
        mStrDeviceState = state;
        return this;
    }

    public DevicePbjDialog setDeviceLock(String lock) {
        mStrDeviceLock = lock;
        return this;
    }

    public DevicePbjDialog setDeviceMode(String mode) {
        mStrDeviceMode = String.format(mContext.getResources().getString(R.string.tagcloud_pbms_var), mode);
        return this;
    }

    public DevicePbjDialog setDeviceSpeed(String speed) {
        mStrDeviceSpeed = String.format(mContext.getResources().getString(R.string.tagcloud_pbzs_var), speed);
        return this;
    }

    public DevicePbjDialog setDeviceTemp(String temp) {
        mStrDeviceTemp = String.format(mContext.getResources().getString(R.string.tagcloud_jrwd_var), temp);
        return this;
    }

    public DevicePbjDialog setDeviceYypb(String info) {
        mStrDeviceYypb = info;
        return this;
    }

    public DevicePbjDialog setYypbButton(OnClickListenerInterface event) {
        mYypbButtonListener = event;
        return this;
    }

    public interface OnClickListenerInterface {
        public void onClick();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
