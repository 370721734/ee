package com.jarhero790.eub.adapter;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.Video;
import com.jarhero790.eub.message.souye.GuanPanView;
import com.jarhero790.eub.message.souye.Love;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//播放视频适配
public class TikTokAdapter extends RecyclerView.Adapter<TikTokAdapter.VideoHolder> {

    private List<Video> videos;
    private Context context;
    RotateAnimation rotateAnimation;//旋转动画
    private TikTokAdapter.OnItemClickListener mOnItemClickListerer;

    private boolean isshow = false;

    //为RecyclerView的Item添加监听
    public interface OnItemClickListener {
        void onItemClick(int position, String type, View view, View view1, View view2);
    }

    public void setOnItemClickListerer(TikTokAdapter.OnItemClickListener listerer) {
        this.mOnItemClickListerer = listerer;
    }


    public TikTokAdapter(List<Video> videos, Context context) {
        this.videos = videos;
        this.context = context;
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(4000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
    }


    /**
     * 当Item进入这个页面的时候调用
     */
    @Override
    public void onViewAttachedToWindow(@NonNull VideoHolder holder) {
        super.onViewAttachedToWindow(holder);
    }


    /**
     * 当Item离开这个页面的时候调用
     */
    @Override
    public void onViewDetachedFromWindow(@NonNull VideoHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }


    /**
     * 加载ViewHolder的布局
     */
    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.souye_vedio_recyleview_item, parent, false);
        return new VideoHolder(itemView);
    }


    /**
     * 获取显示类型，返回值可在onCreateViewHolder中拿到，以决定加载哪种ViewHolder

     @Override public int getItemViewType(int position) {
     return super.getItemViewType(position);
     }
     */


    /**
     * 重写onViewRecycled方法，当item被隐藏的时候，调用 Glide.with(context).clear(imageView)对资源进行清理;
     */
    @Override
    public void onViewRecycled(@NonNull VideoHolder holder) {
        super.onViewRecycled(holder);
        //注释一下试
        ImageView imageView1 = holder.video_thumb;
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


    /**
     * 将数据绑定到布局上，以及业务逻辑的控制就写在该方法
     */
    @Override
    public void onBindViewHolder(final VideoHolder holder, int position) {

//        Log.e("------------------", "??????" + position);
        Video video = videos.get(position);
        /** Fresco方式加载
         Uri uri = Uri.parse(video.getVideo_img());
         holder.video_thumb.setImageURI(uri);
         **/
        Log.e("---------tu",video.getHeadimgurl()+"   "+video.getId());//没有图片
        /**Glide方式*/
//        Glide.with(context).load(video.getVideo_img())
//                .apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor))
//                .into(holder.video_thumb);

        if (video.getAnyhow().equals("1")) {
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            holder.video_thumb.setLayoutParams(params);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            Glide.with(context).load(video.getVideo_img())
                    .apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(holder.video_thumb);
//            Log.e("---------","竖");

        }else {
//            holder.video_thumb.setMaxHeight(400);
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.video_thumb.setLayoutParams(params);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            holder.video_thumb.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.video_thumb.setAdjustViewBounds(true);
            Glide.with(context).load(video.getVideo_img())
                    .apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(holder.video_thumb);
//            Log.e("---------","横");
        }

        if (video.getIs_zan().equals("1")) {
//            holder.iv_like.setImageResource(R.drawable.iv_like_selected);
            holder.iv_like.setSelected(true);
        } else {
//            holder.iv_like.setImageResource(R.drawable.iv_like_unselected);
            holder.iv_like.setSelected(false);
        }


        if (video.getTitle() != null && video.getTitle().length() > 0) {
            holder.tv_content.setVisibility(View.VISIBLE);
            holder.tv_content.setText(video.getTitle());
        } else {
            holder.tv_content.setVisibility(View.GONE);
        }

//        holder.tv_content.setText("钻视tv迭代开发火热进行中，请耐心等待下一个    版本的到来 ");
        holder.tv_uname.setText("@" + video.getNickname());
        //点赞数量
        holder.tv_like.setText(video.getZan()+"");
        //评论数量
        holder.tv_pinglun.setText(video.getCommentNum()+"");
//        Log.e("-----------ping=",video.getCommentNum());//????
        //财富`
        holder.caifu.setText(video.getCaifu());

        //商品浏览数量
        holder.tvgoodsnum.setText(video.getClick_num());

        //tou
        if (video.getHeadimgurl().startsWith("http")) {
            Glide.with(context).load(video.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo)
                    .error(R.mipmap.zuanshi_logo)).into(holder.userimage);
        } else {
            Glide.with(context).load(Api.TU + video.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo)
                    .error(R.mipmap.zuanshi_logo)).into(holder.userimage);
        }
        Glide.with(context).load(video.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo)
                .error(R.mipmap.zuanshi_logo)).into(holder.circleImageView);
        //旋转图
