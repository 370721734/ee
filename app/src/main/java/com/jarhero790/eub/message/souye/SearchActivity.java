package com.jarhero790.eub.message.souye;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.MenuPagerAdapter;
import com.jarhero790.eub.message.adapter.OnItemClickListener;
import com.jarhero790.eub.message.adapter.SearchAdapter;
import com.jarhero790.eub.message.bean.SearchBean;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.record.CustomProgressDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.silladus.pagerindicator.PagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.ivsearch)
    ImageView ivsearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.vp_menu)
    ViewPager vpMenu;
    @BindView(R.id.pi_menu)
    PagerIndicator piMenu;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.m_swipe_layout)
    SmartRefreshLayout mSwipeLayout;


    private int page;
    List<SearchBean.DataBean.VisitBean> visitBeans = new ArrayList<>();
    List<SearchBean.DataBean.LikeBean> likeBeans = new ArrayList<>();
    List<SearchBean.DataBean.LikeBean> itemlikeBeans = new ArrayList<>();

    MenuPagerAdapter adapter;


    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(lm);
        LinearItemDecoration itemDecoration=new LinearItemDecoration();
        itemDecoration.setColor(Color.parseColor("#3A3A44"));
        itemDecoration.setSpanSpace(20);
        recyclerView.addItemDecoration(itemDecoration);
        page = 1;
        initDate();

//        initLike();

        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                initDate();
                mSwipeLayout.finishRefresh(100);

            }
        });
        mSwipeLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                initDate();
                mSwipeLayout.finishLoadMore(100);

            }
        });
    }

//    private void initLike() {
//        RetrofitManager.getInstance().getDataServer().search()
//    }

    CustomProgressDialog dialog = new CustomProgressDialog();
    Call<SearchBean> calls = null;

    private void initDate() {
        dialog.createLoadingDialog(this, "正在加载...");
        dialog.setCancelable(true);
        dialog.show();
        RetrofitManager.getInstance().getDataServer().search(page, SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<SearchBean>() {
                    @Override
                    public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                        calls = call;
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body().getCode() == 200) {
                                visitBeans = response.body().getData().getVisit();
                                itemlikeBeans = response.body().getData().getLike();
                                if (visitBeans.size()>0){
                                    int pageCount = MenuPagerAdapter.calculatePageCount(visitBeans);
                                    adapter = new MenuPagerAdapter();
                                    adapter.setReqCode(new int[pageCount]);

                                    adapter.setOnItemClickListener(new OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position, int pagerPosition) {
                                            Log.e("ggg", "," + adapter.getPageLayout(pagerPosition).getData().get(position).getId());
                                        }
                                    });

                                    adapter.setEntry(visitBeans, pageCount);
                                    vpMenu.setAdapter(adapter);
                                    piMenu.setViewPager(vpMenu)
                                            .setIndicatorDrawable(R.drawable.guide_rectangle_select, R.drawable.guide_rectangle_nomal)
                                            .setIndicatorSize(6, 6, 5)
                                            .initDot();
                                }



                                recyclerView.setNestedScrollingEnabled(false);



                                if (page==1){
                                    likeBeans.clear();
                                    likeBeans.addAll(itemlikeBeans);
                                    searchAdapter=new SearchAdapter(SearchActivity.this,likeBeans,myclick);
                                    recyclerView.setAdapter(searchAdapter);
                                } else {
                                    likeBeans.addAll(itemlikeBeans);
                                    searchAdapter.notifyDataSetChanged();
                                }

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchBean> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
    }

    @OnClick({R.id.ivback, R.id.ivsearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivback:
                finish();
                break;
            case R.id.ivsearch:
                String sear=etSearch.getText().toString();
                if (TextUtils.isEmpty(sear)){
                    Toast.makeText(this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent=new Intent(this,SearchResultActivity.class);
                intent.putExtra("word",sear);
                startActivity(intent);

                break;
        }
    }

    SearchAdapter.Myclick myclick=new SearchAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            Log.e("-----",""+position);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (calls != null) {
            calls.cancel();
        }
    }
}
