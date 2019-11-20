package com.github.studyandroid.okhttp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.okhttp.ui.activity.ItemActivity;

/**
 * Copyright (c) 2019 GitHub, Inc.
 * Description: Main Activity
 * Author(s): Gui Yan (guiyanwork@163.com)
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView mIvExit;
    private TextView tvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        mIvExit = findViewById(R.id.iv_exit);
        tvItem = findViewById(R.id.tv_item);
    }

    private void setListener() {
        mIvExit.setOnClickListener(this);
        tvItem.setOnClickListener(this);
    }

    private void doNetWork() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_exit:
                finish();
                break;
            case R.id.tv_item:
                Intent intentItem = new Intent(this, ItemActivity.class);
                intentItem.putExtra(ItemActivity.INTENT_KEY_TITLE, tvItem.getText());
                startActivity(intentItem);
                break;
            default:
                break;
        }
    }
}
