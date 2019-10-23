package com.jarhero790.eub.ui.souye.child;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.R;
import com.jarhero790.eub.base.BaseMVPCompatFragment;
import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.bean.ShipinDianZan;
import com.jarhero790.eub.bean.Video;
import com.jarhero790.eub.contract.home.SouyeContract;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.bean.HiddBean;
import com.jarhero790.eub.message.bean.attentionchange;
import com.jarhero790.eub.message.bean.souyelookone;
import com.jarhero790.eub.message.bean.souyelookthree;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.message.souye.TongKuanActivity;
import com.jarhero790.eub.presenter.home.SouyePresenter;
import com.jarhero790.eub.ui.souye.BottomGiftDialog;
import com.jarhero790.eub.ui.souye.BottomPingLunDialog;
import com.jarhero790.eub.ui.souye.BottomShareDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.NetworkConnectionUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXVideoInfoReader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SouyeFive_One extends BaseMVPCompatFragment<SouyeContract.SouyePresenter> implements SouyeContract.ISouyeView {

    private static final String TAG = "--------";
    @BindView(R.id.vertical_view_pager)
    VerticalViewPager mVerticalViewPager;
    Unbinder unbinder;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
    @BindView(R.id.wangluoyichang)
    RelativeLayout wangluoyichang;
    //    private VerticalViewPager mVerticalViewPager;
    private MyPagerAdapter mPagerAdapter;
    private TXCloudVideoView mTXCloudVideoView;
    private TextView mTvBack;
    private ImageView mIvCover;
    private ImageView mIvCover_heng;
    // 合拍相关
    private ImageButton mImgBtnFollowShot;
    private ProgressDialog mDownloadProgressDialog;
    private TXVideoInfoReader mVideoInfoReader;
    // 发布者id 、视频地址、 发布者名称、 头像URL、 封面URL
    private List<Video> lists = new ArrayList<>();
    //    private int mInitTCLiveInfoPosition;
    private static int mCurrentPosition;
    /**
     * SDK播放器以及配置
     */
    private TXVodPlayer mTXVodPlayer;
    GlobalApplication app;

    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;
    private IWXAPI api;
    //微信
    private static final int THUMB_SIZE = 150;

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        app = (GlobalApplication) getActivity().getApplication();
        return rootView;
    }

    private static SouyeFive_One instance = null;

    private static Object lock = "lock";

    public static SouyeFive_One newInstance() {

        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new SouyeFive_One();
                }
            }
        }
        cate.set(0);
        page.set(1);
        flag.set(true);
        mCurrentPosition = 0;
        return instance;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.wangluoyichang)
    public void onViewClicked() {
        if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
            if (lists.size() > 0) {
                lists.clear();
            }
            wangluoyichang.setVisibility(View.GONE);
            nodingdan.setVisibility(View.GONE);
            mVerticalViewPager.setVisibility(View.VISIBLE);
            if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
            } else {
                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()), SharePreferenceUtil.getToken(AppUtils.getContext()));
            }
        } else {
            wangluoyichang.setVisibility(View.VISIBLE);
            nodingdan.setVisibility(View.GONE);
            mVerticalViewPager.setVisibility(View.GONE);
        }
    }


    class PlayerInfo {
        public TXVodPlayer txVodPlayer;
        public String playURL;
        public boolean isBegin;
        public View playerView;
        public int pos;
        public int reviewstatus;
    }

    private static AtomicInteger cate = new AtomicInteger(0);
    private static AtomicInteger page = new AtomicInteger(1);
    private static AtomicBoolean flag = new AtomicBoolean(true);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        super.initData();
        //加载数据
        if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
            wangluoyichang.setVisibility(View.GONE);
            nodingdan.setVisibility(View.GONE);
            mVerticalViewPager.setVisibility(View.VISIBLE);
            if (lists.size() > 0) {
                lists.clear();
            }
            if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
            } else {
                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()), SharePreferenceUtil.getToken(AppUtils.getContext()));
            }
        } else {
            wangluoyichang.setVisibility(View.VISIBLE);
            nodingdan.setVisibility(View.GONE);
            mVerticalViewPager.setVisibility(View.GONE);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.playvideotwo_two;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return SouyePresenter.newInstance();
    }

    int sizelent=0;
    @Override
    public void updateVideos(ArrayList<Video> videos) {

        sizelent=lists.size()-1;

        if (dialog!=null){
            dialog.dismiss();
        }

        //设置视频
        Log.e("---------", "有没有数据了1");
        if (videos == null || videos.size() == 0) {
            mVerticalViewPager.setVisibility(View.GONE);
            wangluoyichang.setVisibility(View.GONE);
            nodingdan.setVisibility(View.VISIBLE);
            return;
        }
        nodingdan.setVisibility(View.GONE);
        wangluoyichang.setVisibility(View.GONE);
        mVerticalViewPager.setVisibility(View.VISIBLE);




        if (flag.get()) {
            lists.addAll(videos);
            initViews();
            initPlayerSDK();
            Log.e("----------souyefive","flag=true");

        } else {


            lists.addAll(sizelent,videos);
            Log.e("----------souyefive","lists.size()="+lists.size());
            mCurrentPosition=sizelent;
            Log.e("----------souyefive","mCurrentPosition="+mCurrentPosition);
            mPagerAdapter.setlist(lists);
//            initViews();
//            mTXVodPlayer.startPlay()
//            mPagerAdapter.notifyDataSetChanged();

            Log.e("----------souyefive","flag=flase");
        }

    }

    @Override
    public void updateLikeVideo(ShipinDianZan shipinDianZan) {

    }

    @Override
    public void updateAttentionUser() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    Dialog dialog;
    private void initViews() {
        mVerticalViewPager.setOffscreenPageLimit(2);
        mVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                TXLog.e(TAG, "mVerticalViewPager, onPageScrolled position = " + position);

//                Glide.get(getActivity()).clearDiskCache();
                Glide.get(getActivity()).clearMemory();

//                mCurrentPosition = position;

//                ---: thread ID:1|line:-1|mVerticalViewPager, onPageScrolled position = 49
                //如果滑动到了最后一页 就要加载新的数据了
                if (position == lists.size() - 1) {
                    page.getAndIncrement();
                    if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
                        dialog = new Dialog(getActivity(), R.style.progress_dialog);
                        dialog.setCancelable(false);
                        if (dialog.getWindow()!=null)
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.setContentView(R.layout.dialog);
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
                        mVerticalViewPager.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                TXLog.i(TAG, "mVerticalViewPager, onPageSelected position = " + position);
                mCurrentPosition = position;
                // 滑动界面，首先让之前的播放器暂停，并seek到0
                TXLog.e(TAG, "滑动后，让之前的播放器暂停，mTXVodPlayer = " + mTXVodPlayer);
                if (mTXVodPlayer != null) {
                    mTXVodPlayer.seek(0);
                    mTXVodPlayer.pause();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Log.e(TAG, "这个页面来了没有3");
        mVerticalViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                TXLog.e(TAG, "mVerticalViewPager, transformPage pisition = " + position + " mCurrentPosition" + mCurrentPosition);
                if (position != 0) {
                    return;
                }

                ViewGroup viewGroup = (ViewGroup) page;
                mIvCover = (ImageView) viewGroup.findViewById(R.id.player_iv_cover);
                mIvCover_heng = (ImageView) viewGroup.findViewById(R.id.player_iv_cover_heng);
                mTXCloudVideoView = (TXCloudVideoView) viewGroup.findViewById(R.id.player_cloud_view);
                ImageView backtu=viewGroup.findViewById(R.id.back);
                backtu.setVisibility(View.GONE);


                PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(mCurrentPosition);
                if (playerInfo != null) {
                    playerInfo.txVodPlayer.resume();
                    mTXVodPlayer = playerInfo.txVodPlayer;
                }
            }
        });

        Log.e(TAG, "这个页面来了没有4");
        mPagerAdapter = new MyPagerAdapter();
        mVerticalViewPager.setAdapter(mPagerAdapter);
        Log.e(TAG, "这个页面来了没有5");
    }

    private void initPlayerSDK() {
        mVerticalViewPager.setCurrentItem(mCurrentPosition);
    }

    class MyPagerAdapter extends PagerAdapter implements ITXVodPlayListener {

        ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();
        public void setlist(List<Video> v){
            lists=v;
            notifyDataSetChanged();
        }


        protected PlayerInfo instantiatePlayerInfo(int position) {
            TXCLog.d(TAG, "instantiatePlayerInfo " + position);

            PlayerInfo playerInfo = new PlayerInfo();
            TXVodPlayer vodPlayer = new TXVodPlayer(getActivity());
            vodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
            vodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
            vodPlayer.setVodListener(this);
            TXVodPlayConfig config = new TXVodPlayConfig();
            config.setCacheFolderPath(Environment.getExternalStorageDirectory().getPath() + "/txcache");
            config.setMaxCacheItems(5);
            vodPlayer.setConfig(config);
            vodPlayer.setAutoPlay(false);

            Video tcLiveInfo = lists.get(position);
            playerInfo.playURL = TextUtils.isEmpty(tcLiveInfo.getUrl()) ? tcLiveInfo.getUrl() : tcLiveInfo.getUrl();
            playerInfo.txVodPlayer = vodPlayer;
//            playerInfo.reviewstatus = tcLiveInfo.getState();
            playerInfo.pos = position;
            playerInfoList.add(playerInfo);

            return playerInfo;
        }

        protected void destroyPlayerInfo(int position) {
            while (true) {
                PlayerInfo playerInfo = findPlayerInfo(position);
                if (playerInfo == null)
                    break;
                playerInfo.txVodPlayer.stopPlay(true);
                playerInfoList.remove(playerInfo);

                TXCLog.d(TAG, "destroyPlayerInfo " + position);
            }
        }

        public PlayerInfo findPlayerInfo(int position) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.pos == position) {
                    return playerInfo;
                }
            }
            return null;
        }

        public PlayerInfo findPlayerInfo(TXVodPlayer player) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.txVodPlayer == player) {
                    return playerInfo;
                }
            }
            return null;
        }

        public void onDestroy() {
            for (PlayerInfo playerInfo : playerInfoList) {
                playerInfo.txVodPlayer.stopPlay(true);
            }
            playerInfoList.clear();
        }

        @Override
        public int getCount() {
            return lists == null ? 0 : lists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TXCLog.i(TAG, "MyPagerAdapter instantiateItem, position = " + position);
            Video tcLiveInfo = lists.get(position);

            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_player_content_two, null);
            view.setId(position);
            // 封面
            ImageView coverImageView = (ImageView) view.findViewById(R.id.player_iv_cover);
            ImageView coverImageView_heng = (ImageView) view.findViewById(R.id.player_iv_cover_heng);



            //tou
            CircleImageView userimage = (CircleImageView) view.findViewById(R.id.souye_logo);
            Glide.with(getActivity()).load(tcLiveInfo.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo)
                    .error(R.mipmap.zuanshi_logo)).into(userimage);
            //旋转图
            CircleImageView guanpan = (CircleImageView) view.findViewById(R.id.circleImageView);
            Glide.with(getActivity()).load(tcLiveInfo.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo)
                    .error(R.mipmap.zuanshi_logo)).into(guanpan);
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setDuration(4000);
            rotateAnimation.setRepeatCount(Animation.INFINITE);
            guanpan.setAnimation(rotateAnimation);

            //点赞
            ImageView iv_like = view.findViewById(R.id.iv_like);
            TextView tv_like=view.findViewById(R.id.tv_like);
            tv_like.setText(tcLiveInfo.getZan());
            if (tcLiveInfo.getIs_zan().equals("1")) {
                iv_like.setSelected(true);
            } else {
                iv_like.setSelected(false);
            }
            iv_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    } else {

                        if (iv_like.isSelected()) {
                            iv_like.setSelected(false);
                            likeVideo();
                            String string = tv_like.getText().toString();
                            int text = (Integer.parseInt(string) - 1);
                            tv_like.setText("" + text);
                        } else {
                            iv_like.setSelected(true);
                            likeVideo();
                            String string = tv_like.getText().toString();
                            int text = (Integer.parseInt(string) + 1);
                            tv_like.setText("" + text);
                        }


                    }
                }
            });


            //评论
            ImageView iv_commit=view.findViewById(R.id.iv_commit);
           TextView tv_pinglun=view.findViewById(R.id.tv_pinglun);
            tv_pinglun.setText(tcLiveInfo.getCommentNum()+"");
            iv_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
