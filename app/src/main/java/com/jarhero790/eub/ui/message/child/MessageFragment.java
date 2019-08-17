package com.jarhero790.eub.ui.message.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jarhero790.eub.R;
import com.jarhero790.eub.activity.FensiActivity;
import com.jarhero790.eub.adapter.MessageAdapter;
import com.jarhero790.eub.base.BaseMVPCompatFragment;
import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.bean.Message;
import com.jarhero790.eub.bean.MessageEntity;
import com.jarhero790.eub.bean.MessageLike;
import com.jarhero790.eub.bean.MessageSystem;
import com.jarhero790.eub.bean.MessagesBean;
import com.jarhero790.eub.contract.message.MessageContract;
import com.jarhero790.eub.presenter.message.MessagePresenter;
import com.jarhero790.eub.utils.AppUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import okhttp3.ResponseBody;


public class MessageFragment extends BaseMVPCompatFragment<MessageContract.MessagePresenter>
        implements MessageContract.IMessageView {

    @BindView(R.id.recyclerViewMessage)
    RecyclerView recyclerView;



    @BindView(R.id.fensi)
    LinearLayout linearLayout_fensi;

    LinearLayoutManager layoutManager;

    private static MessageFragment messageFragment;

    public static MessageFragment newInstance() {
        if(messageFragment==null){
            messageFragment = new MessageFragment();
        }
        return messageFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void updateUI(ResponseBody response) {
       try {
            List<MessageEntity> arrayList=new ArrayList<>();
            String data=response.string();
            Log.e("123456",data);
            JSONObject js = JSONObject.parseObject(data);
            MessagesBean messagesBeans = JSON.toJavaObject(js,MessagesBean.class);
            Log.e("qawe",messagesBeans.toString());
            // Toast.makeText(AppUtils.getContext(),data,Toast.LENGTH_LONG).show();
            JSONObject jsonObject = JSONObject.parseObject(data);
            String code=String.valueOf(jsonObject.get("code"));
            String msg=(String)jsonObject.get("msg");
            ArrayList<MessageSystem> arrayListMessageSystem=new ArrayList<>();
            ArrayList<MessageLike> arrayListMessageLike=new ArrayList<>();
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray systemJsonArray=(JSONArray)jsonObject1.get("system");
            JSONArray likeJsonArray=(JSONArray)jsonObject1.get("like");
            int systemMessageLength=systemJsonArray.size();
            int likeMessageLength=likeJsonArray.size();
            MessageEntity messageEntity=new MessageEntity();

            for(int i=0;i<systemMessageLength;i++){
                JSONObject systemMessageObject=(JSONObject)systemJsonArray.get(i);
                systemMessageObject.put("viewType","1");
                JSONObject jsonobject = JSONObject.parseObject(systemMessageObject.toString());
                MessageSystem messageSystem = JSON.toJavaObject(jsonobject,MessageSystem.class);
                //arrayListMessageSystem.add(messageSystem);
                messageEntity.setMessageSystem(messageSystem);
                arrayList.add(messageEntity);
            }

            for(int j=0;j<likeMessageLength;j++){
                JSONObject likeMessageObject=(JSONObject)likeJsonArray.get(j);
                /**
                 *  { "id":117,"uid":5036,"buid":5035,
                 *    "addtime":"2019-07-30 18:07:42",
                 *    "is_likeEach":1,"is_cancle":1,
                 *    "status":1,"nickname":"java",
                 *    "headimgurl":"\/static\/images\/usertouxiang.png",
                 *    "user_id":5035
                 *  }
                 */
                Log.e("0000",likeMessageObject.toString());

                likeMessageObject.put("viewType","2");
                Log.e("0001",likeMessageObject.toString());
                /**
                {
                 "id":117,
                 "uid":5036,
                 "buid":5035,
                 "addtime":"2019-07-30 18:07:42",
                 "is_likeEach":1,
                 "is_cancle":1,
                 "status":1,
                 "nickname":"java",
                 "headimgurl":"\/static\/images\/usertouxiang.png",
                 "user_id":5035,
                 "viewType":"2"
                 }
                 */


                JSONObject jsonobject = JSONObject.parseObject(likeMessageObject.toString());
                MessageLike messageLike = JSON.toJavaObject(jsonobject,MessageLike.class);

                /**
                 *  MessageLike{id='117', uid='5036',
                 *  buid='5035', addtime='2019-07-30 18:07:42',
                 *  is_likeEach='1',
                 *  is_cancle='1',
                 *  status='1',
                 *  nickname='java',
                 *  headimgurl='/static/images/usertouxiang.png',
                 *  user_id='5035'}
                 *
                 */
                Log.e("hahhahhhha",messageLike.toString());
               // arrayListMessageLike.add(messageLike);
                messageEntity.setMessageLike(messageLike);
                arrayList.add(messageEntity);
            }


            Message message=new Message();
            message.setLike(arrayListMessageLike);
            message.setSystem(arrayListMessageSystem);
            MessagesBean messagesBean=new MessagesBean();
            messagesBean.setCode(code);
            messagesBean.setMsg(msg);
            messagesBean.setData(message);

            if(code.equals("200")){
                recyclerView.setLayoutManager(layoutManager);
                Log.e("hahhha",arrayList.toString());
                recyclerView.setAdapter(new MessageAdapter(arrayList));
            }


        }catch (IOException e1){

        }catch (JSONException e2){

        }



    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //请求数据
        mPresenter.requestMessages();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        layoutManager = new LinearLayoutManager(AppUtils.getContext());
        recyclerView.setLayoutManager(layoutManager);
        linearLayout_fensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AppUtils.getContext(),FensiActivity.class);
                startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return MessagePresenter.newInstance();
    }



}

