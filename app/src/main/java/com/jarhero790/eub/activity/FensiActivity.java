package com.jarhero790.eub.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.adapter.FeiSiAdapter;
import com.jarhero790.eub.message.bean.FenSiTBean;
import com.jarhero790.eub.message.contract.NameContract;
import com.jarhero790.eub.message.message.GeRenInfoActivity;
import com.jarhero790.eub.message.net.LinearItemDecoration;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class FensiActivity extends Activity {

    @BindView(R.id.nodingdan)
    RelativeLayout nodingdan;
    @BindView(R.id.wangluoyichang)
    RelativeLayout wangluoyichang;
    private RecyclerView recyclerViewFensi;

    FeiSiAdapter feiSiAdapter;

    List<FenSiTBean.DataBean> arrayList = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessage(String value) {
//
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fensi);
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
        recyclerViewFensi = findViewById(R.id.recyclerViewFensi);
        CommonUtil.setStatusBarTransparent(this);
        GridLayoutManager manager = new GridLayoutManager(FensiActivity.this, 1);
        recyclerViewFensi.setLayoutManager(manager);
        LinearItemDecoration itemDecoration = new LinearItemDecoration();
        itemDecoration.setColor(getResources().getColor(R.color.backgroudcolor));
        recyclerViewFensi.addItemDecoration(itemDecoration);
//        getfensi();
        getfensitwo();

//        Map<String, String> params=new HashMap<>();
//        params.put("token", SharePreferenceUtil.getToken(AppUtils.getContext())) ;
//        Log.e("--------token",SharePreferenceUtil.getToken(AppUtils.getContext()));
//        OkHttpUtil.getInstance().postDataAsyn(Api.HOST + "web/index/myfensi", params, new OkHttpUtil.MyNetCall() {
//            @Override
//            public void success(Call call, Response response) throws IOException {
//                               String result= response.body().string();
//                               Log.e("------12",result);
//                               //{"code":200,"data":[{"nickname":"5032","headimgurl":"\/static\/images\/usertouxiang.png","rong_id":5032,"rong_token":"VymYdNrAg1tJrBcEbUoMMTC+AcwG9OuaMU77gxnpvCMxkD1T1b7Lk\/mroIXOIJjxL4OSg1toIa2Vo1pe6RwiTQ==","user_id":5032,"is_likeEach":1,"addtime":"2019-08-17 17:05:13"}],"msg":""}
//                               Gson gson=new  Gson();
//                               FenSiBean fenSiBean=gson.fromJson(result, FenSiBean.class);
//                               ArrayList<FenSi> arrayList=fenSiBean.getData();
//                               Toast.makeText(AppUtils.getContext(),arrayList.toString(),Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void failed(Call call, IOException e) {
//                Log.e("------12","");
//            }
//        });

    }

    private Dialog dialog;
    retrofit2.Call<FenSiTBean> calls = null;

    private void getfensitwo() {
        dialog = new Dialog(this, R.style.progress_dialog);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        RetrofitManager.getInstance().getDataServer().getfensi(SharePreferenceUtil.getToken(AppUtils.getContext())).enqueue(new retrofit2.Callback<FenSiTBean>() {
            @Override
            public void onResponse(retrofit2.Call<FenSiTBean> call, retrofit2.Response<FenSiTBean> response) {
                calls = call;
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    int code = response.body().getCode();
                    if (code == 200) {
                        arrayList.clear();
                        arrayList = response.body().getData();
//                        Log.e("---------200", arrayList.get(0).getAddtime() + "");
                        if (arrayList.size() > 0) {
                            nodingdan.setVisibility(View.GONE);
                            wangluoyichang.setVisibility(View.GONE);
                            recyclerViewFensi.setVisibility(View.VISIBLE);

                            feiSiAdapter = new FeiSiAdapter(FensiActivity.this, arrayList, myclick, touclick, speak);
                            recyclerViewFensi.setAdapter(feiSiAdapter);
                        } else {
                            nodingdan.setVisibility(View.VISIBLE);
                            wangluoyichang.setVisibility(View.GONE);
                            recyclerViewFensi.setVisibility(View.GONE);
                        }

                    }
                } else {
                    dialog.dismiss();
                    nodingdan.setVisibility(View.GONE);
                    wangluoyichang.setVisibility(View.VISIBLE);
                    recyclerViewFensi.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<FenSiTBean> call, Throwable t) {
                dialog.dismiss();
                nodingdan.setVisibility(View.GONE);
                wangluoyichang.setVisibility(View.VISIBLE);
                recyclerViewFensi.setVisibility(View.GONE);
            }
        });
    }

