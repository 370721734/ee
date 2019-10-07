package com.jarhero790.eub.message.message;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.bean.GeRenBean;
import com.jarhero790.eub.message.net.RetrofitManager;

import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tou_image)
    CircleImageView touImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.userinfo)
    RelativeLayout userinfo;
    @BindView(R.id.jiubao)
    RelativeLayout jiubao;
    @BindView(R.id.lahei)
    RelativeLayout lahei;
    @BindView(R.id.tv_zhuanhao)
    TextView tvZhuanhao;

    private String userid;//被拉黑用户id
    private String userimg;
    private String username;

    GeRenBean bean;




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object myEvent) {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        initDate(userid);
        if (userid != null)
            tvZhuanhao.setText("钻视tv号：" + userid);
//        username = intent.getStringExtra("username");
//        if (username != null)
//            tvName.setText(username);
//        userimg = intent.getStringExtra("userimg");
//        if (userimg != null)
//            Glide.with(this).load(Api.TU + userimg).apply(new RequestOptions().placeholder(R.mipmap.edit_tou_icon).error(R.mipmap.edit_tou_icon)).into(touImage);


    }
//    CustomProgressDialog dialog=new CustomProgressDialog();
    private void initDate(String uid) {
//        dialog.createLoadingDialog(this,"正在加载...");
//        dialog.show();
        RetrofitManager.getInstance().getDataServer().getgerenuserinfos(SharePreferenceUtil.getToken(AppUtils.getContext()), uid)
                .enqueue(new Callback<GeRenBean>() {
                    @Override
                    public void onResponse(Call<GeRenBean> call, Response<GeRenBean> response) {
                        if (response.isSuccessful()) {
//                            dialog.dismiss();

//                            Log.e("---------1", bean.toString());
                            if (response.body().getCode() == 200) {

                                bean = response.body();
                                if (bean.getData() != null && bean.getData().getUser() != null) {
//                                    Log.e("-----2", bean.getData().getFensi() + " " + bean.getData().getUser().getNickname());
//                                    Log.e("---------3", bean.getData().toString());
//                                    Log.e("-------4", bean.getData().getUser().toString());


                                    tvName.setText(bean.getData().getUser().getNickname());
//                                    tvTvhao.setText("钻视TV号:" + bean.getData().getUser().getId());
                                    Glide.with(MoreActivity.this).load( bean.getData().getUser().getHeadimgurl())
                                            .apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo).error(R.mipmap.zuanshi_logo)).into(touImage);

//                                    tvGuanzhu.setText("" + bean.getData().getLike());
//                                    fensi.setText("" + bean.getData().getFensi());
//                                    tvHuozai.setText("" + bean.getData().getZan());
//                                    tvMemo.setText(bean.getData().getUser().getSign());
                                }
                            }
                        }else {
//                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<GeRenBean> call, Throwable t) {
//                        dialog.dismiss();
                    }
                });

    }

    @OnClick({R.id.back, R.id.userinfo, R.id.jiubao, R.id.lahei})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.userinfo:
                if (bean==null)
                    return;
                if (userid==null)
                    return;
                EventBus.getDefault().post(bean.getData());
                startActivity(new Intent(this, GeRenInfoActivity.class).putExtra("userid", userid).putExtra("bean",bean));
                break;
            case R.id.jiubao:
                startActivity(new Intent(this, JiuBaoActivity.class).putExtra("userid", userid));
                break;
            case R.id.lahei:
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.loading_dialog);
                View layout = View.inflate(this, R.layout.item_more_lahei, null);
                TextView can = layout.findViewById(R.id.cancle);
                TextView sub = layout.findViewById(R.id.submit);
                builder.setView(layout);

                Dialog dialog = builder.create();
                can.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        laheiuser();
                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    private void laheiuser() {
        if (userid != null) {
            RetrofitManager.getInstance().getDataServer().blacklist(SharePreferenceUtil.getToken(AppUtils.getContext()), userid, "1")
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    String json = response.body().string();
                                    Log.e("----------la-", json);
                                    JSONObject object = new JSONObject(json);
                                    int code = object.optInt("code");
                                    String msg = object.optString("msg");
                                    if (code == 200) {
                                        Toast.makeText(MoreActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MoreActivity.this, msg, Toast.LENGTH_SHORT).show();
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
    }
}
