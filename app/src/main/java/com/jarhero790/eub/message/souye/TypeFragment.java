package com.jarhero790.eub.message.souye;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.R;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.bean.HiddBean;
import com.jarhero790.eub.message.bean.souyelookone;
import com.jarhero790.eub.message.bean.souyelookthree;
import com.jarhero790.eub.message.bean.souyelooktwo;
import com.jarhero790.eub.ui.message.child.MessageFragment;
import com.jarhero790.eub.ui.mine.child.MineFragment;
import com.jarhero790.eub.ui.souye.child.SouyeFourFragment;
import com.jarhero790.eub.ui.souye.child.SouyeFragment;
import com.jarhero790.eub.ui.souye.child.SouyeThreeFragment;
import com.jarhero790.eub.ui.souye.child.SouyeTwoFragment;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imkit.fragment.BaseFragment;
import me.yokeyword.fragmentation.SupportFragment;

// 分类页面
public class TypeFragment extends SupportFragment {
    @BindView(R.id.tuijian)
    TextView textViewTuijian;
    @BindView(R.id.zuixin)
    TextView textViewZuixin;
    @BindView(R.id.changshipin)
    TextView textViewChangshipin;
    @BindView(R.id.fl_type)
    FrameLayout flType;
    Unbinder unbinder;
    //    private SegmentTabLayout segmentTabLayout;
    private ImageView iv_type_search;
    private FrameLayout fl_type;
    private List<Fragment> fragmentList;
    private Fragment tempFragment;
    public SouyeFragment listFragment;
    public SouyeTwoFragment tagFragment;
    public SouyeThreeFragment fbcFragment;
//    public SouyeFourFragment fbcFragment;

//    @Override
//    public boolean onBackPressed() {
//        return false;
//    }
//
//    @Override
//    public void onRestoreUI() {
//
//    }

    GlobalApplication app;

    private static TypeFragment instance = null;

    public static TypeFragment newInstance() {
        if (instance == null) {
            instance = new TypeFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_type, null);
        fl_type = (FrameLayout) view.findViewById(R.id.fl_type);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        currentIndex = 0;
        app = (GlobalApplication) getActivity().getApplication();
        app.setIslookone(true);
        app.setIslooktwo(false);
        app.setIslookthree(false);
        initFragment();
//        String[] titles = {"分类", "标签"};

//        if (savedInstanceState!=null){
//            Log.e("-----------dd--",""+savedInstanceState.getInt("currentIndex"));
//        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putInt("currentIndex",currentIndex);
//        Log.e("-------------ff",""+currentIndex);
//        super.onSaveInstanceState(outState);
//
//    }


    @Override
    public void onResume() {
        super.onResume();

//        switchFragment(tempFragment, fragmentList.get(0));


        showFragment();
//        Log.e("-----------ee-", "type_onResume"+currentIndex);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Log.e("--------typefragment", "隐藏");
            EventBus.getDefault().post(new HiddBean("true"));
        } else {
            Log.e("--------typefragment", "可见");
            EventBus.getDefault().post(new HiddBean("false"));
        }
    }

    public void switchFragment(Fragment fromFragment, Fragment nextFragment) {

        if (tempFragment != nextFragment) {

            tempFragment = nextFragment;

            if (nextFragment != null) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }

                    transaction.add(R.id.fl_type, nextFragment, "tagFragment").commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }

                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        listFragment = SouyeFragment.newInstance();
        tagFragment = SouyeTwoFragment.newInstance();
        fbcFragment = SouyeThreeFragment.newInstance();
//        fbcFragment = SouyeFourFragment.newInstance();
        fragmentList.add(listFragment);
        fragmentList.add(tagFragment);
        fragmentList.add(fbcFragment);

//        switchFragment(tempFragment, fragmentList.get(0));

