package com.github.studyandroid.network.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.network.R;

public class OkHttpActivity extends Activity {
    private static final int MSG_UPDATA_TEXTVIEW = 0;
    private ImageView ivToolbarBack;
    private TextView tvToolbarContent;
    private Button mExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
    }
}
