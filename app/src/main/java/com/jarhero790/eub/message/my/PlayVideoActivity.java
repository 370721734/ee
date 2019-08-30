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
import com.jarhero790.eub.adapter.TikTokController;
import com.jarhero790.eub.adapter.ViewPagerLayoutManager;
import com.jarhero790.eub.model.bean.souye.VideoBean;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.DataUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayVideoActivity extends AppCompatActivity{

//    @BindView(R.id.recycler_view)
//    RecyclerView recyclerView;
//    @BindView(R.id.frameLayoutContainer)
//    FrameLayout frameLayoutContainer;
//    @BindView(R.id.back)
//    ImageView back;


//    private TikTokController mTikTokController;
//    private TikTokTwoAdapter tikTokAdapter;
//    private VideoView mVideoView;
//    ViewPagerLayoutManager layoutManager;
//    private int mCurrentPosition;//当前播放的第几个视频 ，
//    private int position;
//    ArrayList<MyFaBuBean.DataBean> list = new ArrayList<>();
//
////    private List<Video> videolist = new ArrayList<>();
//    View viewplaypause;



    private static final String TAG = "TikTokActivity";
    private VideoView mVideoView;
    private TikTokController mTikTokController;
    private int mCurrentPosition;
    private RecyclerView mRecyclerView;
    private List<VideoBean> mVideoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        mVideoView = new VideoView(this);
        mVideoView.setLooping(true);
        mTikTokController = new TikTokController(this);
        mVideoView.setVideoController(mTikTokController);
        mRecyclerView = findViewById(R.id.recycler_view);

        mVideoList = DataUtil.getTikTokVideoList();
        TikTokAdapter tikTokAdapter = new TikTokAdapter(mVideoList, this);
        ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(tikTokAdapter);
        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                //自动播放第一条
                startPlay(0);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (mCurrentPosition == position) {
                    mVideoView.release();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (mCurrentPosition == position) return;
                startPlay(position);
                mCurrentPosition = position;
            }
        });




//        Intent intent=getIntent();
//
//         position=intent.getIntExtra("position",0);
//         list= (ArrayList<MyFaBuBean.DataBean>) intent.getSerializableExtra("vidlist");
//         Log.e("-----------","a="+position);
//         Log.e("-----------","b="+list.size());
//
//
//         if (list!=null && list.size()>0){
//
////             for (int i = 0; i < list.size(); i++) {
////                 videolist.add(new Video(list.get(i).getUid()+"",list.get(i).getUrl(),list.get(i).getZan()+"",list.get(i).getCaifu()+"",list.get(i).getVideo_img()));
////             }
////
////
////
////             if (videolist!=null && videolist.size()>0){
////
////             }
//             tikTokAdapter = new TikTokTwoAdapter(list, this);
//             layoutManager=new ViewPagerLayoutManager(this,OrientationHelper.VERTICAL);
//             recyclerView.setLayoutManager(layoutManager);
//             recyclerView.setAdapter(tikTokAdapter);
//
//             recyclerView.setNestedScrollingEnabled(true);
//             startPlay(position);
//             videosPageListenerOnListener();
//             videosPageListenerOnListener();

//             layoutManager.setOnViewPagerListenertwo(new OnViewPagerListener() {
//                 @Override
//                 public void onInitComplete() {
//                     //自动播放第一条
//                     startPlay(0);
//                 }
//
//                 @Override
//                 public void onPageRelease(boolean isNext, int position) {
//                     if (mCurrentPosition == position) {
//                         mVideoView.release();
//                     }
//                 }
//
//                 @Override
//                 public void onPageSelected(int position, boolean isBottom) {
//                     if (mCurrentPosition == position) return;
//                     startPlay(position);
//                     mCurrentPosition = position;
//                 }
//             });
         }


    private void startPlay(int position) {

        View itemView = mRecyclerView.getChildAt(0);
        if (itemView!=null){
            FrameLayout frameLayout = itemView.findViewById(R.id.container);
            Glide.with(this)
                    .load(mVideoList.get(position).getThumb())
                    .apply(new RequestOptions().placeholder(android.R.color.white))
                    .into(mTikTokController.getThumb());
            ViewParent parent = mVideoView.getParent();
            if (parent instanceof FrameLayout) {
                ((FrameLayout) parent).removeView(mVideoView);
            }
            frameLayout.addView(mVideoView);
            mVideoView.setUrl(mVideoList.get(position).getUrl());
            mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
            mVideoView.start();
        }else {
            mRecyclerView = findViewById(R.id.recycler_view);

            mVideoList = DataUtil.getTikTokVideoList();
            TikTokAdapter tikTokAdapter = new TikTokAdapter(mVideoList, this);
            ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);

            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(tikTokAdapter);
            layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
                @Override
                public void onInitComplete() {
                    //自动播放第一条
                    startPlay(0);
                }

                @Override
                public void onPageRelease(boolean isNext, int position) {
                    if (mCurrentPosition == position) {
                        mVideoView.release();
                    }
                }

                @Override
                public void onPageSelected(int position, boolean isBottom) {
                    if (mCurrentPosition == position) return;
                    startPlay(position);
                    mCurrentPosition = position;
                }
            });


        }

    }





