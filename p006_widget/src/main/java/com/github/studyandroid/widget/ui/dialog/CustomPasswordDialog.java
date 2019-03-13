package com.github.studyandroid.widget.ui.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.studyandroid.widget.R;

/**
 * Copyright (c) 2018 Beeboxes, Inc.
 * Description:
 */

public class CustomPasswordDialog extends RelativeLayout {

    private LinearLayout containerEt;
    private EditText mEditText;
    private int mEtNumber;
    private int mEtWidth, mEtTextSize, mEtTextColor;
    private Drawable mEtDividerDrawable, mEtBackgroundDrawableFocus, mEtBackgroundDrawableNormal;
    private TextView[] mTextViews;
    private OnKeyListener mOnkeyListener;
    private String mEtContent;
    private int mEtContentBeforeChangeLength;
    private boolean mClearAllFlag = false;

    public CustomPasswordDialog(Context context) {
        this(context, null);
    }

    public CustomPasswordDialog(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPasswordDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initTextViews(getContext(), mEtNumber, mEtWidth, mEtDividerDrawable, mEtTextSize,
                mEtTextColor);
        initEtContainer(mTextViews);
        setListener();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.password_layout, this);
        containerEt = (LinearLayout) this.findViewById(R.id.container_et);
        mEditText = (EditText) this.findViewById(R.id.et);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomPasswordDialog, defStyleAttr, 0);
        mEtNumber = typedArray.getInteger(R.styleable.CustomPasswordDialog_icv_et_number, 1);
        mEtWidth = typedArray.getDimensionPixelSize(R.styleable.CustomPasswordDialog_icv_et_width,
                42);
        mEtDividerDrawable = typedArray.getDrawable(
                R.styleable.CustomPasswordDialog_icv_et_divider_drawable);
        mEtTextSize = typedArray.getDimensionPixelSize(
                R.styleable.CustomPasswordDialog_icv_et_text_size, 16);
        mEtTextColor = typedArray.getColor(R.styleable.CustomPasswordDialog_icv_et_text_color,
                Color.WHITE);
        mEtBackgroundDrawableFocus = typedArray.getDrawable(
                R.styleable.CustomPasswordDialog_icv_et_bg_focus);
        mEtBackgroundDrawableNormal = typedArray.getDrawable(
                R.styleable.CustomPasswordDialog_icv_et_bg_normal);
        //释放资源
        typedArray.recycle();
    }

    private void initTextViews(Context context, int etNumber, int etWidth,
                               Drawable etDividerDrawable, float etTextSize, int etTextColor) {
        // 设置 editText 的输入长度
        //将光标隐藏
        mEditText.setCursorVisible(false);
        mEditText.setVisibility(View.VISIBLE);
        //最大输入长度
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(etNumber)});
        // 设置分割线的宽度
        if (etDividerDrawable != null) {
            etDividerDrawable.setBounds(0, 0, 32, 156);
            containerEt.setDividerDrawable(etDividerDrawable);
        }
        mTextViews = new TextView[etNumber];
        for (int i = 0; i < mTextViews.length; i++) {
            TextView textView = new EditText(context);
            textView.setTextSize(etTextSize);
            textView.setTextColor(etTextColor);
            textView.setWidth(etWidth);
            textView.setHeight(etWidth);
            if (i == 0) {
                textView.setBackground(mEtBackgroundDrawableFocus);
            } else {
                textView.setBackground(mEtBackgroundDrawableNormal);
            }
            textView.setGravity(Gravity.CENTER);
            textView.setFocusable(false);
            mTextViews[i] = textView;
        }
    }

    private void initEtContainer(TextView[] mTextViews) {
        for (int i = 0; i < mTextViews.length; i++) {
            containerEt.addView(mTextViews[i]);
        }
    }

    private void setListener() {
        // 监听输入内容
        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mEtContentBeforeChangeLength = mEditText.getText().length();
                if (mInputStartListener != null && (mEtContentBeforeChangeLength == 0)){
                    mInputStartListener.startInput();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mEditText.getText().length() > mEtContentBeforeChangeLength) {
                    mClearAllFlag = false;
                    mEtContent = editable.toString();
                    mEtNumber = mEtContent.length();
                    if (!mEtContent.equals("")) {
                        setText(mEtContent.length());
                    }
                }
                /*else if (mEditText.getText().length() >= 0) {
                    if (!mClearAllFlag) {
                        onKeyDelete();
                    }
                }*/
            }
        });
        mOnkeyListener = new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onKeyDelete();
                    return true;
                }
                return false;
            }
        };

        // 监听删除按键
        mEditText.setOnKeyListener(mOnkeyListener);
    }

    // 给TextView 设置文字
    public void setText(int length) {
        for (int i = 0; i < length; i++) {
            mTextViews[i].setText(R.string.dialog_password);
        }
        if (length == 4){
            if (inputCompleteListener != null) {
                inputCompleteListener.inputComplete();
            }
        }
    }


    // 监听删除
    public void onKeyDelete() {
        if (inputCompleteListener != null) {
            inputCompleteListener.deleteContent();
        }
        for (int i = mTextViews.length - 1; i >= 0; i--) {
            TextView tv = mTextViews[i];
            if (!tv.getText().toString().trim().equals("")) {
                tv.setText("");
                mEditText.getText().delete(i,i+1);
                mEtContent = mEditText.getText().toString();
                tv.setBackground(mEtBackgroundDrawableFocus);
                if (i < mEtNumber - 1) {
                    mTextViews[i + 1].setBackground(mEtBackgroundDrawableNormal);
                }
                break;
            }
        }

    }

    /**
     * 获取输入文本
     */
    public String getTextContent() {
        return mEtContent;
    }

    /**
     * 删除所有内容
     */
    public void clearAllText() {
        for (int i = 0; i < mTextViews.length; i++) {
            if (i == 0) {
                mTextViews[i].setBackground(mEtBackgroundDrawableFocus);
            } else {
                mTextViews[i].setBackground(mEtBackgroundDrawableNormal);
            }
            mTextViews[i].setText("");
        }
        mEditText.setText("");
        mEtContent = null;
        mClearAllFlag = true;
        if (inputCompleteListener != null) {
            inputCompleteListener.deleteContent();
        }

    }


    /**
     * 获取输入的位数
     */
    public int getTextCount() {
        return mEtNumber;
    }


    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener {
        void inputComplete();

        void deleteContent();
    }

    private InputStartListener mInputStartListener;

    public void setInputStartListener (InputStartListener inputStartListener){
        mInputStartListener = inputStartListener;
    }
    public interface InputStartListener{
        void startInput();
    }

}
