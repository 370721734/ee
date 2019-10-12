package com.jarhero790.eub.ui.souye.child;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.danikula.videocache.HttpProxyCacheServer;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.GlobalApplication;
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
import com.jarhero790.eub.message.bean.HiddBean;
import com.jarhero790.eub.message.bean.Zanchange;
import com.jarhero790.eub.message.bean.attentionchange;
import com.jarhero790.eub.message.bean.souyelooktwo;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.message.souye.BusinessWebTwoActivity;
import com.jarhero790.eub.message.souye.ListFragment;
import com.jarhero790.eub.message.souye.TagFragment;
import com.jarhero790.eub.message.souye.TongKuanActivity;
import com.jarhero790.eub.presenter.home.SouyePresenter;
import com.jarhero790.eub.record.bean.FaVBean;
import com.jarhero790.eub.ui.souye.BottomGiftDialog;
import com.jarhero790.eub.ui.souye.BottomPingLunDialog;
import com.jarhero790.eub.ui.souye.BottomShareDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.NetworkConnectionUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.fragment.BaseFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//1.动画旋转问题
//2分享，
public class SouyeTwoFragment extends BaseMVPCompatFragment<SouyeContract.SouyePresenter>
        implements SouyeContract.ISouyeView, View.OnClickListener, OnViewPagerListener {

    View viewplaypause;
    @BindView(R.id.pro_percent)
    ProgressBar proPercent;
    Unbinder unbinder;
    @BindView(R.id.search_icon)
    ImageView searchIcon;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
    @BindView(R.id.wangluoyichang)
    RelativeLayout wangluoyichang;
    private static int mCurrentPosition;//当前播放的第几个视频 ，
    private List<Video> lists = new ArrayList<>();
    private TikTokController mTikTokController;
    private TikTokAdapter tikTokAdapter;
    private VideoView mVideoView;
    private static AtomicInteger cate = new AtomicInteger(1);
    private static AtomicInteger page = new AtomicInteger(1);

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tuijian)
    TextView textViewTuijian;

    @BindView(R.id.zuixin)
    TextView textViewZuixin;

    @BindView(R.id.changshipin)
    TextView textViewChangshipin;

    ViewPagerLayoutManager layoutManager;

    private static AtomicBoolean flag = new AtomicBoolean(true);

    RotateAnimation rotateAnimation;//旋转动画
//    View viewcir;
//    private boolean isPrepared = true;

    private static SouyeTwoFragment instance = null;

    public static SouyeTwoFragment newInstance() {
        if (instance == null) {
            instance = new SouyeTwoFragment();
        }
        cate.set(1);
        page.set(1);
        flag.set(true);
        mCurrentPosition = 0;
        return instance;
    }

    //    private Dialog dialog;
    private boolean isLook = true;//可见

//    private TextView tvzan;//点赞


    private boolean isnext = false;

    GlobalApplication app;

    private List<BaseFragment> fragmentList;
    private Fragment tempFragment;
    public ListFragment listFragment;
    public TagFragment tagFragment;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tuijian://推荐
//                dialog = new Dialog(getActivity(), R.style.progress_dialog);
//                dialog.setContentView(R.layout.dialog);
//                dialog.setCancelable(true);
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                dialog.show();


//                switchFragment(tempFragment, fragmentList.get(0));

                /**
                 getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
                 textViewTuijian.setBackgroundResource(R.drawable.button_shape1);
                 textViewTuijian.setTextColor(Color.parseColor("#0E0E0E"));
                 textViewZuixin.setTextColor(Color.parseColor("#EFEDED"));
                 textViewChangshipin.setTextColor(Color.parseColor("#EFEDED"));
                 textViewZuixin.setBackgroundResource(0);
                 textViewChangshipin.setBackgroundResource(0);


                 viewplaypause = layoutManager.findViewByPosition(mCurrentPosition);
                 if (viewplaypause != null) {
                 viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
                 viewplaypause.findViewById(R.id.circleImageView).startAnimation(rotateAnimation);
                 }

                 mVideoView.release();

                 cate.set(0);
                 page.set(1);
                 flag.set(true);
                 mCurrentPosition = 0;
                 //                //加载数据
                 //                if (lists.size() > 0) {
                 //                    lists.clear();
                 //                    tikTokAdapter.notifyDataSetChanged();
                 //                }

                 //                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
                 //                tikTokAdapter.notifyDataSetChanged();
                 //                Log.e("------1--",cate.get()+"");

                 if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
                 if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                 mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
                 //                        Log.e("-----------","无token");
                 } else {
                 //                        Log.e("-----------","有token");
                 mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()), SharePreferenceUtil.getToken(AppUtils.getContext()));
                 }
                 } else {
                 Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
                 }


                 //                wodeurl(String.valueOf(cate.get()), String.valueOf(page.get()));

                 */
                break;
            case R.id.zuixin://最新
