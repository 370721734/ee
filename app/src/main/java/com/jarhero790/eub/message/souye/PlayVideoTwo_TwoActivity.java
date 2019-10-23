package com.jarhero790.eub.message.souye;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.bean.ShipinDianZanBean;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.bean.SearchBean;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.ui.souye.BottomGiftDialog;
import com.jarhero790.eub.ui.souye.BottomPingLunDialog;
import com.jarhero790.eub.ui.souye.BottomShareDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
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

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayVideoTwo_TwoActivity extends AppCompatActivity implements ITXVodPlayListener {
    @BindView(R.id.vertical_view_pager)
    VerticalViewPager mVerticalViewPager;

    private static final String TAG = "PlayVideoTwo_TwoActivit";//logt+enter
    private int mInitTCLiveInfoPosition;
    private int mCurrentPosition;
    ArrayList<SearchBean.DataBean.VisitBean> list = new ArrayList<>();
    ArrayList<SearchBean.DataBean.LikeBean> likeBeans = new ArrayList<>();
    private String type = "";

    private TXCloudVideoView mTXCloudVideoView;

    /**
     * SDK播放器以及配置
     */
    private TXVodPlayer mTXVodPlayer;
    private MyPagerAdapter mPagerAdapter;
    private ImageView mIvCover;


    private boolean isshow = false;

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
            Toast.makeText(this, "event:" + event, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

    }


    class PlayerInfo {
        public TXVodPlayer txVodPlayer;
        public String playURL;
        public boolean isBegin;
        public View playerView;
        public int pos;
        public int reviewstatus;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onResume();
        }
        if (mTXVodPlayer != null) {
            mTXVodPlayer.resume();
        }
    }

    private void restartPlay() {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.resume();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playvideotwo_two);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        initDatas();
        initViews();
        initPlayerSDK();
    }

    private void initDatas() {
        Intent intent = getIntent();

        mInitTCLiveInfoPosition = intent.getIntExtra("position", 0);
        type = intent.getStringExtra("type");
        if (type != null && type.equals("like")) {
            likeBeans = (ArrayList<SearchBean.DataBean.LikeBean>) intent.getSerializableExtra("vidlist");
//            tikTokAdapter = new TikTokTwoAdapter(likeBeans, this, "like");

        } else {
            list = (ArrayList<SearchBean.DataBean.VisitBean>) intent.getSerializableExtra("vidlist");
//            tikTokAdapter = new TikTokTwoAdapter(list, this);

        }
    }

    private void initViews() {
        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.player_cloud_view);
        mIvCover = (ImageView) findViewById(R.id.player_iv_cover);
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


    RotateAnimation rotateAnimation;//旋转动画

    class MyPagerAdapter extends PagerAdapter {
//         private OnItemClickListener mOnItemClickListerer;
//
//         //为RecyclerView的Item添加监听
//         public interface OnItemClickListener {
//             void onItemClick(int position, String type, View view, View view1, View view2, String listtype);
//         }
//
//         public void setOnItemClickListerer(OnItemClickListener listerer) {
//             this.mOnItemClickListerer = listerer;
//         }


        public MyPagerAdapter() {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setDuration(4000);
            rotateAnimation.setRepeatCount(Animation.INFINITE);
        }

        ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();

        protected PlayerInfo instantiatePlayerInfo(int position) {
            TXCLog.d(TAG, "instantiatePlayerInfo " + position);
            PlayerInfo playerInfo = new PlayerInfo();
            TXVodPlayer vodPlayer = new TXVodPlayer(PlayVideoTwo_TwoActivity.this);
            vodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
            vodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
            vodPlayer.setVodListener(PlayVideoTwo_TwoActivity.this);
            TXVodPlayConfig config = new TXVodPlayConfig();
            config.setCacheFolderPath(Environment.getExternalStorageDirectory().getPath() + "/txcache");
            config.setMaxCacheItems(5);
            vodPlayer.setConfig(config);
            vodPlayer.setAutoPlay(false);

            if (type.equals("like")) {
                SearchBean.DataBean.LikeBean tcLiveInfo = likeBeans.get(position);
                playerInfo.playURL = TextUtils.isEmpty(tcLiveInfo.getUrl()) ? tcLiveInfo.getUrl() : tcLiveInfo.getUrl();
                playerInfo.reviewstatus = tcLiveInfo.getState();
            } else {
                SearchBean.DataBean.VisitBean tcLiveInfo = list.get(position);
                playerInfo.playURL = TextUtils.isEmpty(tcLiveInfo.getUrl()) ? tcLiveInfo.getUrl() : tcLiveInfo.getUrl();
                playerInfo.reviewstatus = tcLiveInfo.getState();
            }

            playerInfo.txVodPlayer = vodPlayer;

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
            if (type.equals("like")) {
                return likeBeans == null ? 0 : likeBeans.size();
            } else {
                return list == null ? 0 : list.size();
            }

        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (type.equals("like")){
                SearchBean.DataBean.LikeBean videoinfo = likeBeans.get(position);
                View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_player_content_two, null);
                view.setId(position);
                // 封面
                ImageView coverImageView = (ImageView) view.findViewById(R.id.player_iv_cover);
                Glide.with(PlayVideoTwo_TwoActivity.this).load(videoinfo.getVideo_img()).apply(new RequestOptions()
                        .placeholder(R.mipmap.mine_bg).error(R.mipmap.mine_bg)).into(coverImageView);

                //tou
                CircleImageView userimage = (CircleImageView) view.findViewById(R.id.souye_logo);
                Glide.with(PlayVideoTwo_TwoActivity.this).load(videoinfo.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo)
                        .error(R.mipmap.zuanshi_logo)).into(userimage);
                //旋转图
                CircleImageView guanpan = (CircleImageView) view.findViewById(R.id.circleImageView);
                Glide.with(PlayVideoTwo_TwoActivity.this).load(videoinfo.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo)
                        .error(R.mipmap.zuanshi_logo)).into(guanpan);
                guanpan.setAnimation(rotateAnimation);

                //zan
                ImageView iv_like = view.findViewById(R.id.iv_like);
                if (videoinfo.getIs_zan() == 1) {
                    iv_like.setSelected(true);
                } else {
                    iv_like.setSelected(false);
                }

                //            关注
                Button btn_attention = view.findViewById(R.id.btn_attention);
                if ((videoinfo.getUid() + "").equals(SharePreferenceUtil.getUserid(AppUtils.getContext()))) {
                    btn_attention.setVisibility(View.INVISIBLE);
                } else {
                    btn_attention.setVisibility(View.VISIBLE);
                    if (videoinfo.getIs_like() == 1) {
                        btn_attention.setText("已关注");
                    } else {
                        btn_attention.setText("+关注");
                    }
                }

                //tong
                RelativeLayout bussiness = view.findViewById(R.id.bussiness);
                if (videoinfo.getGood_id().equals("0")) {
                    bussiness.setVisibility(View.INVISIBLE);
                } else {
                    bussiness.setVisibility(View.VISIBLE);
                }

                // 获取此player
                TXCloudVideoView playView = (TXCloudVideoView) view.findViewById(R.id.player_cloud_view);
                PlayerInfo playerInfo = instantiatePlayerInfo(position);
                playerInfo.playerView = playView;
                playerInfo.txVodPlayer.setPlayerView(playView);
                playerInfo.txVodPlayer.startPlay(playerInfo.playURL);
                onClick(view, position, "like");
                container.addView(view);
                return view;
            }else {
                SearchBean.DataBean.VisitBean videoinfo = list.get(position);
                View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_player_content_two, null);
                view.setId(position);
                // 封面
                ImageView coverImageView = (ImageView) view.findViewById(R.id.player_iv_cover);
                Glide.with(PlayVideoTwo_TwoActivity.this).load(videoinfo.getVideo_img()).apply(new RequestOptions()
                        .placeholder(R.mipmap.mine_bg).error(R.mipmap.mine_bg)).into(coverImageView);

                //tou
                CircleImageView userimage = (CircleImageView) view.findViewById(R.id.souye_logo);
                Glide.with(PlayVideoTwo_TwoActivity.this).load(videoinfo.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo)
                        .error(R.mipmap.zuanshi_logo)).into(userimage);
                //旋转图
                CircleImageView guanpan = (CircleImageView) view.findViewById(R.id.circleImageView);
                Glide.with(PlayVideoTwo_TwoActivity.this).load(videoinfo.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo)
                        .error(R.mipmap.zuanshi_logo)).into(guanpan);
                guanpan.setAnimation(rotateAnimation);

                //zan
                ImageView iv_like = view.findViewById(R.id.iv_like);
                if (videoinfo.getIs_zan() == 1) {
                    iv_like.setSelected(true);
                } else {
                    iv_like.setSelected(false);
                }

                //            关注
                Button btn_attention = view.findViewById(R.id.btn_attention);
                if ((videoinfo.getUid() + "").equals(SharePreferenceUtil.getUserid(AppUtils.getContext()))) {
                    btn_attention.setVisibility(View.INVISIBLE);
                } else {
                    btn_attention.setVisibility(View.VISIBLE);
                    if (videoinfo.getIs_like() == 1) {
                        btn_attention.setText("已关注");
                    } else {
                        btn_attention.setText("+关注");
                    }
                }

                //商城
                RelativeLayout bussiness = view.findViewById(R.id.bussiness);
                if (videoinfo.getGood_id().equals("0")) {
                    bussiness.setVisibility(View.INVISIBLE);
                } else {
                    bussiness.setVisibility(View.VISIBLE);
                }

                // 获取此player
                TXCloudVideoView playView = (TXCloudVideoView) view.findViewById(R.id.player_cloud_view);
                PlayerInfo playerInfo = instantiatePlayerInfo(position);
                playerInfo.playerView = playView;
                playerInfo.txVodPlayer.setPlayerView(playView);
                playerInfo.txVodPlayer.startPlay(playerInfo.playURL);
                onClick(view, position, "visit");
                container.addView(view);
                return view;
            }

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            destroyPlayerInfo(position);

            container.removeView((View) object);
        }


        private void onClick(View itemView, int position, String listtype) {

            ImageView iv_like = itemView.findViewById(R.id.iv_like);
            TextView tv_like = itemView.findViewById(R.id.tv_like);
            //点赞
            iv_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (mOnItemClickListerer != null)
//                        mOnItemClickListerer.onItemClick(position, "点赞", view, tv_like, view, listtype);

                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(PlayVideoTwo_TwoActivity.this, LoginNewActivity.class));
                    } else {
//                        tvzan= (TextView) view1;//有了
//                        ImageView ivlike = (ImageView) view;
//                        TextView tv2 = (TextView) view1;

//                        Log.e("-----------","点了1");
                        if (iv_like.isSelected()) {
                            iv_like.setSelected(false);
                            likeVideo(listtype);
                            String string = tv_like.getText().toString();
                            int text = (Integer.parseInt(string) - 1);
                            tv_like.setText("" + text);
                        } else {
                            iv_like.setSelected(true);
                            likeVideo(listtype);
                            String string = tv_like.getText().toString();
                            int text = (Integer.parseInt(string) + 1);
                            tv_like.setText("" + text);
                        }
                    }
                }
            });

            //评论
            ImageView iv_commit = itemView.findViewById(R.id.iv_commit);
            TextView tv_pinglun = itemView.findViewById(R.id.tv_pinglun);
            iv_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (mOnItemClickListerer != null)
