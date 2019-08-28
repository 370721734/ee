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
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.bean.OtherPingLBean;
import com.jarhero790.eub.utils.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PingLenVideoAdapter extends RecyclerView.Adapter<PingLenVideoAdapter.MyHolder> {



    private Context context;
    private List<OtherPingLBean.DataBean.CommentListBean> list;
    private Myclick myclick;

    public PingLenVideoAdapter(Context context, List<OtherPingLBean.DataBean.CommentListBean> list, Myclick myclick) {
        this.context = context;
        this.list = list;
        this.myclick = myclick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = View.inflate(context, R.layout.item_pinglenvideo, null);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        OtherPingLBean.DataBean.CommentListBean bean = list.get(position);
        Glide.with(context).load(Api.TU + bean.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.music).error(R.mipmap.music)).into(myHolder.touImage);
        myHolder.tvName.setText(bean.getNickname());
        myHolder.content.setText(bean.getContent()+"  "+bean.getAddtime());
        myHolder.tvZan.setText(CommonUtil.showzannum(bean.getZan()));

//
//        if (bean.getAddtime().length() > 9) {
//            myHolder.tvTime.setText(bean.getAddtime().substring(0, 10));
//        }
//
//
//        myHolder.tvGuanzu.setText(bean.getIs_likeEach() == 1 ? "已互关" : "+关注");
//
//        myHolder.tvGuanzu.setTag(position);
//        myHolder.tvGuanzu.setOnClickListener(myclick);

//
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//
//        long stamp=Long.valueOf(bean.getAddtime());
//          Date date=new Date(stamp*1000);


//        myHolder.tvPinglunText.setText(bean.getName());
//        myHolder.tvZhanli.setText(bean.getMoney()+"");

//        PinLenBean.DataBean dataBean=


//        myHolder.tvTgContext.setText(bean.getTitle());
//        myHolder.tvTgContextText.setText(bean.getContext());
//
//        myHolder.tvTgTime.setText(bean.getCreateTime());
//        myHolder.tvTgShape.setText("已分享" + bean.getCount() + "次");
//
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
        @BindView(R.id.tou_image)
        CircleImageView touImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_xin)
        ImageView ivXin;
        @BindView(R.id.tv_zan)
        TextView tvZan;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.content)
        TextView content;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
