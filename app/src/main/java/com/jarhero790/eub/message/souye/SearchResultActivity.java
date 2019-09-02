package com.jarhero790.eub.message.souye;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.activity.FensiActivity;
import com.jarhero790.eub.message.adapter.SearchAdapter;
import com.jarhero790.eub.message.adapter.SearchResultAdapter;
import com.jarhero790.eub.message.bean.SearchResultBean;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silladus.basic.IFragment;

public class SearchResultActivity extends AppCompatActivity {

    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_image)
    CircleImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_guanzu)
    TextView tvGuanzu;
    @BindView(R.id.rlv)
    RecyclerView recyclerView;
    @BindView(R.id.m_swipe_layout)
    SmartRefreshLayout mSwipeLayout;

    SearchResultBean.DataBean.UserBean userBeans;

    ArrayList<SearchResultBean.DataBean.VideoBean> videoBeans = new ArrayList<>();
    ArrayList<SearchResultBean.DataBean.VideoBean> itemvideoBeans = new ArrayList<>();
    @BindView(R.id.tv_memo)
    TextView tvMemo;

    private int page;

    SearchResultAdapter searchAdapter;

    String word;
    View playpause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(lm);
        LinearItemDecoration itemDecoration = new LinearItemDecoration();
        itemDecoration.setColor(Color.parseColor("#3A3A44"));
        itemDecoration.setSpanSpace(20);
        recyclerView.addItemDecoration(itemDecoration);

        Intent intent = getIntent();
         word = intent.getStringExtra("word");
        page = 1;
        initDate(word);

//        playpause=lm.findViewByPosition(R.id.iv_play);
//        if (playpause!=null){
//            playpause.setVisibility(View.VISIBLE);
//
//        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)){
                    tvCancel.setText("取消");
                }else {
                    tvCancel.setText("搜索");
                }
            }
        });
    }

    private void initDate(String w) {
        RetrofitManager.getInstance().getDataServer().dosearch(w, page, SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<SearchResultBean>() {
                    @Override
                    public void onResponse(Call<SearchResultBean> call, Response<SearchResultBean> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getCode() == 200) {
                                userBeans = response.body().getData().getUser();
                                videoBeans = response.body().getData().getVideo();


//                                Glide.with(SearchResultActivity.this).load(userBeans.getHeadimgurl())
//                                        .apply(new RequestOptions().placeholder(R.mipmap.edit_tou_icon).error(R.mipmap.edit_tou_icon))
//                                        .into(ivImage);
                                tvName.setText(userBeans.getNickname());

                                tvMemo.setText("粉丝："+userBeans.getFensi()+"  关注："+userBeans.getLike()+"  点赞："+userBeans.getMyzan());

//                                tvGuanzu.setText(userBeans.getSubscribe());//关注




                                recyclerView.setNestedScrollingEnabled(false);



                                if (page==1){
                                    itemvideoBeans.clear();
                                    videoBeans.addAll(itemvideoBeans);
                                    searchAdapter=new SearchResultAdapter(SearchResultActivity.this,videoBeans,myclick);
                                    recyclerView.setAdapter(searchAdapter);
                                } else {
                                    videoBeans.addAll(itemvideoBeans);
                                    searchAdapter.notifyDataSetChanged();
                                }


                                searchAdapter.setOnItem(new SearchResultAdapter.OnItem() {
                                    @Override
                                    public void Clicklienr(int position) {
                                        Log.e("-------------",position+"");
                                        Intent intent=new Intent(SearchResultActivity.this, PlayVideoThreeActivity.class);
                                        intent.putExtra("position",position);
                                        intent.putExtra("vidlist",videoBeans);
//                                        intent.putExtra("type","like");
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResultBean> call, Throwable t) {

                    }
                });
    }


    SearchResultAdapter.Myclick myclick=new SearchResultAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            Log.e("-----",""+position);
        }
    };

    @OnClick({R.id.ivback, R.id.tv_cancel, R.id.tv_guanzu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivback:
                finish();
                break;
            case R.id.tv_cancel:

                if (tvCancel.getText().toString().equals("取消")){
                    finish();
                }else {
                    String wordds=etSearch.getText().toString();
                    initDate(wordds);
                }


                break;
            case R.id.tv_guanzu:
                guanzu(userBeans.getUser_id() + "");
                break;
        }
    }



    private void guanzu(String id) {
        RetrofitManager.getInstance().getDataServer().getguanzu(id, SharePreferenceUtil.getToken(AppUtils.getContext())).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String json = null;
                    try {
                        json = response.body().string();
                        Log.e("----------jj", json);
                        org.json.JSONObject object = new org.json.JSONObject(json);
                        int code = object.optInt("code");
                        String msg = object.optString("msg");
                        if (code == 200) {
                            Log.e("----------jj", "0" + msg);
//                            initDate(word);//刷新
                            Toast.makeText(SearchResultActivity.this, msg, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(SearchResultActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("--------","playvideothree--onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("--------","playvideothree--onPause");
    }
}
