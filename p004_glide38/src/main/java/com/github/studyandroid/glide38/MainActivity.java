package com.github.studyandroid.glide38;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView mTvRecyclerview, mTvOneMultiplyOne, mTvOneMultiplyTwo, mTvOneMultiplyThree, mTvTwoMultiplyTwo, mTvTwoMultiplyThree, mTvThreeMultiplyThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide38);
        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        ivBack = findViewById(R.id.iv_back);
        mTvRecyclerview = findViewById(R.id.tv_recyclerview);
        mTvOneMultiplyOne = findViewById(R.id.tv_one_multiply_one);
        mTvOneMultiplyTwo = findViewById(R.id.tv_one_multiply_two);
        mTvOneMultiplyThree = findViewById(R.id.tv_one_multiply_three);
        mTvTwoMultiplyTwo = findViewById(R.id.tv_two_multiply_two);
        mTvTwoMultiplyThree = findViewById(R.id.tv_two_multiply_three);
        mTvThreeMultiplyThree = findViewById(R.id.tv_three_multiply_three);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        mTvRecyclerview.setOnClickListener(this);
        mTvOneMultiplyOne.setOnClickListener(this);
        mTvOneMultiplyTwo.setOnClickListener(this);
        mTvOneMultiplyThree.setOnClickListener(this);
        mTvTwoMultiplyTwo.setOnClickListener(this);
        mTvTwoMultiplyThree.setOnClickListener(this);
        mTvThreeMultiplyThree.setOnClickListener(this);
    }

    private void doNetWork() {
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_recyclerview:
                intent = new Intent("hs.ac.github.DemoGlide38MainActivity");
                intent.putExtra("title","Sudoku RecyclerView");
                startActivity(intent);
                break;
            case R.id.tv_one_multiply_one:
                intent = new Intent("hs.ac.github.DemoGlide38BaseMainActivity");
                intent.putExtra("title","Sudoku 1x1");
                intent.putExtra("imageIndex", 1);
                startActivity(intent);
                break;
            case R.id.tv_one_multiply_two:
                intent = new Intent("hs.ac.github.DemoGlide38BaseMainActivity");
                intent.putExtra("title","Sudoku 1x2");
                intent.putExtra("imageIndex", 4);
                startActivity(intent);
                break;
            case R.id.tv_one_multiply_three:
                intent = new Intent("hs.ac.github.DemoGlide38BaseMainActivity");
                intent.putExtra("title","Sudoku 1x3");
                intent.putExtra("imageIndex", 3);
                startActivity(intent);
                break;
            case R.id.tv_two_multiply_two:
                intent = new Intent("hs.ac.github.DemoGlide38BaseMainActivity");
                intent.putExtra("title","Sudoku 2x2");
                intent.putExtra("imageIndex", 2);
                startActivity(intent);
                break;
            case R.id.tv_two_multiply_three:
                intent = new Intent("hs.ac.github.DemoGlide38BaseMainActivity");
                intent.putExtra("title","Sudoku 2x3");
                intent.putExtra("imageIndex", 5);
                startActivity(intent);
                break;
            case R.id.tv_three_multiply_three:
                intent = new Intent("hs.ac.github.DemoGlide38BaseMainActivity");
                intent.putExtra("title","Sudoku 3x3");
                intent.putExtra("imageIndex", 0);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
