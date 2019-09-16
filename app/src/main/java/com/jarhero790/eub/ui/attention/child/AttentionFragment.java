package com.jarhero790.eub.ui.attention.child;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dueeeke.videoplayer.listener.OnVideoViewStateChangeListener;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.AttentionUsersAdapter;
import com.jarhero790.eub.adapter.VideoRecyclerViewAdapter;
import com.jarhero790.eub.base.BaseMVPCompatFragment;
import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.bean.AttentionUser;
import com.jarhero790.eub.bean.AttentionUserAndVideoBen;
import com.jarhero790.eub.bean.AttentionVideo;
import com.jarhero790.eub.bean.ShipinDianZanBean;
import com.jarhero790.eub.contract.attention.AttentionContract;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.attention.OnItemClickear;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.presenter.attention.AttentionPresenter;
import com.jarhero790.eub.ui.souye.BottomPingLunDialog;
import com.jarhero790.eub.ui.souye.BottomShareDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AttentionFragment extends BaseMVPCompatFragment<AttentionContract.AttentionPresenter>
        implements AttentionContract.IAttentionView, OnItemClickear {

    //关注的用户所发布的视频
    LinearLayoutManager linearLayoutManager;
    View viewplaypause;//
    View viewivdeault;
    View viewvideo;
    @BindView(R.id.attentionuser_recyclerview)
    RecyclerView attentionuserRecyclerview;
    Unbinder unbinder;
    private int mCurrentPosition;//当前播放的第几个视频 ，

    @BindView(R.id.recyclerViewAttentionUsers)
    RecyclerView recyclerViewAttentionUsers;

//    AttentionUsersAndVideosAdapter attentionUsersAndVideosAdapter;

    private static AttentionFragment attentionFragment;

    int firstVisibleItem, lastVisibleItem, visibleCount;

    VideoRecyclerViewAdapter adapter;


    private boolean isLook = true;//可见
    private VideoView mVideoView;

    public static AttentionFragment newInstance() {
        if (attentionFragment == null) {
            attentionFragment = new AttentionFragment();
        }
        return attentionFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return AttentionPresenter.newInstance();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    private ArrayList<AttentionVideo> attentionUsersVideos = new ArrayList<>();


    @Override
    public void displayMyAttentionUsersAndVideos(AttentionUserAndVideoBen attentionUserAndVideoBen) {
        //Log.e("接收的数据",attentionUserAndVideoBen.toString());
        attentionUsersVideos = attentionUserAndVideoBen.getData().getVideo();

        ArrayList<AttentionUser> attentionUsers = attentionUserAndVideoBen.getData().getMylike();
//        if (attentionUsers.size() > 0) {
//            for (int i = 0; i < attentionUsers.size(); i++) {
//                Log.e("---------headimgurl=", attentionUsers.get(i).getHeadimgurl());
//            }
//        } else {
//            Log.e("---------headimgurl=", "0");
//        }

        //
        adapter = new VideoRecyclerViewAdapter(attentionUsersVideos, getActivity(), this);
        recyclerViewAttentionUsers.setLayoutManager(linearLayoutManager);
        recyclerViewAttentionUsers.setAdapter(adapter);


        AttentionUsersAdapter attentionUsersAdapter=new AttentionUsersAdapter(attentionUsers,getActivity());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AppUtils.getContext(),LinearLayoutManager.HORIZONTAL,false);
        attentionuserRecyclerview.setLayoutManager(linearLayoutManager);
        attentionuserRecyclerview.setAdapter(attentionUsersAdapter);


//        attentionUsersAndVideosAdapter = new AttentionUsersAndVideosAdapter(attentionUserAndVideoBen,getActivity(),this);
//        recyclerViewAttentionUsers.setLayoutManager(linearLayoutManager);
//        recyclerViewAttentionUsers.setAdapter(attentionUsersAndVideosAdapter);


        recyclerViewAttentionUsers.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                VideoView videoView = view.findViewById(R.id.video_player);
                ImageView ivdeault=view.findViewById(R.id.iv_deault);
//                Log.e("------------", "窗口消失了");
                if (videoView != null && !videoView.isFullScreen()) {
                    videoView.release();
                    ivdeault.setVisibility(View.GONE);
//                    viewivdeault = linearLayoutManager.findViewByPosition(mCurrentPosition);
                    viewplaypause = linearLayoutManager.findViewByPosition(mCurrentPosition);
//                    if (viewivdeault != null && viewivdeault.findViewById(R.id.iv_deault) != null)
//                        viewivdeault.findViewById(R.id.iv_deault).setVisibility(View.VISIBLE);
                    if (viewplaypause != null && viewplaypause.findViewById(R.id.iv_play) != null)
                        ((ImageView) (viewplaypause.findViewById(R.id.iv_play))).setImageDrawable(getResources().getDrawable(R.mipmap.play_icon));
                }
            }
        });

        recyclerViewAttentionUsers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        autoPlayVideo(recyclerView);//滚动停止
