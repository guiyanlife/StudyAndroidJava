package com.github.studyandroid.app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.app.R;

/**
 * Copyright (c) 2019 GitHub, Inc.
 * Description: Item Activity
 * Author(s): Gui Yan (guiyanwork@163.com)
 */
public class ItemActivity extends Activity implements View.OnClickListener {
    public static final String INTENT_KEY_TITLE = "title";
    private ImageView mIvToolbarBack;
    private TextView mTvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        mIvToolbarBack = findViewById(R.id.iv_toolbar_back);
        mTvToolbarTitle = findViewById(R.id.tv_toolbar_title);
    }

    private void setListener() {
        mIvToolbarBack.setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        mTvToolbarTitle.setText(intent.getStringExtra(INTENT_KEY_TITLE));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            default:
                break;
        }
    }
}
