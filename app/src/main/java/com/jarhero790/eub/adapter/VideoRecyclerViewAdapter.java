package com.jarhero790.eub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.AttentionUserVideosComment;
import com.jarhero790.eub.bean.AttentionVideo;
import com.jarhero790.eub.message.attention.AttPinLAdapter;
import com.jarhero790.eub.message.attention.OnItemClickear;
import com.jarhero790.eub.model.bean.souye.VideoBean;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.VideoHolder> {

//    private List<VideoBean> videos = new ArrayList<>();
    private List<AttentionVideo> videos = new ArrayList<>();
    private Context mcontext;

    private OnItemClickear onItemClickear;

//    private ProgressManagerImpl mProgressManager;

//    private PlayerFactory mPlayerFactory = ExoMediaPlayerFactory.create(MyApplication.getInstance());

    public VideoRecyclerViewAdapter(List<AttentionVideo> videos,Context context,OnItemClickear onItemClickear) {
        this.videos.addAll(videos);
        mcontext=context;
        this.onItemClickear = onItemClickear;
    }

    @Override
    @NonNull
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attention_users_videos, parent, false);
        return new VideoHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        AttentionVideo attentionVideo = videos.get(position);
//        ImageView thumb = holder.controller.getThumb();

        if (attentionVideo.getHeadimgurl().startsWith("http")){
            Glide.with(mcontext).load(attentionVideo.getHeadimgurl()).apply(new RequestOptions()
                    .placeholder(R.mipmap.souye_logo).error(R.mipmap.souye_logo)).into(holder.userimage);
        }else {
            Glide.with(mcontext).load(Api.TU+attentionVideo.getHeadimgurl()).apply(new RequestOptions()
                    .placeholder(R.mipmap.souye_logo).error(R.mipmap.souye_logo)).into(holder.userimage);
        }
        String videoImg=attentionVideo.getVideo_img();
        if (videoImg.startsWith("http")){
            Glide.with(mcontext).load(videoImg).apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor))
                    .into(holder.ivdeault);
        }else {
            Glide.with(mcontext).load(Api.TU+videoImg).apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor))
                    .into(holder.ivdeault);
        }
        holder.zan.setText(attentionVideo.getZan()+"人赞过");

        if (attentionVideo.getIs_zan().equals("1")){
            holder.videolike.setImageResource(R.drawable.iv_like_selected);
        }else {
            holder.videolike.setImageResource(R.drawable.iv_like_unselected);
        }

        holder.title.setText(attentionVideo.getTitle());
        holder.attentionsUserName.setText(attentionVideo.getNickname());
        String time=attentionVideo.getAddtime();
        if (time.length()>9){
            holder.date.setText(time.substring(5,10));
        }else {
            holder.date.setText(time);
        }


        ArrayList<AttentionUserVideosComment> comment = attentionVideo.getComment();
        if (comment.size()>0){
            holder.tvmore.setText("查看全部"+comment.size()+"条评论");
            holder.tvmore.setEnabled(true);
        }else {
            holder.tvmore.setText("暂无评论");
            holder.tvmore.setEnabled(false);
        }

        AttPinLAdapter attPinLAdapter=new AttPinLAdapter(mcontext,comment);
        holder.listView.setAdapter(attPinLAdapter);


        holder.videoPlayer.setUrl(attentionVideo.getUrl());
        holder.videoPlayer.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
        holder.videoPlayer.start();


        onClick(holder,position);

//        holder.mVideoView.setUrl(attentionVideo.getUrl());

//        holder.mVideoView.setVideoController(holder.controller);//播放暂停控制器
        //holder.title.setText(videoBean.getTitle());

        //保存播放进度
//        if (mProgressManager == null)
//            mProgressManager = new ProgressManagerImpl();
//        holder.mVideoView.setProgressManager(mProgressManager);
//        holder.mVideoView.setCustomMediaPlayer(new ExoMediaPlayer(holder.itemView.getContext()));
//        holder.mVideoView.setPlayerFactory(mPlayerFactory);
    }

    private void onClick(VideoHolder holder, int position) {
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

        holder.tvmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickear!=null){
                    onItemClickear.linerck(position,"更多",view,view);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }







    public void addData(List<AttentionVideo> videoList) {
        int size = videos.size();
        videos.addAll(videoList);
        //使用此方法添加数据，使用notifyDataSetChanged会导致正在播放的视频中断
        notifyItemRangeChanged(size, videos.size());
    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        private VideoView videoPlayer;
//        private StandardVideoController controller; 控制器
       private TextView date,attentionsUserName,zan,title,tvmore;
        private ImageView share;
        private ImageView pinglun;
        private ImageView videolike;

        CircleImageView userimage;
        ImageView ivdeault;

        ImageView ivplay;

        ListView listView;

        VideoHolder(View itemView) {
            super(itemView);
            videoPlayer = itemView.findViewById(R.id.video_player);
            share=itemView.findViewById(R.id.share);
            pinglun=itemView.findViewById(R.id.pinglun);
            videolike=itemView.findViewById(R.id.videolike);
            date=itemView.findViewById(R.id.date);
            attentionsUserName=itemView.findViewById(R.id.attentionsUserName);
            userimage=itemView.findViewById(R.id.attentionsUserIcon);
            ivdeault=itemView.findViewById(R.id.iv_deault);
            zan=itemView.findViewById(R.id.zanshu);
            ivplay=itemView.findViewById(R.id.iv_play);
            title=itemView.findViewById(R.id.tv_text);
            listView=itemView.findViewById(R.id.lv);
            tvmore=itemView.findViewById(R.id.tv_more);

            //循环播放
            videoPlayer.setLooping(true);
            //非常重要
            videoPlayer.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);

            int widthPixels = itemView.getContext().getResources().getDisplayMetrics().widthPixels;
            int heightPixels= itemView.getContext().getResources().getDisplayMetrics().heightPixels;
            //mVideoView.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, widthPixels * 9 / 16 + 1));

//            mVideoView.setLayoutParams(new RelativeLayout.LayoutParams(widthPixels/10*8,
//                                                                     heightPixels/10*5)); //设置视频的宽高

//            mVideoView.setPadding(15,20,0,5);

//            controller = new StandardVideoController(itemView.getContext());



            title = itemView.findViewById(R.id.tv_text);
        }
    }
}