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
import com.jarhero790.eub.message.adapter.ZanAdapter;
import com.jarhero790.eub.message.bean.ZanBean;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.jarhero790.eub.widget.WaitPorgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZanActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recyclerViewZan)
    RecyclerView recyclerViewZan;

    ZanAdapter zanAdapter;
    List<ZanBean.DataBean> giftBeanList = new ArrayList<>();
    LinearLayoutManager manager;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zan);
        CommonUtil.setStatusBarTransparent(this);
        ButterKnife.bind(this);
        manager = new LinearLayoutManager(this);
        recyclerViewZan.setLayoutManager(manager);
        initDate();
    }

    WaitPorgressDialog dialog;

    private void initDate() {
        dialog = new WaitPorgressDialog(this);
        dialog.isShowing();

        RetrofitManager.getInstance().getDataServer().myzan(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<ZanBean>() {
                    @Override
                    public void onResponse(Call<ZanBean> call, Response<ZanBean> response) {
                        Log.e("-------", "1:" + response.body().getCode());
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body().getCode() == 200) {
                                if (response.body().getData().size() > 0) {
                                    recyclerViewZan.setVisibility(View.VISIBLE);
                                    nodingdan.setVisibility(View.GONE);
                                    Log.e("-------", "1:" + response.body().getData().size());
                                    giftBeanList = response.body().getData();
                                    zanAdapter = new ZanAdapter(ZanActivity.this, giftBeanList, myclick);
                                    recyclerViewZan.setAdapter(zanAdapter);
                                    LinearItemDecoration itemDecoration = new LinearItemDecoration();
                                    itemDecoration.setColor(getResources().getColor(R.color.backgroudcolor));
                                    recyclerViewZan.addItemDecoration(itemDecoration);
                                } else {
                                    recyclerViewZan.setVisibility(View.GONE);
                                    nodingdan.setVisibility(View.VISIBLE);
                                }


                            }

                        } else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ZanBean> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    ZanAdapter.Myclick myclick = new ZanAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {

        }
    };
}
