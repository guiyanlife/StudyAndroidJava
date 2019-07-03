package com.github.studyandroid.opengl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.studyandroid.opengl.ui.activity.GL20RenderBitmapActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvGL20RenderBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivBack = findViewById(R.id.iv_back);
        tvGL20RenderBitmap = findViewById(R.id.tv_gl20_render_bitmap);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvGL20RenderBitmap.setOnClickListener(this);
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
            case R.id.tv_gl20_render_bitmap:
                Intent intentGL20RenderBitmap = new Intent(this, GL20RenderBitmapActivity.class);
                intentGL20RenderBitmap.putExtra("title", tvGL20RenderBitmap.getText());
                startActivity(intentGL20RenderBitmap);
                break;
            default:
                break;
        }
    }

}
