package com.jarhero790.eub.message.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.bumptech.glide.request.RequestOptions;
import com.danikula.videocache.HttpProxyCacheServer;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.OnViewPagerListener;
import com.jarhero790.eub.adapter.TikTokController;
import com.jarhero790.eub.adapter.ViewPagerLayoutManager;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.bean.MyFaBuBean;
import com.jarhero790.eub.message.bean.ZanBean;
import com.jarhero790.eub.message.bean.Zanchange;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.message.souye.BusinessWebTwoActivity;
import com.jarhero790.eub.message.souye.TongKuanActivity;
import com.jarhero790.eub.ui.souye.BottomGiftDialog;
import com.jarhero790.eub.ui.souye.BottomPingLunDialog;
import com.jarhero790.eub.ui.souye.BottomShareDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//播放视频界面
public class PlayVideoActivity extends AppCompatActivity {

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
    private int mposition;

    ArrayList<MyFaBuBean.DataBean> list = new ArrayList<>();//作品


    ArrayList<ZanBean.DataBean> zanBeanList = new ArrayList<>();//赞
    //
////    private List<Video> videolist = new ArrayList<>();
//    View viewplaypause;
    TikTokAdapter tikTokAdapter;

    private static final String TAG = "TikTokActivity";
    private VideoView mVideoView;
    private TikTokController mTikTokController;
    private int mCurrentPosition;
    private RecyclerView mRecyclerView;
    //    private List<VideoBean> mVideoList = new ArrayList<>();
    ViewPagerLayoutManager layoutManager;

    private String videotype = "mine";

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eeee(Zanchange zanchange){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mVideoView = new VideoView(this);
        mVideoView.setLooping(true);
        mTikTokController = new TikTokController(this,mVideoView,"");
        mVideoView.setVideoController(mTikTokController);
        mRecyclerView = findViewById(R.id.recycler_view);


        Intent intent = getIntent();
        videotype = intent.getStringExtra("videotype");
        mposition = intent.getIntExtra("position", 0);


        if (videotype != null && videotype.equals("zan")) {
            zanBeanList = (ArrayList<ZanBean.DataBean>) intent.getSerializableExtra("vidlist");
            tikTokAdapter = new TikTokAdapter(zanBeanList, this, "zan");
        } else {
            list = (ArrayList<MyFaBuBean.DataBean>) intent.getSerializableExtra("vidlist");

            tikTokAdapter = new TikTokAdapter(list, this);
        }


//        Log.e("-----------", "a=" + position);
//        Log.e("-----------", "b=" + list.size());


//        if (list != null && list.size() > 0) {
//
//            for (int i = 0; i < list.size(); i++) {
//                mVideoList.add(new VideoBean(list.get(i).getUid() + "", list.get(i).getVideo_img(), list.get(i).getUrl()));
//            }
//
//
//            if (mVideoList != null && mVideoList.size() > 0) {
//
//
//            }
//        }


//        mVideoList = DataUtil.getTikTokVideoList();


        layoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(tikTokAdapter);

        adapterSetOnItemClickListerer();
        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                //自动播放第一条
                startPlay(mposition);
                mCurrentPosition=mposition;
//                Log.e("----------", "a=" + mCurrentPosition + " m=" + position);

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
//                Log.e("----------", "b=" + mCurrentPosition + " m=" + position);
                if (mCurrentPosition == position) {
                    mVideoView.release();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
//                Log.e("----------", "c=" + mCurrentPosition + " m=" + position);
                if (mCurrentPosition == position) return;
                startPlay(position);
                mCurrentPosition = position;
            }
        });


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

//    private void initViewUI() {
//        View view = layoutManager.findViewByPosition(mCurrentPosition);
//        if (view != null) {
//            TextView zan = view.findViewById(R.id.tv_like);
//            zan.setText(list.get(mCurrentPosition).getZan() + "");
//
//            TextView caifu = view.findViewById(R.id.tv_gold_coin);
//            caifu.setText(list.get(mCurrentPosition).getCaifu() + "");
//
//            TextView pinlen = view.findViewById(R.id.tv_pinglun);
//            pinlen.setText(list.get(mCurrentPosition).getCommentNum() + "");

//            if (list.get(mCurrentPosition).getIs_like() == 1) {
    //关注
//                自己的不写
//            }

//            ImageView ivzan=view.findViewById(R.id.iv_like);
//            if (list.get(mCurrentPosition).getIs_zan()==1){
//                //赞了
//                ivzan.setImageResource(R.drawable.iv_like_selected);
//            }else {
//                ivzan.setImageResource(R.drawable.iv_like_unselected);
//            }
//        }

