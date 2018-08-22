package com.github.baselibrary.tagcloud.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.baselibrary.R;

public class FragmentTestTagCloudActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTitle;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        mTvTitle = findViewById(R.id.tv_title_content);
        mIvBack = findViewById(R.id.iv_title_back);
    }

    private void setListener() {
        mIvBack.setOnClickListener(this);
    }

    private void doNetWork() {
        mTvTitle.setText("Using in Fragment");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, TestTagCloudFragment.newInstance())
                .commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_title_back) {
            onBackPressed();
        }
    }

}