//                dialog = new Dialog(getActivity(), R.style.progress_dialog);
//                dialog.setContentView(R.layout.dialog);
//                dialog.setCancelable(true);
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                dialog.show();

//                switchFragment(tempFragment, fragmentList.get(1));
                /**
                 getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
                 textViewZuixin.setBackgroundResource(R.drawable.button_shape1);
                 textViewTuijian.setTextColor(Color.parseColor("#EFEDED"));
                 textViewZuixin.setTextColor(Color.parseColor("#0E0E0E"));
                 textViewChangshipin.setTextColor(Color.parseColor("#EFEDED"));
                 textViewTuijian.setBackgroundResource(0);
                 textViewChangshipin.setBackgroundResource(0);


                 cate.set(1);
                 page.set(1);
                 flag.set(true);
                 mCurrentPosition = 0;
                 //加载数据
                 //                if (lists.size() > 0) {
                 //                    lists.clear();
                 //                    tikTokAdapter.notifyDataSetChanged();
                 //                }
                 viewplaypause = layoutManager.findViewByPosition(mCurrentPosition);
                 if (viewplaypause != null) {
                 viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
                 viewplaypause.findViewById(R.id.circleImageView).startAnimation(rotateAnimation);
                 }
                 mVideoView.release();
                 //                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));


                 if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
                 if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                 mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
                 } else {
                 mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()), SharePreferenceUtil.getToken(AppUtils.getContext()));
                 }
                 } else {
                 Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
                 }
                 //                tikTokAdapter.notifyDataSetChanged();
                 //                Log.e("-------2-",cate.get()+"");
                 //                wodeurl(String.valueOf(cate.get()), String.valueOf(page.get()));

                 */
                break;
            case R.id.changshipin://长视频
//                dialog = new Dialog(getActivity(), R.style.progress_dialog);
//                dialog.setContentView(R.layout.dialog);
//                dialog.setCancelable(true);
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                dialog.show();

                /**
                 getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
                 textViewChangshipin.setBackgroundResource(R.drawable.button_shape1);
                 textViewTuijian.setTextColor(Color.parseColor("#EFEDED"));
                 textViewZuixin.setTextColor(Color.parseColor("#EFEDED"));
                 textViewChangshipin.setTextColor(Color.parseColor("#0E0E0E"));
                 textViewTuijian.setBackgroundResource(0);
                 textViewZuixin.setBackgroundResource(0);

                 cate.set(2);
                 page.set(1);
                 flag.set(true);
                 mCurrentPosition = 0;
                 //加载数据
                 //                if (lists.size() > 0) {
                 //                    lists.clear();
                 //                    tikTokAdapter.notifyDataSetChanged();
                 //                }
                 viewplaypause = layoutManager.findViewByPosition(mCurrentPosition);
                 if (viewplaypause != null) {
                 viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
                 viewplaypause.findViewById(R.id.circleImageView).startAnimation(rotateAnimation);
                 }
                 mVideoView.release();
                 //                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));


                 if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
                 if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                 mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
                 } else {
                 mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()), SharePreferenceUtil.getToken(AppUtils.getContext()));

                 }
                 } else {
                 Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
                 }
                 //                tikTokAdapter.notifyDataSetChanged();
                 //                Log.e("-------3-",cate.get()+"");
                 //                wodeurl(String.valueOf(cate.get()), String.valueOf(page.get()));
                 break;
                 case R.id.search_icon:
                 if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                 startActivity(new Intent(getActivity(), LoginNewActivity.class));
                 } else {
                 Intent sear = new Intent(getActivity(), SearchActivity.class);
                 startActivity(sear);
                 }

                 */
                break;
            case R.id.wangluoyichang:
                if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
                    if (lists.size() > 0) {
                        lists.clear();
                    }

                    wangluoyichang.setVisibility(View.GONE);
                    nodingdan.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
//                Log.e("-----------", "无token————initdata");
                    } else {
//                Log.e("-----------", "有token----initdata");
                        mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()), SharePreferenceUtil.getToken(AppUtils.getContext()));
                    }
                } else {
//            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
                    wangluoyichang.setVisibility(View.VISIBLE);
                    nodingdan.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }
                break;
        }
    }

