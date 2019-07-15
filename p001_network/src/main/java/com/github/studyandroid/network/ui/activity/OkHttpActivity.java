package com.github.studyandroid.network.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.network.R;

public class OkHttpActivity extends Activity implements View.OnClickListener {
    private static final int MSG_UPDATA_TEXTVIEW = 0;
    private ImageView ivToolbarBack;
    private TextView tvToolbarContent;
    private TextView mResponseTextview;
    private Button mBtnSyncGetText;
    private Button mBtnSyncGetJson;
    private Button mBtnSyncPostJson;
    private Button mBtnSyncPostFile;
    private Button mBtnAsyncGetGson;
    private Button mBtnAsyncPostGson;
    private Button mBtnAsyncGetFile;
    private Button mExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivToolbarBack = findViewById(R.id.iv_toolbar_back);
        tvToolbarContent = findViewById(R.id.tv_toolbar_content);
        mResponseTextview = findViewById(R.id.tv_okhttp_response);
        mBtnSyncGetText = findViewById(R.id.btn_okhttp_sync_get_text);
        mBtnSyncGetJson = findViewById(R.id.btn_okhttp_sync_get_json);
        mBtnSyncPostJson = findViewById(R.id.btn_okhttp_sync_post_json);
        mBtnSyncPostFile = findViewById(R.id.btn_okhttp_sync_post_file);
        mBtnAsyncGetGson = findViewById(R.id.btn_okhttp_async_get_gson);
        mBtnAsyncPostGson = findViewById(R.id.btn_okhttp_async_post_gson);
        mBtnAsyncGetFile = findViewById(R.id.btn_okhttp_async_get_file);
        mExitButton = findViewById(R.id.exit_button);
    }

    private void setListener() {
        ivToolbarBack.setOnClickListener(this);
        mBtnSyncGetText.setOnClickListener(this);
        mBtnSyncGetJson.setOnClickListener(this);
        mBtnSyncPostJson.setOnClickListener(this);
        mBtnSyncPostFile.setOnClickListener(this);
        mBtnAsyncGetGson.setOnClickListener(this);
        mBtnAsyncPostGson.setOnClickListener(this);
        mBtnAsyncGetFile.setOnClickListener(this);
        mExitButton.setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        ivToolbarBack.setVisibility(View.VISIBLE);
        tvToolbarContent.setText(intent.getStringExtra("title"));

        mResponseTextview.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_okhttp_sync_get_text:
                break;
            case R.id.btn_okhttp_sync_get_json:
                break;
            case R.id.btn_okhttp_sync_post_json:
                break;
            case R.id.btn_okhttp_sync_post_file:
                break;
            case R.id.btn_okhttp_async_get_gson:
                break;
            case R.id.btn_okhttp_async_post_gson:
                break;
            case R.id.btn_okhttp_async_get_file:
                break;
            case R.id.exit_button:
                finish();
                break;
            case R.id.iv_toolbar_back:
                finish();
                break;
            default:
                break;
        }
    }
}