//        if (video.getHeadimgurl().startsWith("http")){
//
//        }else {
//            Glide.with(context).load(Api.TU+video.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.edit_tou_icon)
//                    .error(R.mipmap.edit_tou_icon)).into(holder.circleImageView);
//        }


        if (video.getUid().equals(SharePreferenceUtil.getUserid(AppUtils.getContext()))){
            holder.btn_attention.setVisibility(View.INVISIBLE);
        }else {
            holder.btn_attention.setVisibility(View.VISIBLE);
            //关注
            if (video.getIs_like().equals("1")){
                holder.btn_attention.setText("已关注");
            }else {
                holder.btn_attention.setText("+关注");
            }
        }

        holder.bussiness.setVisibility(View.INVISIBLE);// 暂时隐藏
//        if (video.getGood_id().equals("0")){
//            holder.bussiness.setVisibility(View.INVISIBLE);
//        }else {
//            holder.bussiness.setVisibility(View.VISIBLE);
//        }




//        Log.e("--------",Api.GIFT+video.getHeadimgurl());
        holder.circleImageView.startAnimation(rotateAnimation);


//        holder.guanPanView.init();
//        holder.guanPanView.mergeThumbnailBitmap(video.getHeadimgurl());
//        holder.guanPanView.startAnimation(rotateAnimation);
        onClick(holder, position);
    }


    private void onClick(VideoHolder holder, int position) {
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
                mOnItemClickListerer.onItemClick(position, "商城", view, holder.bussiness, holder.tvgoodsnum);
//                Log.e("-----","hehe");
            }
        });
        //tong
        holder.tongkuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                mOnItemClickListerer.onItemClick(position, "同款", view, view, view);
//                Log.e("-----","hehe");
            }
        });
//        holder.love.setCallback(new Love.ClickCallBack() {
//            @Override
//            public void onClick() {
//
//            }
//
//            @Override
//            public void onDoubleClick() {
//
//            }
//        });
//        holder.love.setMoveCallBack(new Love.MoveCallBack() {
//            @Override
//            public void onMove(float x, float y) {
//
//            }
//        });

//        holder.love.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //将mHints数组内的所有元素左移一个位置
//                System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
//                //获得当前系统已经启动的时间
//                mHints[mHints.length - 1] = SystemClock.uptimeMillis();
//
//                if (SystemClock.uptimeMillis()-mHints[0]>=500){
//
//                }
//            }
//        });


    }

    //需要监听几次点击事件数组的长度就为几
    //如果要监听双击事件则数组长度为2，如果要监听3次连续点击事件则数组长度为3...
    long[] mHints = new long[3];//初始全部为0

    @Override
    public int getItemCount() {
        return videos.size();
    }


    public static abstract class MyClcik implements View.OnClickListener {
        public abstract void Onclck(View v, int pos);

        @Override
        public void onClick(View v) {
            Onclck(v, (Integer) v.getTag());
        }
    }


    public class VideoHolder extends RecyclerView.ViewHolder {
        ImageView video_thumb;
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

        TextView caifu,tvgoodsnum;
        Love love;
        RelativeLayout bussiness;
        RelativeLayout tongkuang;


        VideoHolder(View itemView) {
            super(itemView);
            video_thumb = itemView.findViewById(R.id.souye_page_video_thumb);
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
            tongkuang=itemView.findViewById(R.id.tongkuang);
            tvgoodsnum=itemView.findViewById(R.id.tvgoodsnum);
        }
    }


    public void setVideos(List<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    public boolean isIsshow() {
        return isshow;
    }

    public void setIsshow(boolean isshow) {
        this.isshow = isshow;

    }


    public interface OnItemCL {
        void Cliek(View view, int pos);
    }

    private OnItemCL onItemCL;

    public void setOnItemCL(OnItemCL onItemCL) {
        this.onItemCL = onItemCL;
    }
}