//    private void wodeurl(String s1, String s2) {
//        RetrofitManager.getInstance().getDataServer().getVideourl(s1,s2).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()){
//                    try {
//                        String json=response.body().string();
//                        Log.e("-------------jj=",json);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void ee1(souyelookone bean) {
//        if (bean.equals("one")){
//            if (mVideoView != null) {
//                mVideoView.pause();
//            }
//        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ee2(souyelooktwo bean) {
        if (bean.getName().equals("ok")) {
            Log.e("-------souyetwo", "ee2_ok");
            if (mVideoView != null) {
                if (!mVideoView.isPlaying()) {
                    mVideoView.start();
                }
            }
        } else {
//            if (mVideoView != null) {
//                mVideoView.pause();
//            }
//            onHiddenChanged(true);
            Log.e("-------souyetwo", "ee2");
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void ee3(souyelookthree bean) {
//        if (bean.equals("three")){
//            if (mVideoView != null) {
//                mVideoView.pause();
//            }
//        }
//    }


    @Override
    public void initData() {
        super.initData();
        //加载数据
        if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
            if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                if (lists.size() > 0) {
                    lists.clear();
                }
                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
//            Log.e("-----------","无token");
            } else {
//            Log.e("-----------","有token");
                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()), SharePreferenceUtil.getToken(AppUtils.getContext()));
            }
        } else {
//            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
            wangluoyichang.setVisibility(View.VISIBLE);
            nodingdan.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (GlobalApplication) getActivity().getApplication();
//        initFragment();

        mTikTokController = new TikTokController(AppUtils.getContext());
        /**
         * 重要代码  类似抖音垂直加载
         */
        layoutManager = new ViewPagerLayoutManager(AppUtils.getContext(), OrientationHelper.VERTICAL);
        layoutManager.setOnViewPagerListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("-----------", "souyetwo-onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Log.e("-----------", "souyetwo-onActivityCreated");
        api = WXAPIFactory.createWXAPI(getActivity(), GlobalApplication.APP_ID_Wei, true);
        api.registerApp(GlobalApplication.APP_ID_Wei);
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


    private boolean iszanle = false;//是否赞过

    @Override
    public void updateLikeVideo(ShipinDianZan shipinDianZan) {
        //视频点赞  OK
//        View view = layoutManager.findViewByPosition(mCurrentPosition);
//        ImageView ivLike = view.findViewById(R.id.iv_like);
//        TextView tvLike = view.findViewById(R.id.tv_like);
//        tvLike.setText(shipinDianZan.getNum());
//        //1表示已点赞；0表示未点赞或者取消点赞
//        String value = shipinDianZan.getIs();
//        if (value.equals("1")) {
//            ivLike.setImageResource(R.drawable.iv_like_selected);
//            setIszanle(true);
////            Log.e("----------", "gogogogog");
//        } else {
//            ivLike.setImageResource(R.drawable.iv_like_unselected);
//            setIszanle(false);
//        }

        //设置首页UI
        EventBus.getDefault().post(new Zanchange("zan"));
    }


    @Override
    public void updateVideos(ArrayList<Video> videos) {

        //设置视频
        Log.e("---------", "有没有数据了2");
        if (videos == null || videos.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            nodingdan.setVisibility(View.VISIBLE);
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        nodingdan.setVisibility(View.GONE);

        lists.addAll(videos);

        if (dialog != null)
            dialog.dismiss();
        if (flag.get()) {
            tikTokAdapter = new TikTokAdapter(lists, AppUtils.getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(tikTokAdapter);
            flag.set(false);
            videosPageListenerOnListener();
            adapterSetOnItemClickListerer();


            if (mCurrentPosition != 0)
                startPlay(mCurrentPosition);//ok
//            Log.e("----------", "来了没有true");

            tikTokAdapter.notifyDataSetChanged();
        } else {
            //tikTokAdapter.notifyDataSetChanged();
//            startPlay(mCurrentPosition);

            tikTokAdapter.notifyItemRangeInserted(lists.size() - 1, videos.size());
            //tikTokAdapter.notifyItemRangeChanged(lists.size()-1,videos.size());
//            Log.e("----------", "来了没有false");
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

//                Log.e("-------NEW-", newState + " ");
                tikTokAdapter.setIsshow(false);
//                if (newState==RecyclerView.SCROLL_STATE_IDLE){

//                    tikTokAdapter.notifyItemChanged(mCurrentPosition);
//                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

//                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
//                int childCount = layoutManager.getChildCount();
//                Log.e("-------------", "11-->" + childCount);////子数
//                int itemCount = layoutManager.getItemCount();// item总数
//                Log.e("-------------", "22-->" + itemCount);
//                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                //当前屏幕 首个 可见的 Item 的position
//                LogUtils.e("-------------当前屏幕 可见的 Item 个数:" + childCount + ",Item总共的个:" + itemCount + ",当前屏幕 首个 可见的 Item 的position" + firstVisibleItemPosition);

//                if (lists != null && firstVisibleItemPosition != mCurrentPosition && lists.size() > 0) {
////                    Log.e("-----------gg", "1不同");
//                    String is_zan = lists.get(mCurrentPosition).getIs_zan();
////                    Log.e("-----------gg","是否赞了"+is_zan);
//                }

                super.onScrolled(recyclerView, dx, dy);
//                Log.e("-------NEW-", "dx>" + dx + " " + "dy>" + dy);


            }
        });


    }


    /**
     * 关注了用户之后 按钮会显示 "已关注"  ok
     */
    @Override
    public void updateAttentionUser() {
//        View view = layoutManager.findViewByPosition(mCurrentPosition);
//        Button btnAttention = view.findViewById(R.id.btn_attention);
//        btnAttention.setText("已关注");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //可见的并且是初始化之后才加载
//        Log.e("----------", "看看" + isVisibleToUser);
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
        Log.e("-------", "souyetwo-onPause");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (!isnext) {
            if (handler != null) {
                handler.removeMessages(22);
            }
        }

    }

    @Override
    public void onStop() {
//        if (mVideoView != null) {
//            mVideoView.release();
//        }
        //在停止播放后，显示播放图标，点击播放图标继续播放
        super.onStop();
        Log.e("-------", "souyetwo-onStop");
        firstv = true;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mVideoView != null) {
//            Log.e("---------islokk", "" + isLook());
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setDuration(4000);
            rotateAnimation.setRepeatCount(Animation.INFINITE);
            if (isLook()) {
                mVideoView.resume();
                flag.set(true);
//                Log.e("----------4", "是不是这里" + mCurrentPosition);
                startPlay(mCurrentPosition);

            }
//            if (mPresenter!=null)
//            mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
            Log.e("-------", "souyetwo-onResume");
        } else {
            Log.e("-------", "souyetwo-onResumeN");
        }


//        mPresenter.getVideos();
//        Intent intentx=getActivity().getIntent();
//       String isok= intentx.getStringExtra("ok");
//       if (isok!=null)


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fav(FaVBean faVBean) {
//        Log.e("---------youye=",faVBean.getName());
        if (faVBean.getName().equals("video")) {
            cate.set(0);
            page.set(1);
            mCurrentPosition = 0;
//                //加载数据
            if (lists.size() > 0) {
                lists.clear();
            }


            if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                    mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
//                    Log.e("-----------","无token");
                } else {
//                    Log.e("-----------","有token");
                    mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()), SharePreferenceUtil.getToken(AppUtils.getContext()));
                }
            } else {
//                Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
                wangluoyichang.setVisibility(View.VISIBLE);
                nodingdan.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
            }
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ee(HiddBean bean) {
        if (bean != null) {
            if (bean.getName().equals("true")) {
                Log.e("----------", "souyetwo-onHidden" + "隐藏2");
                setLook(false);
                if (mVideoView != null) {
                    mVideoView.pause();
                }
                if (!EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().unregister(this);
                }
            } else {
                if (app.isIslooktwo()) {
                    Log.e("----------", "souyetwo-onHidden可见2");
                    setLook(true);
                    viewplaypause = layoutManager.findViewByPosition(mCurrentPosition);
                    if (viewplaypause != null) {
                        viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
                        viewplaypause.findViewById(R.id.circleImageView).startAnimation(rotateAnimation);
                    }

                    if (mVideoView != null) {
                        mVideoView.resume();
//                Log.e("----------1", "是不是这里" + mCurrentPosition);
                        startPlay(mCurrentPosition);

                    } else {
                        mVideoView = new VideoView(AppUtils.getContext());
                        mVideoView.resume();
                        startPlay(mCurrentPosition);
//                Log.e("----------2", "是不是这里");
                    }
                }

            }

        }
    }

    private boolean firstv = true;

    //ok
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("----------", "souyetwo-onHidden" + hidden);
        if (firstv) {
            if (hidden) {
//            Log.e("----------3", "切换333看看" + hidden);
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
                if (viewplaypause != null) {
                    viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
                    viewplaypause.findViewById(R.id.circleImageView).startAnimation(rotateAnimation);
                }

                if (mVideoView != null) {
                    mVideoView.resume();
//                Log.e("----------1", "是不是这里" + mCurrentPosition);
                    startPlay(mCurrentPosition);

                } else {
                    mVideoView = new VideoView(AppUtils.getContext());
                    mVideoView.resume();
                    startPlay(mCurrentPosition);
//                Log.e("----------2", "是不是这里");
                }
            }
            firstv = false;
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
                        showShare(position);
                    }
                } else if (type.equals("点赞")) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    } else {
//                        tvzan= (TextView) view1;//有了
                        ImageView ivlike = (ImageView) view;
                        TextView tv2 = (TextView) view1;

                        if (ivlike.isSelected()) {
                            ivlike.setSelected(false);
                            likeVideo();
                            String string = tv2.getText().toString();
                            int text = (Integer.parseInt(string) - 1);
                            tv2.setText("" + text);
//                            if (list!=null && list.size()>0){
//                                zanother(list.get(mCurrentPosition).getVideo_id()+"");
//
//
//                            }

//                        Log.e("-----------str=",""+(Integer.parseInt(string)-1));
                        } else {
                            ivlike.setSelected(true);
                            likeVideo();
                            String string = tv2.getText().toString();
                            int text = (Integer.parseInt(string) + 1);
                            tv2.setText("" + text);
//                            if (lists!=null && lists.size()>0){
//                                zanother(lists.get(mCurrentPosition).getVideo_id()+"");
//
////                            tikTokAdapter.notifyItemChanged(mCurrentPosition);//不能刷新？？
//                            }
//                        String string=tv2.getText().toString();
//                        Log.e("-----------str=",""+(Integer.parseInt(string)+1));
                        }


                    }

                } else if (type.equals("礼物")) {
                    showGift();
                } else if (type.equals("关注")) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    } else {
//                        attention(lists.get(position).getUid(), SharePreferenceUtil.getToken(AppUtils.getContext()));

                        Button button = (Button) view;
                        attentions(button);

                    }

                } else if (type.equals("红心")) {
//                    Log.e("-------------", "you");
                    if (mVideoView.isPlaying()) {
                        mVideoView.pause();
                        tikTokAdapter.setIsshow(true);
                    } else {
                        mVideoView.resume();
                        tikTokAdapter.setIsshow(false);
                    }

                } else if (type.equals("红红")) {

                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals(""))
                        return;
                    ImageView ivlike = (ImageView) view1;
                    TextView tvlike = (TextView) view2;

                    if (!ivlike.isSelected()) {
                        likeVideo();
                        ivlike.setSelected(true);
//                        Log.e("-------------", "you ok" + isIszanle());
                        int b = Integer.parseInt(tvlike.getText().toString()) + 1;

                        tvlike.setText("" + b);
                    }

