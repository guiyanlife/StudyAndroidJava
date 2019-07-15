package com.github.studyandroid.security.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.security.R;
import com.github.studyandroid.security.infosecurity.LockSecurity;
import com.github.studyandroid.security.infosecurity.LockSecurityImporter;
import com.github.studyandroid.security.util.FileUtil;

import java.util.List;

public class LockConfigAsymEncActivity extends Activity implements View.OnClickListener {
    private static final int MSG_UPDATA_TEXTVIEW = 0;
    private static final String NEWLINE = System.getProperty("line.separator");
    private ImageView ivToolbarBack;
    private TextView tvToolbarContent;

    private TextView mTipInfo;
    private Button mGenLockConfigButton;
    private Button mImportLockConfigButton;
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
        setContentView(R.layout.activity_lock_config_asym_enc);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivToolbarBack = findViewById(R.id.iv_toolbar_back);
        tvToolbarContent = findViewById(R.id.tv_toolbar_content);
        mExitButton = findViewById(R.id.exit_button);
        mGenLockConfigButton = findViewById(R.id.btn_gen_lock_config_info);
        mImportLockConfigButton = findViewById(R.id.btn_import_lock_config_info);
        mTipInfo = findViewById(R.id.tv_lock_config_tip_info);
    }

    private void setListener() {
        ivToolbarBack.setOnClickListener(this);
        mExitButton.setOnClickListener(this);
        mGenLockConfigButton.setOnClickListener(this);
        mImportLockConfigButton.setOnClickListener(this);
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
            case R.id.btn_gen_lock_config_info:
                String tipInfoGen = genLockConfigInfo();
                Message msgGen = new Message();
                msgGen.what = MSG_UPDATA_TEXTVIEW;
                msgGen.obj = tipInfoGen;
                mHandler.sendMessage(msgGen);
                break;
            case R.id.btn_import_lock_config_info:
                String tipInfoInport = importLockConfigInfo();
                Message msgImport = new Message();
                msgImport.what = MSG_UPDATA_TEXTVIEW;
                msgImport.obj = tipInfoInport;
                mHandler.sendMessage(msgImport);
                break;
            default:
                break;
        }
    }

    private String genLockConfigInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("Generate Path:").append(NEWLINE);
        sb.append(LockSecurity.LOCKSECURITY_DAT_GEN_PATH).append(NEWLINE).append(NEWLINE);
        sb.append("Generate Result:").append(NEWLINE);
        boolean result = LockSecurity.genLockSecurityFile(Build.SERIAL, "00:50:56:bf:12:fc", "2580");
        if (!result)
            return sb.append("Failed").toString();
        sb.append("Success").append(NEWLINE).append(NEWLINE);
        sb.append("File Content:").append(NEWLINE);
        String datContent = FileUtil.file2String(LockSecurity.LOCKSECURITY_DAT_GEN_PATH);
        sb.append(datContent);
        return sb.toString();
    }

    private String importLockConfigInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("Import File Path:").append(NEWLINE);
        sb.append(LockSecurity.LOCKSECURITY_DAT_GEN_PATH).append(NEWLINE).append(NEWLINE);
        sb.append("Import Result:").append(NEWLINE);
        String lockSecurityContent = FileUtil.file2String(LockSecurity.LOCKSECURITY_DAT_GEN_PATH);
        LockSecurityImporter lockSecurityImporter = LockSecurityImporter.getInstance();
        boolean result = lockSecurityImporter.importLockSecurityConfig(lockSecurityContent, "2580");
        if (!result)
            return sb.append("Failed").toString();
        sb.append("Success").append(NEWLINE).append(NEWLINE);
        sb.append("MAC List:").append(NEWLINE);
        List<String> macs = lockSecurityImporter.getPairedMacList();
        for (String mac : macs) {
            sb.append(mac).append(NEWLINE);
        }
        sb.deleteCharAt(sb.length()-NEWLINE.length());
        return sb.toString();
    }
}
