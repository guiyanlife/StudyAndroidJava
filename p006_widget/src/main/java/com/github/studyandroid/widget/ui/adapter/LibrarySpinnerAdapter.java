package com.github.studyandroid.widget.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.github.studyandroid.widget.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */

public class LibrarySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context context ;
    private List<HashMap<String,String>> list;
    private int mSelect;

    public LibrarySpinnerAdapter(Context context, List<HashMap<String,String>> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_dropdown, null);
        TextView dropdownText = convertView.findViewById(R.id.dropdown_text);
        dropdownText.setText(list.get(position).get("name"));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_library,null);
        TextView spinnerText = convertView.findViewById(R.id.spinner_text);
        spinnerText.setText(list.get(position).get("name"));
        if (position == mSelect){
            spinnerText.setTextColor(context.getResources().getColor(R.color.color_button));
        }else{
            spinnerText.setTextColor(context.getResources().getColor(R.color.color_title));
        }
        return convertView;
    }

    public void setSelect(int select){
        mSelect = select;
    }
}
