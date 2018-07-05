package com.github.commonlibs.libwebview.base;

import android.os.Bundle;

import com.github.commonlibs.libwebview.R;


public class WebViewPartActivity extends WebViewActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_webview_part_layout);
        findview();
        onclickListener();
        setupWebView();

        url = "http://liangxiao.blog.51cto.com/";
        loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