//                    if (!isIszanle()) {
////                        Log.e("-------------", "you");
//                    }


//                    View viewh = layoutManager.findViewByPosition(mCurrentPosition);
//                    ImageView ivLike = view.findViewById(R.id.iv_like);
//                    TextView tvLike = view.findViewById(R.id.tv_like);
//                    tvLike.setText(shipinDianZan.getNum());
//                    //1表示已点赞；0表示未点赞或者取消点赞
//                    String value = shipinDianZan.getIs();
//                    if (value.equals("1")) {
//                        ivLike.setImageResource(R.drawable.iv_like_selected);
//                    } else {
//                        ivLike.setImageResource(R.drawable.iv_like_unselected);
//                    }
                } else if (type.equals("商城")) {
//                    Log.e("-------------", "商城");
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    } else {

                        RetrofitManager.getInstance().getDataServer().click_num(lists.get(mCurrentPosition).getGood_id()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
//                                        String json=response.body().string();
//                                        Log.e("-----------click=",json);
                                        TextView tv = (TextView) view2;
//                                        Log.e("----------click2=",tv.getText().toString());
                                        int b = Integer.parseInt(tv.getText().toString()) + 1;
                                        tv.setText("" + b);
                                        Intent intentx = new Intent(getActivity(), BusinessWebTwoActivity.class);
                                        intentx.putExtra("url", "http://www.51ayhd.com/web/Shopping/#/shopindex/token/" + SharePreferenceUtil.getToken(AppUtils.getContext()) + "/good_id/" + lists.get(mCurrentPosition).getGood_id());
                                        intentx.putExtra("good_id", lists.get(mCurrentPosition).getGood_id());
                                        startActivity(intentx);

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

                } else if (type.equals("同款")) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    } else {
                        Intent intentt = new Intent(getActivity(), TongKuanActivity.class);
                        intentt.putExtra("video_id", lists.get(mCurrentPosition).getVideo_id());
                        startActivity(intentt);
                    }
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
//                Log.e("----------", "onPageRelease=" + isNext + "  " + position);
                if (mCurrentPosition == position) {
                    mVideoView.release();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
//                Log.e("----------", "onPageSelected选择位置:" + position + " 下一页:" + isBottom);
                if (mCurrentPosition == position) return;
                startPlay(position);
                mCurrentPosition = position;
//                Log.e("-----------gg","2222222222不同");
                //ok
                viewplaypause = layoutManager.findViewByPosition(position);    //为recyclerView中item位置          删除了有没有问题
                if (viewplaypause != null) {
                    viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
                    viewplaypause.findViewById(R.id.circleImageView).startAnimation(rotateAnimation);
                }
            }
        });

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
        if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {

            mPresenter.zan(lists.get(mCurrentPosition).getVideo_id(), SharePreferenceUtil.getToken(AppUtils.getContext()));
        } else {
//            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
            wangluoyichang.setVisibility(View.VISIBLE);
            nodingdan.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    /**
     * 关注用户
     * <p>
     * buid		被关注的用户id
     * token		用户TOKEN
     */

    @LoginFilter
    public void attention(String build, String token) {
//        if (NetworkConnectionUtils.isNetworkConnected(getActivity())){
//
//            mPresenter.attentionUser(build, token);
//        }else {
//            Toast.makeText(getActivity(),"网络不可用",Toast.LENGTH_SHORT).show();
//        }
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
        wangluoyichang.setOnClickListener(this);

    }

    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;
    private IWXAPI api;
    //微信
    private static final int THUMB_SIZE = 150;

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void showShare(int po) {
        BottomShareDialog bottomShareDialog = BottomShareDialog.newInstance();
        Bundle args = new Bundle();
        args.putString("url", lists.get(po).getUrl());
        args.putString("videoid", lists.get(po).getVideo_id());
        bottomShareDialog.setArguments(args);
        bottomShareDialog.show(getChildFragmentManager(), "share");
        bottomShareDialog.setShareDialog(new BottomShareDialog.ShareDialog() {
            @Override
            public void Clicklinear(View view, String type) {
                if (type.equals("下载")) {
//                    Log.e("-------", "下载");
//
//
//                    bottomShareDialog.dismiss();
                } else if (type.equals("分享")) {
//                    Log.e("-------", "分享");
                    WXWebpageObject webpage = new WXWebpageObject();
                    webpage.webpageUrl = "http://www.51ayhd.com/web/Shopping/share.html";
                    WXMediaMessage msg = new WXMediaMessage(webpage);
                    msg.title = "WebPage Title WebPage";
                    msg.description = "WebPage Description WebPage Description";
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.zuanshi_logo);
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                    bmp.recycle();
                    msg.thumbData = bmpToByteArray(thumbBmp, true);

                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("webpage");
                    req.message = msg;
                    req.scene = mTargetScene;
                    api.sendReq(req);

                    bottomShareDialog.dismiss();
                } else {
                }
            }
        });
    }


    public byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //ok
    public void showPingLun() {
        if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
            startActivity(new Intent(getActivity(), LoginNewActivity.class));
        } else {
            BottomPingLunDialog bottomPingLunDialog = BottomPingLunDialog.newInstance();
            Bundle args = new Bundle();
            args.putString("vid", lists.get(mCurrentPosition).getVideo_id());
            bottomPingLunDialog.setArguments(args);
            bottomPingLunDialog.show(getChildFragmentManager(), "pinglun");
            bottomPingLunDialog.setPinNum(new BottomPingLunDialog.PinNum() {
                @Override
                public void Clicker(int num) {
                    View view = layoutManager.findViewByPosition(mCurrentPosition);
                    if (view != null) {
                        TextView tvLike = view.findViewById(R.id.tv_pinglun);
                        tvLike.setText("" + num);
                    }

                }
            });
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


//    @Override
//    public void showNextImage(String arg) {
//        //Log.e("9999",arg);
//    }


    @Subscribe
    public void messageEvent(Integer posittion) {

    }

    private Dialog dialog;


    private void startPlay(int position) {

        //如果滑动到了最后一页 就要加载新的数据了
        if (position == lists.size() - 1) {
            page.getAndIncrement();
            if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
                dialog = new Dialog(getActivity(), R.style.progress_dialog);
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                flag.set(false);
                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                    mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
                } else {
                    mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()), SharePreferenceUtil.getToken(AppUtils.getContext()));
                }
            } else {
                wangluoyichang.setVisibility(View.VISIBLE);
                nodingdan.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
            }
        }

        View itemView = recyclerView.getChildAt(0);
