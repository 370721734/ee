package com.jarhero790.eub.ui.souye.child;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.listener.OnVideoViewStateChangeListener;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.OnViewPagerListener;
import com.jarhero790.eub.adapter.TikTokAdapter;
import com.jarhero790.eub.adapter.TikTokController;
import com.jarhero790.eub.adapter.ViewPagerLayoutManager;
import com.jarhero790.eub.aop.annotation.LoginFilter;
import com.jarhero790.eub.base.BaseMVPCompatFragment;
import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.bean.ShipinDianZan;
import com.jarhero790.eub.bean.Video;
import com.jarhero790.eub.contract.home.SouyeContract;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.souye.SearchActivity;
import com.jarhero790.eub.presenter.home.SouyePresenter;
import com.jarhero790.eub.ui.souye.BottomGiftDialog;
import com.jarhero790.eub.ui.souye.BottomPingLunDialog;
import com.jarhero790.eub.ui.souye.BottomShareDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.LogUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//1.动画旋转问题
//2分享，3.金币，4光盘，
public class SouyeFragment extends BaseMVPCompatFragment<SouyeContract.SouyePresenter>
        implements SouyeContract.ISouyeView, View.OnClickListener, ViewPagerLayoutManager.OnNextPageImageListener {

    View viewplaypause;
    @BindView(R.id.pro_percent)
    ProgressBar proPercent;
    Unbinder unbinder;
    @BindView(R.id.search_icon)
    ImageView searchIcon;
    private int mCurrentPosition;//当前播放的第几个视频 ，
    private List<Video> lists = new ArrayList<>();
    private TikTokController mTikTokController;
    private TikTokAdapter tikTokAdapter;
    private VideoView mVideoView;
    private AtomicInteger cate = new AtomicInteger(0);
    private AtomicInteger page = new AtomicInteger(1);

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tuijian)
    TextView textViewTuijian;

    @BindView(R.id.zuixin)
    TextView textViewZuixin;

    @BindView(R.id.changshipin)
    TextView textViewChangshipin;

    ViewPagerLayoutManager layoutManager;

    private AtomicBoolean flag = new AtomicBoolean(true);


