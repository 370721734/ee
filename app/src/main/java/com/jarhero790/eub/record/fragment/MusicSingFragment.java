package com.jarhero790.eub.record.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.record.adapter.MusicAdapter;
import com.jarhero790.eub.record.bean.MusicBean;
import com.jarhero790.eub.record.bean.SearchMusicBean;
import com.jarhero790.eub.record.view.MediaPlayUtil;
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
        if (zuang!=null){
            zuang.Clickenear(etSearch);
        }
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH){
                    searchMusic();
                    InputMethodManager imm= (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    if (imm!=null) {
                        imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });
    }

    public void initDate() {
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


    @OnClick(R.id.ivsearch)
    public void onViewClicked() {
        searchMusic();
    }

    List<SearchMusicBean.DataBean> searchMusicBeans = new ArrayList<>();

    private void searchMusic() {
        String text = etSearch.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(getActivity(), "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        RetrofitManager.getInstance().getDataServer().searchMusic(SharePreferenceUtil.getToken(AppUtils.getContext()), text).enqueue(new Callback<SearchMusicBean>() {
            @Override
            public void onResponse(Call<SearchMusicBean> call, Response<SearchMusicBean> response) {
                if (response.isSuccessful()) {
                    list.clear();
                    searchMusicBeans = response.body().getData();
                    if (searchMusicBeans != null && searchMusicBeans.size() > 0) {
                        MusicBean.DataBean md = new MusicBean.DataBean(searchMusicBeans.get(0).getId(), searchMusicBeans.get(0).getName(),
                                searchMusicBeans.get(0).getSinger(), searchMusicBeans.get(0).getUrl(), searchMusicBeans.get(0).getMusic_img());
                        list.add(md);
                        adapter = new MusicAdapter(getActivity(), list, myclick, touclick, speak);
                        rlv.setAdapter(adapter);
                        adapter.setImageClick(new MusicAdapter.ImageClick() {
                            @Override
                            public void ClickNe(int position, View view) {
                                ImageView v = (ImageView) view;
                                if (v.isSelected()) {
//                                    Log.e("---------", "来了jj");
                                    MediaPlayUtil.getInstance().stop();
                                    MediaPlayUtil.getInstance().start(list.get(position).getUrl());
                                } else {
//                                    Log.e("---------", "来了jj2");
                                    MediaPlayUtil.getInstance().stop();
                                }
                            }
                        });
                    }


                }
            }

            @Override
            public void onFailure(Call<SearchMusicBean> call, Throwable t) {

            }
        });
    }


    public interface MusicString{
        void Clicklinener(int position,String url,String mid);
    }
    private MusicString musicString;

    public void setMusicString(MusicString musicString) {
        this.musicString = musicString;
    }


    public interface Zuang{
        void Clickenear( EditText etSearch);
    }
    private Zuang zuang;

    public void setZuang(Zuang zuang) {
        this.zuang = zuang;
    }

}
