package com.jarhero790.eub.ui.souye.child;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
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


public class SouyeFragment extends BaseMVPCompatFragment<SouyeContract.SouyePresenter>
        implements SouyeContract.ISouyeView ,View.OnClickListener,ViewPagerLayoutManager.OnNextPageImageListener{

    private int mCurrentPosition;
    private List<Video> lists;
    private TikTokController mTikTokController;
    private TikTokAdapter tikTokAdapter;
    private VideoView mVideoView;
    private AtomicInteger cate=new AtomicInteger(0);
    private AtomicInteger page=new AtomicInteger(1);

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tuijian)
    TextView textViewTuijian;

    @BindView(R.id.zuixin)
    TextView textViewZuixin;

    @BindView(R.id.changshipin)
    TextView textViewChangshipin;

    ViewPagerLayoutManager layoutManager;

    private AtomicBoolean flag=new AtomicBoolean(true);

    private static SouyeFragment instance=null;

    public static SouyeFragment newInstance() {
        if(instance==null){
            instance= new SouyeFragment();
        }
        return instance;
    }




    @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.tuijian:
                  textViewTuijian.setBackgroundResource(R.drawable.button_shape1);
                  textViewZuixin.setBackgroundResource(0);
                  textViewChangshipin.setBackgroundResource(0);
                  break;
              case R.id.zuixin:
                  textViewZuixin.setBackgroundResource(R.drawable.button_shape1);
                  textViewTuijian.setBackgroundResource(0);
                  textViewChangshipin.setBackgroundResource(0);
                  break;
              case R.id.changshipin:
                  textViewChangshipin.setBackgroundResource(R.drawable.button_shape1);
                  textViewTuijian.setBackgroundResource(0);
                  textViewZuixin.setBackgroundResource(0);
                  break;
          }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lists=new ArrayList<>();
    }



    @Override
    public void initData() {
        super.initData();
        //加载数据
        mPresenter.getVideos(String.valueOf(cate.get()),String.valueOf(page.get()));
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
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
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
        View view = layoutManager.findViewByPosition(mCurrentPosition);
        ImageView ivLike=view.findViewById(R.id.iv_like);
        TextView tvLike=view.findViewById(R.id.tv_like);
        tvLike.setText(shipinDianZan.getNum());
        //1表示已点赞；0表示未点赞或者取消点赞
        String value=shipinDianZan.getIs();
        if(value.equals("1")){
            ivLike.setImageResource(R.drawable.iv_like_selected);
        }else{
            ivLike.setImageResource(R.drawable.iv_like_unselected);
        }
    }




    @Override
    public void updateVideos(ArrayList<Video> videos) {
        lists.addAll(videos);
        if(flag.get()==true){
            tikTokAdapter = new TikTokAdapter(lists, AppUtils.getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(tikTokAdapter);
            flag.set(false);
            videosPageListenerOnListener();
            adapterSetOnItemClickListerer();
        }else {
            //tikTokAdapter.notifyDataSetChanged();
              tikTokAdapter.notifyItemRangeInserted(lists.size()-1,videos.size());
            //tikTokAdapter.notifyItemRangeChanged(lists.size()-1,videos.size());
        }

    }



    /**
     * 关注了用户之后 按钮会显示 "已关注"
     */
    @Override
    public void updateAttentionUser() {
        View view = layoutManager.findViewByPosition(mCurrentPosition);
        Button btnAttention=view.findViewById(R.id.btn_attention);
        btnAttention.setText("已关注");
    }


    @Override
    public void onPause() {
        super.onPause();
        if(mVideoView!=null){
            mVideoView.pause();
        }
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        if(mVideoView!=null){
            mVideoView.resume();
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mVideoView!=null){
           mVideoView.release();
        }
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }





    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            if(mVideoView!=null){
                mVideoView.pause();
            }
            if(!EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().unregister(this);
            }
        }else {
            if(mVideoView!=null){
                mVideoView.resume();
            }
        }
    }




    /**
     * 点击每一页上的评论  分享  点赞  礼物  关注这些图标就会调用这里对应的分支逻辑
     */
    public void  adapterSetOnItemClickListerer(){
        tikTokAdapter.setOnItemClickListerer(new TikTokAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String type, View view, View view1, View view2) {
                if(type.equals("back")) {

                }else if(type.equals("评论")) {
                    showPingLun();
                }else if(type.equals("分享")) {
                    showShare();
                }else if (type.equals("点赞")) {
                    likeVideo();
                }else if(type.equals("礼物")) {
                    showGift();
                }else if(type.equals("关注")) {
                    attention(lists.get(position).getUid(),SharePreferenceUtil.getToken(AppUtils.getContext()));
                }
            }
        });
    }




    public void videosPageListenerOnListener(){
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
        },lists);

    }



    @Override
    public void onDetach() {
        super.onDetach();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }



    /**
     * 喜欢视频 也就是心形点赞
     *
     * vid	视频ID
       token
     */
    public void likeVideo(){
        mPresenter.zan(lists.get(mCurrentPosition).getVideo_id(),SharePreferenceUtil.getToken(AppUtils.getContext()));
    }

    /**
     * 关注用户
     *
     buid		被关注的用户id
     token		用户TOKEN
     */

    @LoginFilter
    public void attention(String build,String token){
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
    }


    public void showShare() {
        BottomShareDialog bottomShareDialog = BottomShareDialog.newInstance();
        bottomShareDialog.show(getChildFragmentManager(), "share");
    }

    public void showPingLun(){
        BottomPingLunDialog bottomPingLunDialog = BottomPingLunDialog.newInstance();
        bottomPingLunDialog.show(getChildFragmentManager(), "pinglun");
    }


    public void showGift() {
        BottomGiftDialog bottomGiftDialog = BottomGiftDialog.newInstance();
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
        bottomDialogFr.setOnDismissListener(new DialogInterface.OnDismissListener(){
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
    public void messageEvent(Integer posittion){

    }

    private void startPlay(int position) {
        Video vedio=lists.get(position);
        //EventBus.getDefault().post(position);
        /**
         Uri uri = Uri.parse(vedio.getVideo_img());
         mTikTokController.getThumb().setImageURI(uri);
         */
          // 加载视频的预览图片 Glide方式
        Glide.with(this)
                .load(vedio.getVideo_img())
                .placeholder(android.R.color.white)
                .into(mTikTokController.getThumb());

        View itemView = recyclerView.getChildAt(0);
        //设置播放的url
        mVideoView.setUrl(vedio.getUrl());
        //重要
        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
        //获取视频宽高,其中width: mVideoSize[0], height: mVideoSize[1]
        int[] size= mVideoView.getVideoSize();
        String value="位置"+position+",width:"+size[0]+" "+",height:"+size[1];
        Log.e("Android短视频1",value);

        int[] screenWidthHeight=AppUtils.getScreenWidthHeight(AppUtils.getContext());
        int value2=screenWidthHeight[0];
        int value3=screenWidthHeight[0];
        Log.e("Android短视频2",value2+"---"+value3);

        int width=AppUtils.getWindowWidth(AppUtils.getContext());
        int height=AppUtils.getWindowHeigh(AppUtils.getContext());
        Log.e("Android短视频3",width+"---"+height);

        int screenWidth=AppUtils.getScreenWidth(getBindActivity());
        int screenHeight=AppUtils.getWindowHeigh(AppUtils.getContext());
        Log.e("Android短视频4",screenWidth+"---"+screenHeight);

        Log.e("11111111111","位置:"+position+"---"+vedio.getUrl());

        if(position==0||position==1||position==3||position==6||position==7){
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,500);
            params.setMargins(0, 400, 0, 0);
            mVideoView.setLayoutParams(params);
            View view = layoutManager.findViewByPosition(position);	//为recyclerView中item位置
            view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.INVISIBLE);
        }else{
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(
                                                       RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                       RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0);
            mVideoView.setLayoutParams(params);
            View view = layoutManager.findViewByPosition(position);	//为recyclerView中item位置
            view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.VISIBLE);
        }

        //开始播放视频
        mVideoView.start();
        RelativeLayout relativeLayout = itemView.findViewById(R.id.souye_page_video_relativeLayout);
        ViewParent parent = mVideoView.getParent();
        if (parent instanceof RelativeLayout) {
            ((RelativeLayout) parent).removeView(mVideoView);
        }
        relativeLayout.addView(mVideoView,2);
        //如果滑动到了最后一页 就要加载新的数据了
        if(position==lists.size()-1){
            page.getAndIncrement();
            //加载数据
            mPresenter.getVideos(String.valueOf(cate.get()),String.valueOf(page.get()));
        }
    }






    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return SouyePresenter.newInstance();
    }



}