//    public void getfensi() {
//        //通过RequestBody.create 创建requestBody对象
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("token", SharePreferenceUtil.getToken(AppUtils.getContext()))
//                .build();
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(Api.HOST + "web/index/myfensi").post(requestBody).build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("注册异常", e.getMessage());
//                Toast.makeText(FensiActivity.this, "异常" + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                try {
////
//
//                    String result = response.body().string();
//                    Log.e("------12", result);
//                    //{"code":200,"data":[{"nickname":"5032","headimgurl":"\/static\/images\/usertouxiang.png","rong_id":5032,"rong_token":"VymYdNrAg1tJrBcEbUoMMTC+AcwG9OuaMU77gxnpvCMxkD1T1b7Lk\/mroIXOIJjxL4OSg1toIa2Vo1pe6RwiTQ==","user_id":5032,"is_likeEach":1,"addtime":"2019-08-17 17:05:13"}],"msg":""}
//                    Gson gson = new Gson();
//                    FenSiTBean fenSiBean = gson.fromJson(result, FenSiTBean.class);
//                    List<FenSiTBean.DataBean> arrayList = fenSiBean.getData();
//
//                    Log.e("--------4", "" + arrayList.size() + arrayList.get(0).getNickname());
//
//                    JSONObject jsonObject = JSON.parseObject(result);
//                    int code = jsonObject.getInteger("code");
//                    String msg = (String) jsonObject.getString("msg");
////                    Log.e("注册结果msg值", msg);
//                    if (code == 200) {
////                        JSONObject data = jsonObject.optJSONObject("data");
////                        msgid=data.optString("msgId");
//                        Log.e("----------2:", "ok");//ok
//
////                        Toast.makeText(FensiActivity.this, msg, Toast.LENGTH_LONG).show();
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                feiSiAdapter = new FeiSiAdapter(FensiActivity.this, arrayList, myclick, touclick, speak);
//                                recyclerViewFensi.setAdapter(feiSiAdapter);
//                            }
//                        });
//
//                    } else {
//                        Toast.makeText(FensiActivity.this, msg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//
//                }
//
////                Log.e("注册结果", result);
//                //Toast.makeText(RegisterByUsernameActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }


    FeiSiAdapter.Myclick myclick = new FeiSiAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            Log.e("--------1", "" + position + "  " + arrayList.get(position).getUser_id());
            guanzu(arrayList.get(position).getUser_id() + "");
        }
    };
    FeiSiAdapter.Myclick touclick = new FeiSiAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
//            Log.e("--------2", "" + position + "  " + arrayList.get(position).getUser_id());
            if (arrayList.get(position).getUser_id() > 0) {
                Intent intent = new Intent(FensiActivity.this, GeRenInfoActivity.class);
                intent.putExtra("userid", "" + arrayList.get(position).getUser_id() + "");
                startActivity(intent);
            }


        }
    };
    FeiSiAdapter.Myclick speak = new FeiSiAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {
            Log.e("--------3", "" + position + "  " + arrayList.get(position).getUser_id() + "  " + arrayList.get(position).getNickname());
//            --------3: 0  5032  5032
            //1.判断是否互关
            if (arrayList.get(position).getIs_likeEach() == 1) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(FensiActivity.this, arrayList.get(position).getRong_id() + "", arrayList.get(position).getNickname());


                    //不用了
//                EventBus.getDefault().post(new Conver(arrayList.get(position).getNickname()));
//                    if (nameContract!=null){
//                        nameContract.getNickName(arrayList.get(position).getNickname());
//
//                    }
                }
            } else {
                Toast.makeText(FensiActivity.this, "要互相关注后才能聊天", Toast.LENGTH_SHORT).show();
            }


        }
    };

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
                            getfensitwo();//刷新
                            Toast.makeText(FensiActivity.this, msg, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(FensiActivity.this, msg, Toast.LENGTH_SHORT).show();
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


    private NameContract nameContract;

    public void setNameContract(NameContract nameContract) {
        this.nameContract = nameContract;
    }

    @OnClick({R.id.back, R.id.wangluoyichang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.wangluoyichang:
                getfensitwo();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (calls != null) {
            calls.cancel();
        }
    }
}
