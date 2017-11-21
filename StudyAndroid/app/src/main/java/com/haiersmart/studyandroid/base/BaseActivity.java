package com.haiersmart.studyandroid.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.haiersmart.studyandroid.MainActivity;
import com.haiersmart.studyandroid.R;
import com.haiersmart.studyandroid.utils.AppManager;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
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
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected abstract int getLayoutId();

    protected void setup(@Nullable Bundle savedInstanceState) {
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        hideSoftKeyboard();
        super.finish();
        AppManager.getInstance().remove(this);
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    protected void onDestroy() {
        AppManager.getInstance().remove(this);
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

    public void noAnimFinish() {
        hideSoftKeyboard();
        super.finish();
        AppManager.getInstance().remove(this);
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public String getIdentifier() {
        return getClass().getName() + mCurrentMs;
    }
}
