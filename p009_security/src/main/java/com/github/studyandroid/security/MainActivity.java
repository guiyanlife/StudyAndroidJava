package com.github.studyandroid.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.security.ui.activity.LockConfigAsymEncActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvAsymEncBtLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivBack = findViewById(R.id.iv_back);
        tvAsymEncBtLock = findViewById(R.id.tv_asym_encrypt_btlock_conf);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvAsymEncBtLock.setOnClickListener(this);
    }

    private void doNetWork() {
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_asym_encrypt_btlock_conf:
                Intent intentGL20RenderBitmap = new Intent(this, LockConfigAsymEncActivity.class);
                intentGL20RenderBitmap.putExtra("title", tvAsymEncBtLock.getText());
                startActivity(intentGL20RenderBitmap);
                break;
            default:
                break;
        }
    }

}