//                                View view = layoutManager.findViewByPosition(mCurrentPosition);
//                                if (view != null) {
//                                    TextView tvLike = view.findViewById(R.id.tv_pinglun);
//                                    tvLike.setText("" + num);
//                                }
                               tv_pinglun.setText("" + num);
                            }
                        });
                    }
                }
            });

            //财富
            TextView caifu=view.findViewById(R.id.tv_gold_coin);
            caifu.setText(tcLiveInfo.getCaifu());


            //礼物
            ImageView iv_gift=view.findViewById(R.id.iv_gift);
            iv_gift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
            });


            //分享
            ImageView iv_share=view.findViewById(R.id.iv_share);
            iv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    } else {
                        BottomShareDialog bottomShareDialog = BottomShareDialog.newInstance();
                        Bundle args = new Bundle();
                        args.putString("url", lists.get(mCurrentPosition).getUrl());
                        args.putString("videoid", lists.get(mCurrentPosition).getVideo_id());
                        args.putString("userid", lists.get(mCurrentPosition).getUid());
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
                                    msg.thumbData = CommonUtil.bmpToByteArray(thumbBmp, true);

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
                }
            });



            //            关注
            Button btn_attention = view.findViewById(R.id.btn_attention);
            if ((tcLiveInfo.getUid() + "").equals(SharePreferenceUtil.getUserid(AppUtils.getContext()))) {
                btn_attention.setVisibility(View.INVISIBLE);
            } else {
                btn_attention.setVisibility(View.VISIBLE);
                if (tcLiveInfo.getIs_like().equals("1")) {
                    btn_attention.setText("已关注");
                } else {
                    btn_attention.setText("+关注");
                }
            }
            btn_attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    } else {
//                        attention(lists.get(position).getUid(), SharePreferenceUtil.getToken(AppUtils.getContext()));

//                        Button button = (Button) view;
                        attentions(btn_attention);

                    }
                }
            });






            //商城
            RelativeLayout bussiness = view.findViewById(R.id.bussiness);
            bussiness.setVisibility(View.INVISIBLE); // 暂时隐藏
