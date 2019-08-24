package com.jarhero790.eub.ui.souye.child;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import com.jarhero790.eub.presenter.home.SouyePresenter;
import com.jarhero790.eub.ui.souye.BottomGiftDialog;
import com.jarhero790.eub.ui.souye.BottomPingLunDialog;
import com.jarhero790.eub.ui.souye.BottomShareDialog;
import com.jarhero790.eub.utils.AppUtils;
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
//2分享，3.金币，4光盘，5进度
public class SouyeFragment extends BaseMVPCompatFragment<SouyeContract.SouyePresenter>
        implements SouyeContract.ISouyeView, View.OnClickListener, ViewPagerLayoutManager.OnNextPageImageListener {

    @BindView(R.id.pro_percent)
    ProgressBar proPercent;
    Unbinder unbinder;
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


    private boolean isPrepared=false;

    private static SouyeFragment instance = null;

    public static SouyeFragment newInstance() {
        if (instance == null) {
            instance = new SouyeFragment();
        }
        return instance;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tuijian://推荐
                textViewTuijian.setBackgroundResource(R.drawable.button_shape1);
                textViewTuijian.setTextColor(Color.parseColor("#0E0E0E"));
                textViewZuixin.setTextColor(Color.parseColor("#EFEDED"));
                textViewChangshipin.setTextColor(Color.parseColor("#EFEDED"));
                textViewZuixin.setBackgroundResource(0);
                textViewChangshipin.setBackgroundResource(0);


                cate.set(0);
                page.set(1);
//                //加载数据
                if (lists.size()>0){
                    lists.clear();
                    tikTokAdapter.notifyDataSetChanged();
                }
                if (mVideoView.isPlaying()){
                    mVideoView.release();
                }
                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
                tikTokAdapter.notifyDataSetChanged();
                Log.e("------1--",cate.get()+"");
                break;
            case R.id.zuixin://最新
                textViewZuixin.setBackgroundResource(R.drawable.button_shape1);
                textViewTuijian.setTextColor(Color.parseColor("#EFEDED"));
                textViewZuixin.setTextColor(Color.parseColor("#0E0E0E"));
                textViewChangshipin.setTextColor(Color.parseColor("#EFEDED"));
                textViewTuijian.setBackgroundResource(0);
                textViewChangshipin.setBackgroundResource(0);


                cate.set(1);
                page.set(1);
                //加载数据
                if (lists.size()>0){
                    lists.clear();
                    tikTokAdapter.notifyDataSetChanged();
                }
                if (mVideoView.isPlaying()){
                    mVideoView.release();
                }
                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
                tikTokAdapter.notifyDataSetChanged();
                Log.e("-------2-",cate.get()+"");
                break;
            case R.id.changshipin://长视频
                textViewChangshipin.setBackgroundResource(R.drawable.button_shape1);
                textViewTuijian.setTextColor(Color.parseColor("#EFEDED"));
                textViewZuixin.setTextColor(Color.parseColor("#EFEDED"));
                textViewChangshipin.setTextColor(Color.parseColor("#0E0E0E"));
                textViewTuijian.setBackgroundResource(0);
                textViewZuixin.setBackgroundResource(0);

                cate.set(2);
                page.set(1);
                //加载数据
                if (lists.size()>0){
                    lists.clear();
                    tikTokAdapter.notifyDataSetChanged();
                }
                if (mVideoView.isPlaying()){
                    mVideoView.release();
                }
                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
                tikTokAdapter.notifyDataSetChanged();
                Log.e("-------3-",cate.get()+"");
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
        Log.e("-----------","souye-onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("-----------","souye-onActivityCreated");
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
        Log.e("---------","有没有反应");
        lists.clear();
        lists.addAll(videos);
        if (flag.get() == true) {
            tikTokAdapter = new TikTokAdapter(lists, AppUtils.getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(tikTokAdapter);
            flag.set(false);
            videosPageListenerOnListener();
            adapterSetOnItemClickListerer();
        } else {
            //tikTokAdapter.notifyDataSetChanged();
            tikTokAdapter.notifyItemRangeInserted(lists.size() - 1, videos.size());
            //tikTokAdapter.notifyItemRangeChanged(lists.size()-1,videos.size());
        }

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
        Log.e("----------","看看"+isVisibleToUser);
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
        if (mVideoView != null) {
            mVideoView.release();
        }
        //在停止播放后，显示播放图标，点击播放图标继续播放
        super.onStop();
        Log.e("-------", "souye-onStop");
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.resume();
        }
        Log.e("-------", "souye-onResume");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    //ok
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        Log.e("------------","切换333看看"+hidden);
        if (hidden) {
            if (mVideoView != null) {
                mVideoView.pause();
            }
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } else {
            if (mVideoView != null) {
                mVideoView.resume();
//                Log.e("----------1","是不是这里"+mCurrentPosition);
                startPlay(mCurrentPosition);
            }else {
                mVideoView = new VideoView(AppUtils.getContext());
                mVideoView.resume();
                startPlay(mCurrentPosition);
//                Log.e("----------2","是不是这里");
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
                    showShare();
                } else if (type.equals("点赞")) {
                    likeVideo();
                } else if (type.equals("礼物")) {
                    showGift();
                } else if (type.equals("关注")) {
                    attention(lists.get(position).getUid(), SharePreferenceUtil.getToken(AppUtils.getContext()));
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
//        boolean fullScreen = mVideoView.isFullScreen();
//        if (!fullScreen){
//            Log.e("---------","没有全屏");
//            mVideoView.startFullScreen();
//
//
//        }else {
//            Log.e("---------","当前是全屏");
//        }
        mTikTokController = new TikTokController(AppUtils.getContext());

        mVideoView.setVideoController(mTikTokController);
        mVideoView.startFullScreen();
        /**
         * 推荐  最新  长视频
         */
        textViewTuijian.setOnClickListener(this);
        textViewZuixin.setOnClickListener(this);
        textViewChangshipin.setOnClickListener(this);
    }


    public void showShare() {
        BottomShareDialog bottomShareDialog = BottomShareDialog.newInstance();
        bottomShareDialog.show(getChildFragmentManager(), "share");
    }

    public void showPingLun() {
        BottomPingLunDialog bottomPingLunDialog = BottomPingLunDialog.newInstance();
        bottomPingLunDialog.show(getChildFragmentManager(), "pinglun");
    }

    //ok
    public void showGift() {
        BottomGiftDialog bottomGiftDialog = BottomGiftDialog.newInstance();
        bottomGiftDialog.setCuposition(lists.get(mCurrentPosition).getVideo_id());
        bottomGiftDialog.show(getChildFragmentManager(), "gift");
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
//        mVideoView.startFullScreen();
        //重要
        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
        //获取视频宽高,其中width: mVideoSize[0], height: mVideoSize[1]
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

        int screenWidth = AppUtils.getScreenWidth(getBindActivity());
        int screenHeight = AppUtils.getWindowHeigh(AppUtils.getContext());
//        Log.e("Android短视频4",screenWidth+"---"+screenHeight);

//        Log.e("11111111111","位置:"+position+"---"+vedio.getUrl());

        if (position == 0 || position == 1 || position == 3 || position == 6 || position == 7) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    screenWidth, screenHeight);//RelativeLayout.LayoutParams.MATCH_PARENT 500
            params.setMargins(0, 0, 0, 0);//top 400
            mVideoView.setLayoutParams(params);
//            View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置          删除了有没有问题
//            view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.INVISIBLE);
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0);
            mVideoView.setLayoutParams(params);
            View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置
            view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.VISIBLE);
        }

        //开始播放视频
        mVideoView.start();
        Log.e("---------max-",""+"  "+mVideoView.getDuration());
        RelativeLayout relativeLayout = itemView.findViewById(R.id.souye_page_video_relativeLayout);
        ViewParent parent = mVideoView.getParent();
        Log.e("---------max-",""+"  "+mVideoView.getDuration());
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

        int duration = (int) mVideoView.getDuration();
        Log.e("---------max-",""+duration+"  "+mVideoView.getDuration());
        proPercent.setMax(duration);



        int currentPosition = (int) mVideoView.getCurrentPosition();

        Message message=new Message();
        message.what=11;
        message.obj=currentPosition;
//        handler.sendMessageAtTime(message,500);
//        Log.e("-----pro",""+currentPosition+"  "+mVideoView.getCurrentPosition());
        if (duration!=0){
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    while (mVideoView.isPlaying()){
                        proPercent.setProgress(currentPosition);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }.start();
        }



//        mVideoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("---------","点击了视频");
//            }
//        });
//        mVideoView.setOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
//            @Override
//            public void onPlayerStateChanged(int playerState) {
//                Log.e("onPlayerStateChanged",""+playerState);
//            }
//
//            @Override
//            public void onPlayStateChanged(int playState) {
//                Log.e("onPlayStateChanged",""+playState);
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
        isPrepared = true;
        return rootView;
    }

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           switch (msg.what){
               case 11:
                   int curr= (int) msg.obj;
                   Log.e("-----pro11",""+curr+"  "+mVideoView.getCurrentPosition());
                   proPercent.setProgress(curr);
                   break;
           }

        }
    };
}
