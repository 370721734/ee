package com.jarhero790.eub.message.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.PinLAdapter;
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

    private int page = 1;

    List<MyPL.DataBean> list = new ArrayList<>();
    List<MyPL.DataBean> itemlist = new ArrayList<>();
    PinLAdapter pinLAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_len);
        CommonUtil.setStatusBarTransparent(this);
        ButterKnife.bind(this);

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
                page++;
                initDate();
                mSwipeLayout.finishLoadMore(100);

            }
        });
    }

    WaitPorgressDialog dialog;

    private void initDate() {
        dialog = new WaitPorgressDialog(this);
        dialog.isShowing();

        RetrofitManager.getInstance().getDataServer().mypinlen(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<MyPL>() {
                    @Override
                    public void onResponse(Call<MyPL> call, Response<MyPL> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();

                            if (response.body().getData().size() > 0) {
                                mSwipeLayout.setVisibility(View.VISIBLE);
                                recyclerViewPinLen.setVisibility(View.VISIBLE);
                                nodingdan.setVisibility(View.GONE);
                                itemlist.clear();
                                MyPL.DataBean body = (MyPL.DataBean) response.body().getData();
                                itemlist.add(body);

                                Log.e("---------1", itemlist.size() + "");

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
                                    nodingdan.setVisibility(View.VISIBLE);

                                } else {
                                    Toast.makeText(PinLenActivity.this, "没有相关数据", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPL> call, Throwable t) {
                        dialog.dismiss();
                    }
                });

    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    PinLAdapter.Myclick myclick = new PinLAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {

        }
    };
}
