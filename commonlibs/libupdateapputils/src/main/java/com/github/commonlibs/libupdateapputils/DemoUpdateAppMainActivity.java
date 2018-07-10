package com.github.commonlibs.libupdateapputils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.commonlibs.libupdateapputils.customview.ConfirmDialog;
import com.github.commonlibs.libupdateapputils.feature.Callback;
import com.github.commonlibs.libupdateapputils.util.UpdateAppUtils;

public class DemoUpdateAppMainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button BtnNormalUpdate;
    private Button BtnWebDownlaod;
    private Button BtnForceUpdate;
    private Button BtnCheckNameUpdate;

    //服务器apk path,这里放了百度云盘的apk 作为测试
    public static final String apkPath = "https://gzcu01.baidupcs.com/file/fbe04ab97ffc3f1a1df8f89b72c046be?bkt=p3-1400fbe04ab97ffc3f1a1df8f89b72c046be04caf4aa00000016cf8c&fid=949727843-250528-329376434616312&time=1531209429&sign=FDTAXGERLQBHSK-DCb740ccc5511e5e8fedcff06b081203-iyJs6pg%2FQgq%2FM1gGcO40CaJejXQ%3D&to=86&size=1494924&sta_dx=1494924&sta_cs=0&sta_ft=apk&sta_ct=0&sta_mt=0&fm2=MH%2CYangquan%2CAnywhere%2C%2Cshandong%2Ccnc&vuk=282335&iv=-2&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=1400fbe04ab97ffc3f1a1df8f89b72c046be04caf4aa00000016cf8c&sl=82640974&expires=8h&rt=sh&r=480562062&mlogid=9033738385069134932&vbdid=3161151419&fin=app-debug.apk&rtype=1&dp-logid=9033738385069134932&dp-callid=0.1.1&hps=1&tsl=50&csl=78&csign=0vnYzTYv2VV%2Ff%2FRkrbacf8q2JPs%3D&so=0&ut=8&uter=4&serv=0&uc=1093133633&ic=3776359306&ti=26fa64dbec2882241f1721f891ccd3f01d4a766ae094f7e8305a5e1275657320&by=themis";
    //String apkPath = "http://issuecdn.baidupcs.com/issue/netdisk/apk/BaiduNetdisk_7.15.1.apk";
    private int code = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_update);

        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        BtnNormalUpdate = findViewById(R.id.btn_normalUpdate);
        BtnWebDownlaod = findViewById(R.id.btn_webDownlaod);
        BtnForceUpdate = findViewById(R.id.btn_forceUpdate);
        BtnCheckNameUpdate = findViewById(R.id.btn_checkNameUpdate);
    }

    private void setListener() {
        BtnNormalUpdate.setOnClickListener(this);
        BtnWebDownlaod.setOnClickListener(this);
        BtnForceUpdate.setOnClickListener(this);
        BtnCheckNameUpdate.setOnClickListener(this);
    }

    private void doNetWork() {
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_normalUpdate) {
            checkAndUpdate(1); //基本更新
        } else if (id == R.id.btn_webDownlaod) {
            checkAndUpdate(2); //通过浏览器下载
        } else if (id == R.id.btn_forceUpdate) {
            checkAndUpdate(3); //强制更新
        } else if (id == R.id.btn_checkNameUpdate) {
            checkAndUpdate(4); //根据versionName判断跟新
        }
    }

    private void checkAndUpdate(int code) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            realUpdate(code);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                realUpdate(code);
            } else {//申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void realUpdate(int code) {
        this.code = code;
        switch (code) {
            case 1:
                update1();
                break;
            case 2:
                update2();
                break;
            case 3:
                update3();
                break;
            case 4:
                update4();
                break;
        }
    }

    //基本更新（使用本APP进行下载）
    private void update1() {
        UpdateAppUtils.from(this)
                .serverVersionCode(getVersionCode(this))
                .serverVersionName(getVersionName(this))
                .downloadDirectory("studyandroid/retrofit/app/")
                .apkPath(apkPath)
                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP)    //default
                .checkBy(UpdateAppUtils.CHECK_BY_VERSION_CODE) //default
                .updateInfoTitle("新版本已准备好")
                .updateInfo("版本：7.42" + "    " + "大小：28.41M\n" + "1.商户加入群聊，在线沟通更方便\n2.配送费专属优惠，下单更便宜\n3.新客加大福利，更多优惠等你来")
//                .showNotification(false)
//                .needFitAndroidN(false)
                .update();
    }

    //通过浏览器下载
    private void update2() {
        UpdateAppUtils.from(this)
                .serverVersionCode(getVersionCode(this) + 1)
                .serverVersionName(getVersionName(this))
                .apkPath(apkPath)
                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_BROWSER)
                .update();
    }

    //强制更新
    private void update3() {
        UpdateAppUtils.from(this)
                .serverVersionCode(getVersionCode(this) + 1)
                .serverVersionName(getVersionName(this))
                .apkPath(apkPath)
                .isForce(true)
                .update();
    }

    //根据versionName判断更新
    private void update4() {
        UpdateAppUtils.from(this)
                .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME)
                .serverVersionCode(getVersionCode(this) + 1)
                .serverVersionName(getVersionName(this) + "diff")
                .apkPath(apkPath)
                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_BROWSER)
                .isForce(true)
                .update();
    }

    //权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    realUpdate(code);
                } else {
                    new ConfirmDialog(this, new Callback() {
                        @Override
                        public void callback(int position) {
                            if (position == 1) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                                startActivity(intent);
                            }
                        }
                    }).setContent("暂无读写SD卡权限\n是否前往设置？").show();
                }
                break;
        }
    }

    /**
     * 获取版本号
     */
    private String getVersionName(Context ctx) {
        try {
            PackageInfo pi = ctx.getPackageManager().getPackageInfo(
                    ctx.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


}