//                        mOnItemClickListerer.onItemClick(position, "评论", view, tv_pinglun, view, listtype);
                    showPingLun(tv_pinglun, listtype);

                }
            });

            //分享
            ImageView iv_share = itemView.findViewById(R.id.iv_share);
            iv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (mOnItemClickListerer != null)
//                        mOnItemClickListerer.onItemClick(position, "分享", view, view, view, listtype);

                    showShare(listtype);
                }
            });

            //礼物
            ImageView iv_gift = itemView.findViewById(R.id.iv_gift);
            iv_gift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (mOnItemClickListerer != null)
//                        mOnItemClickListerer.onItemClick(position, "礼物", view, view, view, listtype);

                    showGift(listtype);
                }
            });

            //关注
            Button btn_attention = itemView.findViewById(R.id.btn_attention);
            btn_attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (mOnItemClickListerer != null)
//                        mOnItemClickListerer.onItemClick(position, "关注", btn_attention, view, view, listtype);

                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(PlayVideoTwo_TwoActivity.this, LoginNewActivity.class));
                    } else {
                        Button button = (Button) view;
                        attentions(listtype, button);
                    }
                }
            });

            //红心ok
            RelativeLayout rlhead = itemView.findViewById(R.id.rlhead);
            ImageView play_pause = itemView.findViewById(R.id.iv_play_pause);
            ImageView back = itemView.findViewById(R.id.back);
            rlhead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (mOnItemClickListerer != null) {
//                            mOnItemClickListerer.onItemClick(position, "红心", view, view, view, listtype);
//
//
//                    }
//                    if (isIsshow()) {
//                        play_pause.setVisibility(View.VISIBLE);
//                    } else {
//                        play_pause.setVisibility(View.GONE);
//                    }

                    if (mTXVodPlayer.isPlaying()) {
                        mTXVodPlayer.pause();
                        play_pause.setVisibility(View.VISIBLE);
                    } else {
                        mTXVodPlayer.resume();
                        play_pause.setVisibility(View.GONE);
                    }

                }
            });

            //ok
            play_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (mOnItemClickListerer != null)
