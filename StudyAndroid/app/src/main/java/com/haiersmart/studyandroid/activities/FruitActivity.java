package com.haiersmart.studyandroid.activities;

import android.os.Bundle;
import android.widget.ListView;

import com.haiersmart.studyandroid.R;
import com.haiersmart.studyandroid.adapter.FruitListAdapter;
import com.haiersmart.studyandroid.base.BaseActivity;

public class FruitActivity extends BaseActivity {
    FruitListAdapter mAdapter;
    ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FruitListAdapter(FruitActivity.this, mListview);
        mListview = (ListView) findViewById(R.id.fruit_list);
        mListview.setAdapter(mAdapter);
        mAdapter.clearItems();
        mAdapter.addItem(R.mipmap.fruit_apple,  "Apple",  "Three apples");
        mAdapter.addItem(R.mipmap.fruit_banana, "Banana", "Five bananas");
        mAdapter.addItem(R.mipmap.fruit_orange, "Orange", "Two oranges");
        mAdapter.update();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fruit;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
