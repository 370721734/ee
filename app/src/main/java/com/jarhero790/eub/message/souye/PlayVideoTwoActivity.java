package com.jarhero790.eub.message.souye;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.dueeeke.videoplayer.listener.OnVideoViewStateChangeListener;
import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.OnViewPagerListener;
import com.jarhero790.eub.adapter.TikTokController;
import com.jarhero790.eub.adapter.ViewPagerLayoutManager;
import com.jarhero790.eub.bean.AttentionBean;
import com.jarhero790.eub.bean.ShipinDianZanBean;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.adapter.GlideLoadUtils;
import com.jarhero790.eub.message.bean.MyFaBuBean;
import com.jarhero790.eub.message.bean.SearchBean;
import com.jarhero790.eub.message.my.PlayVideoActivity;
import com.jarhero790.eub.message.my.TikTokAdapter;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.ui.souye.BottomGiftDialog;
import com.jarhero790.eub.ui.souye.BottomPingLunDialog;
import com.jarhero790.eub.ui.souye.BottomShareDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayVideoTwoActivity extends AppCompatActivity {

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
    private int mCurrentPosition;//当前播放的第几个视频 ，
//    private int mposition;
    ArrayList<SearchBean.DataBean.VisitBean> list = new ArrayList<>();

    ArrayList<SearchBean.DataBean.LikeBean> likeBeans = new ArrayList<>();
    //
////    private List<Video> videolist = new ArrayList<>();
//    View viewplaypause;
    TikTokTwoAdapter tikTokAdapter;

    //    private static final String TAG = "TikTokActivity";
    private VideoView mVideoView;
    private TikTokController mTikTokController;
//    private int mCurrentPosition;
    private RecyclerView mRecyclerView;
    //    private List<VideoBean> mVideoList = new ArrayList<>();
    ViewPagerLayoutManager layoutManager;

    private String type = "";
    private boolean isfirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        mVideoView = new VideoView(this);
        mVideoView.setLooping(true);
        mTikTokController = new TikTokController(this,mVideoView,"");
        mVideoView.setVideoController(mTikTokController);
        mRecyclerView = findViewById(R.id.recycler_view);
        api = WXAPIFactory.createWXAPI(this, GlobalApplication.APP_ID_Wei, true);
        api.registerApp(GlobalApplication.APP_ID_Wei);

        Intent intent = getIntent();

        mCurrentPosition = intent.getIntExtra("position", 0);
        type = intent.getStringExtra("type");
        if (type != null && type.equals("like")) {
            likeBeans = (ArrayList<SearchBean.DataBean.LikeBean>) intent.getSerializableExtra("vidlist");
            tikTokAdapter = new TikTokTwoAdapter(likeBeans, this, "like");

        } else {
            list = (ArrayList<SearchBean.DataBean.VisitBean>) intent.getSerializableExtra("vidlist");
            tikTokAdapter = new TikTokTwoAdapter(list, this);

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
                startPlay(mCurrentPosition);
//                Log.e("----------", "开始=" + mCurrentPosition + " m=" + mposition);
//                mCurrentPosition=mposition;

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
//                Log.e("----------onpagerelease", "b=" + mCurrentPosition + " m=" + position);
                if (mCurrentPosition == position) {
                    mVideoView.release();
                }
                if (mCurrentPosition > 0 && isfirst) {
                    mVideoView.release();
                    isfirst = false;
//                    Log.e("----------", "onPageRelease bb=" + mCurrentPosition + "  onPageRelease mm=" + position);
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
//                Log.e("----------", "结束=" + mCurrentPosition + " m=" + position);
                if (mCurrentPosition == position) {
                    startPlay(0);
                    return;
                }

                startPlay(position);
                mCurrentPosition = position;
//                if (type != null && type.equals("like")) {
//                    int sb=(position+mCurrentPosition)>likeBeans.size()?0:(position+mCurrentPosition);
//                    startPlay(sb);
//                    mCurrentPosition = sb;
//                    Log.e("----------------","来了没有，="+sb);
//                }else {
//                    int sb=(position+mCurrentPosition)>list.size()?0:(position+mCurrentPosition);
//                    startPlay(sb);
//                    mCurrentPosition = sb;
//                    Log.e("----------------","来了没有，="+sb);
//                }

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

//    private void initViewUI(int zannum) {
//        View view = layoutManager.findViewByPosition(mCurrentPosition);
//        if (type!=null && type.equals("like")) {
//            if (view != null) {
//                TextView zan = view.findViewById(R.id.tv_like);
//                zan.setText((likeBeans.get(mCurrentPosition).getZan()+zannum) + "");
//
//                TextView caifu = view.findViewById(R.id.tv_gold_coin);
//                caifu.setText(likeBeans.get(mCurrentPosition).getCaifu() + "");
//
//                TextView pinlen = view.findViewById(R.id.tv_pinglun);
//                pinlen.setText(likeBeans.get(mCurrentPosition).getCommentNum() + "");
//            }
//        } else {
//            if (view != null) {
//                TextView zan = view.findViewById(R.id.tv_like);
//                zan.setText((list.get(mCurrentPosition).getZan()+zannum) + "");
//
//                TextView caifu = view.findViewById(R.id.tv_gold_coin);
//                caifu.setText(list.get(mCurrentPosition).getCaifu() + "");
//
//                TextView pinlen = view.findViewById(R.id.tv_pinglun);
//                pinlen.setText(list.get(mCurrentPosition).getCommentNum() + "");
//            }
//        }
//
//
//    }

    private void adapterSetOnItemClickListerer() {
        tikTokAdapter.setOnItemClickListerer(new TikTokTwoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String itemtype, View view, View view1, View view2, String listtype) {
                if (itemtype.equals("评论")) {
//                    Log.e("-----", "1" + listtype);
                    showPingLun(listtype);


                } else if (itemtype.equals("分享")) {
//                    Log.e("-----", "2"+listtype);
                    showShare();
                } else if (itemtype.equals("点赞")) {
//                    Log.e("-----", "3"+listtype);
//                    ImageView ivLike = (ImageView) view;
//                    String vids="";
//                    if (type.equals("like")){
//                        vids=likeBeans.get(position).getVideo_id()+"";
//                    }else {
//                        vids=list.get(position).getVideo_id()+"";
//                    }


                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(PlayVideoTwoActivity.this, LoginNewActivity.class));
                    } else {
//                        tvzan= (TextView) view1;//有了
                        ImageView ivlike = (ImageView) view;
                        TextView tv2 = (TextView) view1;

//                        Log.e("-----------","点了1");
                        if (ivlike.isSelected()) {
                            ivlike.setSelected(false);
                            likeVideo(listtype);
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
                            likeVideo(listtype);
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
                } else if (itemtype.equals("礼物")) {
                    showGift(listtype);
                } else if (itemtype.equals("关注")) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                        startActivity(new Intent(PlayVideoTwoActivity.this, LoginNewActivity.class));
                    } else {
                        Button button = (Button) view;
                        attentions(listtype, button);
                    }
                } else if (itemtype.equals("红心")) {
                    if (mVideoView.isPlaying()) {
                        mVideoView.pause();
                        tikTokAdapter.setIsshow(true);
                    } else {
                        mVideoView.resume();
                        tikTokAdapter.setIsshow(false);
                    }

                } else if (itemtype.equals("红红")) {
                    if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals(""))
                        return;
                    ImageView ivlike = (ImageView) view1;
                    TextView tvlike = (TextView) view2;
                    if (!ivlike.isSelected()) {
                        likeVideo(listtype);
                        ivlike.setSelected(true);
                        int b = Integer.parseInt(tvlike.getText().toString()) + 1;
                        tvlike.setText("" + b);
                    }
                } else if (itemtype.equals("返回")) {
                    finish();
                }
            }
        });
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
                    public void onResponse(Call<ShipinDianZanBean> call, Response<ShipinDianZanBean> response) {
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


    private void startPlay(int position) {

        View itemView = mRecyclerView.getChildAt(0);

//        FrameLayout frameLayout = itemView.findViewById(R.id.container);
        RelativeLayout relativeLayout = itemView.findViewById(R.id.container);
        RelativeLayout buss=itemView.findViewById(R.id.bussiness);
        TextView tvlike=itemView.findViewById(R.id.tv_like);
        ImageView ivlike=itemView.findViewById(R.id.iv_like);

        TextView tv_gold_coin=itemView.findViewById(R.id.tv_gold_coin);
        TextView tv_pinglun=itemView.findViewById(R.id.tv_pinglun);



        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 0);
        mVideoView.setLayoutParams(params);
        View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置
        if (view != null)
            view.findViewById(R.id.thumb).setVisibility(View.VISIBLE);


//            RelativeLayout relativeLayout = itemView.findViewById(R.id.souye_page_video_relativeLayout);

        if (type != null && type.equals("like")) {
            Glide.with(this)
                    .load(likeBeans.get(position).getVideo_img())
                    .apply(new RequestOptions().placeholder(android.R.color.black))
                    .into(mTikTokController.getThumb());
//            GlideLoadUtils.getInstance().glideLoad(this,likeBeans.get(position).getVideo_img(),mTikTokController.getThumb(),R.color.backgroudcolor);

            HttpProxyCacheServer proxy = GlobalApplication.getProxy(PlayVideoTwoActivity.this);
            String proxyUrl = proxy.getProxyUrl(likeBeans.get(position).getUrl());
            mVideoView.setUrl(proxyUrl);
//            File file=new File(proxyUrl);
//            if (file.exists()){
//                Log.e("-------------","mp4存在");
//            }else {
//                Log.e("-------------","mp4不存在");
//            }
//            if (proxyUrl.length() > 0) {
//
//            } else {
//                mVideoView.setUrl(likeBeans.get(position).getUrl());
//            }


            if (likeBeans.get(position).getGood_id().equals("0")){
                buss.setVisibility(View.INVISIBLE);
            }else {
                buss.setVisibility(View.VISIBLE);
            }

            if (likeBeans.get(position).getIs_like()==1){
                ivlike.setSelected(true);
            }else {
                ivlike.setSelected(false);
            }

            tvlike.setText(likeBeans.get(position).getZan()+"");
            tv_gold_coin.setText(likeBeans.get(position).getCaifu()+"");
            tv_pinglun.setText(likeBeans.get(position).getCommentNum()+"");

//            if (likeBeans.get(position).getAnyhow()==1){
//                //竖
//
//            }else {
//
//            }

        } else {
            Glide.with(this)
                    .load(list.get(position).getVideo_img())
                    .apply(new RequestOptions().placeholder(android.R.color.black))
                    .into(mTikTokController.getThumb());

//            GlideLoadUtils.getInstance().glideLoad(this,list.get(position).getVideo_img(),mTikTokController.getThumb(),R.color.backgroudcolor);

            HttpProxyCacheServer proxy = GlobalApplication.getProxy(PlayVideoTwoActivity.this);
            String proxyUrl = proxy.getProxyUrl(list.get(position).getUrl());
            mVideoView.setUrl(proxyUrl);
//            File file=new File(proxyUrl);
//            if (file.exists()){
//                Log.e("-------------","mp4存在");
//            }else {
//                Log.e("-------------","mp4不存在");
//            }

//            if (proxyUrl.length() > 0) {
//
//            } else {
//                mVideoView.setUrl(list.get(position).getUrl());
//            }


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
        }


        ViewParent parent = mVideoView.getParent();
        if (parent instanceof RelativeLayout) {
            ((RelativeLayout) parent).removeView(mVideoView);
        }
        relativeLayout.addView(mVideoView, 2);


        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
        mVideoView.start();




        //确定高度
//        mVideoView.setOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
//            @Override
//            public void onPlayerStateChanged(int playerState) {
////                Log.e("---Player",""+playerState);
////                int[] size= mVideoView.getVideoSize();
////                String value="位置"+position+",width:"+size[0]+" "+",height:"+size[1];
////                Log.e("Android短视频5",value);
//
//
//            }
//
//            @Override
//            public void onPlayStateChanged(int playState) {
//
////                isplay=mVideoView.isPlaying();
////                if (mVideoView.isPlaying()){
////                    tikTokAdapter.setIsshow(false);
//////                    tikTokAdapter.notifyItemChanged(mCurrentPosition);
////                }else {
////                    tikTokAdapter.setIsshow(true);
//////                    tikTokAdapter.notifyItemChanged(mCurrentPosition);
////                }
//
//
////                Log.e("---Play",""+playState);
//                int[] size = mVideoView.getVideoSize();
////                String value="位置"+position+",width:"+size[0]+" "+",height:"+size[1];
////                Log.e("Android短视频6",value);
////
////                Log.e("-------------s------",""+size[1]);
//                //高度OK
//                if (size[1] < 640) {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                            RelativeLayout.LayoutParams.MATCH_PARENT, 700);//RelativeLayout.LayoutParams.MATCH_PARENT 500
//                    params.addRule(RelativeLayout.CENTER_VERTICAL);
//                    params.setMargins(0, 0, 0, 0);//top 400
//                    mVideoView.setLayoutParams(params);
//                    View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置          删除了有没有问题
//                    if (view != null)
//                        view.findViewById(R.id.thumb).setVisibility(View.INVISIBLE);
//                } else {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                            RelativeLayout.LayoutParams.MATCH_PARENT,
//                            RelativeLayout.LayoutParams.MATCH_PARENT);
//                    params.setMargins(0, 0, 0, 0);
//                    mVideoView.setLayoutParams(params);
//                    View view = layoutManager.findViewByPosition(position);    //为recyclerView中item位置
//                    if (view != null)
//                        view.findViewById(R.id.thumb).setVisibility(View.VISIBLE);
//                }
//
//
//            }
//        });

        //UI
//        initViewUI(0);
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


//    public void videosPageListenerOnListener() {
//        layoutManager.setOnViewPagerListenertwo(new OnViewPagerListener() {
//            @Override
//            public void onInitComplete() {
//                //自动播放第一条
////                startPlay(position);
//            }
//
//            @Override
//            public void onPageRelease(boolean isNext, int position) {
//                if (mCurrentPosition == position) {
//                    mVideoView.release();
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position, boolean isBottom) {
//                if (mCurrentPosition == position) return;
//                startPlay(position);
//                mCurrentPosition = position;
//                Log.e("-----------gg","2222222222不同");
//                //ok
//                viewplaypause = layoutManager.findViewByPosition(position);    //为recyclerView中item位置          删除了有没有问题
//                if (viewplaypause != null)
//                    viewplaypause.findViewById(R.id.iv_play_pause).setVisibility(View.INVISIBLE);
//            }
//        },list);
//
//    }


    public void showPingLun(String like) {
        if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
            startActivity(new Intent(PlayVideoTwoActivity.this, LoginNewActivity.class));
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
                    View view = layoutManager.findViewByPosition(mCurrentPosition);
                    if (view != null) {
                        TextView tvLike = view.findViewById(R.id.tv_pinglun);
                        tvLike.setText("" + num);
                    }

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

    public void showShare() {
        BottomShareDialog bottomShareDialog = BottomShareDialog.newInstance();
        Bundle args = new Bundle();
        args.putString("url", list.get(mCurrentPosition).getUrl());
        args.putString("videoid", list.get(mCurrentPosition).getVideo_id()+"");
        args.putString("userid",list.get(mCurrentPosition).getUid()+"");
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
}
