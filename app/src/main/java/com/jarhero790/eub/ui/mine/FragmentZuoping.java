package com.jarhero790.eub.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.LikeAdapter;
import com.jarhero790.eub.message.adapter.ZuoPingAdapter;
import com.jarhero790.eub.message.bean.MyFaBuBean;
import com.jarhero790.eub.message.my.PlayVideoActivity;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;

import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentZuoping extends SupportFragment {

    RecyclerView rlv;
    Unbinder unbinder;
    RelativeLayout nodingdan;
    RelativeLayout wangluoyichang, rl_rlv;
    private View view;
    private static FragmentZuoping instance = null;
//    SmartRefreshLayout mSwipeLayout;

    ZuoPingAdapter adapter;
    ArrayList<MyFaBuBean.DataBean> list = new ArrayList<>();
    ArrayList<MyFaBuBean.DataBean> itemlist = new ArrayList<>();
    private int page = 1;

    public static FragmentZuoping newInstance() {
        if (instance == null) {
            instance = new FragmentZuoping();
        }
        return instance;
    }

    public FragmentZuoping() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_zuoping, container, false);
        rlv = view.findViewById(R.id.rlv);
        unbinder = ButterKnife.bind(this, view);
        nodingdan = view.findViewById(R.id.nodingdan);
        wangluoyichang = view.findViewById(R.id.wangluoyichang);
        rl_rlv = view.findViewById(R.id.rl_rlv);
//        mSwipeLayout = view.findViewById(R.id.m_swipe_layout);
//        int height = rl_rlv.getHeight();///没用
//        Log.e("--------------height=",height+"  seenheight="+CommonUtil.measureWidthAndHeight(rl_rlv));
        return view;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            page = 1;
//            initDate();
//            Log.e("----------zuoping","onHiddenChanged");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.e("----------zuoping","onResume");
        page = 1;
        initDate();
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


//        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshLayout) {
//                page = 1;
//                initDate();
//                mSwipeLayout.finishRefresh(1000);
//
//            }
//        });
//        mSwipeLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshLayout) {
////                page++;
//                page = 1;
//                initDate();
//                mSwipeLayout.finishLoadMore(1000);
//
//            }
//        });

    }

    private Dialog dialog;
    Call<MyFaBuBean> calls = null;

    private void initDate() {
        dialog = new Dialog(getActivity(), R.style.progress_dialog);

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog);
        dialog.show();
        RetrofitManager.getInstance().getDataServer().myfabu(SharePreferenceUtil.getToken(AppUtils.getContext()), page)
                .enqueue(new Callback<MyFaBuBean>() {
                    @Override
                    public void onResponse(Call<MyFaBuBean> call, Response<MyFaBuBean> response) {
                        calls = call;
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body() != null && response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    itemlist.clear();
                                    rlv.setVisibility(View.VISIBLE);
                                    nodingdan.setVisibility(View.GONE);
                                    wangluoyichang.setVisibility(View.GONE);
                                    rl_rlv.setVisibility(View.VISIBLE);
                                    itemlist = response.body().getData();
                                    if (page == 1) {
                                        list.clear();
                                        list.addAll(itemlist);
                                        adapter = new ZuoPingAdapter(getActivity(), list, myclickdele, myclicktu);
                                        rlv.setAdapter(adapter);
                                    } else {
                                        list.addAll(itemlist);
                                        adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    rlv.setVisibility(View.GONE);
                                    nodingdan.setVisibility(View.VISIBLE);
                                    wangluoyichang.setVisibility(View.GONE);
                                    rl_rlv.setVisibility(View.GONE);
                                }
                            } else {
                                rlv.setVisibility(View.GONE);
                                nodingdan.setVisibility(View.VISIBLE);
                                wangluoyichang.setVisibility(View.GONE);
                                rl_rlv.setVisibility(View.GONE);
                            }

                        } else {
                            dialog.dismiss();
                            rlv.setVisibility(View.GONE);
                            nodingdan.setVisibility(View.GONE);
                            wangluoyichang.setVisibility(View.VISIBLE);
                            rl_rlv.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MyFaBuBean> call, Throwable t) {
                        dialog.dismiss();
                        rlv.setVisibility(View.GONE);
                        nodingdan.setVisibility(View.GONE);
                        rl_rlv.setVisibility(View.GONE);
                        wangluoyichang.setVisibility(View.VISIBLE);
                    }
                });
    }

    ZuoPingAdapter.Myclick myclickdele = new ZuoPingAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {
//            Log.e("-------1", "" + position);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.loading_dialog);
            View layout = View.inflate(getActivity(), R.layout.item_delete_video, null);
            builder.setView(layout);
            Dialog dialog = builder.create();
            TextView cancle = layout.findViewById(R.id.cancle);
            TextView submit = layout.findViewById(R.id.submit);
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletevideo(list.get(position).getVideo_id());
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    };

    private void deletevideo(int video_id) {
        RetrofitManager.getInstance().getDataServer().delvideo(SharePreferenceUtil.getToken(AppUtils.getContext()), video_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String json = response.body().string();
//                        Log.e("--------",json);
                        JSONObject object = new JSONObject(json);
                        int code = object.optInt("code");
                        String msg = object.optString("msg");
                        if (code == 200) {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            initDate();
                            adapter.notifyDataSetChanged();//??
                        } else {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

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

    ZuoPingAdapter.Myclick myclicktu = new ZuoPingAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {
//            Log.e("-------2", "" + position+","+list.get(position).getUrl());
            Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("vidlist", list);
            intent.putExtra("videotype", "mine");
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
        page = 1;
        initDate();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (calls != null) {
            calls.cancel();
        }
    }
}

