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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import com.jarhero790.eub.message.attention.MyScrollViewScroll;
import com.jarhero790.eub.message.attention.OnItemClickear;
import com.jarhero790.eub.message.bean.attentionchange;
import com.jarhero790.eub.message.bean.pinglunchange;
import com.jarhero790.eub.message.message.GeRenInfoActivity;
import com.jarhero790.eub.message.my.GuangZuActivity;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.presenter.attention.AttentionPresenter;
import com.jarhero790.eub.ui.souye.BottomPingLunDialog;
import com.jarhero790.eub.ui.souye.BottomShareDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
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
    @BindView(R.id.framelayout_container)
    LinearLayout framelayoutContainer;
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;
    //    @BindView(R.id.myscroll)
//    MyScrollViewScroll myscroll;
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

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        framelayoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GuangZuActivity.class));
            }
        });
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
//        adapter.setGetP(new VideoRecyclerViewAdapter.GetP() {
//            @Override
//            public void ostion(View view, int po) {
//                Log.e("------------jjjj",view.getTop()+"    "+po);
//                if (po==0){
//                    rlUser.setVisibility(View.VISIBLE);
//                }else {
//                    rlUser.setVisibility(View.GONE);
//                }
//            }
//        });


        AttentionUsersAdapter attentionUsersAdapter = new AttentionUsersAdapter(attentionUsers, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppUtils.getContext(), LinearLayoutManager.HORIZONTAL, false);
        attentionuserRecyclerview.setLayoutManager(linearLayoutManager);
        attentionuserRecyclerview.setAdapter(attentionUsersAdapter);

        attentionUsersAdapter.setOnItem(new AttentionUsersAdapter.OnItem() {
            @Override
            public void Clicklinear(View view, int position) {
//                Log.e("------------",""+position);
                Intent intent = new Intent(getActivity(), GeRenInfoActivity.class);
                intent.putExtra("userid", attentionUsers.get(position).getId());
                startActivity(intent);
//                if (position == 4) {
//
//                } else {
//
//                }
            }
        });


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
//                ImageView ivdeault = view.findViewById(R.id.iv_deault);
//                ImageView ivplay = view.findViewById(R.id.iv_play);
//                ivdeault.setVisibility(View.VISIBLE);
//                ivplay.setImageDrawable(getResources().getDrawable(R.mipmap.play_icon));
//                Log.e("------------", "窗口消失了");
                if (videoView != null && !videoView.isFullScreen()) {
                    videoView.release();

//                    viewivdeault = linearLayoutManager.findViewByPosition(mCurrentPosition);
//                    viewplaypause = linearLayoutManager.findViewByPosition(mCurrentPosition);
//                    if (viewivdeault != null && viewivdeault.findViewById(R.id.iv_deault) != null)
//                        viewivdeault.findViewById(R.id.iv_deault).setVisibility(View.VISIBLE);
//                    if (viewplaypause != null && viewplaypause.findViewById(R.id.iv_play) != null)
//                        ((ImageView) (viewplaypause.findViewById(R.id.iv_play))).setImageDrawable(getResources().getDrawable(R.mipmap.play_icon));
                }
            }
        });

//        myscroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.e("-----------------","");
//                VideoView videoView = view.findViewById(R.id.video_player);
//                int height = videoView.getHeight();
//                Log.e("--------",height+"    "+scrollY+"    "+oldScrollY);
//
//
//
//
//                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                visibleCount = lastVisibleItem - firstVisibleItem;//记录可视区域item个数
//                Log.e("--------------1", firstVisibleItem + "  " + lastVisibleItem + "  " + visibleCount);
//                mCurrentPosition = lastVisibleItem;
//            }
//
//        });
//
//        myscroll.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//            @Override
//            public void onViewAttachedToWindow(View v) {
//
//            }
//
//            @Override
//            public void onViewDetachedFromWindow(View view) {
//                VideoView videoView = view.findViewById(R.id.video_player);
//                ImageView ivdeault = view.findViewById(R.id.iv_deault);
////                Log.e("------------", "窗口消失了");
//                if (videoView != null && !videoView.isFullScreen()) {
//                    videoView.release();
//                    ivdeault.setVisibility(View.GONE);
////                    viewivdeault = linearLayoutManager.findViewByPosition(mCurrentPosition);
//                    viewplaypause = linearLayoutManager.findViewByPosition(mCurrentPosition);
////                    if (viewivdeault != null && viewivdeault.findViewById(R.id.iv_deault) != null)
////                        viewivdeault.findViewById(R.id.iv_deault).setVisibility(View.VISIBLE);
//                    if (viewplaypause != null && viewplaypause.findViewById(R.id.iv_play) != null)
//                        ((ImageView) (viewplaypause.findViewById(R.id.iv_play))).setImageDrawable(getResources().getDrawable(R.mipmap.play_icon));
//                }
//            }
//        });

