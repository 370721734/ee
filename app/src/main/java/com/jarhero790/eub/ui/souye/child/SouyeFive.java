package com.jarhero790.eub.ui.souye.child;

import android.app.ProgressDialog;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.base.BaseMVPCompatFragment;
import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.bean.ShipinDianZan;
import com.jarhero790.eub.bean.Video;
import com.jarhero790.eub.contract.home.SouyeContract;
import com.jarhero790.eub.message.souye.PlayVideoTwo_TwoActivity;
import com.jarhero790.eub.presenter.home.SouyePresenter;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.NetworkConnectionUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXVideoInfoReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class SouyeFive extends BaseMVPCompatFragment<SouyeContract.SouyePresenter> implements SouyeContract.ISouyeView {

    private static final String TAG = "--------";
    @BindView(R.id.vertical_view_pager)
    VerticalViewPager mVerticalViewPager;
    Unbinder unbinder;
    //    private VerticalViewPager mVerticalViewPager;
    private MyPagerAdapter mPagerAdapter;
    private TXCloudVideoView mTXCloudVideoView;
    private TextView mTvBack;
    private ImageView mIvCover;
    // 合拍相关
    private ImageButton mImgBtnFollowShot;
    private ProgressDialog mDownloadProgressDialog;
    private TXVideoInfoReader mVideoInfoReader;
    // 发布者id 、视频地址、 发布者名称、 头像URL、 封面URL
    private List<Video> lists=new ArrayList<>();
//    private int mInitTCLiveInfoPosition;
    private static int mCurrentPosition;
    /**
     * SDK播放器以及配置
     */
    private TXVodPlayer mTXVodPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private static SouyeFive instance = null;

    private static Object lock="lock";
    public static SouyeFive newInstance() {

        if (instance == null) {
            synchronized (lock){
                if (instance==null){
                    instance = new SouyeFive();
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
    public void initData() {
        super.initData();
        //加载数据
        if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
            if (lists.size() > 0) {
                lists.clear();
            }

            if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()));
//                Log.e("-----------", "无token————initdata");
            } else {
//                Log.e("-----------", "有token----initdata");
                mPresenter.getVideos(String.valueOf(cate.get()), String.valueOf(page.get()), SharePreferenceUtil.getToken(AppUtils.getContext()));
            }
        } else {
            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
//            wangluoyichang.setVisibility(View.VISIBLE);
//            nodingdan.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.GONE);
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

    @Override
    public void updateVideos(ArrayList<Video> videos) {
        lists.addAll(videos);
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
        initViews();
        initPlayerSDK();
    }

    private void initViews() {
        mVerticalViewPager.setOffscreenPageLimit(2);
        mVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                TXLog.e(TAG, "mVerticalViewPager, onPageScrolled position = " + position);
//                mCurrentPosition = position;
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
                mTXCloudVideoView = (TXCloudVideoView) viewGroup.findViewById(R.id.player_cloud_view);


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

            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_player_content, null);
            view.setId(position);
            // 封面
            ImageView coverImageView = (ImageView) view.findViewById(R.id.player_iv_cover);

            Glide.with(getActivity()).load(tcLiveInfo.getUrl())
                    .apply(new RequestOptions().placeholder(R.color.backgroudcolor).error(R.color.backgroudcolor))
                   .into(coverImageView);  //不支持 glide 4.9版本



            // 获取此player
            TXCloudVideoView playView = (TXCloudVideoView) view.findViewById(R.id.player_cloud_view);
            PlayerInfo playerInfo = instantiatePlayerInfo(position);
            playerInfo.playerView = playView;
            playerInfo.txVodPlayer.setPlayerView(playView);
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
                    TXCLog.i(TAG, "onPlayEvent, event begin, cover remove");
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
}
