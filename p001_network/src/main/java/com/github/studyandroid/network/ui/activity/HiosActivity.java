package com.github.studyandroid.network.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.commonlibs.libwebview.hios2.HiosHelper;
import com.github.studyandroid.network.R;

public class HiosActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MSG_INTRODUCE = 0;
    private ImageView ivToolbarBack;
    private TextView tvToolbarContent;
    private TextView mTvContent;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INTRODUCE:
                    mTvContent.setText("Demo1:\n" +
                            "    普通的Activity包名未HiosRegister跳转\n\n" +
                            "Demo2:\n" +
                            "    普通的ActivityAction跳转\n\n" +
                            "Demo3:\n" +
                            "    继承webviewactivity请求接口\n\n" +
                            "Demo4:\n" +
                            "    继承webviewactivity不请求接口\n\n" +
                            "Demo5:\n" +
                            "    继承webviewactivity调用JS按钮\n\n" +
                            "Demo6:\n" +
                            "    嵌套webview布局\n\n" +
                            "Demo7:\n" +
                            "    进入WebView测试界面");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hios);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivToolbarBack = findViewById(R.id.iv_toolbar_back);
        tvToolbarContent = findViewById(R.id.tv_toolbar_content);
        mTvContent = findViewById(R.id.tv_content);
    }

    private void setListener() {
        ivToolbarBack.setOnClickListener(this);
        findViewById(R.id.demo1_button).setOnClickListener(this);
        findViewById(R.id.demo2_button).setOnClickListener(this);
        findViewById(R.id.demo3_button).setOnClickListener(this);
        findViewById(R.id.demo4_button).setOnClickListener(this);
        findViewById(R.id.demo5_button).setOnClickListener(this);
        findViewById(R.id.demo6_button).setOnClickListener(this);
        findViewById(R.id.demo7_button).setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        ivToolbarBack.setVisibility(View.VISIBLE);
        tvToolbarContent.setText(intent.getStringExtra("title"));

        Message msg = new Message();
        msg.what = MSG_INTRODUCE;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.demo1_button:
                // TODO 普通的Activity包名未HiosRegister跳转
                HiosHelper.resolveAd(HiosActivity.this, HiosActivity.this, "hios://com.github.commonlibs.libwebview.activity.NoHiosMainActivity?act={i}1&sku_id={s}2A&category_id={s}3B");
                break;
            case R.id.demo2_button:
                // TODO 普通的ActivityAction跳转
                HiosHelper.resolveAd(HiosActivity.this, HiosActivity.this, "hios://hs.ac.github.NoHiosMainActivity{act}?act={i}1&sku_id={s}2A&category_id={s}3B");
                break;
            case R.id.demo3_button:
                // TODO 继承webviewactivity请求接口
                HiosHelper.click(HiosActivity.this, HiosActivity.this, "1", "");
                break;
            case R.id.demo4_button:
                // TODO 继承webviewactivity不请求接口
                HiosHelper.resolveAd(HiosActivity.this, HiosActivity.this, "http://liangxiao.blog.51cto.com/");
                break;
            case R.id.demo5_button:
                // TODO 继承webviewactivity调用JS按钮
                HiosHelper.resolveAd(HiosActivity.this, HiosActivity.this, "file:///android_asset/demo/web.html");
                break;
            case R.id.demo6_button:
                // TODO 嵌套webview布局
                HiosHelper.resolveAd(HiosActivity.this, HiosActivity.this, "hios://ad.github.web.page.part{act}");
                break;
            case R.id.demo7_button:
                // TODO 进入WebView测试界面
                HiosHelper.resolveAd(HiosActivity.this, HiosActivity.this, "hios://hs.ac.github.webview.DemoWebviewMainActivity{act}");
                break;
            case R.id.iv_toolbar_back:
                finish();
                break;
            default:
                break;
        }
    }
}
