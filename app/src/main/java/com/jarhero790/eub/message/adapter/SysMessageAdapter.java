package com.jarhero790.eub.message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.bean.SysMessageBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SysMessageAdapter extends RecyclerView.Adapter<SysMessageAdapter.MyHolder> {



    private Context context;
    private List<SysMessageBean.DataBean.SystemBean> list;
    private Myclick myclick;

    public SysMessageAdapter(Context context, List<SysMessageBean.DataBean.SystemBean> list, Myclick myclick) {
        this.context = context;
        this.list = list;
        this.myclick = myclick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = View.inflate(context, R.layout.item_sysmessage, null);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        SysMessageBean.DataBean.SystemBean bean = list.get(position);
        Glide.with(context).load(bean.getImg()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo).error(R.mipmap.zuanshi_logo)).into(myHolder.touImage);
        myHolder.content.setText(bean.getContent());

        if (bean.getAddtime().length() > 10) {
            myHolder.tvTime.setText(bean.getAddtime().substring(0, 10));
        }
//
//        Glide.with(context).load(Api.HOST + bean.getVideo_img()).apply(new RequestOptions().placeholder(R.mipmap.music).error(R.mipmap.music)).into(myHolder.ivIcon);
//
//
//        myHolder.tvGuanzu.setText(bean.getIs_likeEach() == 1 ? "已互关" : "+关注");
//
//        myHolder.tvGuanzu.setTag(position);
//        myHolder.tvGuanzu.setOnClickListener(myclick);


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
        @BindView(R.id.tou_image)
        CircleImageView touImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_guanzu)
        TextView tvGuanzu;
        @BindView(R.id.content)
        TextView content;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
