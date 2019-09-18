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
import com.jarhero790.eub.message.bean.TongKuanBean;
import com.jarhero790.eub.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TongKuanAdapter extends RecyclerView.Adapter<TongKuanAdapter.MyHolder> {

    private Context context;
    private List<TongKuanBean.DataBean.IdenticalBean> list;
    private Myclick myclick_delete;
    private Myclick myclick_tu;

    public TongKuanAdapter(Context context, List<TongKuanBean.DataBean.IdenticalBean> list, Myclick myclick_delete, Myclick myclick_tu) {
        this.context = context;
        this.list = list;
        this.myclick_delete = myclick_delete;
        this.myclick_tu = myclick_tu;
    }

    @NonNull
    @Override
    public TongKuanAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_souye_tong_kuan, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TongKuanAdapter.MyHolder holder, int position) {
        TongKuanBean.DataBean.IdenticalBean bean = list.get(position);
        Glide.with(context).load(bean.getVideo_img()).apply(new RequestOptions().placeholder(R.mipmap.video_deault).error(R.mipmap.video_deault)).into(holder.ivIcon);
//        holder.tvXin.setText(CommonUtil.showzannum(bean.getZan()));
//        holder.tvGo.setText(CommonUtil.showzannum(bean.getVisit_val()));

        holder.tvposition.setText("NO."+(position+1));
//
//
//        holder.rll.setTag(position);
//        holder.rll.setOnClickListener(myclick_tu);
//
//        holder.rldelete.setTag(position);
//        holder.rldelete.setOnClickListener(myclick_delete);
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
        @BindView(R.id.tv_position)
        TextView tvposition;
        @BindView(R.id.tv_go)
        TextView tvGo;
        @BindView(R.id.rlll)
        RelativeLayout rll;
        @BindView(R.id.rl_delete)
        RelativeLayout rldelete;



        MyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}