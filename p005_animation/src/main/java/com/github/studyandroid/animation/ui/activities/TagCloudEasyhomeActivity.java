package com.github.studyandroid.animation.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.baselibrary.tagcloud.base.TagCloudView;
import com.github.studyandroid.animation.R;
import com.github.studyandroid.animation.adapter.TagCloudViewTagAdapter;
import com.github.studyandroid.animation.domain.EasyhomeDeviceData;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TagCloudEasyhomeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTitle;
    private ImageView mIvBack;
    private TagCloudView tagCloudView;
    private TagCloudViewTagAdapter viewTagsAdapter;

    private static final int DEALY_TIME = 1;  //定时延时间隔 1s
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;  //定时器任务
    private int mWorkTime = 0;
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            viewTagsAdapter.setItemDeviceStateInfo(4, "烤箱\n已工作" + mWorkTime + "秒");
            viewTagsAdapter.notifyUpdateData();
            mWorkTime++;
        }
    };

    private void findView() {
        mTvTitle = findViewById(R.id.tv_toolbar_content);
        mIvBack = findViewById(R.id.iv_toolbar_back);
        tagCloudView = findViewById(R.id.tag_cloud_easyhome);
    }

    private void setListener() {
        mIvBack.setOnClickListener(this);
    }

    private void doNetWork() {
        mTvTitle.setText("Easyhome Device Cloud");
        viewTagsAdapter = new TagCloudViewTagAdapter(this, EasyhomeDeviceData.get());
        tagCloudView.setAdapter(viewTagsAdapter);
        viewTagsAdapter.notifyDataSetChanged();

        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(timerRunnable, 0, DEALY_TIME, TimeUnit.SECONDS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_cloud_easyhome);
        findView();
        setListener();
        doNetWork();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scheduledThreadPoolExecutor != null)
            scheduledThreadPoolExecutor.shutdown();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_toolbar_back) {
            onBackPressed();
        }
    }
}
