package com.github.studyandroid.animation.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.animation.R;

public class DeviceStateDialog extends Dialog {
    private TextView mTvDeviceType, mTvDeviceState;
    private ImageView mIvDeviceClose;
    private String mStrDeviceType, mStrDeviceState;

    public DeviceStateDialog(Context context) {
        super(context, R.style.TagCloudDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tagcloud_dialog_device_state);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        mTvDeviceType = findViewById(R.id.tv_tagcloud_dialog_device_type);
        mTvDeviceState = findViewById(R.id.iv_tagcloud_dialog_device_state);
        mIvDeviceClose = findViewById(R.id.iv_tagcloud_dialog_device_close);
    }

    private void setListener() {
        mIvDeviceClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void doNetWork() {
        setCanceledOnTouchOutside(false); //触摸dialog外围区域不消失
        if (mStrDeviceType != null && mStrDeviceType.length() > 0)
            mTvDeviceType.setText(mStrDeviceType);
        if (mStrDeviceState != null && mStrDeviceState.length() > 0)
            mTvDeviceState.setText(mStrDeviceState);
    }

    public DeviceStateDialog setDeviceType(String type) {
        mStrDeviceType = type;
        return this;
    }

    public DeviceStateDialog setDeviceState(String state) {
        mStrDeviceState = state;
        return this;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