//    }

    private void adapterSetOnItemClickListerer() {
        tikTokAdapter.setOnItemClickListerer(new TikTokAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String type, View view, View view2, View view3, String videotype) {
                if (type.equals("评论")) {
                    TextView pin = (TextView) view2;
                    showPingLun(pin, videotype);//ok
                } else if (type.equals("分享")) {
                    showShare();
                } else if (type.equals("点赞")) {
                    ImageView ivlike = (ImageView) view;
                    TextView tv2 = (TextView) view2;
//                    Log.e("-------------mc=","ttt"+mCurrentPosition);
                    if (ivlike.isSelected()) {
                        ivlike.setSelected(false);
                        if (videotype.equals("zan")) {
                            if (zanBeanList != null && zanBeanList.size() > 0) {
                                zanother(zanBeanList.get(mCurrentPosition).getVideo_id() + "");
//                                Log.e("------------vid0=",zanBeanList.get(mCurrentPosition).getHeadimgurl());
                                String string = tv2.getText().toString();
                                int text = (Integer.parseInt(string) - 1);
                                tv2.setText("" + text);
                            }
                        } else {
                            if (list != null && list.size() > 0) {
                                zanother(list.get(mCurrentPosition).getVideo_id() + "");
//                                Log.e("------------vid1=",list.get(mCurrentPosition).getHeadimgurl());
                                String string = tv2.getText().toString();
                                int text = (Integer.parseInt(string) - 1);
                                tv2.setText("" + text);
                            }
                        }


//                        Log.e("-----------str=",""+(Integer.parseInt(string)-1));
                    } else {
                        ivlike.setSelected(true);
                        if (videotype.equals("zan")) {
                            if (zanBeanList != null && zanBeanList.size() > 0) {
                                zanother(zanBeanList.get(mCurrentPosition).getVideo_id() + "");
                                String string = tv2.getText().toString();
                                int text = (Integer.parseInt(string) + 1);
                                tv2.setText("" + text);
                            }
                        } else {
                            if (list != null && list.size() > 0) {
                                zanother(list.get(mCurrentPosition).getVideo_id() + "");
                                String string = tv2.getText().toString();
                                int text = (Integer.parseInt(string) + 1);
                                tv2.setText("" + text);
//                            tikTokAdapter.notifyItemChanged(mCurrentPosition);//不能刷新？？
                            }
                        }

//                        String string=tv2.getText().toString();
//                        Log.e("-----------str=",""+(Integer.parseInt(string)+1));
                    }

//                        Log.e("-----","ivlike.isSelected=true");


                } else if (type.equals("礼物")) {
                    showGift();
                } else if (type.equals("关注")) {
//                    Log.e("-----", "5");
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(PlayVideoActivity.this, LoginNewActivity.class));
                    } else {
                        Button button = (Button) view;
                        attentions(button, videotype);
                    }

                } else if (type.equals("红红")) {

                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals(""))
                        return;
                    ImageView ivlike = (ImageView) view2;
                    TextView tvlike = (TextView) view3;

                    if (!ivlike.isSelected()) {
                        if (videotype.equals("zan")) {
                            zanother(zanBeanList.get(mCurrentPosition).getVideo_id() + "");
//                            Log.e("------------vid3=",zanBeanList.get(mCurrentPosition).getHeadimgurl());
                        } else {
                            zanother(list.get(mCurrentPosition).getVideo_id() + "");
//                            Log.e("------------vid4=",list.get(mCurrentPosition).getHeadimgurl());
                        }

                        ivlike.setSelected(true);
                        int b = Integer.parseInt(tvlike.getText().toString()) + 1;
                        tvlike.setText("" + b);
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

                } else if (type.equals("商城")) {
//                    Log.e("-------------","商城");
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(PlayVideoActivity.this, LoginNewActivity.class));
                    } else {

                        Intent intentx = new Intent(PlayVideoActivity.this, BusinessWebTwoActivity.class);
//                        Log.e("---------goodid=",""+mCurrentPosition+"     "+position+"   "+ list.get(mCurrentPosition).getGood_id());//后台没有goodid字段
                        if (videotype.equals("zan")) {
                            intentx.putExtra("good_id", zanBeanList.get(mCurrentPosition).getGood_id());
                            intentx.putExtra("url", "http://www.51ayhd.com/web/Shopping/#/shopindex/token/" + SharePreferenceUtil.getToken(AppUtils.getContext()) + "/good_id/" + zanBeanList.get(mCurrentPosition).getGood_id());
                        } else {
                            intentx.putExtra("good_id", list.get(mCurrentPosition).getGood_id());
                            intentx.putExtra("url", "http://www.51ayhd.com/web/Shopping/#/shopindex/token/" + SharePreferenceUtil.getToken(AppUtils.getContext()) + "/good_id/" + list.get(mCurrentPosition).getGood_id());
                        }

                        startActivity(intentx);
                    }

                } else if (type.equals("返回")) {
                    finish();
                } else if (type.equals("同款")) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(PlayVideoActivity.this, LoginNewActivity.class));
                    } else {
                        Intent intentt = new Intent(PlayVideoActivity.this, TongKuanActivity.class);
                        if (videotype.equals("zan")) {
//                            Log.e("---------zan=", zanBeanList.get(mCurrentPosition).getVideo_id() + "");
                            intentt.putExtra("video_id", zanBeanList.get(mCurrentPosition).getVideo_id() + "");
                        } else {
                            intentt.putExtra("video_id", list.get(mCurrentPosition).getVideo_id() + "");
//                            Log.e("----------mine=", list.get(mCurrentPosition).getVideo_id() + "");
                        }

                        startActivity(intentt);
                    }
                }
            }
        });
    }
    @Override
    public void onStop() {
        super.onStop();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void zanother(String vid) {


        RetrofitManager.getInstance().getDataServer().zanoter(vid, SharePreferenceUtil.getToken(AppUtils.getContext())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


//                tikTokAdapter.notifyItemChanged(mCurrentPosition);
                if (response.isSuccessful()){
                    EventBus.getDefault().post(new Zanchange("zan"));
//                    try {
//                        String json=response.body().string();
//                        Log.e("------",json);
//                        JSONObject object=new JSONObject(json);
//                        int code=object.optInt("code");
//                        if (code==200){
//                            JSONObject data=object.optJSONObject("data");
//                            String is=data.optString("is");
//                            String num=data.optString("num");
//                            if (is.equals("1")){
//                                views.setSelected(true);
//
//                            }else {
//                                views.setSelected(false);
//                            }
//                            tv.setText(num);
//                        }
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void startPlay(int position) {

        if (videotype!=null && videotype.equals("zan")) {
            if (zanBeanList.size() == 0)
                return;
            View itemView = mRecyclerView.getChildAt(0);

//        FrameLayout frameLayout = itemView.findViewById(R.id.container);
            RelativeLayout relativeLayout = itemView.findViewById(R.id.container);
            RelativeLayout buss=itemView.findViewById(R.id.bussiness);
            TextView tvlike=itemView.findViewById(R.id.tv_like);
            ImageView ivlike=itemView.findViewById(R.id.iv_like);

            TextView tv_gold_coin=itemView.findViewById(R.id.tv_gold_coin);
            TextView tv_pinglun=itemView.findViewById(R.id.tv_pinglun);
//            RelativeLayout relativeLayout = itemView.findViewById(R.id.souye_page_video_relativeLayout);
            Glide.with(this)
                    .load(zanBeanList.get(position).getVideo_img())
                    .apply(new RequestOptions().placeholder(android.R.color.black))
                    .into(mTikTokController.getThumb());
            ViewParent parent = mVideoView.getParent();
            if (parent instanceof RelativeLayout) {
                ((RelativeLayout) parent).removeView(mVideoView);
            }
            relativeLayout.addView(mVideoView, 2);
            HttpProxyCacheServer proxy = GlobalApplication.getProxy(PlayVideoActivity.this);
            String proxyUrl = proxy.getProxyUrl(zanBeanList.get(position).getUrl());
            if (proxyUrl.length() > 0) {
                mVideoView.setUrl(proxyUrl);
            } else {
                mVideoView.setUrl(zanBeanList.get(position).getUrl());
            }
            if (zanBeanList.get(position).getGood_id().equals("0")){
                buss.setVisibility(View.INVISIBLE);
            }else {
                buss.setVisibility(View.VISIBLE);
            }


            if (zanBeanList.get(position).getIs_like()==1){
                ivlike.setSelected(true);
            }else {
                ivlike.setSelected(false);
            }

            tvlike.setText(zanBeanList.get(position).getZan()+"");
            tv_gold_coin.setText(zanBeanList.get(position).getCaifu()+"");
            tv_pinglun.setText(zanBeanList.get(position).getCommentNum()+"");



            if (zanBeanList.get(position).getAnyhow().equals("1")){
                mVideoView.setScreenScale(VideoView.SCREEN_SCALE_MATCH_PARENT);//q
//                Log.e("-------------","竖屏视频");
            }else {
                mVideoView.setScreenScale(VideoView.SCREEN_SCALE_DEFAULT);//默认1：1
//            mVideoView.setRotation();
//                Log.e("-------------","横屏视频");
            }

//            mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
            mVideoView.start();
        } else {
            if (list.size() == 0)
                return;
            View itemView = mRecyclerView.getChildAt(0);

//        FrameLayout frameLayout = itemView.findViewById(R.id.container);
            RelativeLayout relativeLayout = itemView.findViewById(R.id.container);
            RelativeLayout buss=itemView.findViewById(R.id.bussiness);
            TextView tvlike=itemView.findViewById(R.id.tv_like);
            ImageView ivlike=itemView.findViewById(R.id.iv_like);

            TextView tv_gold_coin=itemView.findViewById(R.id.tv_gold_coin);
            TextView tv_pinglun=itemView.findViewById(R.id.tv_pinglun);
//            RelativeLayout relativeLayout = itemView.findViewById(R.id.souye_page_video_relativeLayout);
            Glide.with(this)
                    .load(list.get(position).getVideo_img())
                    .apply(new RequestOptions().placeholder(android.R.color.black))
                    .into(mTikTokController.getThumb());

            HttpProxyCacheServer proxy = GlobalApplication.getProxy(PlayVideoActivity.this);
            String proxyUrl = proxy.getProxyUrl(list.get(position).getUrl());
            if (proxyUrl.length() > 0) {
                mVideoView.setUrl(proxyUrl);
            } else {
                mVideoView.setUrl(list.get(position).getUrl());
            }
            if (list.get(position).getGood_id().equals("0")){
                buss.setVisibility(View.INVISIBLE);
            }else {
                buss.setVisibility(View.VISIBLE);
            }


            if (list.get(position).getIs_like()==1){
                ivlike.setSelected(true);
            }else {
                ivlike.setSelected(false);
            }

            tvlike.setText(list.get(position).getZan()+"");
            tv_gold_coin.setText(list.get(position).getCaifu()+"");
            tv_pinglun.setText(list.get(position).getCommentNum()+"");


            if (list.get(position).getAnyhow().equals("1")){
                mVideoView.setScreenScale(VideoView.SCREEN_SCALE_MATCH_PARENT);//q
                Log.e("-------------","竖屏视频");
            }else {
                mVideoView.setScreenScale(VideoView.SCREEN_SCALE_DEFAULT);//默认1：1
//            mVideoView.setRotation();
                Log.e("-------------","横屏视频");
            }

//            mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
            mVideoView.start();

            ViewParent parent = mVideoView.getParent();
            if (parent instanceof RelativeLayout) {
                ((RelativeLayout) parent).removeView(mVideoView);
            }
            relativeLayout.addView(mVideoView, 2);

            //全屏
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, 0);
            mVideoView.setLayoutParams(params);
            View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置
            if (view != null)
                view.findViewById(R.id.thumb).setVisibility(View.VISIBLE);
        }


        /**

         //确定高度
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
        view.findViewById(R.id.thumb).setVisibility(View.INVISIBLE);
        } else {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 0);
        mVideoView.setLayoutParams(params);
        View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置
        if (view != null)
        view.findViewById(R.id.thumb).setVisibility(View.VISIBLE);
        }


        }
        });

         */

        //UI
//        initViewUI();
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
        if (mVideoView != null) {
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


    public void showPingLun(TextView pin, String videotype) {
        if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
            startActivity(new Intent(this, LoginNewActivity.class));
        } else {
            BottomPingLunDialog bottomPingLunDialog = BottomPingLunDialog.newInstance();
            Bundle args = new Bundle();
            if (videotype.equals("zan")) {
                args.putString("vid", zanBeanList.get(mCurrentPosition).getVideo_id() + "");
            } else {
                args.putString("vid", list.get(mCurrentPosition).getVideo_id() + "");
            }
            bottomPingLunDialog.setArguments(args);
            bottomPingLunDialog.show(getSupportFragmentManager(), "pinglun");
            bottomPingLunDialog.setPinNum(new BottomPingLunDialog.PinNum() {
                @Override
                public void Clicker(int num) {
//                    Log.e("-----------num=",""+num);
                    pin.setText("" + num);
                }
            });
        }
    }

    //ok
    public void showGift() {
        if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
            startActivity(new Intent(this, LoginNewActivity.class));
        } else {
            BottomGiftDialog bottomGiftDialog = BottomGiftDialog.newInstance();
            Bundle args = new Bundle();
            if (videotype.equals("zan")) {
                args.putString("vid", zanBeanList.get(mCurrentPosition).getVideo_id() + "");
            } else {
                args.putString("vid", list.get(mCurrentPosition).getVideo_id() + "");
            }

            bottomGiftDialog.setArguments(args);
            bottomGiftDialog.show(getSupportFragmentManager(), "gift");
        }
    }

    public void showShare() {
        BottomShareDialog bottomShareDialog = BottomShareDialog.newInstance();
        bottomShareDialog.show(getSupportFragmentManager(), "share");
    }


    private void attentions(Button button, String videotype) {
        String uid = "";
        if (videotype.equals("zan")) {
            uid = zanBeanList.get(mCurrentPosition).getUid() + "";
        } else {
            uid = list.get(mCurrentPosition).getUid() + "";
        }

        RetrofitManager.getInstance().getDataServer().attentionUserTwo(uid, SharePreferenceUtil.getToken(AppUtils.getContext())).enqueue(new Callback<ResponseBody>() {
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
