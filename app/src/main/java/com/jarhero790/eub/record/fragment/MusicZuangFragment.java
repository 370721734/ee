package com.jarhero790.eub.record.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.record.adapter.MusicAdapter;
import com.jarhero790.eub.record.bean.MusicBean;
import com.jarhero790.eub.record.view.MediaPlayUtil;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicZuangFragment extends Fragment {


    List<MusicBean.DataBean> list = new ArrayList<>();
    MusicAdapter adapter;
    @BindView(R.id.ivsearch)
    ImageView ivsearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    Unbinder unbinder;

    LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_zuang, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layoutManager=new LinearLayoutManager(getActivity());
        rlv.setLayoutManager(layoutManager);
        LinearItemDecoration itemDecoration=new LinearItemDecoration();
        itemDecoration.setColor(Color.parseColor("#191925"));
        itemDecoration.setSpanSpace(30);
        rlv.addItemDecoration(itemDecoration);
        initDate();
    }

    private void initDate() {
        RetrofitManager.getInstance().getDataServer().musicList(SharePreferenceUtil.getToken(AppUtils.getContext())).enqueue(new Callback<MusicBean>() {
            @Override
            public void onResponse(Call<MusicBean> call, Response<MusicBean> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getCode() == 200) {
                        list = response.body().getData();
                        adapter = new MusicAdapter(getActivity(), list, myclick, touclick, speak);
                        rlv.setAdapter(adapter);


                        adapter.setImageClick(new MusicAdapter.ImageClick() {
                            @Override
                            public void ClickNe(int position, View view) {
                                ImageView v= (ImageView) view;
                                if (v.isSelected()){
                                    Log.e("---------","来了");
                                    MediaPlayUtil.getInstance().stop();
                                    MediaPlayUtil.getInstance().start(list.get(position).getUrl());
                                }else {
                                    Log.e("---------","来了2");
                                    MediaPlayUtil.getInstance().stop();
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<MusicBean> call, Throwable t) {

            }
        });
    }

    MusicAdapter.Myclick myclick = new MusicAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {

        }
    };


    @Override
    public void onPause() {
        super.onPause();
        MediaPlayUtil.getInstance().pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        MediaPlayUtil.getInstance().stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayUtil.getInstance().release();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            MediaPlayUtil.getInstance().pause();
        }
    }

    private boolean isplay=true;
    MusicAdapter.Myclick touclick = new MusicAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            //video image
            Log.e("-------------",list.get(position).getUrl());
            RelativeLayout relativeLayout= (RelativeLayout) view;
            if (relativeLayout.isSelected()){
                Log.e("---------","来了");
                MediaPlayUtil.getInstance().stop();
                MediaPlayUtil.getInstance().start(list.get(position).getUrl());
            }else {
                Log.e("---------","来了2");
                MediaPlayUtil.getInstance().stop();
            }

//            if (isplay){
//                MediaPlayUtil.getInstance().stop();
//                MediaPlayUtil.getInstance().start(list.get(position).getUrl());
//                isplay=false;
//            }else {
//                MediaPlayUtil.getInstance().stop();
//                isplay=true;
//            }



        }
    };

    MusicAdapter.Myclick speak = new MusicAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            //add

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
