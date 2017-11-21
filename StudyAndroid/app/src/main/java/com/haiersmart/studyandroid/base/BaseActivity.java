package com.haiersmart.studyandroid.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.haiersmart.studyandroid.MainActivity;
import com.haiersmart.studyandroid.utils.AppManager;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {
    public final String TAG = getClass().getSimpleName().toString();
    public static final String REQUEST_CODE = "request_code";

    protected Handler mHandler;

    private long mCurrentMs = SystemClock.uptimeMillis();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().add(this);

        setContentView(getLayoutId());

        ButterKnife.bind(this);
        mHandler = new Handler();

        setup(savedInstanceState);

//        HookUtil.hookClick(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) { HookUtil.hookClick(this);}
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            MyLogUtil.d("qibin", "mobClickEvent");
            MobEventHelper.onEvent(this, "effective_click");
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgentFixed.onResume(this);
        MobEvent.onResume(this);
    }

    protected abstract int getLayoutId();

    protected void setup(@Nullable Bundle savedInstanceState) {

    }

    protected final <T extends View> T f(@IdRes int resId) {
        return ViewHelper.f(this, resId);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    public void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }

    public void startActivityForResult(Class<? extends Activity> activity, int requestCode) {
        startActivityForResult(new Intent(this, activity), requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (requestCode != -1 && intent.getIntExtra(REQUEST_CODE, -1) == -1) {
            intent.putExtra(REQUEST_CODE, requestCode);
        }

        super.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到指定activity，如果未登录，则弹出登录窗口
     *
     * @param activity
     */
    public void targetToIfLogin(final Class<? extends BaseActivity> activity) {
        UserUtils.loginToDo(this, new Runnable() {
            @Override
            public void run() {
                startActivity(activity);
            }
        });
    }

    /**
     * 跳转到指定activity，如果未登录，则弹出登录窗口
     *
     * @param intent
     */
    public void targetToIfLogin(final Intent intent) {
        UserUtils.loginToDo(this, new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        });
    }

    /**
     * 跳转到指定activity，如果未登录，则弹出登录窗口
     *
     * @param intent
     */
    public void targetToForResultIfLogin(final Intent intent, final int requestCode) {
        UserUtils.loginToDo(this, new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent, requestCode);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgentFixed.onPause(this);
        MobEvent.onPause(this);
    }

    @Override
    public void finish() {
        hideSoftKeyboard();
        ShowLoadingUtil.onDestory();
        super.finish();
        AppManager.getInstance().remove(this);
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    protected void onDestroy() {
        Net.getInstance().get().cancel(getIdentifier());
        AppManager.getInstance().remove(this);
//        ShowLoadingUtil.onDestory();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onHomePressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void noAnimFinish(){
        hideSoftKeyboard();
        ShowLoadingUtil.onDestory();
        super.finish();
        AppManager.getInstance().remove(this);
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public String getIdentifier() {
        return getClass().getName() + mCurrentMs;
    }
}
