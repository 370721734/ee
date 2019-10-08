package com.jarhero790.eub.message.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.GiftAdapter;
import com.jarhero790.eub.message.bean.FenSiTBean;
import com.jarhero790.eub.message.bean.GiftBean;
import com.jarhero790.eub.message.bean.GiftBeanTwo;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;

import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.jarhero790.eub.widget.WaitPorgressDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiftActivity extends AppCompatActivity {

    List<GiftBeanTwo.DataBean> giftBeanList = new ArrayList<>();
    GiftAdapter giftAdapter;

    @BindView(R.id.recyclerViewPinLen)
    RecyclerView recyclerViewPinLen;
    @BindView(R.id.m_swipe_layout)
    SmartRefreshLayout mSwipeLayout;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
    LinearLayoutManager manager;
    @BindView(R.id.wangluoyichang)
    RelativeLayout wangluoyichang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        manager = new LinearLayoutManager(this);
        recyclerViewPinLen.setLayoutManager(manager);
        LinearItemDecoration itemDecoration = new LinearItemDecoration();
        itemDecoration.setColor(getResources().getColor(R.color.backgroudcolor));
        recyclerViewPinLen.addItemDecoration(itemDecoration);
        initDate();
    }

//    CustomProgressDialog dialog = new CustomProgressDialog();
    retrofit2.Call<GiftBeanTwo> calls=null;

    private void initDate() {
//        dialog.createLoadingDialog(this, "正在加载...");
//        dialog.show();

        RetrofitManager.getInstance().getDataServer().getmygift(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<GiftBeanTwo>() {
                    @Override
                    public void onResponse(Call<GiftBeanTwo> call, Response<GiftBeanTwo> response) {
//                        Log.e("-------55", "1:" + response.body().getCode());
                        calls=call;
                        if (response.isSuccessful()) {
//                            dialog.dismiss();
                            if (response.body()!=null && response.body().getData().size()>0) {
//                                Log.e("-------55", "1:" + response.body().getData().size());
                                giftBeanList.clear();
                                nodingdan.setVisibility(View.GONE);
                                wangluoyichang.setVisibility(View.GONE);
                                mSwipeLayout.setVisibility(View.VISIBLE);
                                giftBeanList = response.body().getData();
                                giftAdapter = new GiftAdapter(GiftActivity.this, giftBeanList, myclick);
                                recyclerViewPinLen.setAdapter(giftAdapter);

                            }else {
                                nodingdan.setVisibility(View.VISIBLE);
                                wangluoyichang.setVisibility(View.GONE);
                                mSwipeLayout.setVisibility(View.GONE);
                            }

                        } else {
//                            dialog.dismiss();
                            nodingdan.setVisibility(View.GONE);
                            wangluoyichang.setVisibility(View.VISIBLE);
                            mSwipeLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<GiftBeanTwo> call, Throwable t) {
//                        dialog.dismiss();
                        nodingdan.setVisibility(View.GONE);
                        wangluoyichang.setVisibility(View.VISIBLE);
                        mSwipeLayout.setVisibility(View.GONE);
                    }
                });
    }


    GiftAdapter.Myclick myclick = new GiftAdapter.Myclick() {
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
