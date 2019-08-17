package com.jarhero790.eub.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.FenSi;
import com.jarhero790.eub.bean.FenSiBean;
import com.jarhero790.eub.okhttp.OkHttpUtil;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


public class FensiActivity extends Activity {

    private RecyclerView recyclerViewFensi;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(String value) {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fensi);
        EventBus.getDefault().register(this);
        recyclerViewFensi = findViewById(R.id.recyclerViewFensi);
        Map<String, String> params = new HashMap<>();
        params.put("token", SharePreferenceUtil.getToken(AppUtils.getContext()));
        OkHttpUtil.getInstance().postDataAsyn(Api.HOST + "web/index/myfensi", params, new OkHttpUtil.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e("-----1", result);
                Gson gson = new Gson();
                FenSiBean fenSiBean = gson.fromJson(result, FenSiBean.class);
                ArrayList<FenSi> arrayList = fenSiBean.getData();
                Toast.makeText(AppUtils.getContext(), arrayList.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failed(Call call, IOException e) {
                Log.e("-----2", e.getMessage());
            }
        });

    }
}
