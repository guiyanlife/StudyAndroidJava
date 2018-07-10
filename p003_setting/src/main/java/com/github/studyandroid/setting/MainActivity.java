package com.github.studyandroid.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.baselibrary.baseactivitys.BaseActivity;
import com.github.baselibrary.cacheutil.CacheUtil;
import com.github.baselibrary.common.BaseApp;
import com.github.baselibrary.toasts2.Toasty;
import com.github.baselibrary.widget.AlertView;
import com.github.baselibrary.widget.SmoothCheckBox;
import com.github.baselibrary.widget.SwitchButton;
import com.github.commonlibs.libupdateapputils.util.UpdateAppUtils;
import com.github.commonlibs.libwebview.hios2.HiosHelper;

/**
 * Author: Gui Yan
 * E-mail: guiyanwork@163.com
 * Date: on 18/6/17
 * Description: 设置
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, SmoothCheckBox.OnCheckedChangeListener, SwitchButton.OnCheckedChangeListener {
    private static final int MSG_UPDATE_CACHEMEM_UI = 0;
    private Context mContext;
    private ImageView ivBack;
    private TextView tvCenDegree;
    private TextView tvFahDegree;
    private TextView tvTitle;
    private TextView tvClearMemory;
    private TextView tvCheckUpdates;
    private TextView tvlogout;
    private boolean isUpdate = true;
    private SwitchButton sbMsgNotice;
    private SmoothCheckBox scbCenDegree;
    private SmoothCheckBox scbFahDegree;
    private TextView tvAbout;
    private TextView tvCacheMem;
    private TextView tvVolume;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE_CACHEMEM_UI:
                    try {
                        tvCacheMem.setText(CacheUtil.getTotalCacheSize(BaseApp.get()));
                    } catch (Exception e) {
                        tvCacheMem.setText("0.0KB");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mContext = MainActivity.this;
        initView();
        setListener();
        doNetWork();
    }

    private void initView() {
        ivBack = f(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);
        tvTitle = f(R.id.tv_title);
        tvTitle.setText(getResources().getText(R.string.app_name));
        tvClearMemory = f(R.id.tv_clear_memory);
        tvCheckUpdates = f(R.id.tv_check_updates);
        tvAbout = f(R.id.tv_about);
        tvCacheMem = f(R.id.tv_cache_memory);
        sbMsgNotice = f(R.id.sb_msg_notice);
        scbCenDegree = f(R.id.scb_cen_degree);
        scbFahDegree = f(R.id.scb_fah_degree);
        tvCenDegree = f(R.id.tv_cen_degree);
        tvFahDegree = f(R.id.tv_fah_degree);
        tvVolume = f(R.id.tv_volume);
    }

    private void setListener() {
        tvlogout = f(R.id.tv_logout);
        ivBack.setOnClickListener(this);
        tvClearMemory.setOnClickListener(this);
        tvCheckUpdates.setOnClickListener(this);
        tvlogout.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        tvCenDegree.setOnClickListener(this);
        tvFahDegree.setOnClickListener(this);
        sbMsgNotice.setOnCheckedChangeListener(this);
        scbCenDegree.setOnCheckedChangeListener(this);
        scbFahDegree.setOnCheckedChangeListener(this);
        tvVolume.setOnClickListener(this);
    }

    private void doNetWork() {
        Message msg = new Message();
        msg.what = MSG_UPDATE_CACHEMEM_UI;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.tv_cen_degree) {
            if (scbCenDegree.isChecked())
                scbCenDegree.setChecked(false, true);
            else
                scbCenDegree.setChecked(true, true);
        } else if (id == R.id.tv_fah_degree) {
            if (scbFahDegree.isChecked())
                scbFahDegree.setChecked(false, true);
            else
                scbFahDegree.setChecked(true, true);
        } else if (id == R.id.tv_clear_memory) {
            showAlert("确定要清除缓存吗？", 1);
        } else if (id == R.id.tv_check_updates) {
            UpdateAppUtils.from(this)
                    .serverVersionCode(99)
                    .serverVersionName("P003_Setting_90")
                    .downloadPath("studyandroid/retrofit/app/" + "P003_Setting" + ".apk")
                    .apkPath("https://gzcu01.baidupcs.com/file/fbe04ab97ffc3f1a1df8f89b72c046be?bkt=p3-1400fbe04ab97ffc3f1a1df8f89b72c046be04caf4aa00000016cf8c&fid=949727843-250528-329376434616312&time=1531209429&sign=FDTAXGERLQBHSK-DCb740ccc5511e5e8fedcff06b081203-iyJs6pg%2FQgq%2FM1gGcO40CaJejXQ%3D&to=86&size=1494924&sta_dx=1494924&sta_cs=0&sta_ft=apk&sta_ct=0&sta_mt=0&fm2=MH%2CYangquan%2CAnywhere%2C%2Cshandong%2Ccnc&vuk=282335&iv=-2&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=1400fbe04ab97ffc3f1a1df8f89b72c046be04caf4aa00000016cf8c&sl=82640974&expires=8h&rt=sh&r=480562062&mlogid=9033738385069134932&vbdid=3161151419&fin=app-debug.apk&rtype=1&dp-logid=9033738385069134932&dp-callid=0.1.1&hps=1&tsl=50&csl=78&csign=0vnYzTYv2VV%2Ff%2FRkrbacf8q2JPs%3D&so=0&ut=8&uter=4&serv=0&uc=1093133633&ic=3776359306&ti=26fa64dbec2882241f1721f891ccd3f01d4a766ae094f7e8305a5e1275657320&by=themis")
                    .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP)    //default
                    .checkBy(UpdateAppUtils.CHECK_BY_VERSION_CODE) //default
                    .updateInfoTitle("新版本已准备好")
                    .updateInfo("版本：7.42" + "    " + "大小：28.41M\n" + "1.商户加入群聊，在线沟通更方便\n2.配送费专属优惠，下单更便宜\n3.新客加大福利，更多优惠等你来")
//                .showNotification(false)
//                .needFitAndroidN(false)
                    .update();
        } else if (id == R.id.tv_logout) {
//            showAlert("你确定退出此账号", 3);
            startActivity(new Intent("hs.act.github.phone.uploadpic"));
        } else if (id == R.id.tv_about) {
            Toasty.normal(this, "tv_about").show();
            HiosHelper.resolveAd(MainActivity.this, MainActivity.this, "http://pc.jiuzhidao.com/portal/page/index/id/9.html");
        } else if (id == R.id.tv_volume) {
            startActivity(new Intent("hs.act.github.phone.RingActivity"));
        }
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        int id = view.getId();
        if (id == R.id.sb_msg_notice) {
            if (isChecked) {
                Toasty.normal(this, "sb_msg_notice: checked").show();
            } else {
                Toasty.normal(this, "sb_msg_notice: unchecked").show();
            }
        }
    }

    @Override
    public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
        int id = checkBox.getId();
        if (id == R.id.scb_cen_degree) {
            if (isChecked)
                Toasty.normal(this, "scb_cen_degree: checked").show();
            else
                Toasty.normal(this, "scb_cen_degree: unchecked").show();
        } else if (id == R.id.scb_fah_degree) {
            if (isChecked)
                Toasty.normal(this, "scb_fah_degree: checked").show();
            else
                Toasty.normal(this, "scb_fah_degree: unchecked").show();
        }
    }

    private void showAlert(String str, final int flag) {
        final AlertView dialog = new AlertView(mContext, "温馨提示", str,
                "取消", "确定");
        dialog.show();
        dialog.setClicklistener(new AlertView.ClickListenerInterface() {
            @Override
            public void doLeft() {
                dialog.dismiss();
            }

            @Override
            public void doRight() {
                dialog.dismiss();
                if (flag == 1) {
                    //TODO 清除应用缓存
                    CacheUtil.clearAllCache(BaseApp.get());
                    Toasty.normal(mContext, "清除完毕").show();

                    Message msg = new Message();
                    msg.what = MSG_UPDATE_CACHEMEM_UI;
                    mHandler.sendMessageDelayed(msg, 500);
                } else if (flag == 3) {
                    //TODO 退出应用
                }
            }
        });
    }
}
