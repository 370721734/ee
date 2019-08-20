package com.jarhero790.eub.message.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.bean.GuangZuBean;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuangZuActivity extends AppCompatActivity {


    List<GuangZuBean.DataBean> list = new ArrayList<>();
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guang_zu);
        ButterKnife.bind(this);

        initDate();
    }

    private void initDate() {

        RetrofitManager.getInstance().getDataServer().mylike(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<GuangZuBean>() {
                    @Override
                    public void onResponse(Call<GuangZuBean> call, Response<GuangZuBean> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getCode() == 200) {
                                list = response.body().getData();
                                if (list.size() > 0) {

                                } else {

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GuangZuBean> call, Throwable t) {

                    }
                });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
