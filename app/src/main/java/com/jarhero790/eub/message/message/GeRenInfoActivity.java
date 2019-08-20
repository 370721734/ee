package com.jarhero790.eub.message.message;

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

import com.jarhero790.eub.R;
import com.jarhero790.eub.ui.mine.FragmentLike;
import com.jarhero790.eub.ui.mine.FragmentZuoping;
import com.jarhero790.eub.utils.CommonUtil;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_ren_info);
        CommonUtil.setStatusBarTransparent(this);
        ButterKnife.bind(this);

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
        transaction.replace(R.id.container, FragmentZuoping.newInstance()); //连接TabLayout下的Fragment需要放置的位置
        transaction.commit();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fm =getSupportFragmentManager();
                //开启事务
                FragmentTransaction transaction;
                switch (tab.getPosition()) {
                    case 0:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.container, FragmentZuoping.newInstance()); //连接TabLayout下的Fragment需要放置的位置
                        transaction.commit();
                        break;

                    case 1:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.container, FragmentLike.newInstance()); //连接TabLayout下的Fragment需要放置的位置
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

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

}