//                        Log.e("--------------1", "滚动停止");
                        break;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                visibleCount = lastVisibleItem - firstVisibleItem;//记录可视区域item个数
//                Log.e("--------------1", firstVisibleItem + "  " + lastVisibleItem + "  " + visibleCount);
                mCurrentPosition = lastVisibleItem;
            }


            private void autoPlayVideo(RecyclerView recyclerView) {
                //循环遍历可视区域videoview,如果完全可见就开始播放
                for (int i = 0; i <= visibleCount; i++) {
                    if (recyclerView == null || recyclerView.getChildAt(i) == null) {
//                        Log.e("----------", "1");
                        continue;
                    }

                    VideoView videoView = recyclerView.getChildAt(i).findViewById(R.id.video_player);
                    ImageView ivdeault=recyclerView.getChildAt(i).findViewById(R.id.iv_deault);
                    if (videoView != null && ivdeault!=null) {
//                        Log.e("----------", "2");
                        Rect rect = new Rect();
                        videoView.getLocalVisibleRect(rect);

                        int videoHeight = videoView.getHeight();
//                        Log.e("-----------height=",videoHeight+" "+rect.top+"  "+rect.bottom);

//                        if (Math.abs((rect.top-rect.bottom))==videoHeight){
//                            Log.e("----------","3");
//                            videoView.start();
//                            viewivdeault = linearLayoutManager.findViewByPosition(mCurrentPosition);
//                            if (viewivdeault != null)
//                                viewivdeault.findViewById(R.id.iv_deault).setVisibility(View.INVISIBLE);
//                            return;
//                        }


                        if (rect.top == 0 && rect.bottom == videoHeight) {
//                            Log.e("----------", "3");
                            videoView.start();

//                            videoView.addOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
//                                @Override
//                                public void onPlayerStateChanged(int playerState) {
//                                }
//
//                                @Override
//                                public void onPlayStateChanged(int playState) {
//                                    ivdeault.setVisibility(View.GONE);
//                                }
//                            });
                            ivdeault.setVisibility(View.GONE);

//                            viewivdeault = linearLayoutManager.findViewByPosition(mCurrentPosition);
//                            viewplaypause = linearLayoutManager.findViewByPosition(mCurrentPosition);
//                            if (viewivdeault != null && viewivdeault.findViewById(R.id.iv_deault) != null)
//                                viewivdeault.findViewById(R.id.iv_deault).setVisibility(View.INVISIBLE);
//                            if (viewplaypause != null && viewplaypause.findViewById(R.id.iv_play) != null)
//                                ((ImageView) (viewplaypause.findViewById(R.id.iv_play))).setImageDrawable(getResources().getDrawable(R.mipmap.play_pause_icon));
                            return;
                        }
                    }
                }

            }
        });


        //ok
