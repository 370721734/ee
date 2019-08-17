package com.jarhero790.eub.ui.mine.child;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.base.BaseMVPCompatFragment;
import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.bean.User;
import com.jarhero790.eub.contract.mine.MineMainContract;
import com.jarhero790.eub.eventbus.MessageEventUser;
import com.jarhero790.eub.presenter.mine.MineMainPresenter;
import com.jarhero790.eub.ui.mine.FragmentLike;
import com.jarhero790.eub.ui.mine.FragmentZuoping;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;

import butterknife.BindView;


public class MineFragment extends BaseMVPCompatFragment<MineMainContract.MineMainPresenter>
        implements MineMainContract.IMineMainView {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.textViewNickName)
    TextView textViewNickName;


    public static MineFragment  fragment;



    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        if(fragment==null){
            fragment = new MineFragment();
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }


    /**
     * 在com.zuanshitv.app.activity.UserNameLoginActivity中登录成功后获取到的数据发送
     * 在这里接收
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEventUser messageEvent) {
        User user=messageEvent.getMessage().getData();
        textViewNickName.setText(user.getNickname());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getTabList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }







    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        tabLayout.addTab(tabLayout.newTab().setText("作 品"));//给TabLayout添加Tab
        tabLayout.addTab(tabLayout.newTab().setText("喜 欢"));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 50, 50);
            }
        });

        tabLayout.getTabAt(0).select();
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, FragmentZuoping.newInstance()); //连接TabLayout下的Fragment需要放置的位置
        transaction.commit();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fm = getChildFragmentManager();
                //开启事务
                FragmentTransaction transaction;
                switch (tab.getPosition()){
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

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return MineMainPresenter.newInstance();
    }

    @Override
    public void showTabList(String[] tabs) {

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