        showFragment();
    }

    private int currentIndex = 0;
    private Fragment currentFragment = new Fragment();
    private FragmentManager fragmentManager;

    private void showFragment() {

        fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //如果之前没有添加过
        if (!fragmentList.get(currentIndex).isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.fl_type, fragmentList.get(currentIndex), "" + currentIndex);  //第三个参数为添加当前的fragment时绑定一个tag

        } else {
            transaction
                    .hide(currentFragment)
                    .show(fragmentList.get(currentIndex));
        }

        currentFragment = fragmentList.get(currentIndex);

        transaction.commit();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ee(HiddBean bean) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ee1(souyelookone bean) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ee2(souyelooktwo bean) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ee3(souyelookthree bean) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.e("-------------","???????????");
//        removeneedfragment();
    }

    private void removeneedfragment() {
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment fragment = fragmentList.get(i);
            if (fragment instanceof SouyeFragment || fragment instanceof SouyeTwoFragment || fragment instanceof SouyeThreeFragment) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.remove(fragment);
                ft.commit();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tuijian, R.id.zuixin, R.id.changshipin, R.id.search_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tuijian:
//                switchFragment(tempFragment, fragmentList.get(0));
//                Log.e("---------", "1");
                currentIndex = 0;
                textViewTuijian.setBackgroundResource(R.drawable.button_shape1);
                textViewTuijian.setTextColor(Color.parseColor("#0E0E0E"));
                textViewZuixin.setTextColor(Color.parseColor("#EFEDED"));
                textViewChangshipin.setTextColor(Color.parseColor("#EFEDED"));
                textViewZuixin.setBackgroundResource(0);
                textViewChangshipin.setBackgroundResource(0);
                EventBus.getDefault().post(new souyelookone("ok"));
                EventBus.getDefault().post(new souyelooktwo("two"));
                EventBus.getDefault().post(new souyelookthree("three"));
                app.setIslookone(true);
                app.setIslooktwo(false);
                app.setIslookthree(false);
                break;
            case R.id.zuixin:
//                switchFragment(tempFragment, fragmentList.get(1));
//                Log.e("---------", "2");
                currentIndex = 1;
                textViewZuixin.setBackgroundResource(R.drawable.button_shape1);
                textViewTuijian.setTextColor(Color.parseColor("#EFEDED"));
                textViewZuixin.setTextColor(Color.parseColor("#0E0E0E"));
                textViewChangshipin.setTextColor(Color.parseColor("#EFEDED"));
                textViewTuijian.setBackgroundResource(0);
                textViewChangshipin.setBackgroundResource(0);


                EventBus.getDefault().post(new souyelookone("one"));
                EventBus.getDefault().post(new souyelooktwo("ok"));
                EventBus.getDefault().post(new souyelookthree("three"));
                app.setIslookone(false);
                app.setIslooktwo(true);
                app.setIslookthree(false);
                break;
            case R.id.changshipin:
//                switchFragment(tempFragment, fragmentList.get(2));
//                Log.e("---------", "3");
                currentIndex = 2;
                textViewChangshipin.setBackgroundResource(R.drawable.button_shape1);
                textViewTuijian.setTextColor(Color.parseColor("#EFEDED"));
                textViewZuixin.setTextColor(Color.parseColor("#EFEDED"));
                textViewChangshipin.setTextColor(Color.parseColor("#0E0E0E"));
                textViewTuijian.setBackgroundResource(0);
                textViewZuixin.setBackgroundResource(0);

                EventBus.getDefault().post(new souyelooktwo("two"));
                EventBus.getDefault().post(new souyelookone("one"));
                EventBus.getDefault().post(new souyelookthree("ok"));
                app.setIslookone(false);
                app.setIslooktwo(false);
                app.setIslookthree(true);
                break;
            case R.id.search_icon:
                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")) {
                    startActivity(new Intent(getActivity(), LoginNewActivity.class));
                } else {
                    Intent sear = new Intent(getActivity(), SearchActivity.class);
                    startActivity(sear);
                }
                break;
        }
        showFragment();
    }
}
