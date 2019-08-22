package com.jarhero790.eub.message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.bean.FenSiTBean;
import com.jarhero790.eub.message.bean.GuangZuBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class GuangZuAdapter extends RecyclerView.Adapter<GuangZuAdapter.MyHolder> {



    private Context context;
    private List<GuangZuBean.DataBean> list;
    private Myclick myclick;
    private Myclick touclick;
    private Myclick speak;

    public GuangZuAdapter(Context context, List<GuangZuBean.DataBean> list, Myclick myclick, Myclick touclick, Myclick speak) {
        this.context = context;
        this.list = list;
        this.myclick = myclick;
        this.touclick = touclick;
        this.speak = speak;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = View.inflate(context, R.layout.item_guangzu, null);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        GuangZuBean.DataBean bean = list.get(position);
        Glide.with(context).load(Api.TU + bean.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.music).error(R.mipmap.music)).into(myHolder.touImage);
        myHolder.tvName.setText(bean.getNickname());

//        if (bean.getAddtime().length() > 10) {
//            myHolder.tvTime.setText(bean.getAddtime().substring(0, 10));
//        }


        myHolder.tvGuanzu.setText(bean.getIs_likeEach() == 1 ? "已互关" : "+关注");

        myHolder.tvGuanzu.setTag(position);
        myHolder.tvGuanzu.setOnClickListener(myclick);

        myHolder.rlAll.setTag(position);
        myHolder.rlAll.setOnClickListener(touclick);


        myHolder.speak.setTag(position);
        myHolder.speak.setOnClickListener(speak);




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
        @BindView(R.id.rl_all)
        RelativeLayout rlAll;
        @BindView(R.id.speak)
        RelativeLayout speak;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
