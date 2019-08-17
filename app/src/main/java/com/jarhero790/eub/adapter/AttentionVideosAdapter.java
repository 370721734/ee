package com.jarhero790.eub.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.bean.AttentionUserVideosComment;
import com.jarhero790.eub.bean.AttentionVideo;
import com.jarhero790.eub.utils.AppUtils;
import java.util.ArrayList;


class AttentionVideosAdapter extends RecyclerView.Adapter<AttentionVideosAdapter.CustomViewHolder> {
    private ArrayList<AttentionVideo> attentionUsersVideos;

    //13410484747测试账号
    public AttentionVideosAdapter(ArrayList<AttentionVideo> attentionUsersVideos){
        this.attentionUsersVideos=attentionUsersVideos;
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
        String vedioTitle=attentionVideo.getTitle();
        String time=attentionVideo.getAddtime();
        holder.date.setText(time);
        Glide.with(AppUtils.getContext()).load(R.mipmap.icon_video_share).into(holder.share);
    }

    @Override
    public int getItemCount() {
        return attentionUsersVideos.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private VideoView videoPlayer;
        private ImageView share;
        private ImageView pinglun;
        private ImageView videolike;

        public CustomViewHolder(View view) {
            super(view);
            videoPlayer=view.findViewById(R.id.video_player);
            share=view.findViewById(R.id.share);
            pinglun=view.findViewById(R.id.pinglun);
            videolike=view.findViewById(R.id.videolike);
            date=view.findViewById(R.id.date);
        }
    }
}

