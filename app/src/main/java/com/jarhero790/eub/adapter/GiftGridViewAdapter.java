package com.jarhero790.eub.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.Gift;
import com.jarhero790.eub.message.bean.GiftBean;
import com.jarhero790.eub.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GiftGridViewAdapter extends BaseAdapter {

    private List<GiftBean.DataBean> giftList;
    private Context context;

    public GiftGridViewAdapter(List<GiftBean.DataBean> giftList, Context context) {

        this.giftList = giftList;
        this.context = context;
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

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(AppUtils.getContext()).inflate(R.layout.gift_gridview_item, null);
            holder = new ViewHolder(convertView);

//            holder.giftIcon = convertView.findViewById(R.id.gift_icon);
            //设置显示图片
//            holder.giftIcon.setImageURI(Uri.parse(Api.TU+giftList.get(position).getImg()));
//            Glide.with(context).load(Api.GIFT + giftList.get(position).getImg()).apply(new RequestOptions().placeholder(R.mipmap.gift1)
//                    .error(R.mipmap.gift1)).into(holder.giftIcon);
//            Log.e("h",Api.TU+giftList.get(position).getImg());
//            holder.giftName = convertView.findViewById(R.id.giftName);
//            holder.giftName.setText(giftList.get(position).getName());

//            holder.giftJiaZhi = convertView.findViewById(R.id.giftJiaZhi);
//            holder.giftJiaZhi.setText(giftList.get(position).getMoney());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bindView(giftList.get(position));

        holder.framelayoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                if (giftClick!=null){
                    giftClick.onItemclick(view,position);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.gift_icon)
        ImageView giftIcon;
        @BindView(R.id.giftName)
        TextView giftName;
        @BindView(R.id.giftJiaZhi)
        TextView giftJiaZhi;
        @BindView(R.id.framelayout_container)
        LinearLayout framelayoutContainer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        private void bindView(GiftBean.DataBean gift){
            Glide.with(context).load(Api.GIFT + gift.getImg()).apply(new RequestOptions().placeholder(R.mipmap.gift1)
                    .error(R.mipmap.gift1)).into(giftIcon);
            giftName.setText(gift.getName());
            giftJiaZhi.setText(gift.getMoney());
        }
    }


    public interface GiftClick{
        void onItemclick(View view,int position);
    }
    private GiftClick giftClick;

    public void setGiftClick(GiftClick giftClick) {
        this.giftClick = giftClick;
    }
}
