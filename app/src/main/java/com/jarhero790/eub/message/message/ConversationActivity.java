package com.jarhero790.eub.message.message;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.bean.UserInfo;
import com.jarhero790.eub.message.contract.NameContract;
import com.jarhero790.eub.utils.CommonUtil;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.plugin.location.IUserInfoProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;

public class ConversationActivity extends FragmentActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.name)
    TextView names;
    @BindView(R.id.menu_more)
    ImageView menuMore;

    private String userid;
    private String username="333";
    private String userimg="/upload/avatar/5d5bb5c80d3d6.png";


    private String mTargetId; //目标 Id
    private String title;//名字
    private Conversation.ConversationType mConversationType; //会话类型

    private int latestMessageId=-1;
    private int count=10;

    List<Message> messageList=new ArrayList<>();//保存所有聊天消息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
//        TargetUserId
//        String name = Conversation.ConversationType.PRIVATE.getName();
//        int value = Conversation.ConversationType.PRIVATE.getValue();
//        Log.e("---------333",name+"   "+value);//-------333: private   1


        /* 从 intent 携带的数据里获取 targetId 和会话类型*/
        Intent intent=getIntent();
        mTargetId=intent.getData().getQueryParameter("targetId");
        title=intent.getData().getQueryParameter("title");
        mConversationType= Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.US));
        Log.e("----------hehe",mTargetId+" "+title+" "+mConversationType);
        names.setText(title);








        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        String targetId = fragement.getTargetId();
        userid=targetId;
        Log.e("--------1",targetId);

        Conversation.ConversationType conversationType = fragement.getConversationType();
        Log.e("---------2",conversationType.getName());

        List<Message> checkedMessages = fragement.getCheckedMessages();
        for (int i = 0; i < checkedMessages.size(); i++) {

            Log.e("--------",checkedMessages.get(i).getContent().toString());
        }




        //历史消息
        RongIMClient.getInstance().getConversation(conversationType, targetId, new RongIMClient.ResultCallback<Conversation>() {
            @Override
            public void onSuccess(Conversation conversation) {
                if (conversation!=null){
                    int latestMessageId=   conversation.getLatestMessageId();

                    RongIMClient.getInstance().getHistoryMessages(conversationType, targetId, latestMessageId, count, new RongIMClient.ResultCallback<List<Message>>() {
                        @Override
                        public void onSuccess(List<Message> messages) {//消息有了，怎么保存
                            messageList.addAll(messages);//添加消息
                            Log.e("--------d0", messages.size() + "");//1
                            List<String> searchableWord = new ArrayList<>();
                            for (int i = 0; i < messages.size(); i++) {
                                MessageContent content = messages.get(i).getContent();
                                searchableWord = content.getSearchableWord();

                                Log.e("-----------a0", messages.get(i).getObjectName());
                                Log.e("-----------b0", messages.get(i).getExtra());
                            }

                            for (int i = 0; i < searchableWord.size(); i++) {
                                Log.e("----------c0", searchableWord.get(i));
                            }
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {

                        }
                    });
                }

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });



        //在会话界面中拿到当前页面的最后一条消息
        RongIM.getInstance().getConversation(conversationType, targetId, new RongIMClient.ResultCallback<Conversation>() {
                    @Override
                    public void onSuccess(Conversation conversation) {
                        if (conversation!=null){
                            latestMessageId = conversation.getLatestMessageId();
                            RongIMClient.getInstance().getHistoryMessages(conversationType, targetId, latestMessageId, count, new RongIMClient.ResultCallback<List<Message>>() {
                                @Override
                                public void onSuccess(List<Message> messages) {
                                    Log.e("--------d", messages.size() + "");//1
                                    List<String> searchableWord = new ArrayList<>();
                                    for (int i = 0; i < messages.size(); i++) {
                                        MessageContent content = messages.get(i).getContent();
                                        searchableWord = content.getSearchableWord();

                                        Log.e("-----------a", messages.get(i).getObjectName());
                                        Log.e("-----------b", messages.get(i).getExtra());
                                    }

                                    for (int i = 0; i < searchableWord.size(); i++) {
                                        Log.e("----------c", searchableWord.get(i));
                                    }
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {

                                }
                            });
                        }


                }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }});







//        String title = fragement.getArguments().getString("title");
//        Log.e("----------4",title);

//        fragement.getUserInfo(targetId, new IUserInfoProvider.UserInfoCallback() {
//            @Override
//            public void onGotUserInfo(UserInfo userInfo) {
//                Log.e("-----------3",userInfo.getUserId()+","+userInfo.getName()+","+userInfo.getPortraitUri());
//            }
//        });


        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(conversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", targetId).build();

//        List<Message> checkedMessages = fragement.getCheckedMessages();
//        for (int i = 0; i < checkedMessages.size(); i++) {
//            Log.e("--------message",checkedMessages.get(i).getTargetId()+"  "+checkedMessages.get(i).getObjectName()+" "+checkedMessages.get(i).getUId());
//        }





//        UserInfo userInfo = message.getUserInfo();
//        Log.e("--------usr-",userInfo.getUserId()+" "+userInfo.getName()+"  "+userInfo.getPortraitUri());

        fragement.setUri(uri);
    }

    @OnClick({R.id.back, R.id.menu_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.menu_more:
                Intent intent=new Intent(ConversationActivity.this,MoreActivity.class);
                intent.putExtra("userid",mTargetId);
                intent.putExtra("username",title);
                intent.putExtra("userimg",userimg);
                startActivity(intent);
                break;
        }
    }

//    @Override
//    public void getNickName(String name) {
//        Log.e("------------name:",name+"  "+Conversation.ConversationType.PRIVATE.getName());
//    }


//    @Override
//    public UserInfo getUserInfo(String s) {
//        Log.e("---------who:",s);
//        return new UserInfo(userid,username,Uri.parse(Api.TU+userimg));
//    }
}
