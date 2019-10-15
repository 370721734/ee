package com.jarhero790.eub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.controller.MediaPlayerControl;
import com.dueeeke.videoplayer.player.VideoView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.ui.souye.child.SouyeFragment;
import com.jarhero790.eub.utils.AppUtils;


public class TikTokController extends BaseVideoController {

    private ImageView thumb;
    private ImageView iv_thumb_heng;
//    private boolean isheng;
//    private String url;

    public TikTokController(@NonNull Context context) {
        super(context);
    }

//    public void TikTokContro(boolean ish,String ur){
//        isheng=ish;
//          url=ur;
//    }

    public TikTokController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TikTokController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_tiktok_controller;
    }

    @Override
    protected void initView() {
        super.initView();
        thumb = mControllerView.findViewById(R.id.iv_thumb);
        iv_thumb_heng = mControllerView.findViewById(R.id.iv_thumb_heng);



//        SouyeFragment.newInstance().setTuaddurl(new SouyeFragment.Tuaddurl() {
//            @Override
//            public void onClickear(boolean isheng, String url) {
//                if (isheng) {
//                    //"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/e2be68c318c2ba58ba3edf63020cbb93.png"
//                    RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    thumb.setLayoutParams(params);
//                    params.addRule(RelativeLayout.CENTER_VERTICAL);
//                    thumb.setScaleType(ImageView.ScaleType.FIT_XY);
//                    thumb.setAdjustViewBounds(true);
//                    Glide.with(AppUtils.getContext()).load(url)
//                            .apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor).diskCacheStrategy(DiskCacheStrategy.ALL))
//                            .into(thumb);
//                    Log.e("---------","横"+url);
//
//                }else {
//                    //"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/f809a817ff1b1f7dada462b000ce19c3.png"
////                    RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
////                    thumb.setLayoutParams(params);
////                    params.addRule(RelativeLayout.CENTER_VERTICAL);
//                    Glide.with(AppUtils.getContext()).load(url)
//                            .apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor).diskCacheStrategy(DiskCacheStrategy.ALL))
//                            .into(thumb);
//                    Log.e("---------","竖"+url);
//
//                }
//            }
//        });


    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);
        switch (playState) {
            case VideoView.STATE_IDLE:
                //L.e("STATE_IDLE");
               thumb.setVisibility(VISIBLE);
                iv_thumb_heng.setVisibility(VISIBLE);
//                Log.e("--------------","显示图片");
                break;
            case VideoView.STATE_PLAYING:
                //L.e("STATE_PLAYING");
                thumb.setVisibility(GONE);
                iv_thumb_heng.setVisibility(GONE);
//                Log.e("--------------","隐藏图片");
                break;
            case VideoView.STATE_PREPARED:
               // L.e("STATE_PREPARED");
                break;
//            case VideoView.STATE_ERROR:
//                if (videoView!=null){
//
//
//                    if (url.length()>0){
//                        videoView.release();
//                        videoView.resume();
//                        videoView.setUrl(url);
//                        videoView.start();
////                    if (!videoView.isPlaying()){
////                        videoView.start();
////                    }
//                        videoView.setLooping(true);
//                        Log.e("-------------err","eeerr");
//                    }
////
//                }else {
//                    Log.e("-------------err","eeerr2");
//                }
//
//                break;

        }
//
    }

    public ImageView getThumb() {
        return thumb;
    }
    public ImageView getThumbHeng() {
        return iv_thumb_heng;
    }


}
