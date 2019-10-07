package com.jarhero790.eub.message.my;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.GuangZuAdapter;
import com.jarhero790.eub.message.bean.GuangZuBean;
import com.jarhero790.eub.message.bean.SearchResultBean;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuangZuActivity extends AppCompatActivity {


    List<GuangZuBean.DataBean> list = new ArrayList<>();
    SearchResultBean.DataBean.UserBean userlist = new SearchResultBean.DataBean.UserBean();
    GuangZuAdapter adapter;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
    @BindView(R.id.wangluoyichang)
    RelativeLayout wangluoyichang;

    LinearLayoutManager layoutManager;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ivwa)
    ImageView ivwa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guang_zu);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        layoutManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(layoutManager);
        LinearItemDecoration linearItemDecoration = new LinearItemDecoration();
        linearItemDecoration.setSpanSpace(20);
        linearItemDecoration.setColor(getResources().getColor(R.color.backgroudcolor));
        rlv.addItemDecoration(linearItemDecoration);

        initDate();
    }

    private Dialog dialog;
    Call<GuangZuBean> calls = null;

    private void initDate() {
        dialog = new Dialog(this, R.style.progress_dialog);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        RetrofitManager.getInstance().getDataServer().mylike(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<GuangZuBean>() {
                    @Override
                    public void onResponse(Call<GuangZuBean> call, Response<GuangZuBean> response) {
                        calls = call;
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body() != null && response.body().getCode() == 200) {
//                                Log.e("---?",response.body().getData().size()+"");
                                list.clear();
                                nodingdan.setVisibility(View.GONE);
                                wangluoyichang.setVisibility(View.GONE);
                                rlv.setVisibility(View.VISIBLE);
                                list = response.body().getData();
//                                    GridLayoutManager manager = new GridLayoutManager(GuangZuActivity.this, 1);
//                                    rlv.setLayoutManager(manager);
                                adapter = new GuangZuAdapter(GuangZuActivity.this, list, myclick, touclick, speak);
                                rlv.setAdapter(adapter);

                            } else {
                                nodingdan.setVisibility(View.VISIBLE);
                                wangluoyichang.setVisibility(View.GONE);
                                rlv.setVisibility(View.GONE);
                            }
                        } else {
                            dialog.dismiss();
                            nodingdan.setVisibility(View.GONE);
                            wangluoyichang.setVisibility(View.VISIBLE);
                            rlv.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<GuangZuBean> call, Throwable t) {
                        dialog.dismiss();
                        nodingdan.setVisibility(View.GONE);
                        wangluoyichang.setVisibility(View.VISIBLE);
                        rlv.setVisibility(View.GONE);
                    }
                });
    }


    GuangZuAdapter.Myclick myclick = new GuangZuAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            //关注
            guanzu(list.get(position).getId() + "");
        }
    };
    GuangZuAdapter.Myclick touclick = new GuangZuAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            //个人信息
        }
    };
    GuangZuAdapter.Myclick speak = new GuangZuAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            //聊天
            if (list.get(position).getIs_likeEach() == 1) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(GuangZuActivity.this, list.get(position).getRong_id() + "", list.get(position).getNickname());
                }
            } else {
                Toast.makeText(GuangZuActivity.this, "要互相关注后才能聊天", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @OnClick({R.id.back, R.id.wangluoyichang, R.id.iv_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.wangluoyichang:
                initDate();
                break;
            case R.id.iv_icon:
                String content=etSearch.getText().toString();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

//                RetrofitManager.getInstance().getDataServer().dosearchattion(content,SharePreferenceUtil.getToken(AppUtils.getContext())).enqueue(new Callback<GuangZuBean>() {
//                    @Override
//                    public void onResponse(Call<GuangZuBean> call, Response<GuangZuBean> response) {
//                        if (response.isSuccessful()){
//                            list.clear();
//                            if (response.body()!=null)
//                            list=response.body().getData();
//
//                            Log.e("--------------","go");
//                            if (list.size()>0){
//                                nodingdan.setVisibility(View.GONE);
//                                wangluoyichang.setVisibility(View.GONE);
//                                rlv.setVisibility(View.VISIBLE);
//                                adapter = new GuangZuAdapter(GuangZuActivity.this, list, myclick, touclick, speak);
//                                rlv.setAdapter(adapter);
//                            }else {
//                                nodingdan.setVisibility(View.VISIBLE);
//                                wangluoyichang.setVisibility(View.GONE);
//                                rlv.setVisibility(View.GONE);
//                            }
//
//
//
//                        }else {
//                            nodingdan.setVisibility(View.GONE);
//                            wangluoyichang.setVisibility(View.VISIBLE);
//                            rlv.setVisibility(View.GONE);
//                            Log.e("--------------","go2");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<GuangZuBean> call, Throwable t) {
//                        nodingdan.setVisibility(View.GONE);
//                        wangluoyichang.setVisibility(View.VISIBLE);
//                        rlv.setVisibility(View.GONE);
//                        Log.e("--------------","go3");
//                    }
//                });



                RetrofitManager.getInstance().getDataServer().dosearch(content,1,SharePreferenceUtil.getToken(AppUtils.getContext()))
                        .enqueue(new Callback<SearchResultBean>() {
                            @Override
                            public void onResponse(Call<SearchResultBean> call, Response<SearchResultBean> response) {
                                if (response.isSuccessful()){
                                    list.clear();
                                    if (response.body()!=null && response.body().getData()!=null) {
                                        userlist = response.body().getData().getUser();

//                                    Log.e("--------------","go4"+userlist.getNickname());
                                        list.add(new GuangZuBean.DataBean(userlist.getId(), userlist.getNickname(), userlist.getHeadimgurl(), userlist.getSign(), userlist.getIs_like(), userlist.getIs_likeEach()));
                                        if (list.size() > 0) {
                                            nodingdan.setVisibility(View.GONE);
                                            wangluoyichang.setVisibility(View.GONE);
                                            rlv.setVisibility(View.VISIBLE);
                                            adapter = new GuangZuAdapter(GuangZuActivity.this, list, myclick, touclick, speak);
                                            rlv.setAdapter(adapter);
                                        } else {
                                            nodingdan.setVisibility(View.VISIBLE);
                                            wangluoyichang.setVisibility(View.GONE);
                                            rlv.setVisibility(View.GONE);
                                        }

                                    }else {
                                        nodingdan.setVisibility(View.VISIBLE);
                                        wangluoyichang.setVisibility(View.GONE);
                                        rlv.setVisibility(View.GONE);
                                    }

                                }else {
                                    nodingdan.setVisibility(View.GONE);
                                    wangluoyichang.setVisibility(View.VISIBLE);
                                    rlv.setVisibility(View.GONE);
                                    Log.e("--------------","go5");
                                }
                            }

                            @Override
                            public void onFailure(Call<SearchResultBean> call, Throwable t) {
                                Log.e("--------------","go6");
                                nodingdan.setVisibility(View.GONE);
                        wangluoyichang.setVisibility(View.VISIBLE);
                                rlv.setVisibility(View.GONE);
                            }
                        });
                break;
        }
    }

    private void guanzu(String id) {
        RetrofitManager.getInstance().getDataServer().getguanzu(id, SharePreferenceUtil.getToken(AppUtils.getContext())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String json = null;
                    try {
                        json = response.body().string();
//                        Log.e("----------jj", json);
                        JSONObject object = new JSONObject(json);
                        int code = object.optInt("code");
                        String msg = object.optString("msg");
                        if (code == 200) {
//                            Log.e("----------jj", "0" + msg);
                            initDate();
                            Toast.makeText(GuangZuActivity.this, msg, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(GuangZuActivity.this, msg, Toast.LENGTH_SHORT).show();
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
    protected void onStop() {
        super.onStop();
        if (calls != null) {
            calls.cancel();
        }
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