//                        mOnItemClickListerer.onItemClick(position, "红心", view, view, view, listtype);
//                    if (isIsshow()) {
//                        play_pause.setVisibility(View.VISIBLE);
//                    } else {
//                        play_pause.setVisibility(View.GONE);
//                    }

                    if (mTXVodPlayer.isPlaying()) {
                        mTXVodPlayer.pause();
                        play_pause.setVisibility(View.VISIBLE);
                    } else {
                        mTXVodPlayer.resume();
                        play_pause.setVisibility(View.GONE);
                    }
                }
            });


            //hong
            Love lovev = itemView.findViewById(R.id.love);
            lovev.setLoveTrue(new Love.LoveTrue() {
                @Override
                public void Onclick(boolean love) {
                    if (love) {
//                    Log.e("-----","hehe");
//                        if (mOnItemClickListerer != null)
//                            mOnItemClickListerer.onItemClick(position, "红红", lovev, iv_like, tv_like, listtype);


                        if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals(""))
                            return;
//                        ImageView ivlike = (ImageView) view1;
//                        TextView tvlike = (TextView) view2;
                        if (!iv_like.isSelected()) {
                            likeVideo(listtype);
                            iv_like.setSelected(true);
                            int b = Integer.parseInt(tv_like.getText().toString()) + 1;
                            tv_like.setText("" + b);
                        }
                    }
                }
            });

            //business
            RelativeLayout bussiness = itemView.findViewById(R.id.bussiness);
            bussiness.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (mOnItemClickListerer != null)
