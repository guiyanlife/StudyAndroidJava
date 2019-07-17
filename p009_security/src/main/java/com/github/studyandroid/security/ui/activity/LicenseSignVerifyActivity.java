package com.github.studyandroid.security.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.security.R;

public class LicenseSignVerifyActivity extends Activity implements View.OnClickListener {
    private static final int MSG_UPDATA_TEXTVIEW = 0;
    private static final String NEWLINE = System.getProperty("line.separator");
    private ImageView ivToolbarBack;
    private TextView tvToolbarContent;

    private TextView mTipInfo;
    private Button mGenLicenseButton;
    private Button mImportLicenseButton;
    private Button mExitButton;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATA_TEXTVIEW:
                    mTipInfo.scrollTo(0, 0);
                    mTipInfo.setText((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_sign_verify);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivToolbarBack = findViewById(R.id.iv_toolbar_back);
        tvToolbarContent = findViewById(R.id.tv_toolbar_content);
        mExitButton = findViewById(R.id.exit_button);
        mGenLicenseButton = findViewById(R.id.btn_gen_license_sign_info);
        mImportLicenseButton = findViewById(R.id.btn_import_license_sign_info);
        mTipInfo = findViewById(R.id.tv_license_sign_tip_info);
    }

    private void setListener() {
        ivToolbarBack.setOnClickListener(this);
        mExitButton.setOnClickListener(this);
        mGenLicenseButton.setOnClickListener(this);
        mImportLicenseButton.setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        ivToolbarBack.setVisibility(View.VISIBLE);
        tvToolbarContent.setText(intent.getStringExtra("title"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_button:
                finish();
                break;
            case R.id.iv_toolbar_back:
                finish();
                break;
            case R.id.btn_gen_license_sign_info:
                String tipInfoGen = genLicenseInfo();
                Message msgGen = new Message();
                msgGen.what = MSG_UPDATA_TEXTVIEW;
                msgGen.obj = tipInfoGen;
                mHandler.sendMessage(msgGen);
                break;
            case R.id.btn_import_license_sign_info:
                String tipInfoInport = importLicenseInfo();
                Message msgImport = new Message();
                msgImport.what = MSG_UPDATA_TEXTVIEW;
                msgImport.obj = tipInfoInport;
                mHandler.sendMessage(msgImport);
                break;
            default:
                break;
        }
    }

    private String genLicenseInfo() {
        return "genLicenseInfo";
    }

    private String importLicenseInfo() {
        return "importLicenseInfo";
    }
}
