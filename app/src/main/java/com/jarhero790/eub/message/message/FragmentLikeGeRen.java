package com.jarhero790.eub.message.message;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.jarhero790.eub.message.bean.MyFaBuBean;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentLikeGeRen extends Fragment {
    @BindView(R.id.rlv)
    RecyclerView rlv;
    Unbinder unbinder;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
    @BindView(R.id.wangluoyichang)
    RelativeLayout wangluoyichang;

    RelativeLayout rl_rlv;
//    @BindView(R.id.m_swipe_layout)
//    SmartRefreshLayout mSwipeLayout;
    private View view;

    private static FragmentLikeGeRen instance = null;

    ArrayList<MyFaBuBean.DataBean> list = new ArrayList<>();
    ArrayList<MyFaBuBean.DataBean> itemlist = new ArrayList<>();
    LikeAdapter adapter;
    private int page;

    public static FragmentLikeGeRen newInstance() {
        if (instance == null) {
            instance = new FragmentLikeGeRen();
        }
        return instance;
    }

    public FragmentLikeGeRen() {

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
        rl_rlv=view.findViewById(R.id.rl_rlv);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    private String userid;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Intent intent = getActivity().getIntent();
        userid = intent.getStringExtra("userid");
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        rlv.setLayoutManager(manager);
        LinearItemDecoration linearItemDecoration = new LinearItemDecoration();
        linearItemDecoration.setSpanSpace(10);
        linearItemDecoration.setColor(getResources().getColor(R.color.backgroudcolor));
        rlv.addItemDecoration(linearItemDecoration);
        page = 1;
        initDate(userid);

//        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshLayout) {
//                page = 1;
//                initDate(userid);
//                mSwipeLayout.finishRefresh(1000);
//
//            }
//        });
//        mSwipeLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshLayout) {
////                page++;
//                page = 1;
//                initDate(userid);
//                mSwipeLayout.finishLoadMore(1000);
//
//            }
//        });

    }

    Call<MyFaBuBean> calls = null;
    private Dialog dialog;

    private void initDate(String s) {
        dialog = new Dialog(getActivity(), R.style.progress_dialog);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        RetrofitManager.getInstance().getDataServer().zanvideoother(SharePreferenceUtil.getToken(AppUtils.getContext()), s, page)
                .enqueue(new Callback<MyFaBuBean>() {
                    @Override
                    public void onResponse(Call<MyFaBuBean> call, Response<MyFaBuBean> response) {
                        calls = call;
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body() != null && response.body().getData().size() > 0) {
                                itemlist.clear();
                                rlv.setVisibility(View.VISIBLE);
                                rl_rlv.setVisibility(View.VISIBLE);
                                nodingdan.setVisibility(View.GONE);
                                wangluoyichang.setVisibility(View.GONE);
                                itemlist = response.body().getData();

                                if (page == 1) {
                                    list.clear();
                                    list.addAll(itemlist);
                                    adapter = new LikeAdapter(getActivity(), list, myclickdele, myclicktu);
                                    rlv.setAdapter(adapter);
                                } else {
                                    list.addAll(itemlist);
                                    adapter.notifyDataSetChanged();
                                }

                            } else {
                                if (page == 1) {
                                    rlv.setVisibility(View.GONE);
                                    rl_rlv.setVisibility(View.GONE);
                                    nodingdan.setVisibility(View.VISIBLE);
                                    wangluoyichang.setVisibility(View.GONE);
                                }

                            }
                        } else {
                            dialog.dismiss();
                            rlv.setVisibility(View.GONE);
                            rl_rlv.setVisibility(View.GONE);
                            nodingdan.setVisibility(View.GONE);
                            wangluoyichang.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MyFaBuBean> call, Throwable t) {
                        dialog.dismiss();
                        rlv.setVisibility(View.GONE);
                        rl_rlv.setVisibility(View.GONE);
                        nodingdan.setVisibility(View.GONE);
                        wangluoyichang.setVisibility(View.VISIBLE);
                    }
                });
    }

    LikeAdapter.Myclick myclickdele = new LikeAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {
//            Log.e("-------1", "" + position);
        }
    };

    LikeAdapter.Myclick myclicktu = new LikeAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {
//            Log.e("-------2", "" + position);
            Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("vidlist", list);
            intent.putExtra("videotype","mine");
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
        if (userid != null)
            page = 1;
        initDate(userid);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (calls != null) {
            calls.cancel();
        }
    }
}