//                        mOnItemClickListerer.onItemClick(position, "商城", view, bussiness, view, listtype);
//                Log.e("-----","hehe");
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(PlayVideoTwo_TwoActivity.this, LoginNewActivity.class));
                    } else {

                        Intent intentx = new Intent(PlayVideoTwo_TwoActivity.this, BusinessWebTwoActivity.class);
                        if (listtype.equals("like")) {
                            intentx.putExtra("url", "http://www.51ayhd.com/web/Shopping/#/shopindex/token/" + SharePreferenceUtil.getToken(AppUtils.getContext()) + "/good_id/" + likeBeans.get(mCurrentPosition).getGood_id());
                            intentx.putExtra("good_id", likeBeans.get(mCurrentPosition).getGood_id());
                        } else {
                            intentx.putExtra("url", "http://www.51ayhd.com/web/Shopping/#/shopindex/token/" + SharePreferenceUtil.getToken(AppUtils.getContext()) + "/good_id/" + list.get(mCurrentPosition).getGood_id());
                            intentx.putExtra("good_id", list.get(mCurrentPosition).getGood_id());
                        }
                        startActivity(intentx);
                    }

                }
            });
            //同款
            CircleImageView guanpan = (CircleImageView) itemView.findViewById(R.id.circleImageView);
            guanpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(PlayVideoTwo_TwoActivity.this, LoginNewActivity.class));
                    } else {
                        Intent intentt = new Intent(PlayVideoTwo_TwoActivity.this, TongKuanActivity.class);
                        if (listtype.equals("like")) {
                            intentt.putExtra("video_id", likeBeans.get(mCurrentPosition).getVideo_id());
                        } else {
                            intentt.putExtra("video_id", list.get(mCurrentPosition).getVideo_id());
                        }

                        startActivity(intentt);
                    }
                }
            });


            //back
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mOnItemClickListerer.onItemClick(position, "返回", view, view, view, listtype);
                    finish();
                }
            });

        }

    }


    private void initPlayerSDK() {
        mVerticalViewPager.setCurrentItem(mInitTCLiveInfoPosition);
    }


    public boolean isIsshow() {
        return isshow;
    }

    public void setIsshow(boolean isshow) {
        this.isshow = isshow;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onPause();
        }
        if (mTXVodPlayer != null) {
            mTXVodPlayer.pause();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onDestroy();
            mTXCloudVideoView = null;
        }
        stopPlay(true);
        mTXVodPlayer = null;


    }

    protected void stopPlay(boolean clearLastFrame) {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.stopPlay(clearLastFrame);
        }
    }


    private void likeVideo(String listtype) {

        String vids = "";
        if (listtype.equals("like")) {
            vids = likeBeans.get(mCurrentPosition).getVideo_id() + "";
        } else {
            vids = list.get(mCurrentPosition).getVideo_id() + "";
        }

//        Log.e("------------","点了2="+vids);
        RetrofitManager.getInstance().getDataServer().zanorno(vids, SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<ShipinDianZanBean>() {
                    @Override
                    public void onResponse(retrofit2.Call<ShipinDianZanBean> call, Response<ShipinDianZanBean> response) {
//                        if (response.isSuccessful()){
//                            if (response.body()!=null && response.body().getCode().equals("200")){
//                                String value = response.body().getData().getIs();
//                                if (value.equals("1")) {
//                                    ivLike.setImageResource(R.drawable.iv_like_selected);
////                                                likeBeans.get(position).getIs_zan()//如果要刷新，就要放到上面的里面去
////                                                initViewUI(1);
//                                } else {
//                                    ivLike.setImageResource(R.drawable.iv_like_unselected);
////                                                initViewUI(-1);
//                                }


//                            }
//                        }
                    }

                    @Override
                    public void onFailure(Call<ShipinDianZanBean> call, Throwable t) {

                    }
                });

    }

    public void showPingLun(TextView tv, String like) {
        if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
            startActivity(new Intent(PlayVideoTwo_TwoActivity.this, LoginNewActivity.class));
        } else {
            BottomPingLunDialog bottomPingLunDialog = BottomPingLunDialog.newInstance();
            Bundle args = new Bundle();
            if (like.equals("like")) {
                args.putString("vid", likeBeans.get(mCurrentPosition).getVideo_id() + "");
            } else {
                args.putString("vid", list.get(mCurrentPosition).getVideo_id() + "");
            }

            bottomPingLunDialog.setArguments(args);
            bottomPingLunDialog.show(getSupportFragmentManager(), "pinglun");
            bottomPingLunDialog.setPinNum(new BottomPingLunDialog.PinNum() {
                @Override
                public void Clicker(int num) {
//                    View view = layoutManager.findViewByPosition(mCurrentPosition);
//                    if (view != null) {
//                        TextView tvLike = view.findViewById(R.id.tv_pinglun);
//                        tvLike.setText("" + num);
//                    }
                    tv.setText("" + num);
                }
            });


        }
    }

    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;
    private IWXAPI api;
    //微信
    private static final int THUMB_SIZE = 150;

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void showShare(String liketype) {
        BottomShareDialog bottomShareDialog = BottomShareDialog.newInstance();
        Bundle args = new Bundle();
        if (liketype.equals("like")) {
            args.putString("url", likeBeans.get(mCurrentPosition).getUrl());
            args.putString("videoid", likeBeans.get(mCurrentPosition).getVideo_id() + "");
            args.putString("userid", likeBeans.get(mCurrentPosition).getUid() + "");
        } else {
            args.putString("url", list.get(mCurrentPosition).getUrl());
            args.putString("videoid", list.get(mCurrentPosition).getVideo_id() + "");
            args.putString("userid", list.get(mCurrentPosition).getUid() + "");
        }

        bottomShareDialog.setArguments(args);
        bottomShareDialog.show(getSupportFragmentManager(), "share");
        bottomShareDialog.setShareDialog(new BottomShareDialog.ShareDialog() {
            @Override
            public void Clicklinear(View view, String type) {
                if (type.equals("下载")) {
                    Log.e("-------", "下载");


                    bottomShareDialog.dismiss();
                } else if (type.equals("分享")) {
                    Log.e("-------", "分享");
                    WXWebpageObject webpage = new WXWebpageObject();
                    webpage.webpageUrl = "http://www.qq.com";
                    WXMediaMessage msg = new WXMediaMessage(webpage);
                    msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
                    msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
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


    public void showGift(String listtype) {
        if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
            startActivity(new Intent(this, LoginNewActivity.class));
        } else {
            BottomGiftDialog bottomGiftDialog = BottomGiftDialog.newInstance();
            Bundle args = new Bundle();
            if (listtype.equals("like")) {
                args.putString("vid", likeBeans.get(mCurrentPosition).getVideo_id() + "");
            } else {
                args.putString("vid", list.get(mCurrentPosition).getVideo_id() + "");
            }

            bottomGiftDialog.setArguments(args);
            bottomGiftDialog.show(getSupportFragmentManager(), "gift");
        }
    }


    private void attentions(String listtype, Button button) {
        String vid = "";
        if (listtype.equals("like")) {
            vid = likeBeans.get(mCurrentPosition).getUid() + "";
        } else {
            vid = list.get(mCurrentPosition).getUid() + "";
        }

        RetrofitManager.getInstance().getDataServer().attentionUserTwo(vid, SharePreferenceUtil.getToken(AppUtils.getContext())).enqueue(new Callback<ResponseBody>() {
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
