package com.jarhero790.eub.message.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.GuangZuAdapter;
import com.jarhero790.eub.message.bean.FenSiTBean;
import com.jarhero790.eub.message.bean.GuangZuBean;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.record.CustomProgressDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuangZuActivity extends AppCompatActivity {


    List<GuangZuBean.DataBean> list = new ArrayList<>();
    GuangZuAdapter adapter;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
    @BindView(R.id.wangluoyichang)
    RelativeLayout wangluoyichang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guang_zu);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);

        initDate();
    }

    CustomProgressDialog dialog = new CustomProgressDialog();
    retrofit2.Call<GuangZuBean> calls=null;
    private void initDate() {
        dialog.createLoadingDialog(this, "正在加载...");
        dialog.show();
        RetrofitManager.getInstance().getDataServer().mylike(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<GuangZuBean>() {
                    @Override
                    public void onResponse(Call<GuangZuBean> call, Response<GuangZuBean> response) {
                        calls=call;
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body().getCode() == 200) {
                                if (list.size() > 0) {
                                    list.clear();
                                    nodingdan.setVisibility(View.GONE);
                                    wangluoyichang.setVisibility(View.GONE);
                                    rlv.setVisibility(View.VISIBLE);
                                    list = response.body().getData();
                                    GridLayoutManager manager = new GridLayoutManager(GuangZuActivity.this, 1);
                                    rlv.setLayoutManager(manager);
                                    adapter = new GuangZuAdapter(GuangZuActivity.this, list, myclick, touclick, speak);
                                    rlv.setAdapter(adapter);

                                } else {
                                    nodingdan.setVisibility(View.VISIBLE);
                                    wangluoyichang.setVisibility(View.GONE);
                                    rlv.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            dialog.dismiss();
                            nodingdan.setVisibility(View.GONE);
                            wangluoyichang.setVisibility(View.VISIBLE);
                            rlv.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<GuangZuBean> call, Throwable t) {
                        dialog.dismiss();
                        nodingdan.setVisibility(View.GONE);
                        wangluoyichang.setVisibility(View.VISIBLE);
                        rlv.setVisibility(View.GONE);
                    }
                });
    }



    GuangZuAdapter.Myclick myclick = new GuangZuAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            //关注

        }
    };
    GuangZuAdapter.Myclick touclick = new GuangZuAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            //个人信息
        }
    };
    GuangZuAdapter.Myclick speak = new GuangZuAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            //聊天
            if (list.get(position).getIs_likeEach() == 1) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(GuangZuActivity.this, list.get(position).getRong_id() + "", list.get(position).getNickname());
                }
            } else {
                Toast.makeText(GuangZuActivity.this, "要互相关注后才能聊天", Toast.LENGTH_SHORT).show();
            }
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