//        View bb=View.inflate(this,R.layout.item_tik_tok,null);不行这句
        if (itemView==null) return;
        RelativeLayout relativeLayout = itemView.findViewById(R.id.souye_page_video_relativeLayout);
        Glide.with(this)
                .load(lists.get(position).getVideo_img())
                .apply(new RequestOptions().placeholder(android.R.color.white))
                .into(mTikTokController.getThumb());
        ViewParent parent = mVideoView.getParent();
        if (parent instanceof RelativeLayout) {
            ((RelativeLayout) parent).removeView(mVideoView);
        }
        if (relativeLayout==null) return;
        relativeLayout.addView(mVideoView,2);
        HttpProxyCacheServer proxy = GlobalApplication.getProxy(getActivity());
        String proxyUrl = proxy.getProxyUrl(lists.get(position).getUrl());
        mVideoView.setUrl(proxyUrl);
        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
        mVideoView.start();
//        Log.e("-----------tu7",lists.get(position).getVideo_img());
//        Log.e("-----------url",lists.get(position).getUrl());
    }

    private void startPlay2(int position) {
        if (lists == null || lists.size() == 0)
            return;
        Video vedio = lists.get(position);
//        int positiontwo = ((position + 1) > (lists.size() - 1)) ? position : (position + 1);
//        Video vediotwo = lists.get(positiontwo);
        //EventBus.getDefault().post(position);
        /**
         Uri uri = Uri.parse(vedio.getVideo_img());
         mTikTokController.getThumb().setImageURI(uri);
         */

//        Log.e("---------videoid=",vedio.getVideo_id());
        viewplaypause = layoutManager.findViewByPosition(mCurrentPosition);
        if (viewplaypause != null) {
            viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
            viewplaypause.findViewById(R.id.circleImageView).startAnimation(rotateAnimation);
        }
        // 加载视频的预览图片 Glide方式
//        Glide.with(this)
//                .load(vedio.getVideo_img())
//                .apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor).diskCacheStrategy(DiskCacheStrategy.ALL))
//                .into(mTikTokController.getThumb());

        if (vedio.getAnyhow().equals("1")) {
            mVideoView.setScreenScale(VideoView.SCREEN_SCALE_MATCH_PARENT);//q
//            Log.e("-------------","竖屏视频");

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mTikTokController.getThumb().setLayoutParams(params);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            mTikTokController.getThumb().setScaleType(ImageView.ScaleType.FIT_XY);
            mTikTokController.getThumb().setAdjustViewBounds(true);
            Glide.with(getActivity())
                    .load(vedio.getVideo_img())
//                .thumbnail(0.1f)
                    .apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(mTikTokController.getThumb());

        } else {
            mVideoView.setScreenScale(VideoView.SCREEN_SCALE_DEFAULT);//默认1：1
//            mVideoView.setRotation();
//            Log.e("-------------","横屏视频");

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mTikTokController.getThumb().setLayoutParams(params);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            mTikTokController.getThumb().setScaleType(ImageView.ScaleType.FIT_XY);
            mTikTokController.getThumb().setAdjustViewBounds(true);
            Glide.with(getActivity())
                    .load(vedio.getVideo_img())
//                .thumbnail(0.1f)
                    .apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(mTikTokController.getThumb());
        }

        View itemView = recyclerView.getChildAt(0);

        HttpProxyCacheServer proxy = GlobalApplication.getProxy(getActivity());
        String proxyUrl = proxy.getProxyUrl(vedio.getUrl());
//        String proxyUrltwo = proxy.getProxyUrl(vediotwo.getUrl());
        mVideoView.setUrl(proxyUrl);
//        Log.e("----------------hurl=",proxyUrl);
//        Log.e("----------------vurl=",vedio.getUrl());
//        if (proxyUrl.length() > 0) {
//            mVideoView.setUrl(proxyUrl);
//        } else {
//            //设置播放的url
//            mVideoView.setUrl(vedio.getUrl());
//        }

//        if (vedio.getAnyhow().equals("1")) {
//            mVideoView.setScreenScale(VideoView.SCREEN_SCALE_MATCH_PARENT);//q
////            Log.e("-------------","竖屏视频");
//        } else {
//            mVideoView.setScreenScale(VideoView.SCREEN_SCALE_DEFAULT);//默认1：1
////            mVideoView.setRotation();
////            Log.e("-------------","横屏视频");
//        }

        //重要
//        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);//中心载剪
//        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_ORIGINAL);//中心 小屏
//        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_DEFAULT);//默认1：1
//        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_MATCH_PARENT);//q
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


//        Log.e("---------max-", "" + "  " + mVideoView.getDuration());
        if (itemView == null)
            return;
        RelativeLayout relativeLayout = itemView.findViewById(R.id.souye_page_video_relativeLayout);
        ViewParent parent = mVideoView.getParent();
//        Log.e("---------max-", "" + "  " + mVideoView.getDuration());
        if (parent instanceof RelativeLayout) {
            ((RelativeLayout) parent).removeView(mVideoView);
        }
        relativeLayout.addView(mVideoView, 2);
        //如果滑动到了最后一页 就要加载新的数据了
        if (position == lists.size() - 1) {
            page.getAndIncrement();
            //加载数据
//            mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));


            if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                    dialog = new Dialog(getActivity(), R.style.progress_dialog);
                    dialog.setContentView(R.layout.dialog);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                    flag.set(false);
                    mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
                } else {
                    mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()), SharePreferenceUtil.getToken(AppUtils.getContext()));
                }
            } else {
//                Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
                wangluoyichang.setVisibility(View.VISIBLE);
                nodingdan.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
            }
        }

        //全屏
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 0);
        mVideoView.setLayoutParams(params);
        View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置
        if (view != null)
            view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.VISIBLE);


        //开始播放视频
        mVideoView.start();
        //确定高度
