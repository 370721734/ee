package com.jarhero790.eub.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.LikeAdapter;
import com.jarhero790.eub.message.adapter.ZuoPingAdapter;
import com.jarhero790.eub.message.bean.LikeBean;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentLike extends SupportFragment {
    @BindView(R.id.rlv)
    RecyclerView rlv;
    Unbinder unbinder;
    private View view;

    private static FragmentLike instance = null;

    List<LikeBean.DataBean> likeBeans = new ArrayList<>();
    LikeAdapter adapter;

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
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initDate();

    }

    private void initDate() {
        RetrofitManager.getInstance().getDataServer().zanvideo(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<LikeBean>() {
                    @Override
                    public void onResponse(Call<LikeBean> call, Response<LikeBean> response) {
                        if (response.isSuccessful()) {
                            likeBeans = response.body().getData();
                            GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
                            rlv.setLayoutManager(manager);
                            adapter = new LikeAdapter(getActivity(), likeBeans, myclickdele, myclicktu);
                            rlv.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<LikeBean> call, Throwable t) {

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
}
