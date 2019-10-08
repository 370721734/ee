package com.jarhero790.eub.message.souye;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.jarhero790.eub.message.bean.SearchBean;
import com.jarhero790.eub.message.bean.SearchResultBean;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TikTokThreeAdapter extends RecyclerView.Adapter<TikTokThreeAdapter.VideoHolder> {

    private List<SearchResultBean.DataBean.VideoBean> videos;


    private Context context;
    RotateAnimation rotateAnimation;//旋转动画
    private TikTokThreeAdapter.OnItemClickListener mOnItemClickListerer;

    //为RecyclerView的Item添加监听
    public interface OnItemClickListener {
        void onItemClick(int position, String type, View view, View view1, View view2);
    }

    public void setOnItemClickListerer(TikTokThreeAdapter.OnItemClickListener listerer) {
        this.mOnItemClickListerer = listerer;
    }

    private boolean isshow = false;

    public TikTokThreeAdapter(List<SearchResultBean.DataBean.VideoBean> videos, Context context) {
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
    public void onViewRecycled(@NonNull TikTokThreeAdapter.VideoHolder holder) {
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


        SearchResultBean.DataBean.VideoBean video = videos.get(position);
            Glide.with(context)
                    .load(video.getVideo_img())
                    .apply(new RequestOptions().placeholder(android.R.color.black))
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
            //点赞数量
            holder.tv_like.setText(video.getZan() + "");
            //评论数量
//            holder.tv_pinglun.setText(video.getCommentNum() + "");
            //财富`
            holder.caifu.setText(video.getCaifu() + "");

        //商品浏览数量
        holder.tvgoodsnum.setText(video.getClick_num());
            //tou
            Glide.with(context).load(video.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo)
                    .error(R.mipmap.zuanshi_logo)).into(holder.userimage);
            //旋转图
            Glide.with(context).load(video.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo)
                    .error(R.mipmap.zuanshi_logo)).into(holder.circleImageView);

            //zan
            if (video.getIs_like().equals("1")){
//                holder.iv_like.setImageDrawable(context.getResources().getDrawable(R.mipmap.iv_like_selected));
                holder.iv_like.setSelected(true);
            }else {
//                holder.iv_like.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_video_heart));
                holder.iv_like.setSelected(false);
            }



//        Log.e("--------",Api.GIFT+video.getHeadimgurl());
        holder.circleImageView.startAnimation(rotateAnimation);


        //关注
        if ((video.getUid()+"").equals(SharePreferenceUtil.getUserid(AppUtils.getContext()))){
            holder.btn_attention.setVisibility(View.INVISIBLE);
        }else {
            holder.btn_attention.setVisibility(View.VISIBLE);
            if (video.getIs_like().equals("1")){
                holder.btn_attention.setText("已关注");
            }else {
                holder.btn_attention.setText("+关注");
            }
        }



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


    private void onClick(TikTokThreeAdapter.VideoHolder holder, int position) {
        //点赞
        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "点赞", view, holder.tv_like, view);
            }
        });

        //评论
        holder.iv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "评论", view, holder.tv_pinglun, view);
            }
        });

        //分享
        holder.iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "分享", view, view, view);
            }
        });

        //礼物
        holder.iv_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "礼物", view, view, view);
            }
        });

        //关注
        holder.btn_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "关注", view, view, view);
            }
        });

        //红心ok
        holder.rlhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListerer != null) {
                    mOnItemClickListerer.onItemClick(position, "红心", view, view, view);

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
                mOnItemClickListerer.onItemClick(position, "红心", view, view, view);
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

        //back
        holder.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "返回", view, view, view);
            }
        });
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
        ImageView play_pause,back;

        TextView caifu,tvgoodsnum;
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
            tvgoodsnum=itemView.findViewById(R.id.tvgoodsnum);
            back=itemView.findViewById(R.id.back);
        }
    }


    public boolean isIsshow() {
        return isshow;
    }

    public void setIsshow(boolean isshow) {
        this.isshow = isshow;
    }
}