package com.github.studyandroid.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvJbdt, tvJbszdt, tvSzybp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivBack = findViewById(R.id.iv_back);
        tvJbdt = findViewById(R.id.tv_amap_jbdt);
        tvJbszdt = findViewById(R.id.tv_wmap_jbszdt);
        tvSzybp = findViewById(R.id.tv_wmap_szybp);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvJbdt.setOnClickListener(this);
        tvJbszdt.setOnClickListener(this);
        tvSzybp.setOnClickListener(this);
    }

    private void doNetWork() {
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_amap_jbdt:
                intent = new Intent("hs.act.github.phone.AmapBaseMapActivity");
                intent.putExtra("title", getResources().getString(R.string.amap_jbdt));
                startActivity(intent);
                break;
            case R.id.tv_wmap_jbszdt:
                intent = new Intent("hs.act.github.phone.WmapBaseMapActivity");
                intent.putExtra("title", getResources().getString(R.string.wmap_jbszdt));
                startActivity(intent);
                break;
            case R.id.tv_wmap_szybp:
                intent = new Intent("hs.act.github.phone.WmapYbpActivity");
                intent.putExtra("title", getResources().getString(R.string.wmap_szybp));
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
