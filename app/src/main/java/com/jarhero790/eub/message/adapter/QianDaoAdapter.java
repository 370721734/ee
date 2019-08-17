package com.jarhero790.eub.message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.bean.UserBean;
import com.jarhero790.eub.message.bean.PinLenBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QianDaoAdapter extends RecyclerView.Adapter<QianDaoAdapter.MyHolder> {



    private Context context;
    private List<PinLenBean.DataBean.TaskTypeBean> list;
    private Myclick myclick;

    public QianDaoAdapter(Context context, List<PinLenBean.DataBean.TaskTypeBean> list, Myclick myclick) {
        this.context = context;
        this.list = list;
        this.myclick = myclick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = View.inflate(context, R.layout.item_pinlen, null);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        PinLenBean.DataBean.TaskTypeBean bean = list.get(position);
        myHolder.tvPinglunText.setText(bean.getName());
        myHolder.tvZhanli.setText(bean.getMoney()+"");

//        PinLenBean.DataBean dataBean=


//        myHolder.tvTgContext.setText(bean.getTitle());
//        myHolder.tvTgContextText.setText(bean.getContext());
//
//        myHolder.tvTgTime.setText(bean.getCreateTime());
//        myHolder.tvTgShape.setText("已分享" + bean.getCount() + "次");
//        myHolder.tvTgCop.setTag(position);
//        myHolder.tvTgCop.setOnClickListener(myclick);
//
//        if (bean.getImgs().equals("-1")){
//            myHolder.ivTgContextTu.setVisibility(View.GONE);
//            myHolder.llTgTu.setVisibility(View.GONE);
//        }
//
//
//
//        if (bean.getImgs().length() > 0 && bean.getImgs().contains("|")) {
//            myHolder.ivTgContextTu.setVisibility(View.GONE);
//            myHolder.llTgTu.setVisibility(View.VISIBLE);
//            String[] str = bean.getImgs().split("\\|");
//            if (str.length==3){
//                myHolder.ivTgContextTuone.setVisibility(View.VISIBLE);
//                myHolder.ivTgContextTutwo.setVisibility(View.VISIBLE);
//                myHolder.ivTgContextTuthree.setVisibility(View.VISIBLE);
//
//                Glide.with(context).load(str[0]).into(myHolder.ivTgContextTuone);//图片
//                Glide.with(context).load(str[1]).into(myHolder.ivTgContextTutwo);//图片
//                Glide.with(context).load(str[2]).into(myHolder.ivTgContextTuthree);//图片
//            }else if (str.length==2){
//                myHolder.ivTgContextTuone.setVisibility(View.VISIBLE);
//                myHolder.ivTgContextTutwo.setVisibility(View.VISIBLE);
//
//                Glide.with(context).load(str[0]).into(myHolder.ivTgContextTuone);//图片
//                Glide.with(context).load(str[1]).into(myHolder.ivTgContextTutwo);//图片
//            }else {
//                myHolder.ivTgContextTuone.setVisibility(View.VISIBLE);
//                Glide.with(context).load(str[0]).into(myHolder.ivTgContextTuone);//图片
//            }
//
//        }
//
//
//
//        if (bean.getImgs().length() > 0 && !bean.getImgs().contains("|")) {
//            Glide.with(context).load(bean.getImgs()).into(myHolder.ivTgContextTu);//图片
//        }


    }


    public static abstract class Myclick implements View.OnClickListener {
        public abstract void myClick(int position, View view);

        @Override
        public void onClick(View v) {
            myClick((Integer) v.getTag(), v);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_pinglun_text)
        TextView tvPinglunText;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_zhanli)
        TextView tvZhanli;
        @BindView(R.id.view)
        View view;
        @BindView(R.id.tvview_text)
        TextView tvviewText;
        @BindView(R.id.tvview_text1)
        TextView tvviewText1;
        @BindView(R.id.tv_lin)
        TextView tvLin;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
