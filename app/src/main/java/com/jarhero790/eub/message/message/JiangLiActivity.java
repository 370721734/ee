package com.jarhero790.eub.message.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.JiangLiAdapter;
import com.jarhero790.eub.message.bean.FenSiTBean;
import com.jarhero790.eub.message.bean.JiangLiBean;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.record.CustomProgressDialog;
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

public class JiangLiActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewZan)
    RecyclerView recyclerViewZan;


    JiangLiAdapter jiangLiAdapter;
    List<JiangLiBean.DataBean> giftBeanList = new ArrayList<>();
    LinearLayoutManager manager;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
    @BindView(R.id.wangluoyichang)
    RelativeLayout wangluoyichang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiang_li);
        CommonUtil.setStatusBarTransparent(this);
        ButterKnife.bind(this);
        manager = new LinearLayoutManager(this);
        recyclerViewZan.setLayoutManager(manager);
        initDate();
    }

    CustomProgressDialog dialog = new CustomProgressDialog();
    retrofit2.Call<JiangLiBean> calls=null;

    private void initDate() {
        dialog.createLoadingDialog(this, "正在加载...");
        dialog.show();
        RetrofitManager.getInstance().getDataServer().myreward(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<JiangLiBean>() {
                    @Override
                    public void onResponse(Call<JiangLiBean> call, Response<JiangLiBean> response) {
//                        Log.e("-------", "1:" + response.body().getCode());
                        calls=call;
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body().getCode() == 200) {
                                if (response.body().getData().size() > 0) {
                                    giftBeanList.clear();
                                    recyclerViewZan.setVisibility(View.VISIBLE);
                                    nodingdan.setVisibility(View.GONE);
                                    wangluoyichang.setVisibility(View.GONE);
//                                    Log.e("-------", "1:" + response.body().getData().size());
                                    giftBeanList = response.body().getData();
                                    jiangLiAdapter = new JiangLiAdapter(JiangLiActivity.this, giftBeanList, myclick);
                                    recyclerViewZan.setAdapter(jiangLiAdapter);
                                    LinearItemDecoration itemDecoration = new LinearItemDecoration();
                                    itemDecoration.setColor(getResources().getColor(R.color.backgroudcolor));
                                    recyclerViewZan.addItemDecoration(itemDecoration);
                                } else {
                                    recyclerViewZan.setVisibility(View.GONE);
                                    wangluoyichang.setVisibility(View.GONE);
                                    nodingdan.setVisibility(View.VISIBLE);
                                }


                            }

                        } else {
                            dialog.dismiss();
                            recyclerViewZan.setVisibility(View.GONE);
                            wangluoyichang.setVisibility(View.VISIBLE);
                            nodingdan.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<JiangLiBean> call, Throwable t) {
                        dialog.dismiss();
                        recyclerViewZan.setVisibility(View.GONE);
                        wangluoyichang.setVisibility(View.VISIBLE);
                        nodingdan.setVisibility(View.GONE);
                    }
                });
    }


    JiangLiAdapter.Myclick myclick = new JiangLiAdapter.Myclick() {
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
}
