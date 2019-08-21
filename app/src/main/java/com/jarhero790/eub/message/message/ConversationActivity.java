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
import com.jarhero790.eub.message.contract.NameContract;
import com.jarhero790.eub.utils.CommonUtil;

import java.lang.annotation.Target;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.plugin.location.IUserInfoProvider;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

public class ConversationActivity extends FragmentActivity implements NameContract, RongIM.UserInfoProvider {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.name)
    TextView names;
    @BindView(R.id.menu_more)
    ImageView menuMore;

    private String userid;
    private String username="333";
    private String userimg="/upload/avatar/5d5bb5c80d3d6.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
//        TargetUserId
        String name = Conversation.ConversationType.PRIVATE.getName();
        int value = Conversation.ConversationType.PRIVATE.getValue();
        Log.e("---------333",name+"   "+value);//-------333: private   1


        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        String targetId = fragement.getTargetId();
        userid=targetId;
        Log.e("--------1",targetId);

        Conversation.ConversationType conversationType = fragement.getConversationType();
        Log.e("---------2",conversationType.getName());

        names.setText(targetId);

//        String title = fragement.getArguments().getString("title");
//        Log.e("----------4",title);

        fragement.getUserInfo(targetId, new IUserInfoProvider.UserInfoCallback() {
            @Override
            public void onGotUserInfo(UserInfo userInfo) {
                Log.e("-----------3",userInfo.getUserId()+","+userInfo.getName()+","+userInfo.getPortraitUri());
            }
        });


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
                intent.putExtra("userid",userid);
                intent.putExtra("username",username);
                intent.putExtra("userimg",userimg);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getNickName(String name) {
        Log.e("------------name:",name+"  "+Conversation.ConversationType.PRIVATE.getName());
    }


    @Override
    public UserInfo getUserInfo(String s) {
        Log.e("---------who:",s);
        return new UserInfo(userid,username,Uri.parse(Api.TU+userimg));
    }
}
