package com.jarhero790.eub.message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.bean.JiangLiBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class JiangLiAdapter extends RecyclerView.Adapter<JiangLiAdapter.MyHolder> {



    private Context context;
    private List<JiangLiBean.DataBean> list;
    private Myclick myclick;

    public JiangLiAdapter(Context context, List<JiangLiBean.DataBean> list, Myclick myclick) {
        this.context = context;
        this.list = list;
        this.myclick = myclick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = View.inflate(context, R.layout.item_jiangli, null);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        JiangLiBean.DataBean bean = list.get(position);
        Glide.with(context).load(Api.HOST + bean.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.music).error(R.mipmap.music)).into(myHolder.touImage);
//
//
        if (bean.getAddtime().length() > 10) {
            myHolder.tvTime.setText(bean.getAddtime().substring(0, 10));
        }

        switch (bean.getType()){
            case 0:
                myHolder.tvName.setText("今日注册账号奖励"+bean.getMoney()+"金币");
                break;
            case 1:
                myHolder.tvName.setText("今日邀请码注册将励"+bean.getMoney()+"金币");
                break;
            case 2:
                myHolder.tvName.setText("今日邀请注册奖励"+bean.getMoney()+"金币");
                break;
            case 3:
                myHolder.tvName.setText("今日签到奖励"+bean.getMoney()+"金币");
                break;
        }


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

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