//        startPlay(0);
        recyclerViewAttentionUsers.post(() -> {
            //自动播放第一个

            View view = recyclerViewAttentionUsers.getChildAt(0);
            if (view != null) {
                ImageView ivdeault=view.findViewById(R.id.iv_deault);
                ivdeault.setVisibility(View.VISIBLE);
                VideoView videoView = view.findViewById(R.id.video_player);

                videoView.start();
//                Log.e("----------","false");
//                 if (videoView.isPlaying()){
//                     Log.e("----------","true");
//                 }
                 videoView.addOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
                     @Override
                     public void onPlayerStateChanged(int playerState) {
//                         Log.e("----------","true1"+playerState);
                     }

                     @Override
                     public void onPlayStateChanged(int playState) {
//                         Log.e("----------","true2"+playState);
                         ivdeault.setVisibility(View.GONE);
                     }
                 });

//                viewivdeault = linearLayoutManager.findViewByPosition(mCurrentPosition);
                viewplaypause = linearLayoutManager.findViewByPosition(mCurrentPosition);
//                if (viewivdeault != null && viewivdeault.findViewById(R.id.iv_deault) != null)
//                    viewivdeault.findViewById(R.id.iv_deault).setVisibility(View.INVISIBLE);
                if (viewplaypause != null && viewplaypause.findViewById(R.id.iv_play) != null)
                    ((ImageView) (viewplaypause.findViewById(R.id.iv_play))).setImageDrawable(getResources().getDrawable(R.mipmap.play_pause_icon));
            }

        });


        /**
         this.attentionUsers=attentionUsers;
         attentionUsersAdapter = new AttentionUsersAdapter(attentionUsers);
         recyclerViewAttentionUsers.setLayoutManager(attentionUsersLinearLayoutManager);
         recyclerViewAttentionUsers.setAdapter(attentionUsersAdapter);
         //点击更多图标 跳转到用户搜索
         moreAttentUser.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        Intent intent=new Intent(AppUtils.getContext(),AttentionUsersActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("attentionUsersList",attentionUsers);
        intent.putExtra("customBundle",args);
        startActivity(intent);
        }
        });
         **/
    }


    private void startPlay(int position) {
        //自动播放第一个
//        View view=recyclerViewAttentionUsers.getChildAt(position);
//        VideoView videoView=view.findViewById(R.id.video_player);
//        videoView.start();
        if (attentionUsersVideos == null || attentionUsersVideos.size() == 0)
            return;
        mVideoView.setUrl(attentionUsersVideos.get(position).getUrl());
        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
        mVideoView.start();
        viewivdeault = linearLayoutManager.findViewByPosition(mCurrentPosition);
        viewplaypause = linearLayoutManager.findViewByPosition(mCurrentPosition);
        if (viewivdeault != null)
            viewivdeault.findViewById(R.id.iv_deault).setVisibility(View.INVISIBLE);
        if (viewplaypause != null)
            ((ImageView) (viewplaypause.findViewById(R.id.iv_play))).setImageDrawable(getResources().getDrawable(R.mipmap.play_pause_icon));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (linearLayoutManager != null)
            viewvideo = linearLayoutManager.findViewByPosition(mCurrentPosition);
        if (viewvideo != null)
            mVideoView = viewvideo.findViewById(R.id.video_player);
        if (hidden) {
            //不可见
            setLook(false);
            if (mVideoView != null) {
                mVideoView.pause();
//                Log.e("------------", "a1=" + hidden);
            }
//            else {
//                Log.e("------------","a2="+hidden);
//                mVideoView = new VideoView(AppUtils.getContext());
//                mVideoView.pause();
//            }
        } else {
            //可见
//            Log.e("------------","b="+hidden);
            setLook(true);
            if (mVideoView != null) {
                mVideoView.resume();
//                Log.e("----------b1", "是不是这里" + mCurrentPosition);
                startPlay(mCurrentPosition);

            }
//            else {
//                mVideoView = new VideoView(AppUtils.getContext());
//                mVideoView.resume();
//                startPlay(mCurrentPosition);
//                Log.e("----------b2", "是不是这里");
//            }
        }
    }

    @Override
    public void showNetworkError() {

    }


    @Override
    public void onPause() {
        if (mVideoView != null) {
            mVideoView.pause();
        }
        super.onPause();
        VideoViewManager.instance().release();
    }


    /**
     * 懒加载
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(AppUtils.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //加载数据
        mPresenter.requestMyAttentionUsersAndUsersVideos();
        viewvideo = linearLayoutManager.findViewByPosition(mCurrentPosition);
        if (viewvideo != null)
            mVideoView = viewvideo.findViewById(R.id.video_player);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_attention;
    }


    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {


    }


    public void showShare() {
        BottomShareDialog bottomShareDialog = BottomShareDialog.newInstance();
        bottomShareDialog.show(getChildFragmentManager(), "share");
    }

    //ok
    public void showPingLun(int po) {
//        Log.e("--------token","token");
//        Log.e("--------token",SharePreferenceUtil.getToken(AppUtils.getContext()));
//
//        if (SharePreferenceUtil.getToken(AppUtils.getContext())==null){
//            Log.e("--------token","token1");
//        }

        if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
//            Log.e("--------token","token2");
            startActivity(new Intent(getActivity(), LoginNewActivity.class));
        } else {
            BottomPingLunDialog bottomPingLunDialog = BottomPingLunDialog.newInstance();
            Bundle args = new Bundle();
            args.putString("vid", attentionUsersVideos.get(po).getVideo_id());
            bottomPingLunDialog.setArguments(args);
            bottomPingLunDialog.show(getChildFragmentManager(), "pinglun");
        }

    }


    @Override
    public void linerck(int position, String type, View view, View view2) {
//        Log.e("---------", type + "  " + position);
        if (type.equals("播放")) {  //点了能播放
            ImageView ivplay = (ImageView) view;
            VideoView videoView = (VideoView) view2;
//            videoView.setUrl(attentionUsersVideos.get(position).getUrl());
//            videoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
//            videoView.setLooping(true);
//            videoView.start();

            if (videoView.isPlaying()) {
//                Log.e("------------","暂停");
                ivplay.setImageDrawable(getResources().getDrawable(R.mipmap.play_icon));
                videoView.pause();
            } else {
                videoView.start();
                ivplay.setImageDrawable(getResources().getDrawable(R.mipmap.play_pause_icon));
//                Log.e("------------","正在播放");
            }


        } else if (type.equals("分享")) {
            showShare();
        } else if (type.equals("评论")) {
            showPingLun(position);
        } else if (type.equals("点赞")) {
            ImageView ivLike = (ImageView) view;
            RetrofitManager.getInstance().getDataServer().zanorno(attentionUsersVideos.get(position).getVideo_id(), SharePreferenceUtil.getToken(AppUtils.getContext()))
                    .enqueue(new Callback<ShipinDianZanBean>() {
                        @Override
                        public void onResponse(Call<ShipinDianZanBean> call, Response<ShipinDianZanBean> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null && response.body().getCode().equals("200")) {
                                    String value = response.body().getData().getIs();
                                    if (value.equals("1")) {
                                        ivLike.setImageResource(R.drawable.iv_like_selected);
                                    } else {
                                        ivLike.setImageResource(R.drawable.iv_like_unselected);
                                    }


                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ShipinDianZanBean> call, Throwable t) {

                        }
                    });


        } else if (type.equals("更多")) {
            showPingLun(position);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mVideoView != null) {
//            Log.e("---------islokk", "" + isLook());
            if (isLook()) {
                mVideoView.resume();
//                flag.set(true);


            }
//            if (mPresenter!=null)
//            mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
//            Log.e("-------", "attention-onResume");
        } else {
//            Log.e("-------", "attention-onResumeN");
        }

    }


    public boolean isLook() {
        return isLook;
    }

    public void setLook(boolean look) {
        isLook = look;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

