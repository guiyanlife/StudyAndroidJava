package com.github.studyandroid.widget.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.studyandroid.widget.R;
import com.github.studyandroid.widget.ui.adapter.LibrarySpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpinnerActivity extends Activity implements View.OnClickListener {
    private ImageView ivToolbarBack;
    private TextView tvToolbarContent;
    private Spinner mSpImportPicMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivToolbarBack = findViewById(R.id.iv_toolbar_back);
        tvToolbarContent = findViewById(R.id.tv_toolbar_content);
        mSpImportPicMin = findViewById(R.id.Sp_import_pic_min);
    }

    private void setListener() {
        ivToolbarBack.setOnClickListener(this);
    }

    private void doNetWork() {
        Intent intent = getIntent();
        ivToolbarBack.setVisibility(View.VISIBLE);
        tvToolbarContent.setText(intent.getStringExtra("title"));
        initSpinnerImportPicMin();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void initSpinnerImportPicMin() {
        String NAME = "name";
        String[] minSizeTypes = getResources().getStringArray(R.array.pic_min_size_setting);
        List<HashMap<String, String>> data = new ArrayList<>();
        for (String minSizeType : minSizeTypes) {
            HashMap<String, String> map = new HashMap<>();
            map.put(NAME, minSizeType);
            data.add(map);
        }
        final LibrarySpinnerAdapter adapter = new LibrarySpinnerAdapter(this, data);
        mSpImportPicMin.setAdapter(adapter);
        int minSizeType = 2; //从数据库获取相应的配置
        switch (minSizeType) {
            case 0:
                mSpImportPicMin.setSelection(0);
                break;
            case 1:
                mSpImportPicMin.setSelection(1);
                break;
            case 2:
                mSpImportPicMin.setSelection(2);
                break;
        }

        mSpImportPicMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelect(i);
                //根据i值，做对应的处理操作
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
