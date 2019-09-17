package com.jarhero790.eub.message.message;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//别、个人信息页面
@SuppressLint("RestrictedApi")
public class GeRenInfoActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.textViewNickName)
    TextView textViewNickName;
    @BindView(R.id.tv_tvhao)
    TextView tvTvhao;
    @BindView(R.id.iv_userimage)
    CircleImageView ivUserimage;
    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;
    @BindView(R.id.fensi)
    TextView fensi;
    @BindView(R.id.tv_huozai)
    TextView tvHuozai;
    @BindView(R.id.dingwei)
    LinearLayout dingwei;
    @BindView(R.id.tv_qiandao)
    TextView tvQiandao;
    @BindView(R.id.tv_memo)
    TextView tvMemo;
    @BindView(R.id.vp_shuffling)
    LinearLayout vpShuffling;
    @BindView(R.id.rel)
    LinearLayout rel;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.iv_bot1)
    View ivBot1;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.iv_bot2)
    View ivBot2;
    @BindView(R.id.ll2)
    LinearLayout ll2;

    //    private int uid;
    private String userid;

    @Override
    protected void onResume() {
        super.onResume();
//        initDate();
    }

//    CustomProgressDialog dialog = new CustomProgressDialog();

    private GeRenBean.DataBean bean;

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void geren(GeRenBean.DataBean beans) {
        if (beans != null) {
            setBean(beans);

//            Log.e("-----------", "什么" + beans.getFensi());//有了
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_ren_info);
        CommonUtil.setStatusBarTransparent(this);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
//        uid = intent.getIntExtra("userid", 5044);
        userid = intent.getStringExtra("userid");
//        Log.e("----------","eee"+userid);
//        GeRenBean bean=intent.getParcelableExtra("bean");
//        Log.e("---------1", bean.toString());
//        if (getBean()!=null && getBean().getData()!=null && getBean().getCode() == 200) {
//            if (bean.getData() != null && bean.getData().getUser() != null) {
//                Log.e("-----2", getBean().getData().getFensi() + " " + getBean().getData().getUser().getNickname());
//                Log.e("---------3", getBean().getData().toString());
//                Log.e("-------4", getBean().getData().getUser().toString());
//
//                textViewNickName.setText(getBean().getData().getUser().getNickname());
//                tvTvhao.setText("钻视TV号:" + getBean().getData().getUser().getId());
//                Glide.with(GeRenInfoActivity.this).load(Api.TU + getBean().getData().getUser().getHeadimgurl())
//                        .apply(new RequestOptions().placeholder(R.mipmap.edit_tou_icon).error(R.mipmap.edit_tou_icon)).into(ivUserimage);
//
//                tvGuanzhu.setText("" + getBean().getData().getLike());
//                fensi.setText("" + getBean().getData().getFensi());
//                tvHuozai.setText("" + getBean().getData().getZan());
//                tvMemo.setText(getBean().getData().getUser().getSign());
//            }
//        }


        initDate(userid);
        tabLayout.addTab(tabLayout.newTab().setText("作 品"));//给TabLayout添加Tab
        tabLayout.addTab(tabLayout.newTab().setText("喜 欢"));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 80, 80);
            }
        });

        tabLayout.getTabAt(0).select();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, FragmentZuopingGeRen.newInstance()); //连接TabLayout下的Fragment需要放置的位置
        transaction.commit();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fm = getSupportFragmentManager();
                //开启事务
                FragmentTransaction transaction;
                switch (tab.getPosition()) {
                    case 0:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.container, FragmentZuopingGeRen.newInstance()); //连接TabLayout下的Fragment需要放置的位置
                        transaction.commit();
                        break;

                    case 1:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.container, FragmentLikeGeRen.newInstance()); //连接TabLayout下的Fragment需要放置的位置
                        transaction.commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private Dialog dialog;

    private void initDate(String userid) {
        dialog = new Dialog(this, R.style.progress_dialog);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        RetrofitManager.getInstance().getDataServer().getgerenuserinfos(SharePreferenceUtil.getToken(AppUtils.getContext()), userid)
                .enqueue(new Callback<GeRenBean>() {
                    @Override
                    public void onResponse(Call<GeRenBean> call, Response<GeRenBean> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body() != null && response.body().getCode() == 200) {
                                GeRenBean bean = response.body();
//                                Log.e("---------1", bean.toString());
                                if (bean.getData() != null && bean.getData().getUser() != null) {
//                                    Log.e("-----2", bean.getData().getFensi() + " " + bean.getData().getUser().getNickname());
//                                    Log.e("---------3", bean.getData().toString());
//                                    Log.e("-------4", bean.getData().getUser().toString());

                                    textViewNickName.setText(bean.getData().getUser().getNickname());
                                    tvTvhao.setText("钻视TV号:" + bean.getData().getUser().getId());
                                    if (bean.getData().getUser().getHeadimgurl().startsWith("http")) {
                                        Glide.with(GeRenInfoActivity.this).load(bean.getData().getUser().getHeadimgurl())
                                                .apply(new RequestOptions().placeholder(R.mipmap.about_icon).error(R.mipmap.about_icon)).into(ivUserimage);
                                    } else {
                                        Glide.with(GeRenInfoActivity.this).load(Api.TU + bean.getData().getUser().getHeadimgurl())
                                                .apply(new RequestOptions().placeholder(R.mipmap.about_icon).error(R.mipmap.about_icon)).into(ivUserimage);
                                    }


                                    tvGuanzhu.setText("" + bean.getData().getLike());
                                    fensi.setText("" + bean.getData().getFensi());
                                    tvHuozai.setText("" + bean.getData().getZan());
                                    tvMemo.setText(bean.getData().getUser().getSign());
                                }
                            }
                        } else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<GeRenBean> call, Throwable t) {
                        dialog.dismiss();
                    }
                });

    }


    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        LinearLayout llTab = null;
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            llTab = (LinearLayout) tabStrip.get(tabs);
            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    public GeRenBean.DataBean getBean() {
        return bean;
    }

    public void setBean(GeRenBean.DataBean bean) {
        this.bean = bean;
    }

    @OnClick({R.id.back, R.id.ll1, R.id.ll2})
    public void onViewClicked(View view) {
        FragmentManager fm = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll1:
                ivBot1.setVisibility(View.VISIBLE);
                ivBot2.setVisibility(View.INVISIBLE);
//                vp.setCurrentItem(0);

                transaction = fm.beginTransaction();
                transaction.replace(R.id.container, FragmentZuopingGeRen.newInstance()); //连接TabLayout下的Fragment需要放置的位置
                transaction.commit();
                break;
            case R.id.ll2:
                ivBot1.setVisibility(View.INVISIBLE);
                ivBot2.setVisibility(View.VISIBLE);
//                vp.setCurrentItem(1);

                transaction = fm.beginTransaction();
                transaction.replace(R.id.container, FragmentLikeGeRen.newInstance()); //连接TabLayout下的Fragment需要放置的位置
                transaction.commit();
                break;
        }
    }
}
