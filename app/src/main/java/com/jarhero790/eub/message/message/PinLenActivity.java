package com.jarhero790.eub.message.message;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.PinLAdapter;
import com.jarhero790.eub.message.bean.FenSiTBean;
import com.jarhero790.eub.message.bean.MyPL;
import com.jarhero790.eub.message.net.RetrofitManager;

import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.jarhero790.eub.widget.WaitPorgressDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinLenActivity extends AppCompatActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recyclerViewPinLen)
    RecyclerView recyclerViewPinLen;
    @BindView(R.id.m_swipe_layout)
    SmartRefreshLayout mSwipeLayout;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
    @BindView(R.id.wangluoyichang)
    RelativeLayout wangluoyichang;

    private int page = 1;

    List<MyPL.DataBean> list = new ArrayList<>();
    List<MyPL.DataBean> itemlist = new ArrayList<>();
    PinLAdapter pinLAdapter;

    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_len);
        CommonUtil.setStatusBarTransparent(this);
        ButterKnife.bind(this);
        manager = new LinearLayoutManager(this);
        recyclerViewPinLen.setLayoutManager(manager);
        initDate();

        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                initDate();
                mSwipeLayout.finishRefresh(100);

            }
        });
        mSwipeLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
//                page++;
                page = 1;
                initDate();
                mSwipeLayout.finishLoadMore(100);

            }
        });
    }

//    CustomProgressDialog dialog = new CustomProgressDialog();
    retrofit2.Call<MyPL> calls=null;
    private Dialog dialog;

    private void initDate() {
//        dialog.createLoadingDialog(this, "正在加载...");
//        dialog.show();
        dialog = new Dialog(this, R.style.progress_dialog);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        RetrofitManager.getInstance().getDataServer().mypinlen(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<MyPL>() {
                    @Override
                    public void onResponse(Call<MyPL> call, Response<MyPL> response) {
                        calls=call;
                        if (response.isSuccessful()) {
                            dialog.dismiss();

                            if (response.body()!=null && response.body().getData().size() > 0) {
                                mSwipeLayout.setVisibility(View.VISIBLE);
                                recyclerViewPinLen.setVisibility(View.VISIBLE);
                                nodingdan.setVisibility(View.GONE);
                                wangluoyichang.setVisibility(View.GONE);
                                itemlist.clear();
                                MyPL body = response.body();
                                itemlist.addAll(body.getData());
//                                Log.e("---------1", itemlist.size() + "");
                                if (page == 1) {
                                    list.clear();
                                    list.addAll(itemlist);
                                    pinLAdapter = new PinLAdapter(PinLenActivity.this, list, myclick);
                                    recyclerViewPinLen.setAdapter(pinLAdapter);
                                } else {
                                    list.addAll(itemlist);
                                    pinLAdapter.notifyDataSetChanged();
                                }
                            } else {
                                if (page == 1) {
                                    recyclerViewPinLen.setVisibility(View.GONE);
                                    mSwipeLayout.setVisibility(View.GONE);
                                    wangluoyichang.setVisibility(View.GONE);
                                    nodingdan.setVisibility(View.VISIBLE);

                                } else {
                                    Toast.makeText(PinLenActivity.this, "没有相关数据", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            dialog.dismiss();
                            recyclerViewPinLen.setVisibility(View.GONE);
                            mSwipeLayout.setVisibility(View.GONE);
                            wangluoyichang.setVisibility(View.VISIBLE);
                            nodingdan.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPL> call, Throwable t) {
                        dialog.dismiss();
                        recyclerViewPinLen.setVisibility(View.GONE);
                        mSwipeLayout.setVisibility(View.GONE);
                        wangluoyichang.setVisibility(View.VISIBLE);
                        nodingdan.setVisibility(View.GONE);
                    }
                });

    }


    PinLAdapter.Myclick myclick = new PinLAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {

        }
    };

    @OnClick({R.id.back, R.id.wangluoyichang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.wangluoyichang:
                initDate();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (calls!=null){
            calls.cancel();
        }
    }
}
