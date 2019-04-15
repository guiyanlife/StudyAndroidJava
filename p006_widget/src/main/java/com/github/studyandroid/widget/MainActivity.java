package com.github.studyandroid.widget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.studyandroid.widget.ui.activity.Glide38Activity;
import com.github.studyandroid.widget.ui.dialog.CustomPasswordDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvPwddialog;
    private TextView tvSpinner;
    private TextView tvGlide38;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivBack = findViewById(R.id.iv_back);
        tvPwddialog = findViewById(R.id.tv_pwddialog);
        tvSpinner = findViewById(R.id.tv_spinner);
        tvGlide38 = findViewById(R.id.tv_glide38);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvPwddialog.setOnClickListener(this);
        tvSpinner.setOnClickListener(this);
        tvGlide38.setOnClickListener(this);
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
            case R.id.tv_pwddialog:
                showCustomPasswordDialog();
                break;
            case R.id.tv_spinner:
                Intent intentSpinner = new Intent("com.github.studyandroid.widget.activity.SpinnerActivity");
                intentSpinner.putExtra("title", tvSpinner.getText());
                startActivity(intentSpinner);
                break;
            case R.id.tv_glide38:
                Intent intentGlide38 = new Intent(this, Glide38Activity.class);
                intentGlide38.putExtra("title", tvGlide38.getText());
                startActivity(intentGlide38);
                break;
            default:
                break;
        }
    }

    private void showCustomPasswordDialog() {
        CustomPasswordDialog dialog = new CustomPasswordDialog(this, R.style.alertdialog);
        dialog.setYesButton(new CustomPasswordDialog.OnClickListenerInterface() {
            @Override
            public void onClick() {
                Toast.makeText(getApplicationContext(), "Pass", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNoButton(new CustomPasswordDialog.OnClickListenerInterface() {
            @Override
            public void onClick() {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