//        recyclerViewAttentionUsers.setHasFixedSize(true);
//        recyclerViewAttentionUsers.setNestedScrollingEnabled(false);

        recyclerViewAttentionUsers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        autoPlayVideo(recyclerView);//滚动停止
//                        View vieew=recyclerView.getChildAt(0).findViewById(R.id.attentionsUserIcon);
//                        Log.e("--------------1", "滚动停止"+mCurrentPosition);
                        if (mCurrentPosition == 0) {
                            rlUser.setVisibility(View.VISIBLE);
                        }
//
                        break;
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

//                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                visibleCount = lastVisibleItem - firstVisibleItem;//记录可视区域item个数
//                Log.e("--------------ggg", firstVisibleItem + "  " + lastVisibleItem + "  " + visibleCount);
//                mCurrentPosition = lastVisibleItem;
                super.onScrolled(recyclerView, dx, dy);

                if (RecyclerView.SCROLL_STATE_IDLE != recyclerView.getScrollState()) {
//                    Log.e("----------", "来了没有,滑动");
////                    attentionuserRecyclerview.scrollBy(dx, dy);
//                    rlUser.scrollBy(dx, dy);
//                    rlUser.setBackgroundColor(Color.TRANSPARENT);
                    rlUser.setVisibility(View.GONE);
                }
//                else {
//                    Log.e("----------", "来了没有，静止");
//
//                }

//                Log.e("-------",dx+"       "+dy);


//                else {
//                    rlUser.setVisibility(View.VISIBLE);
//                }


                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();


                    int visibleCount = lastItemPosition - firstItemPosition;//记录可视区域item个数
//                    if (foodsArrayList.get(firstItemPosition) instanceof Foods) {
//                        int foodTypePosion = ((Foods) foodsArrayList.get(firstItemPosition)).getFood_stc_posion();
//                        FoodsTypeListview.getChildAt(foodTypePosion).setBackgroundResource(R.drawable.choose_item_selected);
//                    }
//                    System.out.println(lastItemPosition + "   " + firstItemPosition);
                    mCurrentPosition = lastItemPosition;
                    adapter.setmCurrentPosition(lastItemPosition);
