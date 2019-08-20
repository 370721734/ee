package com.jarhero790.eub.message.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.MessageAdapter;
import com.jarhero790.eub.bean.MessageEntity;
import com.jarhero790.eub.bean.MessageLike;
import com.jarhero790.eub.bean.MessageSystem;
import com.jarhero790.eub.bean.MessagesBean;
import com.jarhero790.eub.message.bean.SysMessageBean;
import com.jarhero790.eub.message.net.RetrofitManager;
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

    List<MessageEntity> arrayList = new ArrayList<>();
    ArrayList<MessageSystem> arrayListMessageSystem = new ArrayList<>();
    ArrayList<MessageLike> arrayListMessageLike = new ArrayList<>();
    MessageEntity messageEntity = new MessageEntity();
    LinearLayoutManager layoutManager;

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

    private void initDate() {
        RetrofitManager.getInstance().getDataServer().getSysMessages(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<MessagesBean>() {
                    @Override
                    public void onResponse(Call<MessagesBean> call, Response<MessagesBean> response) {
                        if (response.isSuccessful()){
                            if (response.body().getCode().equals("200")){
                                arrayListMessageSystem=response.body().getData().getSystem();
                                arrayListMessageLike=response.body().getData().getLike();
                                if (arrayListMessageSystem!=null && arrayListMessageSystem.size()>0){
                                    for (int i = 0; i < arrayListMessageSystem.size(); i++) {
                                        messageEntity.setMessageSystem(arrayListMessageSystem.get(i));
                                    }
                                }

                              if (arrayListMessageLike!=null && arrayListMessageLike.size()>0){
                                  for (int i = 0; i < arrayListMessageLike.size(); i++) {
                                      messageEntity.setMessageLike(arrayListMessageLike.get(i));
                                  }
                              }
                              arrayList.add(messageEntity);

                                Log.e("---------=>",arrayList.size()+"");
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(new MessageAdapter(arrayList));


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessagesBean> call, Throwable t) {

                    }
                });
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
