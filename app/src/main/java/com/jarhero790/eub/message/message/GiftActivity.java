package com.jarhero790.eub.message.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.GiftAdapter;
import com.jarhero790.eub.message.bean.GiftBean;
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

    List<GiftBean.DataBean> giftBeanList = new ArrayList<>();
    GiftAdapter giftAdapter;

    @BindView(R.id.recyclerViewPinLen)
    RecyclerView recyclerViewPinLen;
    @BindView(R.id.m_swipe_layout)
    SmartRefreshLayout mSwipeLayout;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        manager=new LinearLayoutManager(this);
        recyclerViewPinLen.setLayoutManager(manager);

        initDate();
    }

    WaitPorgressDialog dialog;

    private void initDate() {
        dialog = new WaitPorgressDialog(this);
        dialog.isShowing();

        RetrofitManager.getInstance().getDataServer().mygift(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<GiftBean>() {
                    @Override
                    public void onResponse(Call<GiftBean> call, Response<GiftBean> response) {
                        Log.e("-------","1:"+response.body().getCode());
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body().getCode() == 200) {
                                Log.e("-------","1:"+response.body().getData().size());
                                giftBeanList = response.body().getData();
                                giftAdapter = new GiftAdapter(GiftActivity.this, giftBeanList, myclick);
                                recyclerViewPinLen.setAdapter(giftAdapter);
                                LinearItemDecoration itemDecoration=new LinearItemDecoration();
                                itemDecoration.setColor(getResources().getColor(R.color.backgroudcolor));
                                recyclerViewPinLen.addItemDecoration(itemDecoration);

                            }

                        } else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<GiftBean> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
    GiftAdapter.Myclick myclick=new GiftAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {

        }
    };
}
