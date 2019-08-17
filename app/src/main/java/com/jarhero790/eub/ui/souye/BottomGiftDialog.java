package com.jarhero790.eub.ui.souye;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.GiftGridViewAdapter;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.Gift;
import com.jarhero790.eub.bean.GiftBean;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class BottomGiftDialog extends DialogFragment implements AdapterView.OnItemClickListener {
    private View view;
    private Window window;
    private GridView gridView;
    ArrayList<Gift> giftList;
    private static BottomGiftDialog instance=null;

    private DialogInterface.OnDismissListener mOnClickListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener listener){
        this.mOnClickListener = listener;
    }



    public static BottomGiftDialog newInstance() {
        if(instance==null){
            instance= new BottomGiftDialog();
        }
        return instance;
    }


    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getContext(),"BottomPingLunDialog onPause",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(),"BottomPingLunDialog onResume",Toast.LENGTH_LONG).show();
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String message) {
        GiftGridViewAdapter adapter=new GiftGridViewAdapter(giftList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.souye_gift, null);
        gridView=view.findViewById(R.id.gridview);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }



    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        requestData();
        // 下面这些设置必须在此方法(onStart())中才有效
        window = getDialog().getWindow();
        // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置动画
        window.setWindowAnimations(R.style.bottomDialog);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }



public void requestData(){
    //通过RequestBody.create 创建requestBody对象
    RequestBody requestBody = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("token", SharePreferenceUtil.getToken(getContext()))
            .build();

    OkHttpClient okHttpClient = new OkHttpClient();
    Request request = new Request.Builder().url(Api.HOST+"web/index/index").post(requestBody).build();
    Call call = okHttpClient.newCall(request);
    call.enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("注册异常",e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String result = response.body().string();
            Gson gson=new Gson();
            GiftBean giftBean = gson.fromJson(result, GiftBean.class);
            if(giftBean.getCode().equals("200")){
                giftList =giftBean.getData();
                //发送
                EventBus.getDefault().post("ok");
            }
        }
    });

}







    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(mOnClickListener != null) {
            mOnClickListener.onDismiss(dialog);
        }
    }


}
