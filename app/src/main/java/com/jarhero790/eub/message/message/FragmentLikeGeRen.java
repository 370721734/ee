package com.jarhero790.eub.message.message;

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
import com.jarhero790.eub.message.bean.LikeBean;
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
    private View view;

    private static FragmentLikeGeRen instance = null;

    List<LikeBean.DataBean> likeBeans = new ArrayList<>();
    LikeAdapter adapter;

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
        Intent intent = getActivity().getIntent();
        userid = intent.getStringExtra("userid");
        initDate(userid);
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
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    private String userid;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    CustomProgressDialog dialog = new CustomProgressDialog();
    retrofit2.Call<LikeBean> calls=null;
    private void initDate(String s) {
        dialog.createLoadingDialog(getActivity(), "正在加载...");
        dialog.show();
        RetrofitManager.getInstance().getDataServer().zanvideoother(SharePreferenceUtil.getToken(AppUtils.getContext()), s)
                .enqueue(new Callback<LikeBean>() {
                    @Override
                    public void onResponse(Call<LikeBean> call, Response<LikeBean> response) {
                        calls=call;
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body().getData().size()>0){
                                likeBeans.clear();
                                rlv.setVisibility(View.VISIBLE);
                                nodingdan.setVisibility(View.GONE);
                                wangluoyichang.setVisibility(View.GONE);
                                likeBeans = response.body().getData();
                                GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
                                rlv.setLayoutManager(manager);
                                LinearItemDecoration linearItemDecoration = new LinearItemDecoration();
                                linearItemDecoration.setSpanSpace(10);
                                linearItemDecoration.setColor(getResources().getColor(R.color.backgroudcolor));
                                rlv.addItemDecoration(linearItemDecoration);
                                adapter = new LikeAdapter(getActivity(), likeBeans, myclickdele, myclicktu);
                                rlv.setAdapter(adapter);
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
                    public void onFailure(Call<LikeBean> call, Throwable t) {
                        dialog.dismiss();
                        rlv.setVisibility(View.GONE);
                        nodingdan.setVisibility(View.GONE);
                        wangluoyichang.setVisibility(View.VISIBLE);
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
        if (userid!=null)
            initDate(userid);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (calls!=null){
            calls.cancel();
        }
    }
}
