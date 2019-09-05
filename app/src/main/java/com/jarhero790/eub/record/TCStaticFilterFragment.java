package com.jarhero790.eub.record;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jarhero790.eub.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hans on 2017/11/6.
 * 静态滤镜的Fragment
 */
public class TCStaticFilterFragment extends Fragment implements BaseRecyclerAdapter.OnItemClickListener {
    private static final int[] FILTER_ARR = {
            R.drawable.filter_biaozhun, R.drawable.filter_yinghong,
            R.drawable.filter_yunshang, R.drawable.filter_chunzhen,
            R.drawable.filter_bailan, R.drawable.filter_yuanqi,
            R.drawable.filter_chaotuo, R.drawable.filter_xiangfen,


            R.drawable.filter_langman, R.drawable.filter_qingxin,
            R.drawable.filter_weimei, R.drawable.filter_fennen,
            R.drawable.filter_huaijiu, R.drawable.filter_landiao,
            R.drawable.filter_qingliang, R.drawable.filter_rixi};

    private List<Integer> mFilterList;
    private RecyclerView mRvFilter;
    private StaticFilterAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_static_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFilterList = new ArrayList<Integer>();
        mFilterList.add(R.mipmap.orginal);

        mFilterList.add(R.mipmap.biaozhun);
        mFilterList.add(R.mipmap.yinghong);
        mFilterList.add(R.mipmap.yunshang);
        mFilterList.add(R.mipmap.chunzhen);
        mFilterList.add(R.mipmap.bailan);
        mFilterList.add(R.mipmap.yuanqi);
        mFilterList.add(R.mipmap.chaotuo);
        mFilterList.add(R.mipmap.xiangfen);

        mFilterList.add(R.mipmap.langman);
        mFilterList.add(R.mipmap.qingxin);
        mFilterList.add(R.mipmap.weimei);
        mFilterList.add(R.mipmap.fennen);
        mFilterList.add(R.mipmap.huaijiu);
        mFilterList.add(R.mipmap.landiao);
        mFilterList.add(R.mipmap.qingliang);
        mFilterList.add(R.mipmap.rixi);

        mRvFilter = (RecyclerView) view.findViewById(R.id.filter_rv_list);
        mRvFilter.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new StaticFilterAdapter(mFilterList);
        mAdapter.setOnItemClickListener(this);
        mRvFilter.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Bitmap bitmap = null;
        if (position == 0) {
            bitmap = null;  // 没有滤镜
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), FILTER_ARR[position - 1]);
        }
        mAdapter.setCurrentSelectedPos(position);
        // 设置滤镜图片
        TCVideoEditerWrapper.getInstance().getEditer().setFilter(bitmap);
    }

}
