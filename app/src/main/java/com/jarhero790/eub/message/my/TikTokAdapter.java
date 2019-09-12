package com.jarhero790.eub.message.my;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.bean.MyFaBuBean;
import com.jarhero790.eub.message.souye.GuanPanView;
import com.jarhero790.eub.message.souye.Love;
import com.jarhero790.eub.model.bean.souye.VideoBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TikTokAdapter extends RecyclerView.Adapter<TikTokAdapter.VideoHolder> {

    private List<MyFaBuBean.DataBean> videos;
    private Context context;
    RotateAnimation rotateAnimation;//旋转动画
    private TikTokAdapter.OnItemClickListener mOnItemClickListerer;

    //为RecyclerView的Item添加监听
    public interface OnItemClickListener {
        void onItemClick(int position, String type, View view,View view2,View view3);
    }

    public void setOnItemClickListerer(TikTokAdapter.OnItemClickListener listerer) {
        this.mOnItemClickListerer = listerer;
    }

    private boolean isshow = false;

    public TikTokAdapter(List<MyFaBuBean.DataBean> videos, Context context) {
        this.videos = videos;
        this.context = context;

        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(4000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_tik_tok, parent, false);
        return new VideoHolder(itemView);

    }

    /**
     * 重写onViewRecycled方法，当item被隐藏的时候，调用 Glide.with(context).clear(imageView)对资源进行清理;
     */
    @Override
    public void onViewRecycled(@NonNull TikTokAdapter.VideoHolder holder) {
        super.onViewRecycled(holder);
        ImageView imageView1 = holder.thumb;
        ImageView imageView2 = holder.iv_like;
        ImageView imageView3 = holder.iv_commit;
        ImageView imageView4 = holder.iv_share;
        ImageView imageView5 = holder.iv_gift;

        if (imageView1 != null) {
            Glide.with(context).clear(imageView1);
        }
        if (imageView2 != null) {
            Glide.with(context).clear(imageView2);
        }
        if (imageView3 != null) {
            Glide.with(context).clear(imageView3);
        }
        if (imageView4 != null) {
            Glide.with(context).clear(imageView4);
        }
        if (imageView5 != null) {
            Glide.with(context).clear(imageView5);
        }
    }

    @Override
    public void onBindViewHolder(final VideoHolder holder, int position) {

        MyFaBuBean.DataBean video = videos.get(position);
        Glide.with(context)
                .load(video.getVideo_img())
                .apply(new RequestOptions().placeholder(android.R.color.white))
                .into(holder.thumb);


        //标题
        if (video.getTitle() != null && video.getTitle().length() > 0) {
            holder.tv_content.setVisibility(View.VISIBLE);
            holder.tv_content.setText(video.getTitle());
        } else {
            holder.tv_content.setVisibility(View.GONE);
        }

//        holder.tv_content.setText("钻视tv迭代开发火热进行中，请耐心等待下一个    版本的到来 ");
        holder.tv_uname.setText("@" + video.getNickname());


        if (video.getIs_zan() == 1) {
            holder.iv_like.setSelected(true);
        } else {
            holder.iv_like.setSelected(false);
        }

        //点赞数量
        holder.tv_like.setText(video.getZan() + "");
        //评论数量
        holder.tv_pinglun.setText(video.getCommentNum() + "");
        //财富`
        holder.caifu.setText(video.getCaifu() + "");
        //tou
        if (video.getHeadimgurl().startsWith("http")) {
            Glide.with(context).load(video.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.souye_logo)
                    .error(R.mipmap.souye_logo)).into(holder.userimage);
        } else {
            Glide.with(context).load(Api.TU + video.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.souye_logo)
                    .error(R.mipmap.souye_logo)).into(holder.userimage);
        }

        //旋转图
        if (video.getHeadimgurl().startsWith("http")) {
            Glide.with(context).load(video.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.edit_tou_icon)
                    .error(R.mipmap.edit_tou_icon)).into(holder.circleImageView);
        } else {
            Glide.with(context).load(Api.TU + video.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.edit_tou_icon)
                    .error(R.mipmap.edit_tou_icon)).into(holder.circleImageView);
        }




