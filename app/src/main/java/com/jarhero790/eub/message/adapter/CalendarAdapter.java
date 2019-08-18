package com.jarhero790.eub.message.adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jarhero790.eub.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarAdapter extends ListItemAdapter<Integer> {
//    AbsListView.MarginLayoutParams params;
//    ViewGroup.MarginLayoutParams params;
//    LinearLayout.LayoutParams params;

    private int number=-1;
    public void setNumber(int number){
        this.number=number;
    }
    public CalendarAdapter(Context context, List<Integer> list) {
        super(context, list);
        int width=(context.getResources().getDisplayMetrics().widthPixels-10)/9;

//        params=new ViewGroup.MarginLayoutParams(width,width);
//        params=new LinearLayout.LayoutParams(width,width);
//        params.setMargins(0,5,0,5);
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.item_calendar, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        if (number==getItem(i)){
            Log.e("-----------2",""+number);
            holder.tvText.setVisibility(View.GONE);
            holder.ivIcon.setVisibility(View.VISIBLE);
        }
        holder.bindView(list.get(i));
        return view;
    }

    class ViewHolder {
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        private void bindView(int b){
//            tvText.setLayoutParams(params);
//            tvText.setGravity(Gravity.CENTER);

            if (b==0){
                tvText.setVisibility(View.INVISIBLE);
            }else {
                tvText.setText(""+b);
                Log.e("-----------3",""+b);
//                if (number==getItem(po)){
//                    tvText.setVisibility(View.GONE);
//                    ivIcon.setVisibility(View.VISIBLE);
//                }else {
//
//                }
            }

        }
    }
}
