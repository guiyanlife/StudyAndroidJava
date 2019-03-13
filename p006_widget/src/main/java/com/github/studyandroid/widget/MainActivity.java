package com.github.studyandroid.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.studyandroid.widget.ui.dialog.CustomPasswordDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvPwddialog;

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
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvPwddialog.setOnClickListener(this);
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
                inputPassword();
                break;
            default:
                break;
        }
    }

    private void inputPassword() {
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_reboot_password, null);
        final CustomPasswordDialog etPassword = view.findViewById(R.id.et_password);
        final TextView tvHint = view.findViewById(R.id.tv_hint);
        Dialog sPasswordDialog = new Dialog(this, R.style.alertdialog);
        Button btn_no = view.findViewById(R.id.btn_no);
        Button btn_yes = view.findViewById(R.id.btn_yes);

        etPassword.setInputStartListener(new CustomPasswordDialog.InputStartListener() {
            @Override
            public void startInput() {
                tvHint.setVisibility(View.INVISIBLE);
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_SHORT).show();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "False", Toast.LENGTH_SHORT).show();
            }
        });

        sPasswordDialog.setContentView(view);
        sPasswordDialog.setCanceledOnTouchOutside(true);
        //sPasswordDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG); //锁屏时显示的对话框
        sPasswordDialog.show();
    }
}
