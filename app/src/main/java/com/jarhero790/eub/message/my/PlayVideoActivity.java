package com.jarhero790.eub.message.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.listener.OnVideoViewStateChangeListener;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.OnViewPagerListener;
import com.jarhero790.eub.adapter.TikTokAdapter;
import com.jarhero790.eub.adapter.TikTokController;
import com.jarhero790.eub.adapter.ViewPagerLayoutManager;
import com.jarhero790.eub.bean.Video;
import com.jarhero790.eub.message.adapter.TikTokTwoAdapter;
import com.jarhero790.eub.message.bean.MyFaBuBean;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayVideoActivity extends AppCompatActivity implements ViewPagerLayoutManager.OnNextPageImageListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.frameLayoutContainer)
    FrameLayout frameLayoutContainer;
    @BindView(R.id.back)
    ImageView back;


    private TikTokController mTikTokController;
    private TikTokTwoAdapter tikTokAdapter;
    private VideoView mVideoView;
    ViewPagerLayoutManager layoutManager;

    private int position;
    ArrayList<MyFaBuBean.DataBean> list = new ArrayList<>();

//    private List<Video> videolist = new ArrayList<>();
    View viewplaypause;
    private int mCurrentPosition;//当前播放的第几个视频 ，

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        mTikTokController = new TikTokController(AppUtils.getContext());
        /**
         * 重要代码  类似抖音垂直加载
         */
        layoutManager = new ViewPagerLayoutManager(AppUtils.getContext(), OrientationHelper.VERTICAL);
        layoutManager.setOnNextPageImageListener(this);





        mVideoView = new VideoView(AppUtils.getContext());
        //视频循环播放
        mVideoView.setLooping(true);
        mTikTokController = new TikTokController(AppUtils.getContext());
        mVideoView.setVideoController(mTikTokController);



        Intent intent=getIntent();

         position=intent.getIntExtra("position",0);
         list= (ArrayList<MyFaBuBean.DataBean>) intent.getSerializableExtra("vidlist");
         Log.e("-----------","a="+position);
         Log.e("-----------","b="+list.size());


         if (list!=null && list.size()>0){

//             for (int i = 0; i < list.size(); i++) {
//                 videolist.add(new Video(list.get(i).getUid()+"",list.get(i).getUrl(),list.get(i).getZan()+"",list.get(i).getCaifu()+"",list.get(i).getVideo_img()));
//             }
//
//
//
//             if (videolist!=null && videolist.size()>0){
//
//             }
             tikTokAdapter = new TikTokTwoAdapter(list, AppUtils.getContext());
             recyclerView.setLayoutManager(layoutManager);
             recyclerView.setAdapter(tikTokAdapter);

             mVideoView.resume();
//             flag.set(false);
//             videosPageListenerOnListener();
//             adapterSetOnItemClickListerer();
             startPlay(position);
             videosPageListenerOnListener();
         }

    }






    private void startPlay(int position) {
        Log.e("-------eeeee","="+position+"  "+list.get(position).getUrl());
        // 加载视频的预览图片 Glide方式
        Glide.with(this)
                .load(list.get(position).getVideo_img())
                .apply(new RequestOptions().placeholder(android.R.color.white))
                .into(mTikTokController.getThumb());
        //设置播放的url
        mVideoView.setUrl(list.get(position).getUrl());
        //重要
        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
        //开始播放视频
        mVideoView.start();

        View itemView = recyclerView.getChildAt(0);
        //开始播放视频
        mVideoView.start();


        //确定高度
        mVideoView.setOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int playerState) {
//                Log.e("---Player",""+playerState);
//                int[] size= mVideoView.getVideoSize();
//                String value="位置"+position+",width:"+size[0]+" "+",height:"+size[1];
//                Log.e("Android短视频5",value);


            }

            @Override
            public void onPlayStateChanged(int playState) {

//                isplay=mVideoView.isPlaying();
//                if (mVideoView.isPlaying()){
//                    tikTokAdapter.setIsshow(false);
////                    tikTokAdapter.notifyItemChanged(mCurrentPosition);
//                }else {
//                    tikTokAdapter.setIsshow(true);
////                    tikTokAdapter.notifyItemChanged(mCurrentPosition);
//                }


//                Log.e("---Play",""+playState);
                int[] size = mVideoView.getVideoSize();
//                String value="位置"+position+",width:"+size[0]+" "+",height:"+size[1];
//                Log.e("Android短视频6",value);
//
//                Log.e("-------------s------",""+size[1]);
                //高度OK
                if (size[1] < 640) {
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT, 600);//RelativeLayout.LayoutParams.MATCH_PARENT 500
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    params.setMargins(0, 0, 0, 0);//top 400
                    mVideoView.setLayoutParams(params);
                    View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置          删除了有没有问题
                    if (view != null)
                        view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.INVISIBLE);
                } else {
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT);
                    params.setMargins(0, 0, 0, 0);
                    mVideoView.setLayoutParams(params);
                    View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置
                    if (view != null)
                        view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.VISIBLE);
                }




            }
        });

        tikTokAdapter.notifyDataSetChanged();
        if (recyclerView.getScrollState()==RecyclerView.SCROLL_STATE_IDLE && !recyclerView.isComputingLayout()){

            Log.e("----","nodo");
        }else {
            Log.e("----","do");
        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tikTokAdapter.notifyItemChanged(position);
//                    }
//                });
//
//            }
//        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showNextImage(String video_img_url) {
        Log.e("-----9999",video_img_url);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView!=null){
            mVideoView.resume();
        }
    }


    @Override
    protected void onPause() {
        if (mVideoView != null) {
            mVideoView.pause();
        }
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
        }
    }


    public void videosPageListenerOnListener() {
        layoutManager.setOnViewPagerListenertwo(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                //自动播放第一条
//                startPlay(position);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
//                if (mCurrentPosition == position) {
//                    mVideoView.release();
//                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (mCurrentPosition == position) return;
                startPlay(position);
                mCurrentPosition = position;
                Log.e("-----------gg","2222222222不同");
                //ok
                viewplaypause = layoutManager.findViewByPosition(position);    //为recyclerView中item位置          删除了有没有问题
                if (viewplaypause != null)
                    viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
            }
        },list);

    }
}
