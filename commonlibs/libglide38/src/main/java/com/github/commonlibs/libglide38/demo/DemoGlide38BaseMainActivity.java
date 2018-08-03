package com.github.commonlibs.libglide38.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import com.github.commonlibs.libglide38.base.ImageWatcher;
import com.github.commonlibs.libglide38.R;
import com.github.commonlibs.libglide38.base.MessagePicturesLayout;
import com.github.commonlibs.libglide38.base.util.Utils;

public class DemoGlide38BaseMainActivity extends Activity implements MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener {
    private ImageWatcher vImageWatcher;
    private MessagePicturesLayout lPictures;

    List<String> pictureList = Arrays.asList(
            "http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg",
            "http://img.my.csdn.net/uploads/201701/06/1483664940_3308.jpg",
            "http://img.my.csdn.net/uploads/201701/06/1483664927_3920.png",
            "http://img.my.csdn.net/uploads/201701/06/1483664926_8360.png",
            "http://img.my.csdn.net/uploads/201701/06/1483664926_6184.png",
            "http://img.my.csdn.net/uploads/201701/06/1483664925_8382.png",
            "http://img.my.csdn.net/uploads/201701/06/1483664925_2087.jpg",
            "http://img.my.csdn.net/uploads/201701/06/1483664777_5730.png",
            "http://img.my.csdn.net/uploads/201701/06/1483664741_1378.jpg",
            "http://img.my.csdn.net/uploads/201701/06/1483671689_9534.png",
            "http://img.my.csdn.net/uploads/201701/06/1483671689_2126.png",
            "http://img.my.csdn.net/uploads/201701/06/1483671703_7890.png"
            );
    List<String> pictureThumbList = Arrays.asList(
            "http://img.my.csdn.net/uploads/201701/17/1484647899_2806.jpg",
            "http://img.my.csdn.net/uploads/201701/17/1484647798_4500.jpg",
            "http://img.my.csdn.net/uploads/201701/17/1484647897_1367.png",
            "http://img.my.csdn.net/uploads/201701/17/1484650736_2101.png",
            "http://img.my.csdn.net/uploads/201701/17/1484647701_9893.png",
            "http://img.my.csdn.net/uploads/201701/17/1484650700_2514.png",
            "http://img.my.csdn.net/uploads/201701/17/1484647930_5139.jpg",
            "http://img.my.csdn.net/uploads/201701/17/1484647929_8108.png",
            "http://img.my.csdn.net/uploads/201701/17/1484647897_1978.jpg",
            "http://img.my.csdn.net/uploads/201701/17/1484647898_4474.png",
            "http://img.my.csdn.net/uploads/201701/17/1484647930_7735.png",
            "http://img.my.csdn.net/uploads/201701/17/1484647929_9591.png"
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_glide38_base);
        findView();
        setListener();
        doNetWork();
    }


    private void findView() {
        // 一般来讲， ImageWatcher 需要占据全屏的位置
        vImageWatcher = (ImageWatcher) findViewById(R.id.iw_image_watcher);
        lPictures = (MessagePicturesLayout) findViewById(R.id.mpl_pictures);
    }

    private void setListener() {
        lPictures.setCallback(this);
        // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
        vImageWatcher.setOnPictureLongPressListener(this);
    }

    private void doNetWork() {
        boolean isTranslucentStatus = true;
        // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
        vImageWatcher.setTranslucentStatus(!isTranslucentStatus ? Utils.calcStatusBarHeight(this) : 0);
        // 配置error图标
        vImageWatcher.setErrorImageRes(R.drawable.error_picture);
        lPictures.set(pictureThumbList, pictureList);
    }

    @Override
    public void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
        vImageWatcher.show(i, imageGroupList, urlList);
    }

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {
        Toast.makeText(v.getContext().getApplicationContext(), "长按了第" + (pos + 1) + "张图片", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (vImageWatcher.handleBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
