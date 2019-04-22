package com.github.studyandroid.network;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.network.ui.activity.HiosActivity;
import com.github.studyandroid.network.ui.activity.RetrofitActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvRetrofit, tvHios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivBack = findViewById(R.id.iv_back);
        tvRetrofit = findViewById(R.id.tv_retrofit);
        tvHios = findViewById(R.id.tv_hios);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvRetrofit.setOnClickListener(this);
        tvHios.setOnClickListener(this);
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
            case R.id.tv_retrofit:
                Intent intentRetrofit = new Intent(this, RetrofitActivity.class);
                intentRetrofit.putExtra("title", tvRetrofit.getText());
                startActivity(intentRetrofit);
                break;
            case R.id.tv_hios:
                Intent intentHios = new Intent(this, HiosActivity.class);
                intentHios.putExtra("title", tvHios.getText());
                startActivity(intentHios);
                break;
            default:
                break;
        }
    }
}
