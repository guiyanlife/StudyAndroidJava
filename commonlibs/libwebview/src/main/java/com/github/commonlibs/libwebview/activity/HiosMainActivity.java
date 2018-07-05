package com.github.commonlibs.libwebview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.commonlibs.libwebview.R;

public class HiosMainActivity extends AppCompatActivity {
    private int mAction; // default 0
    private String mSkuId = ""; // maybe null
    private String mCategoryId = "";

    private TextView mTvResult;
    private ImageView mIvBack;
    private ImageView mIvClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        mAction = getIntent().getIntExtra("act", 0);
        mSkuId = getIntent().getStringExtra("sku_id");
        mCategoryId = getIntent().getStringExtra("category_id");

        Toast.makeText(this, mAction + ", " + mSkuId + ", " + mCategoryId, Toast.LENGTH_LONG).show();

        findView();
        onclickListener();
    }

    private void findView() {
        mIvBack = findViewById(R.id.ic_back);
        mIvClose = findViewById(R.id.close);
        mTvResult = findViewById(R.id.tv_back);
        mIvClose.setVisibility(View.GONE);
    }

    private void onclickListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back
                finish();
            }
        });
    }
}
