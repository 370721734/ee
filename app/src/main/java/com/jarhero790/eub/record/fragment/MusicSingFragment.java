package com.jarhero790.eub.record.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.record.adapter.MusicAdapter;
import com.jarhero790.eub.record.bean.MusicBean;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicSingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicSingFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_music_sing, container, false);
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
                        if (rlv!=null)
                        rlv.setAdapter(adapter);


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

    MusicAdapter.Myclick touclick = new MusicAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            //video image
        }
    };

    MusicAdapter.Myclick speak = new MusicAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            //add
            if (musicString!=null){
                musicString.Clicklinener(position,list.get(position).getUrl(),list.get(position).getId()+"");
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public interface MusicString{
        void Clicklinener(int position,String url,String mid);
    }
    private MusicString musicString;

    public void setMusicString(MusicString musicString) {
        this.musicString = musicString;
    }

}
