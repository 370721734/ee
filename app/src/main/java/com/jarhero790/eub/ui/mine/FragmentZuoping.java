package com.jarhero790.eub.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.ZuoPingAdapter;
import com.jarhero790.eub.message.bean.MyFaBuBean;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.record.CustomProgressDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

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

public class FragmentZuoping extends SupportFragment {

    RecyclerView rlv;
    Unbinder unbinder;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
    @BindView(R.id.wangluoyichang)
    RelativeLayout wangluoyichang;
    private View view;
    private static FragmentZuoping instance = null;

    ZuoPingAdapter adapter;
    List<MyFaBuBean.DataBean> list = new ArrayList<>();

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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initDate();

    }

    CustomProgressDialog dialog = new CustomProgressDialog();
    Call<MyFaBuBean> calls = null;

    private void initDate() {
        dialog.createLoadingDialog(getActivity(), "正在加载...");
        dialog.show();
        RetrofitManager.getInstance().getDataServer().myfabu(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<MyFaBuBean>() {
                    @Override
                    public void onResponse(Call<MyFaBuBean> call, Response<MyFaBuBean> response) {
                        calls = call;
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body()!=null && response.body().getData()!=null){
                                if (response.body().getData().size() > 0) {
                                    list.clear();
                                    rlv.setVisibility(View.VISIBLE);
                                    nodingdan.setVisibility(View.GONE);
                                    wangluoyichang.setVisibility(View.GONE);
                                    list = response.body().getData();
                                    GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
                                    rlv.setLayoutManager(manager);
                                    LinearItemDecoration linearItemDecoration = new LinearItemDecoration();
                                    linearItemDecoration.setSpanSpace(10);
                                    linearItemDecoration.setColor(getResources().getColor(R.color.backgroudcolor));
                                    rlv.addItemDecoration(linearItemDecoration);
                                    adapter = new ZuoPingAdapter(getActivity(), list, myclickdele, myclicktu);
                                    rlv.setAdapter(adapter);
                                }else {
                                    rlv.setVisibility(View.GONE);
                                    nodingdan.setVisibility(View.VISIBLE);
                                    wangluoyichang.setVisibility(View.GONE);
                                }
                            }else {
                                rlv.setVisibility(View.GONE);
                                nodingdan.setVisibility(View.VISIBLE);
                                wangluoyichang.setVisibility(View.GONE);
                            }

                        } else {
                            dialog.dismiss();
                            rlv.setVisibility(View.GONE);
                            nodingdan.setVisibility(View.GONE);
                            wangluoyichang.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MyFaBuBean> call, Throwable t) {
                        dialog.dismiss();
                        rlv.setVisibility(View.GONE);
                        nodingdan.setVisibility(View.GONE);
                        wangluoyichang.setVisibility(View.VISIBLE);
                    }
                });
    }

    ZuoPingAdapter.Myclick myclickdele = new ZuoPingAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {
            Log.e("-------1", "" + position);
        }
    };

    ZuoPingAdapter.Myclick myclicktu = new ZuoPingAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {
            Log.e("-------2", "" + position);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.wangluoyichang)
    public void onClick() {
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

