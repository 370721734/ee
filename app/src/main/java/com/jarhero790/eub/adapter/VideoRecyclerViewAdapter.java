package com.jarhero790.eub.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.model.bean.souye.VideoBean;
import java.util.ArrayList;
import java.util.List;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.VideoHolder> {

    private List<VideoBean> videos = new ArrayList<>();

//    private ProgressManagerImpl mProgressManager;

//    private PlayerFactory mPlayerFactory = ExoMediaPlayerFactory.create(MyApplication.getInstance());

    public VideoRecyclerViewAdapter(List<VideoBean> videos) {
        this.videos.addAll(videos);
    }

    @Override
    @NonNull
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_attention, parent, false);
        return new VideoHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        VideoBean videoBean = videos.get(position);
        ImageView thumb = holder.controller.getThumb();
        Glide.with(thumb.getContext())
                .load(videoBean.getThumb())
                .apply(new RequestOptions().placeholder(android.R.color.white))
                .into(thumb);
        holder.mVideoView.setUrl(videoBean.getUrl());
        holder.controller.setTitle(videoBean.getTitle());
        holder.mVideoView.setVideoController(holder.controller);
        //holder.title.setText(videoBean.getTitle());
        holder.title.setText("遨游互动科技有限公司");
        //保存播放进度
//        if (mProgressManager == null)
//            mProgressManager = new ProgressManagerImpl();
//        holder.mVideoView.setProgressManager(mProgressManager);
//        holder.mVideoView.setCustomMediaPlayer(new ExoMediaPlayer(holder.itemView.getContext()));
//        holder.mVideoView.setPlayerFactory(mPlayerFactory);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }


    public void addData(List<VideoBean> videoList) {
        int size = videos.size();
        videos.addAll(videoList);
        //使用此方法添加数据，使用notifyDataSetChanged会导致正在播放的视频中断
        notifyItemRangeChanged(size, videos.size());
    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        private VideoView mVideoView;
        private StandardVideoController controller;
        private TextView title;

        VideoHolder(View itemView) {
            super(itemView);
            mVideoView = itemView.findViewById(R.id.videoPlayer);
            //循环播放
            mVideoView.setLooping(true);
            //非常重要
            mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);

            int widthPixels = itemView.getContext().getResources().getDisplayMetrics().widthPixels;
            int heightPixels= itemView.getContext().getResources().getDisplayMetrics().heightPixels;
            //mVideoView.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, widthPixels * 9 / 16 + 1));

            mVideoView.setLayoutParams(new LinearLayout.LayoutParams(widthPixels/10*8,
                                                                     heightPixels/10*5));

            mVideoView.setPadding(15,20,0,5);

            controller = new StandardVideoController(itemView.getContext());


            title = itemView.findViewById(R.id.item_name);
        }
    }
}