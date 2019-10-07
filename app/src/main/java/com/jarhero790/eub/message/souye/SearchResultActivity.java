package com.jarhero790.eub.message.souye;

import android.content.Context;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.SearchResultAdapter;
import com.jarhero790.eub.message.bean.SearchResultBean;
import com.jarhero790.eub.message.bean.attentionchange;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;

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
//    @BindView(R.id.m_swipe_layout)
//    SmartRefreshLayout mSwipeLayout;

    SearchResultBean.DataBean.UserBean userBeans;

    ArrayList<SearchResultBean.DataBean.VideoBean> videoBeans = new ArrayList<>();
    ArrayList<SearchResultBean.DataBean.VideoBean> itemvideoBeans = new ArrayList<>();
    @BindView(R.id.tv_memo)
    TextView tvMemo;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
//    @BindView(R.id.rl_rlv)
//    RelativeLayout rlRlv;

    private int page;

    SearchResultAdapter searchAdapter;

    String word;
//    View playpause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        EventBus.getDefault().register(this);
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
                if (TextUtils.isEmpty(editable)) {
                    tvCancel.setText("取消");
                } else {
                    tvCancel.setText("搜索");
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eeee(attentionchange attentionchange) {

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

//                                Log.e("----------jj", "tttttttttt");
//                                Glide.with(SearchResultActivity.this).load(userBeans.getHeadimgurl())
//                                        .apply(new RequestOptions().placeholder(R.mipmap.edit_tou_icon).error(R.mipmap.edit_tou_icon))
//                                        .into(ivImage);
                                tvName.setText(userBeans.getNickname());

                                tvMemo.setText("粉丝：" + userBeans.getFensi() + "  关注：" + userBeans.getLike() + "  点赞：" + userBeans.getMyzan());

                                if ((userBeans.getId()+"").equals(SharePreferenceUtil.getUserid(AppUtils.getContext()))){
                                    tvGuanzu.setVisibility(View.INVISIBLE);
                                }else {
                                    tvGuanzu.setVisibility(View.VISIBLE);
                                    if (userBeans.getIs_like() == 1) {
                                        if (userBeans.getIs_likeEach() == 1) {
                                            tvGuanzu.setText("已互关");
                                        } else {
                                            tvGuanzu.setText("已关注");
                                        }
                                    } else {
                                        tvGuanzu.setText("+关注");
                                    }
                                }


                                recyclerView.setNestedScrollingEnabled(false);



                                if (page == 1) {
                                    itemvideoBeans.clear();
                                    videoBeans.addAll(itemvideoBeans);


                                    if (videoBeans.size()>0){

                                        recyclerView.setVisibility(View.VISIBLE);
                                        ivIcon.setVisibility(View.GONE);
                                        searchAdapter = new SearchResultAdapter(SearchResultActivity.this, videoBeans, myclick);
                                        recyclerView.setAdapter(searchAdapter);

                                    }else {
                                        recyclerView.setVisibility(View.GONE);
                                        ivIcon.setVisibility(View.VISIBLE);
                                    }


                                } else {
                                    videoBeans.addAll(itemvideoBeans);
                                    searchAdapter.notifyDataSetChanged();
                                }



                                searchAdapter.setOnItem(new SearchResultAdapter.OnItem() {
                                    @Override
                                    public void Clicklienr(int position) {
                                        Log.e("------------qq-",position+"");
                                        Intent intent = new Intent(SearchResultActivity.this, PlayVideoThreeActivity.class);
                                        intent.putExtra("position", position);
                                        intent.putExtra("vidlist", videoBeans);
//                                        intent.putExtra("type","like");
                                        startActivity(intent);
                                    }
                                });
                            }else {
//                                Log.e("----------jj", "tttttttttt122");
                                recyclerView.setVisibility(View.GONE);
                                ivIcon.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResultBean> call, Throwable t) {
//                        Log.e("----------jj", "tttttttttt13322");
                        recyclerView.setVisibility(View.GONE);
                        ivIcon.setVisibility(View.VISIBLE);
                    }
                });
    }


    SearchResultAdapter.Myclick myclick = new SearchResultAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
//            Log.e("-----",""+position);
        }
    };

    @OnClick({R.id.ivback, R.id.tv_cancel, R.id.tv_guanzu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivback:
                finish();
                break;
            case R.id.tv_cancel:

                if (tvCancel.getText().toString().equals("取消")) {
                    finish();
                } else {
                    String wordds = etSearch.getText().toString();
                    initDate(wordds);
                }


                break;
            case R.id.tv_guanzu:
                if (userBeans != null && userBeans.getId() != 0)
                    guanzu(userBeans.getUser_id() + "");
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void guanzu(String id) {
        RetrofitManager.getInstance().getDataServer().getguanzu(id, SharePreferenceUtil.getToken(AppUtils.getContext())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String json = null;
                    try {
                        json = response.body().string();

                        JSONObject object = new JSONObject(json);
                        int code = object.optInt("code");
                        String msg = object.optString("msg");
                        if (code == 200) {
//                            Log.e("----------jj", "0" + msg);
//                            initDate(word);//刷新
                            JSONObject data = object.optJSONObject("data");
                            String islike = data.optString("is_like");
                            EventBus.getDefault().post(new attentionchange("关注"));
                            if (islike.equals("1")) {
                                tvGuanzu.setText("已关注");
                            } else {
                                tvGuanzu.setText("关注");
                            }
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
//        Log.e("--------","playvideothree--onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.e("--------","playvideothree--onPause");
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

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
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
