package com.github.studyandroid.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.security.ui.activity.LicenseSignVerifyActivity;
import com.github.studyandroid.security.ui.activity.LockConfigAsymEncActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvAsymEncBtLock;
    private TextView tvSignVerifyLicense;

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
        tvSignVerifyLicense  = findViewById(R.id.tv_sign_verify_license);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvAsymEncBtLock.setOnClickListener(this);
        tvSignVerifyLicense.setOnClickListener(this);
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
                Intent intentLockConfigAsymEnc = new Intent(this, LockConfigAsymEncActivity.class);
                intentLockConfigAsymEnc.putExtra("title", tvAsymEncBtLock.getText());
                startActivity(intentLockConfigAsymEnc);
                break;
            case R.id.tv_sign_verify_license:
                Intent intentLicenseSignVerify = new Intent(this, LicenseSignVerifyActivity.class);
                intentLicenseSignVerify.putExtra("title", tvSignVerifyLicense.getText());
                startActivity(intentLicenseSignVerify);
                break;
            default:
                break;
        }
    }

}
