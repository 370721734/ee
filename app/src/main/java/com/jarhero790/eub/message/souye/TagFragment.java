package com.jarhero790.eub.message.souye;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jarhero790.eub.R;

import io.rong.imkit.fragment.BaseFragment;

/**
 * 标签页面
 */
public class TagFragment extends BaseFragment {


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onRestoreUI() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_souye, null);
        return view;
    }

    //    @Override
//    public View initView() {
//        View view = View.inflate(mContext, R.layout.fragment_list, null);
//        lv_left = (ListView) view.findViewById(R.id.lv_left);
//        rv_right = (RecyclerView) view.findViewById(R.id.rv_right);
//
//        return view;
//    }

//    @Override
//    public void initData() {
//        super.initData();
//
//        //联网请求
//        getDataFromNet(urls[0]);
//    }
}