//    private boolean isPrepared = true;

    private static SouyeFragment instance = null;

    public static SouyeFragment newInstance() {
        if (instance == null) {
            instance = new SouyeFragment();
        }
        return instance;
    }


    private boolean isLook = true;//可见

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tuijian://推荐
                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                    startActivity(new Intent(getActivity(), LoginNewActivity.class));
                } else {
                    textViewTuijian.setBackgroundResource(R.drawable.button_shape1);
                    textViewTuijian.setTextColor(Color.parseColor("#0E0E0E"));
                    textViewZuixin.setTextColor(Color.parseColor("#EFEDED"));
                    textViewChangshipin.setTextColor(Color.parseColor("#EFEDED"));
                    textViewZuixin.setBackgroundResource(0);
                    textViewChangshipin.setBackgroundResource(0);


                    viewplaypause = layoutManager.findViewByPosition(mCurrentPosition);
                    if (viewplaypause != null)
                        viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
                    mVideoView.release();

                    cate.set(0);
                    page.set(1);
//                //加载数据
                    if (lists.size() > 0) {
                        lists.clear();
//                    tikTokAdapter.notifyDataSetChanged();
                    }

                    mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
//                tikTokAdapter.notifyDataSetChanged();
//                Log.e("------1--",cate.get()+"");

                }

                break;
            case R.id.zuixin://最新
                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                    startActivity(new Intent(getActivity(), LoginNewActivity.class));
                } else {
                    textViewZuixin.setBackgroundResource(R.drawable.button_shape1);
                    textViewTuijian.setTextColor(Color.parseColor("#EFEDED"));
                    textViewZuixin.setTextColor(Color.parseColor("#0E0E0E"));
                    textViewChangshipin.setTextColor(Color.parseColor("#EFEDED"));
                    textViewTuijian.setBackgroundResource(0);
                    textViewChangshipin.setBackgroundResource(0);


                    cate.set(1);
                    page.set(1);
                    //加载数据
                    if (lists.size() > 0) {
                        lists.clear();
//                    tikTokAdapter.notifyDataSetChanged();
                    }
                    viewplaypause = layoutManager.findViewByPosition(mCurrentPosition);
                    if (viewplaypause != null)
                        viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
                    mVideoView.release();
                    mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
//                tikTokAdapter.notifyDataSetChanged();
//                Log.e("-------2-",cate.get()+"");
                }

                break;
            case R.id.changshipin://长视频
                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                    startActivity(new Intent(getActivity(), LoginNewActivity.class));
                } else {
                    textViewChangshipin.setBackgroundResource(R.drawable.button_shape1);
                    textViewTuijian.setTextColor(Color.parseColor("#EFEDED"));
                    textViewZuixin.setTextColor(Color.parseColor("#EFEDED"));
                    textViewChangshipin.setTextColor(Color.parseColor("#0E0E0E"));
                    textViewTuijian.setBackgroundResource(0);
                    textViewZuixin.setBackgroundResource(0);

                    cate.set(2);
                    page.set(1);
                    //加载数据
                    if (lists.size() > 0) {
                        lists.clear();
//                    tikTokAdapter.notifyDataSetChanged();
                    }
                    viewplaypause = layoutManager.findViewByPosition(mCurrentPosition);
                    if (viewplaypause != null)
                        viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
                    mVideoView.release();
                    mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
//                tikTokAdapter.notifyDataSetChanged();
//                Log.e("-------3-",cate.get()+"");
                }

                break;
            case R.id.search_icon:
                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                    startActivity(new Intent(getActivity(), LoginNewActivity.class));
                } else {
                    Intent sear = new Intent(getActivity(), SearchActivity.class);
                    startActivity(sear);
                }

                break;
        }
    }


    @Override
    public void initData() {
        super.initData();
        //加载数据
        mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTikTokController = new TikTokController(AppUtils.getContext());
        /**
         * 重要代码  类似抖音垂直加载
         */
        layoutManager = new ViewPagerLayoutManager(AppUtils.getContext(), OrientationHelper.VERTICAL);
        layoutManager.setOnNextPageImageListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("-----------", "souye-onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("-----------", "souye-onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }


    @Override
    public void updateLikeVideo(ShipinDianZan shipinDianZan) {
        //视频点赞  OK
        View view = layoutManager.findViewByPosition(mCurrentPosition);
        ImageView ivLike = view.findViewById(R.id.iv_like);
        TextView tvLike = view.findViewById(R.id.tv_like);
        tvLike.setText(shipinDianZan.getNum());
        //1表示已点赞；0表示未点赞或者取消点赞
        String value = shipinDianZan.getIs();
        if (value.equals("1")) {
            ivLike.setImageResource(R.drawable.iv_like_selected);
        } else {
            ivLike.setImageResource(R.drawable.iv_like_unselected);
        }

        //设置首页UI
    }


    @Override
    public void updateVideos(ArrayList<Video> videos) {

        //设置视频
        Log.e("---------", "有没有反应");


//        lists.clear();
        lists.addAll(videos);

        if (flag.get() == true) {
            tikTokAdapter = new TikTokAdapter(lists, AppUtils.getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(tikTokAdapter);
            flag.set(false);
            videosPageListenerOnListener();
            adapterSetOnItemClickListerer();


            if (mCurrentPosition != 0)
                startPlay(mCurrentPosition);//ok
            Log.e("----------", "来了没有true");

            tikTokAdapter.notifyDataSetChanged();
        } else {
            //tikTokAdapter.notifyDataSetChanged();
//            startPlay(mCurrentPosition);

            tikTokAdapter.notifyItemRangeInserted(lists.size() - 1, videos.size());
            //tikTokAdapter.notifyItemRangeChanged(lists.size()-1,videos.size());
            Log.e("----------", "来了没有false");
        }

//        if (lists.size() > 0) {
//            Log.e("-------size:", "" + lists.size());
//        } else {
//            Log.e("-------size:", "0");
//        }


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                Log.e("-------NEW-", newState + " ");
                tikTokAdapter.setIsshow(false);
//                if (newState==RecyclerView.SCROLL_STATE_IDLE){

//                    tikTokAdapter.notifyItemChanged(mCurrentPosition);
//                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

//                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                int childCount = layoutManager.getChildCount();
                Log.e("-------------", "11-->" + childCount);////子数
                int itemCount = layoutManager.getItemCount();// item总数
                Log.e("-------------", "22-->" + itemCount);
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                //当前屏幕 首个 可见的 Item 的position
                LogUtils.e("-------------当前屏幕 可见的 Item 个数:" + childCount + ",Item总共的个:" + itemCount + ",当前屏幕 首个 可见的 Item 的position" + firstVisibleItemPosition);

                if (firstVisibleItemPosition != mCurrentPosition) {
                    Log.e("-----------gg", "1不同");

                }

                super.onScrolled(recyclerView, dx, dy);
                Log.e("-------NEW-", "dx>" + dx + " " + "dy>" + dy);


            }
        });


    }


    /**
     * 关注了用户之后 按钮会显示 "已关注"  ok
     */
    @Override
    public void updateAttentionUser() {
        View view = layoutManager.findViewByPosition(mCurrentPosition);
        Button btnAttention = view.findViewById(R.id.btn_attention);
        btnAttention.setText("已关注");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //可见的并且是初始化之后才加载
        Log.e("----------", "看看" + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);

//        if (isPrepared && isVisibleToUser) {
//
//
//
//        }
    }

    @Override
    public void onPause() {
        if (mVideoView != null) {
            mVideoView.pause();
        }

        super.onPause();
        Log.e("-------", "souye-onPause");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onStop() {
//        if (mVideoView != null) {
//            mVideoView.release();
//        }
        //在停止播放后，显示播放图标，点击播放图标继续播放
        super.onStop();
        Log.e("-------", "souye-onStop");
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mVideoView != null) {
            Log.e("---------islokk", "" + isLook());
            if (isLook()) {
                mVideoView.resume();
                flag.set(true);


            }
//            if (mPresenter!=null)
//            mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
            Log.e("-------", "souye-onResume");
        } else {
            Log.e("-------", "souye-onResumeN");
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        setLook(true);
        if (mVideoView != null) {
            mVideoView.release();
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        Log.e("--------", "souye-ondestroy");
    }


    //ok
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        Log.e("------------","切换333看看"+hidden);
        if (hidden) {
            setLook(false);
            if (mVideoView != null) {
                mVideoView.pause();
            }
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } else {//可见
            setLook(true);
            viewplaypause = layoutManager.findViewByPosition(mCurrentPosition);
            if (viewplaypause != null)
                viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
            if (mVideoView != null) {
                mVideoView.resume();
                Log.e("----------1", "是不是这里" + mCurrentPosition);
                startPlay(mCurrentPosition);

            } else {
                mVideoView = new VideoView(AppUtils.getContext());
                mVideoView.resume();
                startPlay(mCurrentPosition);
                Log.e("----------2", "是不是这里");
            }
        }
    }


    /**
     * 点击每一页上的评论  分享  点赞  礼物  关注这些图标就会调用这里对应的分支逻辑
     */
    public void adapterSetOnItemClickListerer() {
        tikTokAdapter.setOnItemClickListerer(new TikTokAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String type, View view, View view1, View view2) {
                if (type.equals("back")) {

                } else if (type.equals("评论")) {
                    showPingLun();
                } else if (type.equals("分享")) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    } else {
                        showShare();
                    }
                } else if (type.equals("点赞")) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    } else {
                        likeVideo();
                    }

                } else if (type.equals("礼物")) {
                    showGift();
                } else if (type.equals("关注")) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    } else {
                        attention(lists.get(position).getUid(), SharePreferenceUtil.getToken(AppUtils.getContext()));
                    }

                } else if (type.equals("红心")) {
                    Log.e("-------------", "you");
                    if (mVideoView.isPlaying()) {
                        mVideoView.pause();
                        tikTokAdapter.setIsshow(true);
                    } else {
                        mVideoView.resume();
                        tikTokAdapter.setIsshow(false);
                    }

                } else if (type.equals("红心")) {
                    Log.e("-------------", "you");
                }
            }
        });
    }


    public void videosPageListenerOnListener() {
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
//                Log.e("-----------gg","2222222222不同");
                //ok
                viewplaypause = layoutManager.findViewByPosition(position);    //为recyclerView中item位置          删除了有没有问题
                if (viewplaypause != null)
                    viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
            }
        }, lists);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        unbinder.unbind();
    }


    /**
     * 喜欢视频 也就是心形点赞
     * <p>
     * vid	视频ID
     * token
     */
    public void likeVideo() {
        mPresenter.zan(lists.get(mCurrentPosition).getVideo_id(), SharePreferenceUtil.getToken(AppUtils.getContext()));
    }

    /**
     * 关注用户
     * <p>
     * buid		被关注的用户id
     * token		用户TOKEN
     */

    @LoginFilter
    public void attention(String build, String token) {
        mPresenter.attentionUser(build, token);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_souye;
    }

    /**
     * 最重要  视频播放控件设置
     * VideoView
     */
    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mVideoView = new VideoView(AppUtils.getContext());
        //视频循环播放
        mVideoView.setLooping(true);
        mTikTokController = new TikTokController(AppUtils.getContext());
        mVideoView.setVideoController(mTikTokController);
        /**
         * 推荐  最新  长视频
         */
        textViewTuijian.setOnClickListener(this);
        textViewZuixin.setOnClickListener(this);
        textViewChangshipin.setOnClickListener(this);
        searchIcon.setOnClickListener(this);

    }


    public void showShare() {
        BottomShareDialog bottomShareDialog = BottomShareDialog.newInstance();
        bottomShareDialog.show(getChildFragmentManager(), "share");
    }

    //ok
    public void showPingLun() {
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
            args.putString("vid", lists.get(mCurrentPosition).getVideo_id());
            bottomPingLunDialog.setArguments(args);
            bottomPingLunDialog.show(getChildFragmentManager(), "pinglun");
        }

    }

    //ok
    public void showGift() {
        if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
            startActivity(new Intent(getActivity(), LoginNewActivity.class));
        } else {
            BottomGiftDialog bottomGiftDialog = BottomGiftDialog.newInstance();
            Bundle args = new Bundle();
            args.putString("vid", lists.get(mCurrentPosition).getVideo_id());
            bottomGiftDialog.setArguments(args);
            bottomGiftDialog.show(getChildFragmentManager(), "gift");
        }

    }


    public void showDialog(String value) {
        BottomPingLunDialog bottomDialogFr = BottomPingLunDialog.newInstance();
        bottomDialogFr.show(getChildFragmentManager(), "DF");
        /**Android DialogFragment关闭状态如何监听
         * DialogFragment大家都不陌生，这也是官方推荐使用的弹框方式，关于这个类的使用也很多，
         * 今天就讲一下如何监听的它的关闭状态。在很多情况下，都要对用户的行为进行监听，
         * 比如在弹框消失的时候刷新页面，这是很常见的需求了，使用Dialog做的话，
         * 可以使用dialog.setOnDismissListener(DialogInterface.OnDismissListener listener);方式。
         * 但是DialogFragment并没有提供这样的方法，该怎么办呢？
         * https://www.jianshu.com/p/e988ceb4a2e5
         */
        bottomDialogFr.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }


    @Override
    public void showNextImage(String arg) {
        //Log.e("9999",arg);
    }


    @Subscribe
    public void messageEvent(Integer posittion) {

    }

    private void startPlay(int position) {
        if (lists == null || lists.size() == 0)
            return;
        Video vedio = lists.get(position);
        //EventBus.getDefault().post(position);
        /**
         Uri uri = Uri.parse(vedio.getVideo_img());
         mTikTokController.getThumb().setImageURI(uri);
         */
        // 加载视频的预览图片 Glide方式
        Glide.with(this)
                .load(vedio.getVideo_img())
                .apply(new RequestOptions().placeholder(android.R.color.white))
                .into(mTikTokController.getThumb());

        View itemView = recyclerView.getChildAt(0);
        //设置播放的url
        mVideoView.setUrl(vedio.getUrl());
        //重要
        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
        //获取视频宽高,其中width: mVideoSize[0], height: mVideoSize[1]
//        int width1 = mVideoView.getWidth();
//        int height1 = mVideoView.getHeight();
//        Log.e("-----------w1h1",width1+"  "+height1);//变化 不定

//        int[] size= mVideoView.getVideoSize();
//        String value="位置"+position+",width:"+size[0]+" "+",height:"+size[1];
//        Log.e("Android短视频1",value);

//        int[] screenWidthHeight=AppUtils.getScreenWidthHeight(AppUtils.getContext());
//        int value2=screenWidthHeight[0];
//        int value3=screenWidthHeight[0];
//        Log.e("Android短视频2",value2+"---"+value3);

//        int width=AppUtils.getWindowWidth(AppUtils.getContext());
//        int height=AppUtils.getWindowHeigh(AppUtils.getContext());
//        Log.e("Android短视频3",width+"---"+height);

//        int screenWidth = AppUtils.getScreenWidth(getBindActivity());
//        int screenHeight = AppUtils.getWindowHeigh(AppUtils.getContext());
//        Log.e("Android短视频4",screenWidth+"---"+screenHeight);

//        Log.e("11111111111","位置:"+position+"---"+vedio.getUrl());


//        if (position == 0 || position == 1 || position == 3 || position == 6 || position == 7) {
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.MATCH_PARENT, 500);//RelativeLayout.LayoutParams.MATCH_PARENT 500
//            params.setMargins(0, 400, 0, 0);//top 400
//            mVideoView.setLayoutParams(params);
//            View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置          删除了有没有问题
//            view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.INVISIBLE);
//        } else {
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(0, 0, 0, 0);
//            mVideoView.setLayoutParams(params);
//            View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置
//            view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.VISIBLE);
//        }


        //开始播放视频
        mVideoView.start();
        Log.e("---------max-", "" + "  " + mVideoView.getDuration());
        RelativeLayout relativeLayout = itemView.findViewById(R.id.souye_page_video_relativeLayout);
        ViewParent parent = mVideoView.getParent();
        Log.e("---------max-", "" + "  " + mVideoView.getDuration());
        if (parent instanceof RelativeLayout) {
            ((RelativeLayout) parent).removeView(mVideoView);
        }
        relativeLayout.addView(mVideoView, 2);
        //如果滑动到了最后一页 就要加载新的数据了
        if (position == lists.size() - 1) {
            page.getAndIncrement();
            //加载数据
            mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
        }

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


                //进度//ok
                int duration = (int) mVideoView.getDuration();
                Log.e("---------max-", "" + duration + "  " + mVideoView.getDuration());
                if (proPercent != null)
                    proPercent.setMax(duration);

//                Message message=new Message();
//                message.what=11;
//                message.obj=currentPosition;
//        handler.sendMessageAtTime(message,500);


                if (duration != 0) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();

                            if (mVideoView == null)
                                return;

                            while (mVideoView.isPlaying()) {
                                int currentPosition = (int) mVideoView.getCurrentPosition();
//                                    Log.e("-----pro",""+currentPosition+"  "+mVideoView.getCurrentPosition());
                                if (proPercent != null)
                                    proPercent.setProgress(currentPosition);
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                            }


                        }
                    }.start();
                }


            }
        });


//        mVideoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("---------","点击了视频");
//            }
//        });


    }


    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return SouyePresenter.newInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        //已经初始化
//        isPrepared = true;
        return rootView;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 11:
                    int curr = (int) msg.obj;
                    Log.e("-----pro11", "" + curr + "  " + mVideoView.getCurrentPosition());
                    proPercent.setProgress(curr);
                    break;
            }

        }
    };


    public boolean isLook() {
        return isLook;
    }

    public void setLook(boolean look) {
        isLook = look;
    }


//    public interface PinL{
//        void Clicker(String str);
//    }
//    private PinL pinL;
//
//    public void setPinL(PinL pinL) {
//        this.pinL = pinL;
//    }
}
