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

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.ZuoPingAdapter;
import com.jarhero790.eub.message.bean.MyFaBuBean;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentZuopingGeRen extends Fragment {

    RecyclerView rlv;
    Unbinder unbinder;
    private View view;
    private static FragmentZuopingGeRen instance = null;

    ZuoPingAdapter adapter;
    List<MyFaBuBean.DataBean> list = new ArrayList<>();

    public static FragmentZuopingGeRen newInstance() {
        if (instance == null) {
            instance = new FragmentZuopingGeRen();
        }
        return instance;
    }

    public FragmentZuopingGeRen() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_zuoping, container, false);
        rlv=view.findViewById(R.id.rlv);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getActivity().getIntent();
        uid = intent.getIntExtra("userid", 5044);
        userid=intent.getStringExtra("userid");
        Log.e("---------------uid",userid+"");
        initDate(userid);
    }

    private int uid;
    private String userid;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    private void initDate(String s) {
        RetrofitManager.getInstance().getDataServer().myfabuother(SharePreferenceUtil.getToken(AppUtils.getContext()),s)
                .enqueue(new Callback<MyFaBuBean>() {
                    @Override
                    public void onResponse(Call<MyFaBuBean> call, Response<MyFaBuBean> response) {
                        if (response.isSuccessful()) {
                            list = response.body().getData();
                            GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
                            rlv.setLayoutManager(manager);
                            LinearItemDecoration linearItemDecoration=new LinearItemDecoration();
                            linearItemDecoration.setSpanSpace(10);
                            linearItemDecoration.setColor(getResources().getColor(R.color.backgroudcolor));
                            rlv.addItemDecoration(linearItemDecoration);
                            adapter = new ZuoPingAdapter(getActivity(), list, myclickdele, myclicktu);
                            rlv.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<MyFaBuBean> call, Throwable t) {

                    }
                });
    }

    ZuoPingAdapter.Myclick myclickdele = new ZuoPingAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {
            Log.e("-------1",""+position);
        }
    };

    ZuoPingAdapter.Myclick myclicktu = new ZuoPingAdapter.Myclick() {
        @Override
        public void myclick(int position, View view) {
            Log.e("-------2",""+position);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

