package com.haiersmart.studyandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.haiersmart.studyandroid.R;
import com.haiersmart.studyandroid.domain.FruitModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gui Yan on 2017/11/21.
 */

public class FruitListAdapter extends BaseAdapter {
    private Context mContext;
    private ListView mListView;
    private LayoutInflater mInflater;
    private List<FruitModel> mModels = new ArrayList<>();

    public FruitListAdapter(Activity context, ListView listView) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mListView = listView;
    }

    public void addItem(int imgId, String title, String info) {
        FruitModel model = new FruitModel(imgId, title, info);
        mModels.add(model);
    }

    public void clearItems() {
        mModels.clear();
    }

    public void update() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public Object getItem(int i) {
        if (i >= getCount())
            return null;
        else
            return mModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fruit_list_item, parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.fruit_item_img);
            holder.title = (TextView) convertView.findViewById(R.id.fruit_item_title);
            holder.info = (TextView) convertView.findViewById(R.id.fruit_item_info);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FruitModel model = mModels.get(position);
        holder.img.setImageResource(model.imgId);
        holder.title.setText(model.title);
        holder.info.setText(model.info);

        return null;
    }

    private class ViewHolder {
        ImageView img;
        TextView title;
        TextView info;
    }
}
