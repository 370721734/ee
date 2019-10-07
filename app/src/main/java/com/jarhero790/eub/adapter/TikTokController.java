package com.jarhero790.eub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.controller.MediaPlayerControl;
import com.dueeeke.videoplayer.player.VideoView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jarhero790.eub.R;


public class TikTokController extends BaseVideoController {

    //private SimpleDraweeView thumb;
    private ImageView thumb;

    private VideoView videoView;


    private String url;

    public TikTokController(@NonNull Context context,VideoView m,String u) {
        super(context);
        videoView=m;
        url=u;
    }

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
    }

    @Override
    public void setPlayState(int playState) {


        switch (playState) {
            case VideoView.STATE_IDLE:
                //L.e("STATE_IDLE");
               thumb.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PLAYING:
                //L.e("STATE_PLAYING");
                thumb.setVisibility(GONE);
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
//        super.setPlayState(playState);
    }

    public ImageView getThumb() {
        return thumb;
    }
}
