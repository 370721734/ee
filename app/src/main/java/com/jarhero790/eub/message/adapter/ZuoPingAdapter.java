package com.jarhero790.eub.message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.message.bean.MyFaBuBean;
import com.jarhero790.eub.message.bean.ZanBean;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ZuoPingAdapter extends RecyclerView.Adapter<ZuoPingAdapter.MyHolder> {

    private Context context;
    private List<MyFaBuBean.DataBean> list;
    private Myclick myclick_delete;
    private Myclick myclick_tu;

    public ZuoPingAdapter(Context context, List<MyFaBuBean.DataBean> list, Myclick myclick_delete, Myclick myclick_tu) {
        this.context = context;
        this.list = list;
        this.myclick_delete = myclick_delete;
        this.myclick_tu = myclick_tu;
    }

    @NonNull
    @Override
    public ZuoPingAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_my_zuopin_tu, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZuoPingAdapter.MyHolder holder, int position) {
        MyFaBuBean.DataBean bean = list.get(position);
        Glide.with(context).load(bean.getVideo_img()).apply(new RequestOptions().placeholder(R.mipmap.gift3).error(R.mipmap.gift3)).into(holder.ivIcon);
        holder.tvXin.setText(shownum(bean.getZan()));
        holder.tvGo.setText(shownum(bean.getVisit_val()));


        holder.rll.setTag(position);
        holder.rll.setOnClickListener(myclick_tu);

        holder.ivDel.setTag(position);
        holder.ivDel.setOnClickListener(myclick_delete);
    }


    private String shownum(int s) {
        if (s > 9999) {
            try {
                float b = Float.valueOf(s) / 10000f;
                String format = new DecimalFormat("#.#").format(b);
                return format+"w";
            } catch (Exception e) {
               return s+"";
            }

        } else {
            return "" + s;
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }


    public static abstract class Myclick implements View.OnClickListener {
        public abstract void myclick(int position, View view);

        @Override
        public void onClick(View view) {
            myclick((Integer) view.getTag(), view);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.iv_del)
        ImageView ivDel;
        @BindView(R.id.iv_xin)
        ImageView ivXin;
        @BindView(R.id.tv_xin)
        TextView tvXin;
        @BindView(R.id.tv_go)
        TextView tvGo;
        @BindView(R.id.rlll)
        RelativeLayout rll;

        MyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