//                    Log.e("--------------ggg", firstItemPosition + "  " + lastItemPosition + "  " + visibleCount);




                }


            }


            private void autoPlayVideo(RecyclerView recyclerView) {
                //循环遍历可视区域videoview,如果完全可见就开始播放
                for (int i = 0; i <= visibleCount; i++) {
                    if (recyclerView == null || recyclerView.getChildAt(i) == null) {
//                        Log.e("----------", "1");
                        continue;
                    }

                    VideoView videoView = recyclerView.getChildAt(i).findViewById(R.id.video_player);
                    ImageView ivdeault = recyclerView.getChildAt(i).findViewById(R.id.iv_deault);
                    ImageView ivplay = recyclerView.getChildAt(i).findViewById(R.id.iv_play);

//                    CircleImageView civ=recyclerView.getChildAt(0).findViewById(R.id.attentionsUserIcon);

//                    Log.e("----------","高度="+civ.getTop()+"当前="+civ.getMeasuredHeight()+civ.getPivotY()+" "+civ.getScrollY());

                    if (videoView != null && ivdeault != null) {
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

//                            videoView.setLock(true);
//                            videoView.setOnClickListener(null);
//                            videoView.setFocusable(false);
//                            videoView.setEnabled(false);

//                            int finalI = i;
//                            videoView.addOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
//                                @Override
//                                public void onPlayerStateChanged(int playerState) {
//                                }
//
//                                @Override
//                                public void onPlayStateChanged(int playState) {
////                                    ivdeault.setVisibility(View.GONE);
//
//                                    if (mCurrentPosition== finalI){
//                                        ivplay.setImageDrawable(getActivity().getDrawable(R.mipmap.play_pause_icon));
//                                    }else {
//                                        ivplay.setImageDrawable(getActivity().getDrawable(R.mipmap.play_icon));
//                                    }
//                                }
//                            });
//                            ivdeault.setVisibility(View.GONE);

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


//        attentionuserRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (RecyclerView.SCROLL_STATE_IDLE != recyclerView.getScrollState()) {
//                    Log.e("----------", "来了没有");
//                    recyclerViewAttentionUsers.scrollBy(dx, dy);
//                }
//            }
//        });


        //ok
//        startPlay(0);
        recyclerViewAttentionUsers.post(() -> {
            //自动播放第一个

            View view = recyclerViewAttentionUsers.getChildAt(0);
            if (view != null) {
//                ImageView ivdeault = view.findViewById(R.id.iv_deault);
//                ImageView iv_play = view.findViewById(R.id.iv_play);
//                iv_play.setImageDrawable(getResources().getDrawable(R.mipmap.play_pause_icon));
//                ivdeault.setVisibility(View.VISIBLE);
                VideoView videoView = view.findViewById(R.id.video_player);

                videoView.start();
//                Log.e("----------","false");
//                 if (videoView.isPlaying()){
//                     Log.e("----------","true");
//                 }
//                videoView.addOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
//                    @Override
//                    public void onPlayerStateChanged(int playerState) {
////                         Log.e("----------","true1"+playerState);
//                    }
//
//                    @Override
//                    public void onPlayStateChanged(int playState) {
////                         Log.e("----------","true2"+playState);
//                        ivdeault.setVisibility(View.GONE);
//                    }
//                });

//                viewivdeault = linearLayoutManager.findViewByPosition(mCurrentPosition);
//                viewplaypause = linearLayoutManager.findViewByPosition(mCurrentPosition);
//                if (viewivdeault != null && viewivdeault.findViewById(R.id.iv_deault) != null)
//                    viewivdeault.findViewById(R.id.iv_deault).setVisibility(View.INVISIBLE);
//                if (viewplaypause != null && viewplaypause.findViewById(R.id.iv_play) != null)
//                    ((ImageView) (viewplaypause.findViewById(R.id.iv_play))).setImageDrawable(getResources().getDrawable(R.mipmap.play_pause_icon));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void attentionname(attentionchange name) {
//        Log.e("-------------name=",name.getName());
        mPresenter.requestMyAttentionUsersAndUsersVideos();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pinglunname(pinglunchange name) {
//        Log.e("-------------name=",name.getName());
        mPresenter.requestMyAttentionUsersAndUsersVideos();

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
//        viewivdeault = linearLayoutManager.findViewByPosition(mCurrentPosition);
//        viewplaypause = linearLayoutManager.findViewByPosition(mCurrentPosition);
//        if (viewivdeault != null)
//            viewivdeault.findViewById(R.id.iv_deault).setVisibility(View.INVISIBLE);
//        if (viewplaypause != null)
//            ((ImageView) (viewplaypause.findViewById(R.id.iv_play))).setImageDrawable(getResources().getDrawable(R.mipmap.play_pause_icon));
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
//                Log.e("------------","不可见="+hidden);
//                mVideoView = new VideoView(AppUtils.getContext());
//                mVideoView.pause();
//            }
        } else {
            //可见
//            Log.e("------------","可见="+hidden);
            //刷新关注
//            mPresenter.requestMyAttentionUsersAndUsersVideos();   //用evbus

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
//        Log.e("-----------------","加了吗");
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
                videoView.pause();
                ivplay.setImageDrawable(getResources().getDrawable(R.mipmap.play_icon));

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
        } else if (type.equals("关注")) {
            Button button = (Button) view;
            attentions(button);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
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


    private void attentions(Button button) {

        RetrofitManager.getInstance().getDataServer().attentionUserTwo(attentionUsersVideos.get(mCurrentPosition).getUid() + "", SharePreferenceUtil.getToken(AppUtils.getContext())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String json = response.body().string();
                        JSONObject object = new JSONObject(json);
                        int code = object.optInt("code");
                        if (code == 200) {
                            JSONObject data = object.optJSONObject("data");
                            String islike = data.optString("is_like");
                            EventBus.getDefault().post(new attentionchange("关注"));
                            if (islike.equals("1")) {
                                button.setText("已关注");
                            } else {
                                button.setText("+关注");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}

