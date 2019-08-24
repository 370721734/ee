package com.jarhero790.eub.message.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.MessageAdapter;
import com.jarhero790.eub.bean.MessageEntity;
import com.jarhero790.eub.bean.MessageLike;
import com.jarhero790.eub.bean.MessageSystem;
import com.jarhero790.eub.bean.MessagesBean;
import com.jarhero790.eub.message.adapter.SysMessageAdapter;
import com.jarhero790.eub.message.bean.SysMessageBean;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.record.CustomProgressDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SysMessageActivity extends AppCompatActivity {


    @BindView(R.id.recyclerViewMessage)
    RecyclerView recyclerView;

    //    List<MessageEntity> arrayList = new ArrayList<>();
//    ArrayList<MessageSystem> arrayListMessageSystem = new ArrayList<>();
//    ArrayList<MessageLike> arrayListMessageLike = new ArrayList<>();
//    MessageEntity messageEntity = new MessageEntity();
    LinearLayoutManager layoutManager;
    SysMessageAdapter adapter;

    List<SysMessageBean.DataBean.SystemBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_message);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        layoutManager = new LinearLayoutManager(AppUtils.getContext());
        recyclerView.setLayoutManager(layoutManager);
        initDate();
    }
    CustomProgressDialog dialog=new CustomProgressDialog();
    private void initDate() {
        dialog.createLoadingDialog(this,"正在加载...");
        dialog.show();
        RetrofitManager.getInstance().getDataServer().getSysMessages(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<SysMessageBean>() {
                    @Override
                    public void onResponse(Call<SysMessageBean> call, Response<SysMessageBean> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body().getCode() == 200) {
                                list.clear();
                                Log.e("---------", response.toString());
//                                arrayListMessageSystem=response.body().getData().getSystem();
//                                arrayListMessageLike=response.body().getData().getLike();
//                                if (arrayListMessageSystem!=null && arrayListMessageSystem.size()>0){
//                                    for (int i = 0; i < arrayListMessageSystem.size(); i++) {
//                                        messageEntity.setMessageSystem(arrayListMessageSystem.get(i));
//                                    }
//                                }
//
//                              if (arrayListMessageLike!=null && arrayListMessageLike.size()>0){
//                                  for (int i = 0; i < arrayListMessageLike.size(); i++) {
//                                      messageEntity.setMessageLike(arrayListMessageLike.get(i));
//                                  }
//                              }
//                              arrayList.add(messageEntity);
//
                                list = response.body().getData().getSystem();
                                Log.e("---------=>", list.size() + "");
                                recyclerView.setLayoutManager(layoutManager);
                                adapter = new SysMessageAdapter(SysMessageActivity.this, list, myclick);
                                recyclerView.setAdapter(adapter);


                            }
                        }else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SysMessageBean> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
    }

    SysMessageAdapter.Myclick myclick = new SysMessageAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {

        }
    };

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
