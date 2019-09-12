package com.jarhero790.eub.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.LikeAdapter;
import com.jarhero790.eub.message.bean.LikeBean;
import com.jarhero790.eub.message.bean.MyFaBuBean;
import com.jarhero790.eub.message.message.PinLenActivity;
import com.jarhero790.eub.message.my.PlayVideoActivity;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;

import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentLike extends SupportFragment {
    //    @BindView(R.id.rlv)
    RecyclerView rlv;
    Unbinder unbinder;
//    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
//    @BindView(R.id.wangluoyichang)
    RelativeLayout wangluoyichang;
    SmartRefreshLayout mSwipeLayout;
    private View view;

    private static FragmentLike instance = null;

    ArrayList<MyFaBuBean.DataBean> list = new ArrayList<>();
    ArrayList<MyFaBuBean.DataBean> itemlist = new ArrayList<>();
    LikeAdapter adapter;
    private int page = 1;

    public static FragmentLike newInstance() {
        if (instance == null) {
            instance = new FragmentLike();
        }
        return instance;
    }

    public FragmentLike() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Toast.makeText(getContext(), "可见", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "不可见", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_like, container, false);
        rlv = view.findViewById(R.id.rlv);
        nodingdan=view.findViewById(R.id.nodingdan);
        wangluoyichang=view.findViewById(R.id.wangluoyichang);
        mSwipeLayout=view.findViewById(R.id.m_swipe_layout);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        rlv.setLayoutManager(manager);
        LinearItemDecoration linearItemDecoration = new LinearItemDecoration();
        linearItemDecoration.setSpanSpace(10);
        linearItemDecoration.setColor(getResources().getColor(R.color.backgroudcolor));
        rlv.addItemDecoration(linearItemDecoration);

        page=1;
        initDate();


        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                initDate();
                mSwipeLayout.finishRefresh(1000);

            }
        });
        mSwipeLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
//                page++;
                page = 1;
                initDate();
                mSwipeLayout.finishLoadMore(1000);

            }
        });

    }

//    CustomProgressDialog dialog = new CustomProgressDialog();
private Dialog dialog;
    retrofit2.Call<MyFaBuBean> calls=null;
    private void initDate() {
//        dialog.createLoadingDialog(getActivity(), "正在加载...");
//        dialog.show();
//        Log.e("------------------page=",""+page);
        dialog = new Dialog(getActivity(), R.style.progress_dialog);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        RetrofitManager.getInstance().getDataServer().zanvideo(SharePreferenceUtil.getToken(AppUtils.getContext()),page)
                .enqueue(new Callback<MyFaBuBean>() {
                    @Override
                    public void onResponse(Call<MyFaBuBean> call, Response<MyFaBuBean> response) {
                        calls=call;
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body()!=null && response.body().getData()!=null){
                                Log.e("--------------","size="+response.body().getData().size());
                                itemlist.clear();
                                mSwipeLayout.setVisibility(View.VISIBLE);
                                rlv.setVisibility(View.VISIBLE);
                                nodingdan.setVisibility(View.GONE);
                                wangluoyichang.setVisibility(View.GONE);
                                itemlist = response.body().getData();



                                if (page==1){
                                    list.clear();
                                    list.addAll(itemlist);
                                    adapter = new LikeAdapter(getActivity(), list, myclickdele, myclicktu);
                                    rlv.setAdapter(adapter);
//                                    adapter.setList(list);
                                }else {
                                    list.addAll(itemlist);
//                                    Log.e("-------------","刷新了没");
//                                    adapter.setList(list);
                                    adapter.notifyDataSetChanged();
                                }


                            }else {
                                if (page==1){
                                    rlv.setVisibility(View.GONE);
                                    nodingdan.setVisibility(View.VISIBLE);
                                    wangluoyichang.setVisibility(View.GONE);
                                    mSwipeLayout.setVisibility(View.GONE);
                                }else {
//                                    Toast.makeText(getActivity(), "没有相关数据", Toast.LENGTH_SHORT).show();
                                }


                            }
                        } else {
                            dialog.dismiss();
                            rlv.setVisibility(View.GONE);
                            nodingdan.setVisibility(View.GONE);
                            wangluoyichang.setVisibility(View.VISIBLE);
                            mSwipeLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MyFaBuBean> call, Throwable t) {
                        dialog.dismiss();
                        rlv.setVisibility(View.GONE);
                        nodingdan.setVisibility(View.GONE);
                        wangluoyichang.setVisibility(View.VISIBLE);
                        mSwipeLayout.setVisibility(View.GONE);
                    }
                });
    }

    LikeAdapter.Myclick myclickdele = new LikeAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {
            Log.e("-------1", "" + position);
        }
    };

    LikeAdapter.Myclick myclicktu = new LikeAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {
//            Log.e("-------2", "" + position);
            Intent intent=new Intent(getActivity(), PlayVideoActivity.class);
            intent.putExtra("position",position);
            intent.putExtra("vidlist",list);
            startActivity(intent);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.wangluoyichang)
    public void onClick() {
        page=1;
        initDate();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (calls!=null){
            calls.cancel();
        }
    }
}