//        Log.e("--------",Api.GIFT+video.getHeadimgurl());
        holder.circleImageView.startAnimation(rotateAnimation);

        holder.btn_attention.setText(video.getIs_like()==1?"已关注":"关注");//自己的

        if (video.getGood_id().equals("0")){
            holder.bussiness.setVisibility(View.INVISIBLE);
        }else {
            holder.bussiness.setVisibility(View.VISIBLE);
        }


//        holder.guanPanView.init();
//        holder.guanPanView.mergeThumbnailBitmap(video.getHeadimgurl());
//        holder.guanPanView.startAnimation(rotateAnimation);
        onClick(holder, position);


    }


    private void onClick(TikTokAdapter.VideoHolder holder, int position) {
        //点赞
        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "点赞", view,holder.tv_like,view);
            }
        });

        //评论
        holder.iv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "评论", view,holder.tv_pinglun,view);
            }
        });

        //分享
        holder.iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "分享", view,view,view);
            }
        });

        //礼物
        holder.iv_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "礼物", view,view,view);
            }
        });

        //关注
        holder.btn_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "关注", view,view,view);
            }
        });

        //红心ok
        holder.rlhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListerer != null) {
                    mOnItemClickListerer.onItemClick(position, "红心", view,view,view);

                    if (isIsshow()) {
                        holder.play_pause.setVisibility(View.VISIBLE);
                    } else {
                        holder.play_pause.setVisibility(View.GONE);
                    }
                }

            }
        });

        //ok
        holder.play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "红心", view,view,view);
                if (isIsshow()) {
                    holder.play_pause.setVisibility(View.VISIBLE);
                } else {
                    holder.play_pause.setVisibility(View.GONE);
                }
            }
        });

        //hong
        holder.love.setLoveTrue(new Love.LoveTrue() {
            @Override
            public void Onclick(boolean love) {
                if (love){
//                    Log.e("-----","hehe");
                    mOnItemClickListerer.onItemClick(position, "红红", holder.love, holder.iv_like, holder.tv_like);
                }
            }
        });
        //business
        holder.bussiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                mOnItemClickListerer.onItemClick(position, "商城", view, holder.bussiness, view);
//                Log.e("-----","hehe");
            }
        });
//        holder.love.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                View view=new View(context);
//                mOnItemClickListerer.onItemClick(position, "红红", view, holder.iv_like, holder.tv_like);
////                Log.e("-----","hehe");
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        private ImageView thumb;

        RelativeLayout relativeLayout;
        ImageView iv_like;
        ImageView iv_commit;
        ImageView iv_share;
        ImageView iv_gift;
        TextView tv_content;
        TextView tv_uname;
        TextView tv_like;
        TextView tv_pinglun;
        CircleImageView circleImageView;
        //关注按钮
        Button btn_attention;
        VideoView videoView;
        CircleImageView userimage;

        GuanPanView guanPanView;
        RelativeLayout rlhead;
        ImageView play_pause;

        TextView caifu;
        Love love;
        RelativeLayout bussiness;

        VideoHolder(View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.thumb);

            relativeLayout = itemView.findViewById(R.id.rl_all);

            iv_commit = itemView.findViewById(R.id.iv_commit);
            iv_like = itemView.findViewById(R.id.iv_like);
            iv_share = itemView.findViewById(R.id.iv_share);
            iv_gift = itemView.findViewById(R.id.iv_gift);

            tv_content = itemView.findViewById(R.id.tv_content);
            tv_uname = itemView.findViewById(R.id.tv_uname);
            tv_like = itemView.findViewById(R.id.tv_like);
            tv_pinglun = itemView.findViewById(R.id.tv_pinglun);

            btn_attention = itemView.findViewById(R.id.btn_attention);
            circleImageView = itemView.findViewById(R.id.circleImageView);
            videoView = itemView.findViewById(R.id.videoView);

            userimage = itemView.findViewById(R.id.souye_logo);
            guanPanView = itemView.findViewById(R.id.guanpan);

            rlhead = itemView.findViewById(R.id.rlhead);
            play_pause = itemView.findViewById(R.id.iv_play_pause);
            caifu = itemView.findViewById(R.id.tv_gold_coin);
            love = itemView.findViewById(R.id.love);
            bussiness=itemView.findViewById(R.id.bussiness);
        }
    }


    public boolean isIsshow() {
        return isshow;
    }

    public void setIsshow(boolean isshow) {
        this.isshow = isshow;
    }
}