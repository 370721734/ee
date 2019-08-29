package com.jarhero790.eub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.AttentionUserVideosComment;
import com.jarhero790.eub.bean.AttentionVideo;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


class AttentionVideosAdapter extends RecyclerView.Adapter<AttentionVideosAdapter.CustomViewHolder> {
    private ArrayList<AttentionVideo> attentionUsersVideos;
    private Context mcontext;

    //13410484747测试账号
    public AttentionVideosAdapter(ArrayList<AttentionVideo> attentionUsersVideos,Context context){
        this.attentionUsersVideos=attentionUsersVideos;
        mcontext=context;
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
        Log.e("-------------hed",videoImg);
        if (videoImg.startsWith("http")){
            Glide.with(mcontext).load(videoImg).apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor))
                    .into(holder.ivdeault);
        }else {
            Glide.with(mcontext).load(Api.TU+videoImg).apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor))
                    .into(holder.ivdeault);
        }

        if (attentionVideo.getHeadimgurl().startsWith("http")){
            Glide.with(mcontext).load(attentionVideo.getHeadimgurl()).apply(new RequestOptions()
                    .placeholder(R.mipmap.souye_logo).error(R.mipmap.souye_logo)).into(holder.userimage);
        }else {
            Glide.with(mcontext).load(Api.TU+attentionVideo.getHeadimgurl()).apply(new RequestOptions()
                    .placeholder(R.mipmap.souye_logo).error(R.mipmap.souye_logo)).into(holder.userimage);
        }

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
        holder.attentionsUserName.setText(vedioTitle);
        String time=attentionVideo.getAddtime();
         if (time.length()>9){
             holder.date.setText(time.substring(5,10));
         }else {
             holder.date.setText(time);
         }

        Glide.with(AppUtils.getContext()).load(R.mipmap.icon_video_share).into(holder.share);
    }

    @Override
    public int getItemCount() {
        return attentionUsersVideos.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView date,attentionsUserName,zan;
        private VideoView videoPlayer;
        private ImageView share;
        private ImageView pinglun;
        private ImageView videolike;

        CircleImageView userimage;
        ImageView ivdeault;


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
        }
    }
}

