package com.jarhero790.eub.message.souye;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.R;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.adapter.TongKuanAdapter;
import com.jarhero790.eub.message.bean.TongKuanBean;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.ui.souye.BottomShareDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TongKuanActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.shape)
    ImageView shape;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.iv_video_image)
    ImageView ivVideoImage;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_memo)
    TextView tvMemo;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.recyclerViewtong)
    RecyclerView recyclerViewtong;
    @BindView(R.id.m_swipe_layout)
    SmartRefreshLayout mSwipeLayout;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;

    private int page = 1;
    List<TongKuanBean.DataBean.IdenticalBean> list = new ArrayList<>();
    List<TongKuanBean.DataBean.IdenticalBean> itemlist = new ArrayList<>();

    String video_id = "1110";
    TongKuanAdapter adapter;
    GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_kuan);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerViewtong.setLayoutManager(layoutManager);
        api = WXAPIFactory.createWXAPI(this, GlobalApplication.APP_ID_Wei, true);
        api.registerApp(GlobalApplication.APP_ID_Wei);

        Intent intent = getIntent();
        video_id = intent.getStringExtra("video_id");
        if (video_id==null || video_id.equals("0"))
            video_id="1110";

        page = 1;
        initDate();


//        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshLayout) {
//                page = 1;
//                initDate();
//                mSwipeLayout.finishRefresh(100);
//
//            }
//        });
//        mSwipeLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshLayout) {
////                page++;
//                page = 1;
//                initDate();
//                mSwipeLayout.finishLoadMore(100);
//
//            }
//        });
    }

    private Dialog dialog;
    Call<TongKuanBean> calls = null;

    private void initDate() {
        dialog = new Dialog(this, R.style.progress_dialog);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        RetrofitManager.getInstance().getDataServer().getidentical(page, video_id, SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<TongKuanBean>() {
                    @Override
                    public void onResponse(Call<TongKuanBean> call, Response<TongKuanBean> response) {
                        calls = call;
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body() != null && response.body().getCode() == 200) {
                                itemlist.clear();

                                String video_img = response.body().getData().getVideo().getVideo_img();
                                Glide.with(TongKuanActivity.this).load(video_img).apply(new RequestOptions().placeholder(R.mipmap.mine_bg).error(R.mipmap.mine_bg))
                                        .into(ivVideoImage);
                                String title = response.body().getData().getVideo().getTitle();
                                tvName.setText(title);
                                String describe = response.body().getData().getVideo().getDescribe();
                                tvMemo.setText(describe);
                                String music_count = response.body().getData().getVideo().getMusic_count();
                                tvNum.setText(music_count + "人参与");


                                itemlist = response.body().getData().getIdentical();





                                if (page == 1) {
                                    list.clear();
                                    list.addAll(itemlist);


                                    if (list.size()>0){
                                        mSwipeLayout.setVisibility(View.VISIBLE);
                                        nodingdan.setVisibility(View.GONE);
                                        adapter = new TongKuanAdapter(TongKuanActivity.this, list, mydelete, mytu);
                                        recyclerViewtong.setAdapter(adapter);
                                    }else {
                                        nodingdan.setVisibility(View.VISIBLE);
                                        mSwipeLayout.setVisibility(View.GONE);

                                    }


                                } else {
                                    list.addAll(itemlist);
                                    adapter.notifyDataSetChanged();
                                }


                            }

                        } else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<TongKuanBean> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (calls != null) {
            calls.cancel();
        }
    }


    TongKuanAdapter.Myclick mydelete = new TongKuanAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {

        }
    };
    TongKuanAdapter.Myclick mytu = new TongKuanAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {

        }
    };

    @OnClick({R.id.back, R.id.shape, R.id.ll3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.shape:
                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                    startActivity(new Intent(this, LoginNewActivity.class));
                } else {
                    showShare();
                }
                break;
            case R.id.ll3:
                break;
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
//        Bundle args = new Bundle();
//        args.putString("url", list.get(mCurrentPosition).getUrl());
//        args.putString("videoid", list.get(mCurrentPosition).getVideo_id()+"");
//        args.putString("userid",list.get(mCurrentPosition).getUid()+"");
//        bottomShareDialog.setArguments(args);
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
}
