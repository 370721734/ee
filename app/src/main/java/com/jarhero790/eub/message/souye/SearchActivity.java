package com.jarhero790.eub.message.souye;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.MenuPagerAdapter;
import com.jarhero790.eub.message.adapter.OnItemClickListener;
import com.jarhero790.eub.message.adapter.SearchAdapter;
import com.jarhero790.eub.message.adapter.SearchTwoAdapter;
import com.jarhero790.eub.message.bean.LikeBean;
import com.jarhero790.eub.message.bean.SearchBean;
import com.jarhero790.eub.message.my.PlayVideoActivity;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;

import com.jarhero790.eub.record.BaseRecyclerAdapter;
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
    ArrayList<SearchBean.DataBean.VisitBean> visitBeans = new ArrayList<>();
    ArrayList<SearchBean.DataBean.LikeBean> likeBeans = new ArrayList<>();
    ArrayList<SearchBean.DataBean.LikeBean> itemlikeBeans = new ArrayList<>();

    MenuPagerAdapter adapter;


    SearchAdapter searchAdapter;
    SearchTwoAdapter searchTwoAdapter;

    //避免连击
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(lm);
        LinearItemDecoration itemDecoration = new LinearItemDecoration();
        itemDecoration.setColor(Color.parseColor("#3A3A44"));
        itemDecoration.setSpanSpace(20);
        recyclerView.addItemDecoration(itemDecoration);



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


    Call<SearchBean> calls = null;


    private Dialog dialog;

    private void initDate() {


        dialog = new Dialog(this, R.style.progress_dialog);
        dialog.setCancelable(true);
        if (dialog.getWindow()!=null)
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog);
        dialog.show();
        RetrofitManager.getInstance().getDataServer().search(page, SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<SearchBean>() {
                    @Override
                    public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                        calls = call;
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            visitBeans.clear();
                            itemlikeBeans.clear();
                            if (response.body() != null && response.body().getCode() == 200) {
                                visitBeans = response.body().getData().getVisit();
                                itemlikeBeans = response.body().getData().getLike();
                                if (visitBeans.size() > 0) {
                                    int pageCount = MenuPagerAdapter.calculatePageCount(visitBeans);
                                    adapter = new MenuPagerAdapter();
                                    adapter.setReqCode(new int[pageCount]);

                                    adapter.setOnItemClickListener(new OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position, int pagerPosition) {
//                                            Log.e("ggg", "," + adapter.getPageLayout(pagerPosition).getData().get(position).getId());
//                                            Log.e("ggg",position+"  "+pagerPosition);
                                            //position第几个
                                            //pagerPosition第几个页面

//                                            Intent intent = new Intent(SearchActivity.this, PlayVideoTwoActivity.class);
                                            Intent intent = new Intent(SearchActivity.this, PlayVideoTwo_TwoActivity.class);
                                            intent.putExtra("position", position + (pagerPosition * 6));
                                            intent.putExtra("type", "visit");
                                            intent.putExtra("vidlist", visitBeans);
                                            startActivity(intent);

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


                                if (page == 1) {
                                    likeBeans.clear();
                                    likeBeans.addAll(itemlikeBeans);
                                    searchAdapter = new SearchAdapter(SearchActivity.this, likeBeans, myclick);
                                    searchTwoAdapter = new SearchTwoAdapter(SearchActivity.this, likeBeans);
                                    recyclerView.setAdapter(searchTwoAdapter);
                                } else {
                                    likeBeans.addAll(itemlikeBeans);
                                    searchAdapter.notifyDataSetChanged();
                                    searchTwoAdapter.notifyDataSetChanged();
                                }


                                if (searchTwoAdapter!=null){
                                    searchTwoAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            Log.e("--------------position","="+position+"   ");
                                            try {
                                                if (0 == mLastClickTime || System.currentTimeMillis() - mLastClickTime > 1000) {
                                                    SearchBean.DataBean.LikeBean item = likeBeans.get(position);
                                                    if (item == null) {
                                                        Log.e("--------", "live list item is null at position:" + position);
                                                        return;
                                                    }
                                                    startLivePlay(item, position);
                                                }
                                                mLastClickTime = System.currentTimeMillis();//
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                            }
                        } else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchBean> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
    }

    private void startLivePlay(SearchBean.DataBean.LikeBean item, int position) {
        Log.e("-----searchactivity", "=" + position);
//        Intent intent = new Intent(SearchActivity.this, PlayVideoTwoActivity.class);
        Intent intent = new Intent(SearchActivity.this, PlayVideoTwo_TwoActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("vidlist", likeBeans);
        intent.putExtra("type", "like");
        startActivity(intent);
    }

    @OnClick({R.id.ivback, R.id.ivsearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivback:
                finish();
                break;
            case R.id.ivsearch:
                String sear = etSearch.getText().toString();
                if (TextUtils.isEmpty(sear)) {
                    Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(this, SearchResultActivity.class);
                intent.putExtra("word", sear);
                startActivity(intent);

                break;
        }
    }

    SearchAdapter.Myclick myclick = new SearchAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
//            Log.e("-----searchactivity", "=" + position);
//            Intent intent = new Intent(SearchActivity.this, PlayVideoTwoActivity.class);// 滑动 position 问题
            Intent intent = new Intent(SearchActivity.this, PlayVideoTwo_TwoActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("vidlist", likeBeans);
            intent.putExtra("type", "like");
            startActivity(intent);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (calls != null) {
            calls.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        initDate();
    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //当isShouldHideInput(v, ev)为true时，表示的是点击输入框区域，则需要显示键盘，同时显示光标，反之，需要隐藏键盘、光标
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //处理Editext的光标隐藏、显示逻辑
//                    mEdtFind.clearFocus();
                    etSearch.clearFocus();

                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }






}
