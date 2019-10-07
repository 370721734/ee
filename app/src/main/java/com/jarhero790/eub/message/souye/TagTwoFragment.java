package com.jarhero790.eub.message.souye;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dueeeke.videoplayer.player.VideoViewManager;
import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.MyPagerAdapter;
import com.jarhero790.eub.ui.souye.child.SouyeFragment;
import com.jarhero790.eub.ui.souye.child.SouyeThreeFragment;
import com.jarhero790.eub.ui.souye.child.SouyeTwoFragment;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

public class TagTwoFragment extends SupportFragment implements ViewPager.OnPageChangeListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private VideoViewManager mVideoViewManager;


    private static TagTwoFragment instance = null;

    public static TagTwoFragment newInstance() {
        if (instance == null) {
            instance = new TagTwoFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoViewManager = VideoViewManager.instance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.activity_list_fragment_view_pager, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mTabLayout =view. findViewById(R.id.tl);
        mViewPager = view.findViewById(R.id.vp);
        mViewPager.addOnPageChangeListener(this);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titles.add("List1");
        titles.add("List2");
        titles.add("List3");

//        for (int i = 0; i < titles.size(); i++) {
//            mFragmentList.add(SouyeFragment.newInstance());
//        }
        mFragmentList.add(SouyeFragment.newInstance());
        mFragmentList.add(SouyeTwoFragment.newInstance());
        mFragmentList.add(SouyeThreeFragment.newInstance());

        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mFragmentList, titles));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onPause() {
        super.onPause();
        mVideoViewManager.release();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (!mVideoViewManager.onBackPressed()){
            return super.onBackPressedSupport();
        }
      return false;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mVideoViewManager.release();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
