package com.github.studyandroid.animation.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.animation.R;

public class DeviceXwjDialog extends Dialog {
    private Context mContext;
    private TextView mTvDeviceState, mTvDeviceMode, mTvDeviceSdsw, mTvDeviceYsl, mTvDeviceGlj, mTvDeviceRhy;
    private ImageView mIvDeviceClose, mIvDeviceRhyBh, mIvDeviceGljBh;
    private String mStrDeviceState, mStrDeviceMode, mStrDeviceSdsw, mStrDeviceYsl, mStrDeviceGlj, mStrDeviceRhy;
    private OnClickListenerInterface mGljBhButtonListener, mRhyBhButtonListener;

    public DeviceXwjDialog(Context context) {
        super(context, R.style.TagCloudDialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tagcloud_dialog_device_xwj);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        mTvDeviceState = findViewById(R.id.tv_tagcloud_dialog_device_state);
        mTvDeviceMode = findViewById(R.id.tv_tagcloud_dialog_device_mode);
        mTvDeviceSdsw = findViewById(R.id.tv_tagcloud_dialog_device_sdsw);
        mTvDeviceYsl = findViewById(R.id.tv_tagcloud_dialog_device_ysl);
        mTvDeviceGlj = findViewById(R.id.tv_tagcloud_dialog_device_glj);
        mTvDeviceRhy = findViewById(R.id.tv_tagcloud_dialog_device_rhy);
        mIvDeviceGljBh = findViewById(R.id.iv_tagcloud_dialog_device_glj_bh);
        mIvDeviceRhyBh = findViewById(R.id.iv_tagcloud_dialog_device_rhy_bh);
        mIvDeviceClose = findViewById(R.id.iv_tagcloud_dialog_device_close);
    }

    private void setListener() {
        mIvDeviceClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (mGljBhButtonListener != null) {
            mIvDeviceGljBh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGljBhButtonListener.onClick();
                }
            });
        }
        if (mGljBhButtonListener != null) {
            mIvDeviceRhyBh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRhyBhButtonListener.onClick();
                }
            });
        }
    }

    private void doNetWork() {
        setCanceledOnTouchOutside(false); //触摸dialog外围区域不消失
        if (mStrDeviceState != null && mStrDeviceState.length() > 0)
            mTvDeviceState.setText(mStrDeviceState);
        if (mStrDeviceMode != null && mStrDeviceMode.length() > 0)
            mTvDeviceMode.setText(mStrDeviceMode);
        if (mStrDeviceSdsw != null && mStrDeviceSdsw.length() > 0)
            mTvDeviceSdsw.setText(mStrDeviceSdsw);
        if (mStrDeviceYsl != null && mStrDeviceYsl.length() > 0)
            mTvDeviceYsl.setText(mStrDeviceYsl);
        if (mStrDeviceGlj != null && mStrDeviceGlj.length() > 0)
            mTvDeviceGlj.setText(mStrDeviceGlj);
        if (mStrDeviceRhy != null && mStrDeviceRhy.length() > 0)
            mTvDeviceRhy.setText(mStrDeviceRhy);
    }

    public DeviceXwjDialog setDeviceState(String state) {
        this.mStrDeviceState = state;
        return this;
    }

    public DeviceXwjDialog setDeviceMode(String mode) {
        this.mStrDeviceMode = String.format(mContext.getResources().getString(R.string.tagcloud_gzms_var), mode);
        return this;
    }

    public DeviceXwjDialog setDeviceSdsw(String sdsw) {
        this.mStrDeviceSdsw = String.format(mContext.getResources().getString(R.string.tagcloud_sdsw_var), sdsw);
        return this;
    }

    public DeviceXwjDialog setDeviceYsl(String ysl) {
        this.mStrDeviceYsl = String.format(mContext.getResources().getString(R.string.tagcloud_ysl_var), ysl);
        return this;
    }

    public DeviceXwjDialog setDeviceGlj(String glj) {
        this.mStrDeviceGlj = String.format(mContext.getResources().getString(R.string.tagcloud_glj_var), glj);
        return this;
    }

    public DeviceXwjDialog setDeviceRhy(String rhy) {
        this.mStrDeviceRhy = String.format(mContext.getResources().getString(R.string.tagcloud_rhy_var), rhy);
        return this;
    }

    public DeviceXwjDialog setGljBhButton(OnClickListenerInterface event) {
        mGljBhButtonListener = event;
        return this;
    }

    public DeviceXwjDialog setRhyBhButton(OnClickListenerInterface event) {
        mRhyBhButtonListener = event;
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
