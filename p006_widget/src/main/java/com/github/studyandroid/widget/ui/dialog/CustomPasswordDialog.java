package com.github.studyandroid.widget.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.studyandroid.widget.R;
import com.github.studyandroid.widget.ui.view.CustomPasswordView;

public class CustomPasswordDialog extends Dialog implements View.OnClickListener {
    private Button mBtnNo, mBtnYes;
    private CustomPasswordView mCustomPasswordView;
    private TextView mTvHint;
    private OnClickListenerInterface mYesButtonListener, mNoButtonListener;
    boolean mFlagRightPass = true;

    public CustomPasswordDialog(Context context) {
        super(context);
    }

    public CustomPasswordDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomPasswordDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reboot_password);
        initView();
        initDate();
    }

    private void initView() {  //初始化视图
        mBtnNo = findViewById(R.id.btn_no);
        mBtnYes = findViewById(R.id.btn_yes);
        mCustomPasswordView = findViewById(R.id.et_password);
        mTvHint = findViewById(R.id.tv_hint);
    }

    private void initDate() {  //注册监听器
        mBtnNo.setOnClickListener(this);
        mBtnYes.setOnClickListener(this);
        mCustomPasswordView.setInputStartListener(new CustomPasswordView.InputStartListener() {
            @Override
            public void startInput() {
                mTvHint.setVisibility(View.INVISIBLE);
            }
        });
        setCanceledOnTouchOutside(true); //触摸dialog外围区域消失
        //getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG); //锁屏时显示的对话框
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                String password_input = mCustomPasswordView.getTextContent();
                if (!TextUtils.isEmpty(password_input) && (password_input.length() == 4)) {
                    if (!mFlagRightPass) { //输入密码错误，UI显示提示
                        mTvHint.setVisibility(View.VISIBLE);
                        mCustomPasswordView.clearAllText();
                    } else { //输入密码正确，执行正确的功能
                        dismiss();
                        if (mYesButtonListener != null)
                            mYesButtonListener.onClick();
                        break;
                    }
                } else { //输入密码错误，UI显示提示
                    mTvHint.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_no:
                dismiss();
                if (mNoButtonListener != null)
                    mNoButtonListener.onClick();
                break;
            default:
                break;
        }
    }

    public CustomPasswordDialog setYesButton(OnClickListenerInterface event) {
        mYesButtonListener = event;
        return this;
    }

    public CustomPasswordDialog setNoButton(OnClickListenerInterface event) {
        mNoButtonListener = event;
        return this;
    }

    public interface OnClickListenerInterface {
        void onClick();
    }
}
