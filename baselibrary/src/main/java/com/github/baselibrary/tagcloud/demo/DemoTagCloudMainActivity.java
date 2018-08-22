package com.github.baselibrary.tagcloud.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.baselibrary.R;
import com.github.baselibrary.tagcloud.base.TagCloudView;

public class DemoTagCloudMainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTitle;
    private ImageView mIvBack;
    private TagCloudView tagCloudView;
    private TextTagsAdapter textTagsAdapter;
    private ViewTagsAdapter viewTagsAdapter;
    private VectorTagsAdapter vectorTagsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tag_cloud);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        mTvTitle = findViewById(R.id.tv_title_content);
        mIvBack = findViewById(R.id.iv_title_back);
        tagCloudView = (TagCloudView) findViewById(R.id.tag_cloud);
    }

    private void setListener() {
        mIvBack.setOnClickListener(this);
    }

    private void doNetWork() {
        mTvTitle.setText("DemoTagCloudMainActivity");
        textTagsAdapter = new TextTagsAdapter(new String[20]);
        viewTagsAdapter = new ViewTagsAdapter();
        vectorTagsAdapter = new VectorTagsAdapter();

        tagCloudView.setAdapter(textTagsAdapter);

        findViewById(R.id.tag_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(textTagsAdapter);
            }
        });

        findViewById(R.id.tag_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(viewTagsAdapter);
            }
        });

        findViewById(R.id.tag_vector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(vectorTagsAdapter);
            }
        });

        findViewById(R.id.test_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoTagCloudMainActivity.this, FragmentTestTagCloudActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_title_back) {
            onBackPressed();
        }
    }
}
