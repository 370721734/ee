package com.jarhero790.eub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.AttentionUserVideosComment;
import com.jarhero790.eub.bean.AttentionVideo;
import com.jarhero790.eub.message.attention.AttPinLAdapter;
import com.jarhero790.eub.message.attention.OnItemClickear;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


class AttentionVideosAdapter extends RecyclerView.Adapter<AttentionVideosAdapter.CustomViewHolder> {
    private ArrayList<AttentionVideo> attentionUsersVideos;
    private Context mcontext;

//    public interface OnItemClick{
//        void linerck(int position,String type,View view);
//    }
//    private OnItemClick onItemClick;
//
//    public void setOnItemClick(OnItemClick onItemClick) {
//        this.onItemClick = onItemClick;
//    }

  private  OnItemClickear onItemClickear;


    //13410484747测试账号
    public AttentionVideosAdapter(ArrayList<AttentionVideo> attentionUsersVideos,Context context,OnItemClickear onItemClickear){
        this.attentionUsersVideos=attentionUsersVideos;
        mcontext=context;
        this.onItemClickear = onItemClickear;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomViewHolder holder = new CustomViewHolder(LayoutInflater.from(AppUtils.getContext()).inflate(R.layout.item_attention_users_videos, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        AttentionVideo attentionVideo=attentionUsersVideos.get(position);
        ArrayList<AttentionUserVideosComment> comments=attentionVideo.getComment();
        String videoImg=attentionVideo.getVideo_img();
//        Log.e("-------------hed",videoImg);
        if (videoImg.startsWith("http")){
            Glide.with(mcontext).load(videoImg).apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor))
                    .into(holder.ivdeault);
        }else {
            Glide.with(mcontext).load(Api.TU+videoImg).apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor))
                    .into(holder.ivdeault);
        }
        Glide.with(mcontext).load(attentionVideo.getHeadimgurl()).apply(new RequestOptions()
                .placeholder(R.mipmap.zuanshi_logo).error(R.mipmap.zuanshi_logo)).into(holder.userimage);
//        if (attentionVideo.getHeadimgurl().startsWith("http")){
//
//        }else {
//            Glide.with(mcontext).load(Api.TU+attentionVideo.getHeadimgurl()).apply(new RequestOptions()
//                    .placeholder(R.mipmap.zuanshi_logo).error(R.mipmap.zuanshi_logo)).into(holder.userimage);
//        }

        holder.zan.setText(attentionVideo.getZan()+"人赞过");

        if (attentionVideo.getIs_zan().equals("1")){
            holder.videolike.setImageResource(R.drawable.iv_like_selected);
        }else {
            holder.videolike.setImageResource(R.drawable.iv_like_unselected);
        }


//        if (attentionVideo.getComment()!=null && attentionVideo.getComment().size()>0){
//
//        }


        String vedioTitle=attentionVideo.getTitle();
        holder.title.setText(vedioTitle);
        holder.attentionsUserName.setText(attentionVideo.getNickname());
        String time=attentionVideo.getAddtime();
         if (time.length()>9){
             holder.date.setText(time.substring(5,10));
         }else {
             holder.date.setText(time);
         }

        Glide.with(AppUtils.getContext()).load(R.mipmap.icon_video_share).into(holder.share);

        ArrayList<AttentionUserVideosComment> comment = attentionUsersVideos.get(position).getComment();
        if (comment.size()>0){
            holder.tvmore.setText("查看全部"+comment.size()+"条评论");
        }else {
            holder.tvmore.setText("暂无评论");
        }

        AttPinLAdapter attPinLAdapter=new AttPinLAdapter(mcontext,comment);
        holder.listView.setAdapter(attPinLAdapter);


        holder.videoPlayer.setUrl(attentionUsersVideos.get(position).getUrl());
        holder.videoPlayer.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
        holder.videoPlayer.start();


        onClick(holder,position);
    }

    private void onClick(CustomViewHolder holder, int position) {
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickear!=null){
                    onItemClickear.linerck(position,"分享",view,view);
                }
            }
        });
        holder.pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickear!=null){
                    onItemClickear.linerck(position,"评论",view,view);
                }
            }
        });
        holder.videolike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickear!=null){
                    onItemClickear.linerck(position,"点赞",view,view);
                }
            }
        });
        holder.ivplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickear!=null){
                    onItemClickear.linerck(position,"播放",view,holder.videoPlayer);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return attentionUsersVideos.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView date,attentionsUserName,zan,title,tvmore;
        private VideoView videoPlayer;
        private ImageView share;
        private ImageView pinglun;
        private ImageView videolike;

        CircleImageView userimage;
        ImageView ivdeault;

        ImageView ivplay;

        ListView listView;


        public CustomViewHolder(View view) {
            super(view);
            videoPlayer=view.findViewById(R.id.video_player);
            share=view.findViewById(R.id.share);
            pinglun=view.findViewById(R.id.pinglun);
            videolike=view.findViewById(R.id.videolike);
            date=view.findViewById(R.id.date);
            attentionsUserName=view.findViewById(R.id.attentionsUserName);
            userimage=view.findViewById(R.id.attentionsUserIcon);
            ivdeault=view.findViewById(R.id.iv_deault);
            zan=view.findViewById(R.id.zanshu);
            ivplay=view.findViewById(R.id.iv_play);
            title=view.findViewById(R.id.tv_text);
            listView=view.findViewById(R.id.lv);
            tvmore=view.findViewById(R.id.tv_more);
        }
    }
}

