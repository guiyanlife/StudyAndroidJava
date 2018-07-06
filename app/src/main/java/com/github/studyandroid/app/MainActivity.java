package com.github.studyandroid.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mIvExit;

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
    }

    private void setListener() {
        mIvExit.setOnClickListener(this);
    }

    private void doNetWork() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_exit:
                finish();
                break;
            default:
                break;
        }
    }
}