/**

 mVideoView.setOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
@Override public void onPlayerStateChanged(int playerState) {
//                Log.e("---Player",""+playerState);
//                int[] size= mVideoView.getVideoSize();
//                String value="位置"+position+",width:"+size[0]+" "+",height:"+size[1];
//                Log.e("Android短视频5",value);


}

@Override public void onPlayStateChanged(int playState) {

//                isplay=mVideoView.isPlaying();
//                if (mVideoView.isPlaying()){
//                    tikTokAdapter.setIsshow(false);
////                    tikTokAdapter.notifyItemChanged(mCurrentPosition);
//                }else {
//                    tikTokAdapter.setIsshow(true);
////                    tikTokAdapter.notifyItemChanged(mCurrentPosition);
//                }


//                Log.e("---Play",""+playState);
//                int[] size = mVideoView.getVideoSize();
//                String value="位置"+position+",width:"+size[0]+" "+",height:"+size[1];
//                Log.e("Android短视频6",value);
//
//                Log.e("-------------s------",""+size[1]);
//高度OK
//        if (size[1] < 640) {
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//        RelativeLayout.LayoutParams.MATCH_PARENT, 700);//RelativeLayout.LayoutParams.MATCH_PARENT 500
//        params.addRule(RelativeLayout.CENTER_VERTICAL);
//        params.setMargins(0, 0, 0, 0);//top 400
//        mVideoView.setLayoutParams(params);
//        View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置          删除了有没有问题
//        if (view != null)
//        view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.INVISIBLE);
//        } else {
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//        RelativeLayout.LayoutParams.MATCH_PARENT,
//        RelativeLayout.LayoutParams.MATCH_PARENT);
//        params.setMargins(0, 0, 0, 0);
//        mVideoView.setLayoutParams(params);
//        View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置
//        if (view != null)
//        view.findViewById(R.id.souye_page_video_thumb).setVisibility(View.VISIBLE);
//        }


//进度//ok

int duration = (int) mVideoView.getDuration();
//                Log.e("---------max-", "" + duration + "  " + mVideoView.getDuration());
//                if (proPercent != null)
//                    proPercent.setMax(duration);
if (duration != 0) {
new Thread() {
@Override public void run() {
super.run();
if (mVideoView == null)
return;
while (mVideoView.isPlaying()) {
int currentPosition = (int) mVideoView.getCurrentPosition();
//                                    Log.e("-----pro",""+currentPosition+"  "+mVideoView.getCurrentPosition());


//                                if (proPercent != null)
//                                    proPercent.setProgress(currentPosition);

//                                Log.e("-------------走完了=",currentPosition+"     "+duration);
//                                if (currentPosition==duration){
//                                    Log.e("----------------","走完了");
//                                }
if (currentPosition >= (duration - 200)) {
//                                    Log.e("----------------","走完了2");
handler.sendEmptyMessage(22);
}

try {
Thread.sleep(100);
} catch (InterruptedException e) {
e.printStackTrace();
}
}
}
}.start();
}


}
});


 */
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
//                    int curr = (int) msg.obj;
//                    Log.e("-----pro11", "" + curr + "  " + mVideoView.getCurrentPosition());
//                    proPercent.setProgress(curr);
                    break;
                case 22:

                    isnext = true;

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.loading_dialog);
                    View layout = View.inflate(getActivity(), R.layout.item_hong_one, null);
                    ImageView iv_close = layout.findViewById(R.id.iv_close);
                    CircleImageView civ = layout.findViewById(R.id.civ);
                    TextView tv = layout.findViewById(R.id.tv_name);
                    ImageView iv_ka = layout.findViewById(R.id.iv_ka);

                    builder.setView(layout);
                    Dialog dialog = builder.create();
                    iv_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    iv_ka.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            kaimoney();
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                    break;
            }

        }
    };

    private void kaimoney() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.loading_dialog);
        View layout = View.inflate(getActivity(), R.layout.item_hong_two, null);
        ImageView iv_close = layout.findViewById(R.id.iv_close);
        TextView tv_name = layout.findViewById(R.id.tv_text2);
        TextView tv_money = layout.findViewById(R.id.tv_money);


        builder.setView(layout);
        Dialog dialog = builder.create();
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    public boolean isLook() {
        return isLook;
    }

    public void setLook(boolean look) {
        isLook = look;
    }

    public boolean isIszanle() {
        return iszanle;
    }

    public void setIszanle(boolean iszanle) {
        this.iszanle = iszanle;
    }


    private void attentions(Button button) {

        RetrofitManager.getInstance().getDataServer().attentionUserTwo(lists.get(mCurrentPosition).getUid() + "", SharePreferenceUtil.getToken(AppUtils.getContext())).enqueue(new Callback<ResponseBody>() {
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


    //    public interface PinL{
//        void Clicker(String str);
//    }
//    private PinL pinL;
//
//    public void setPinL(PinL pinL) {
//        this.pinL = pinL;
//    }


    private void initFragment() {
        fragmentList = new ArrayList<>();
        listFragment = new ListFragment();
        tagFragment = new TagFragment();

        fragmentList.add(listFragment);
        fragmentList.add(tagFragment);

        switchFragment(tempFragment, fragmentList.get(0));
    }

    public void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {

        if (tempFragment != nextFragment) {

            tempFragment = nextFragment;

            if (nextFragment != null) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }

                    transaction.add(R.id.fl_type, nextFragment, "tagFragment").commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }

                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    @Override
    public void onInitComplete() {

    }

    @Override
    public void onPageRelease(boolean isNext, int position) {

    }

    @Override
    public void onPageSelected(int position, boolean isBottom) {

    }
}
