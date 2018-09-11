package com.github.studyandroid.animation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.baselibrary.tagcloud.base.TagsAdapter;
import com.github.studyandroid.animation.R;
import com.github.studyandroid.animation.domain.EasyhomeDeviceData;
import com.github.studyandroid.animation.ui.dialog.DevicePbjDialog;
import com.github.studyandroid.animation.ui.dialog.DeviceStateDialog;
import com.github.studyandroid.animation.ui.dialog.DeviceXwjDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guiyan on 18/8/22.
 */
public class TagCloudViewTagAdapter extends TagsAdapter {
    private Context mContext;
    private List<EasyhomeDeviceData> m_Items;
    private ViewGroup parent;

    public TagCloudViewTagAdapter(Context context, List items) {
        mContext = context;
        m_Items = new ArrayList<>();
        m_Items.addAll(items);
    }

    public List<EasyhomeDeviceData> getItems() {
        return m_Items;
    }

    public void setItems(List<EasyhomeDeviceData> items) {
        m_Items.clear();
        m_Items.addAll(items);
    }

    public void setItemDeviceStateInfo(int deviceIndex, String deviceInfo) {
        for (EasyhomeDeviceData bean : m_Items) {
            if (deviceIndex == bean.getDeviceIndex()) {
                bean.setDeviceStateInfo(deviceInfo);
                refView(bean.getDeviceIndex(), deviceInfo);
                break;
            }
        }
    }

    private void refView(int index, String deviceInfo) {
        if (parent == null) {
            return;
        }
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view.getTag() != null && index == (int) view.getTag()) {
                ((TextView) view.findViewById(R.id.tv_view_tag_content)).setText(deviceInfo);
                break;
            }
        }
    }

    public void clear() {
        m_Items.clear();
    }

    @Override
    public int getCount() {
        return m_Items.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        this.parent = parent;
        View view = LayoutInflater.from(context).inflate(R.layout.tagcloud_view_tag_item, parent, false);
        ImageView tagBg = view.findViewById(R.id.iv_view_tag_bg);
        TextView tagContent = view.findViewById(R.id.tv_view_tag_content);
        tagBg.setImageResource(m_Items.get(position).getDeviceImageId());
        tagContent.setText(m_Items.get(position).getDeviceStateInfo());
        view.setTag(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch ((int) v.getTag()) {
                    case 0: //冰箱
                        intent = new Intent("hs.act.github.phone.TagCloudDeviceBxActivity");
                        if (intent.resolveActivity(context.getPackageManager()) != null)
                            context.startActivity(intent);
                        break;
                    case 2: //净水机
                        new DeviceStateDialog(mContext).setDeviceType("净水机").setDeviceState("制作完成").show();
                        break;
                    case 5: //破壁机
                        new DevicePbjDialog(mContext).setDeviceMode("果汁").setDeviceSpeed("4000").setDeviceTemp("80℃").show();
                        break;
                    case 6: //燃气热水器
                        intent = new Intent("hs.act.github.phone.TagCloudDeviceRqesqActivity");
                        if (intent.resolveActivity(context.getPackageManager()) != null)
                            context.startActivity(intent);
                        break;
                    case 9: //洗碗机
                        new DeviceXwjDialog(mContext).setDeviceState("运行中").setDeviceMode("智能洗").setDeviceSdsw("50℃").setDeviceYsl("8L").setDeviceGlj("充足").setDeviceRhy("缺少").show();
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public Object getItem(int position) {
        return m_Items.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor, float alpha) {
        ImageView tagBg = view.findViewById(R.id.iv_view_tag_bg);
        TextView tagContent = view.findViewById(R.id.tv_view_tag_content);
        if (alpha < 0.5)
            alpha = alpha * (float) 1.5;
        else
            alpha = alpha * (float) 0.5 + (float) 0.5;
        tagBg.setAlpha(alpha);
        tagContent.setAlpha(alpha);
    }
}