//    private void startPlay(int position) {
////        Log.e("-------eeeee","="+position+"  "+list.get(position).getUrl());
//        View itemView = recyclerView.getChildAt(0);
//        View bb=View.inflate(this,R.layout.item_tik_tok,null);
//        FrameLayout frameLayout = bb.findViewById(R.id.container);
//        // 加载视频的预览图片 Glide方式
//        Glide.with(this)
//                .load(list.get(position).getVideo_img())
//                .apply(new RequestOptions().placeholder(android.R.color.white))
//                .into(mTikTokController.getThumb());
//        ViewParent parent = mVideoView.getParent();
//        if (parent instanceof FrameLayout) {
//            ((FrameLayout) parent).removeView(mVideoView);
//        }
////        frameLayout.addView(itemView);
////        frameLayout.addView(mVideoView);
//        //设置播放的url
//        mVideoView.setUrl(list.get(position).getUrl());
//        //重要
//        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
//        //开始播放视频
//        mVideoView.start();
//
//
//
////        //确定高度
////        mVideoView.setOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
////            @Override
////            public void onPlayerStateChanged(int playerState) {
//////                Log.e("---Player",""+playerState);
//////                int[] size= mVideoView.getVideoSize();
//////                String value="位置"+position+",width:"+size[0]+" "+",height:"+size[1];
//////                Log.e("Android短视频5",value);
////
////
////            }
////
////            @Override
////            public void onPlayStateChanged(int playState) {
////
//////                isplay=mVideoView.isPlaying();
//////                if (mVideoView.isPlaying()){
//////                    tikTokAdapter.setIsshow(false);
////////                    tikTokAdapter.notifyItemChanged(mCurrentPosition);
//////                }else {
//////                    tikTokAdapter.setIsshow(true);
////////                    tikTokAdapter.notifyItemChanged(mCurrentPosition);
//////                }
////
////
//////                Log.e("---Play",""+playState);
////                int[] size = mVideoView.getVideoSize();
//////                String value="位置"+position+",width:"+size[0]+" "+",height:"+size[1];
//////                Log.e("Android短视频6",value);
//////
//////                Log.e("-------------s------",""+size[1]);
////                //高度OK
////                if (size[1] < 640) {
////                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
////                            RelativeLayout.LayoutParams.MATCH_PARENT, 600);//RelativeLayout.LayoutParams.MATCH_PARENT 500
////                    params.addRule(RelativeLayout.CENTER_VERTICAL);
////                    params.setMargins(0, 0, 0, 0);//top 400
////                    mVideoView.setLayoutParams(params);
////                    View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置          删除了有没有问题
////                    if (view != null)
////                        view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.INVISIBLE);
////                } else {
////                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
////                            RelativeLayout.LayoutParams.MATCH_PARENT,
////                            RelativeLayout.LayoutParams.MATCH_PARENT);
////                    params.setMargins(0, 0, 0, 0);
////                    mVideoView.setLayoutParams(params);
////                    View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置
////                    if (view != null)
////                        view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.VISIBLE);
////                }
////
////
////
////
////            }
////        });
////
////        tikTokAdapter.notifyDataSetChanged();
////        if (recyclerView.getScrollState()==RecyclerView.SCROLL_STATE_IDLE && !recyclerView.isComputingLayout()){
////
////            Log.e("----","nodo");
////        }else {
////            Log.e("----","do");
////        }
//
//    }

//    @OnClick(R.id.back)
//    public void onViewClicked() {
//        finish();
//    }



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


//    public void videosPageListenerOnListener() {
//        layoutManager.setOnViewPagerListenertwo(new OnViewPagerListener() {
//            @Override
//            public void onInitComplete() {
//                //自动播放第一条
////                startPlay(position);
//            }
//
//            @Override
//            public void onPageRelease(boolean isNext, int position) {
//                if (mCurrentPosition == position) {
//                    mVideoView.release();
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position, boolean isBottom) {
//                if (mCurrentPosition == position) return;
//                startPlay(position);
//                mCurrentPosition = position;
//                Log.e("-----------gg","2222222222不同");
//                //ok
//                viewplaypause = layoutManager.findViewByPosition(position);    //为recyclerView中item位置          删除了有没有问题
//                if (viewplaypause != null)
//                    viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
//            }
//        },list);
//
//    }
}
