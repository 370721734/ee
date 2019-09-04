package com.jarhero790.eub.record.adapter;

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
import com.jarhero790.eub.record.bean.MusicBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyHolder> {



    private Context context;
    private List<MusicBean.DataBean> list;
    private Myclick myclick;
    private Myclick touclick;
    private Myclick speak;

    public MusicAdapter(Context context, List<MusicBean.DataBean> list, Myclick myclick, Myclick touclick, Myclick speak) {
        this.context = context;
        this.list = list;
        this.myclick = myclick;
        this.touclick = touclick;
        this.speak = speak;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = View.inflate(context, R.layout.item_music_zhuang, null);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        MusicBean.DataBean bean = list.get(position);

        Glide.with(context).load( bean.getMusic_img()).apply(new RequestOptions().placeholder(R.mipmap.mine_bg).error(R.mipmap.mine_bg)).into(myHolder.ivVideoImage);
        myHolder.tvName.setText(bean.getNickname());
        myHolder.tvMemo.setText(bean.getName());
        myHolder.tvText.setText(bean.getId()+"");//没有



//        if (bean.getAddtime().length() > 10) {
//            myHolder.tvTime.setText(bean.getAddtime().substring(0, 10));
//        }
//
//
//        myHolder.tvGuanzu.setText(bean.getIs_likeEach() == 1 ? "已互关" : "+关注");
//
        myHolder.rl.setTag(position);
        myHolder.rl.setOnClickListener(touclick);
//
        myHolder.recordAdd.setTag(position);
        myHolder.recordAdd.setOnClickListener(speak);
//
//        myHolder.speak.setTag(position);
//        myHolder.speak.setOnClickListener(speak);

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
        @BindView(R.id.iv_video_image)
        ImageView ivVideoImage;
        @BindView(R.id.iv_play)
        ImageView ivPlay;
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_memo)
        TextView tvMemo;
        @BindView(R.id.record_add)
        TextView recordAdd;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
