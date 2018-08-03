package com.github.studyandroid.glide38;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide38);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
    }

    private void setListener() {
        findViewById(R.id.tv_content).setOnClickListener(this);
    }

    private void doNetWork() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content:
                Intent intent = new Intent("hs.ac.github.DemoGlide38BaseMainActivity");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