//            if (tcLiveInfo.getGood_id().equals("0")) {
//                bussiness.setVisibility(View.INVISIBLE);
//            } else {
//                bussiness.setVisibility(View.VISIBLE);
//            }

            //同款
            RelativeLayout tongkuang=view.findViewById(R.id.tongkuang);
            tongkuang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    } else {
                        Intent intentt = new Intent(getActivity(), TongKuanActivity.class);
                        intentt.putExtra("video_id", lists.get(mCurrentPosition).getVideo_id());
                        startActivity(intentt);
                    }
                }
            });

            ImageView play_pause=view.findViewById(R.id.iv_play_pause);
            RelativeLayout rlhead=view.findViewById(R.id.rlhead);
            rlhead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTXVodPlayer!=null){
                        if (mTXVodPlayer.isPlaying()){
                            mTXVodPlayer.pause();
                            play_pause.setVisibility(View.VISIBLE);
                        }else {
//                        mTXVodPlayer.startPlay(lists.get(mCurrentPosition).getUrl());
                            mTXVodPlayer.resume();
                            play_pause.setVisibility(View.GONE);
                        }
                    }
                }
            });


            // 获取此player
            TXCloudVideoView playView = (TXCloudVideoView) view.findViewById(R.id.player_cloud_view);
            PlayerInfo playerInfo = instantiatePlayerInfo(position);
            playerInfo.playerView = playView;
            playerInfo.txVodPlayer.setPlayerView(playView);
            if (lists.get(position).getAnyhow().equals("1")) {

                coverImageView.setVisibility(View.VISIBLE);
                coverImageView_heng.setVisibility(View.GONE);
                Glide.with(getActivity()).load(tcLiveInfo.getUrl())
                        .apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor))
                        .into(coverImageView);  //不支持 glide 4.9版本

            }else {
                coverImageView.setVisibility(View.GONE);
                coverImageView_heng.setVisibility(View.VISIBLE);
                playView.setRotation(270);
                Glide.with(getActivity()).load(tcLiveInfo.getUrl())
                        .apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor))
                        .into(coverImageView_heng);  //不支持 glide 4.9版本

            }
            playerInfo.txVodPlayer.startPlay(playerInfo.playURL);


            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            TXCLog.i(TAG, "MyPagerAdapter destroyItem, position = " + position);

            destroyPlayerInfo(position);

            container.removeView((View) object);


        }

        @Override
        public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
            if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
                int width = param.getInt(TXLiveConstants.EVT_PARAM1);
                int height = param.getInt(TXLiveConstants.EVT_PARAM2);
                if (width > height) {
                    player.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
                } else {
                    player.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
                }
            } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
                restartPlay();
            } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {// 视频I帧到达，开始播放

                PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
                if (playerInfo != null) {
                    playerInfo.isBegin = true;
                }
                if (mTXVodPlayer == player) {
                    TXLog.i(TAG, "onPlayEvent, event I FRAME, player = " + player);
                    mIvCover.setVisibility(View.GONE);
                    mIvCover_heng.setVisibility(View.GONE);
//                TCUserMgr.getInstance().uploadLogs(TCConstants.ELK_ACTION_VOD_PLAY, TCUserMgr.getInstance().getUserId(), event, "点播播放成功", new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                    }
//                });
                }
            } else if (event == TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED) {
                if (mTXVodPlayer == player) {
                    TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);
                    mTXVodPlayer.resume();
                }
            } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
                PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
                if (playerInfo != null && playerInfo.isBegin) {
                    mIvCover.setVisibility(View.GONE);
                    mIvCover_heng.setVisibility(View.GONE);
//                    TXCLog.i(TAG, "onPlayEvent, event begin, cover remove");
                }
            } else if (event < 0) {
                if (mTXVodPlayer == player) {
                    TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);

                    String desc = null;
                    switch (event) {
                        case TXLiveConstants.PLAY_ERR_GET_RTMP_ACC_URL_FAIL:
                            desc = "获取加速拉流地址失败";
                            break;
                        case TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND:
                            desc = "文件不存在";
                            break;
                        case TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL:
                            desc = "h265解码失败";
                            break;
                        case TXLiveConstants.PLAY_ERR_HLS_KEY:
                            desc = "HLS解密key获取失败";
                            break;
                        case TXLiveConstants.PLAY_ERR_GET_PLAYINFO_FAIL:
                            desc = "获取点播文件信息失败";
                            break;
                    }
//                TCUserMgr.getInstance().uploadLogs(TCConstants.ELK_ACTION_VOD_PLAY, TCUserMgr.getInstance().getUserId(), event, desc, new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.i(TAG, "onFailure");
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Log.i(TAG, "onResponse");
//                    }
//                });
                }
                Toast.makeText(getActivity(), "event:" + event, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

        }
    }



    private void restartPlay() {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.resume();
        }
    }


    @Override
    public void onPause()  {
        super.onPause();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onPause();
        }
        if (mTXVodPlayer != null) {
            mTXVodPlayer.pause();
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onDestroy();
            mTXCloudVideoView = null;
        }
        stopPlay(true);
        mTXVodPlayer = null;

        EventBus.getDefault().unregister(this);

    }

    protected void stopPlay(boolean clearLastFrame) {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.stopPlay(clearLastFrame);
        }
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ee(HiddBean bean) {
        if (bean != null) {
            if (bean.getName().equals("true")) {
                Log.e("----------", "souyefive-onHidden" + "隐藏3");
                setLook(false);
                if (mTXVodPlayer != null) {
                    mTXVodPlayer.pause();
                }
                if (!EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().unregister(this);
                }
            } else {
                if (app.isIslookthree()) {
                    Log.e("----------", "souyefive-onHidden可见3");
                    setLook(true);
//                    viewplaypause = layoutManager.findViewByPosition(mCurrentPosition);
//                    if (viewplaypause != null) {
//                        viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
//                        viewplaypause.findViewById(R.id.circleImageView).startAnimation(rotateAnimation);
//                    }

                    if (mTXVodPlayer != null) {
                        mTXVodPlayer.resume();
//                Log.e("----------1", "是不是这里" + mCurrentPosition);
//                        startPlay(mCurrentPosition);
                        mTXVodPlayer.startPlay(lists.get(mCurrentPosition).getUrl());

                    } else {
                        mTXVodPlayer = new TXVodPlayer(AppUtils.getContext());
                        mTXVodPlayer.resume();
//                        startPlay(mCurrentPosition);
                        mTXVodPlayer.startPlay(lists.get(mCurrentPosition).getUrl());
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
        if (firstv) {
            if (hidden) {
                Log.e("----------", "souyefive-onHidden" + hidden + "隐藏");
                setLook(false);
                if (mTXVodPlayer != null) {
                    mTXVodPlayer.pause();
                }
                if (!EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().unregister(this);
                }
            } else {//可见
                Log.e("----------", "souyefive-onHidden" + hidden + "可见");
                setLook(true);
//                viewplaypause = layoutManager.findViewByPosition(mCurrentPosition);
//                if (viewplaypause != null) {
//                    viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
//                    viewplaypause.findViewById(R.id.circleImageView).startAnimation(rotateAnimation);
//                }

                if (mTXVodPlayer != null) {
                    mTXVodPlayer.resume();
                Log.e("----------1", "souyefive-onHidden是不是这里" + mCurrentPosition);
//                    startPlay(mCurrentPosition);
                    mTXVodPlayer.startPlay(lists.get(mCurrentPosition).getUrl());

                } else {
                    mTXVodPlayer = new TXVodPlayer(AppUtils.getContext());
                    mTXVodPlayer.resume();
//                    startPlay(mCurrentPosition);
                    mTXVodPlayer.startPlay(lists.get(mCurrentPosition).getUrl());
                Log.e("----------2", "souyefive-onHidden是不是里");
                }
            }

            firstv = false;
        }
    }
    private boolean isLook = true;//可见
    public boolean isLook() {
        return isLook;
    }

    public void setLook(boolean look) {
        isLook = look;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ee1(souyelookone bean) {
        if (bean.getName().equals("ok")) {
            Log.e("-------souyefive", "ee3_ok");
            if (mTXVodPlayer != null) {
                if (!mTXVodPlayer.isPlaying()) {
                    mTXVodPlayer.startPlay(lists.get(mCurrentPosition).getUrl());
                }
            }
        } else {
            if (mTXVodPlayer != null) {
                mTXVodPlayer.pause();
            }
//
//            onHiddenChanged(true);
            app.setIslookthree(false);
            Log.e("-------souyefive", "ee3");
        }
    }
    RotateAnimation rotateAnimation;//旋转动画

    @Override
    public void onResume() {
        super.onResume();

        if (mTXVodPlayer != null) {
//            Log.e("---------islokk", "" + isLook()+" app="+app.isIslookthree());

            if (app.isIslookthree()) {
                mTXVodPlayer.resume();
                flag.set(true);
//                Log.e("----------4", "souyefive是不是这里" + mCurrentPosition);
//                startPlay(mCurrentPosition);
                mTXVodPlayer.startPlay(lists.get(mCurrentPosition).getUrl());
            }
//            if (mPresenter!=null)
//            mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
            Log.e("-------", "souyefive-onResume");
        } else {
            Log.e("-------", "souyefive-onResumeN");
        }
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
            mVerticalViewPager.setVisibility(View.GONE);
        }
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
}
