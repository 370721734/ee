package com.jarhero790.eub.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.Gift;
import com.jarhero790.eub.utils.AppUtils;
import java.util.ArrayList;

public class GiftGridViewAdapter extends BaseAdapter {

    private ArrayList<Gift> giftList;

    public GiftGridViewAdapter(ArrayList<Gift>  giftList ) {

        this.giftList = giftList;
    }

    @Override
    public int getCount() {
        if (null != giftList) {
            return giftList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return giftList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(AppUtils.getContext()).inflate(R.layout.gift_gridview_item, null);
            holder.giftIcon = convertView.findViewById(R.id.gift_icon);
            //设置显示图片
            holder.giftIcon.setImageURI(Uri.parse(Api.HOST+giftList.get(position).getImg()));

            Log.e("h",Api.HOST+giftList.get(position).getImg());
            holder.giftName = convertView.findViewById(R.id.giftName);
            holder.giftName.setText(giftList.get(position).getName());

            holder.giftJiaZhi = convertView.findViewById(R.id.giftJiaZhi);
            holder.giftJiaZhi.setText(giftList.get(position).getMoney());

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        return convertView;
    }

    class Holder {
        ImageView giftIcon;
        TextView giftName;
        TextView giftJiaZhi;
    }
}
