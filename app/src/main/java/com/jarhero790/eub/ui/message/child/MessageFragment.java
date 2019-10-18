package com.jarhero790.eub.ui.message.child;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jarhero790.eub.R;
import com.jarhero790.eub.activity.FensiActivity;
import com.jarhero790.eub.base.BaseMVPCompatFragment;
import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.bean.Message;
import com.jarhero790.eub.bean.MessageEntity;
import com.jarhero790.eub.bean.MessageLike;
import com.jarhero790.eub.bean.MessageSystem;
import com.jarhero790.eub.bean.MessagesBean;
import com.jarhero790.eub.contract.message.MessageContract;
import com.jarhero790.eub.message.bean.HiddBean;
import com.jarhero790.eub.message.message.GiftActivity;
import com.jarhero790.eub.message.message.JiangLiActivity;
import com.jarhero790.eub.message.message.PinLenActivity;
import com.jarhero790.eub.message.message.SysMessageActivity;
import com.jarhero790.eub.message.message.ZanActivity;
import com.jarhero790.eub.presenter.message.MessagePresenter;
import com.jarhero790.eub.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.fragment.IHistoryDataResultCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import okhttp3.ResponseBody;


public class MessageFragment extends BaseMVPCompatFragment<MessageContract.MessagePresenter>
        implements MessageContract.IMessageView {

//    @BindView(R.id.recyclerViewMessage)
//    RecyclerView recyclerView;


    @BindView(R.id.fensi)
    LinearLayout linearLayout_fensi;

//    LinearLayoutManager layoutManager;

    private static MessageFragment messageFragment;
    Unbinder unbinder;

    public static MessageFragment newInstance() {
        if (messageFragment == null) {
            messageFragment = new MessageFragment();
        }
        return messageFragment;
    }


//    String rong_token = "6a34mMUkLbpLqCOH7kzHD2e7NDDI6gS2aE/afdMaQWwiF5mo1RSLHd4a3r3cubMOxTrpQ2lZSbdx0tHHFX6Z9w==";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ee(HiddBean bean) {

    }
    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void updateUI(ResponseBody response) {




//        try {
//            List<MessageEntity> arrayList = new ArrayList<>();
//            String data = response.string();
//            Log.e("--123456", data);
//            //--123456: {"code":200,"data":{"system":[{"id":1,"admin":"\u94bb\u89c6TV","content":"\u5728\u7f8e\u56fd\u7684\u653f\u6cbb\u4f53\u5236\u4e2d\uff0c\u653f\u6cbb\u662f\u5e38\u6001\uff0c\u6218\u4e89\u662f\u4f8b\u5916\uff0c\u800c\u4e2d\u56fd\u6070\u6070\u76f8\u53cd\u201d\uff0c\u8fd9\u4e00\u89c2\u70b9\u8352\u5510\u5f97\u4ee4\u4eba\u55b7\u996d","status":1,"img":"http:\/\/aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com\/06e160f141261abb8c6a8b7ae9009be14910.png","addtime":"2019-07-27 13:55:17"}]},"msg":"\u6210\u529f"}
//            JSONObject js = JSONObject.parseObject(data);
//            MessagesBean messagesBeans = JSON.toJavaObject(js, MessagesBean.class);
//            Log.e("qawe", messagesBeans.toString());
//            //qawe: MessagesBean{  code='200', msg='成功', data=Message{system=[MessageSystem{viewType=null, id='1', admin='钻视TV', content='在美国的政治体制中，政治是常态，战争是例外，而中国恰恰相反”，这一观点荒唐得令人喷饭', status='1', img='http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/06e160f141261abb8c6a8b7ae9009be14910.png', addtime='2019-07-27 13:55:17'}], like=null}}
//            // Toast.makeText(AppUtils.getContext(),data,Toast.LENGTH_LONG).show();
//            JSONObject jsonObject = JSONObject.parseObject(data);
//            String code = messagesBeans.getCode();
//            String msg = messagesBeans.getMsg();
//            ArrayList<MessageSystem> arrayListMessageSystem = new ArrayList<>();
//            ArrayList<MessageLike> arrayListMessageLike = new ArrayList<>();
//            arrayListMessageSystem=messagesBeans.getData().getSystem();
//            arrayListMessageLike=messagesBeans.getData().getLike();
//
//
////
//            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//            JSONArray systemJsonArray = (JSONArray) jsonObject1.get("system");
//            JSONArray likeJsonArray = (JSONArray) jsonObject1.get("like");
////            int systemMessageLength = systemJsonArray.size();
////            int likeMessageLength = likeJsonArray.size();
//
//            int systemMessageLength=arrayListMessageSystem.size();
//            int likeMessageLength=arrayListMessageLike.size();
//            MessageEntity messageEntity = new MessageEntity();
//
//            for (int i = 0; i < systemMessageLength; i++) {
//                JSONObject systemMessageObject = (JSONObject) systemJsonArray.get(i);
//                systemMessageObject.put("viewType", "1");
//                JSONObject jsonobject = JSONObject.parseObject(systemMessageObject.toString());
//                MessageSystem messageSystem = JSON.toJavaObject(jsonobject, MessageSystem.class);
//                //arrayListMessageSystem.add(messageSystem);
//                messageEntity.setMessageSystem(messageSystem);
//                arrayList.add(messageEntity);
//            }
//
//            for (int j = 0; j < likeMessageLength; j++) {
//                JSONObject likeMessageObject = (JSONObject) likeJsonArray.get(j);
//                /**
//                 *  { "id":117,"uid":5036,"buid":5035,
//                 *    "addtime":"2019-07-30 18:07:42",
//                 *    "is_likeEach":1,"is_cancle":1,
//                 *    "status":1,"nickname":"java",
//                 *    "headimgurl":"\/static\/images\/usertouxiang.png",
//                 *    "user_id":5035
//                 *  }
//                 */
//                Log.e("0000", likeMessageObject.toString());
//
//                likeMessageObject.put("viewType", "2");
//                Log.e("0001", likeMessageObject.toString());
//                /**
//                 {
//                 "id":117,
//                 "uid":5036,
//                 "buid":5035,
//                 "addtime":"2019-07-30 18:07:42",
//                 "is_likeEach":1,
//                 "is_cancle":1,
//                 "status":1,
//                 "nickname":"java",
//                 "headimgurl":"\/static\/images\/usertouxiang.png",
//                 "user_id":5035,
//                 "viewType":"2"
//                 }
//                 */
//
//
//                JSONObject jsonobject = JSONObject.parseObject(likeMessageObject.toString());
//                MessageLike messageLike = JSON.toJavaObject(jsonobject, MessageLike.class);
//
//                /**
//                 *  MessageLike{id='117', uid='5036',
//                 *  buid='5035', addtime='2019-07-30 18:07:42',
//                 *  is_likeEach='1',
//                 *  is_cancle='1',
//                 *  status='1',
//                 *  nickname='java',
//                 *  headimgurl='/static/images/usertouxiang.png',
//                 *  user_id='5035'}
//                 *
//                 */
//                Log.e("hahhahhhha", messageLike.toString());
//                // arrayListMessageLike.add(messageLike);
//                messageEntity.setMessageLike(messageLike);
//                arrayList.add(messageEntity);
//            }
//
//
//            Message message = new Message();
//            message.setLike(arrayListMessageLike);
//            message.setSystem(arrayListMessageSystem);
//            MessagesBean messagesBean = new MessagesBean();
//            messagesBean.setCode(code);
//            messagesBean.setMsg(msg);
//            messagesBean.setData(message);
//
//            if (code.equals("200")) {
//                recyclerView.setLayoutManager(layoutManager);
//                Log.e("hahhha", arrayList.toString());
//                recyclerView.setAdapter(new MessageAdapter(arrayList));
//            }
//
//
//        } catch (IOException e1) {
//
//        } catch (JSONException e2) {
//
//        }


    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //请求数据
//        mPresenter.requestMessages();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //融
//        rongyun();

        initConversationList();




    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        EventBus.getDefault().post(new HiddBean("true"));
//    }

    /**
     * <p> 获取当前用户本地会话列表的默认方法，该方法返回的是以下类型的会话列表：私聊，群组，系统会话。如果
     * 您需要获取其它类型的会话列表,可以使用{@link #)} 方法。
     * <strong>注意：</strong>当更换设备或者清除缓存后，拉取到的是暂存在融云服务器中该账号当天收发过消息的会话列表。</p>
     *
     * @param  。
     */
//    public void getConversationList(final RongIMClient.ResultCallback<List<Conversation>> callback) {
//
//    }

//    private void rongyun() {
//        RongIM.connect(rong_token, new RongIMClient.ConnectCallback() {
//            @Override
//            public void onTokenIncorrect() {
//                Log.d(TAG, "onTokenIncorrect");
//            }
//
//            @Override
//            public void onSuccess(String userId) {
//                Log.d(TAG, "ConnectCallback connect onSuccess=" + userId);
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                Log.d(TAG, "ConnectCallback connect onError-ErrorCode=" + errorCode);
//            }
//        });
//        initConversationList();
//    }


    private void initConversationList() {
        //动态的
        FragmentManager fragmentManage = getChildFragmentManager();
        ConversationListFragment fragement = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        fragement.setUri(uri);

//        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
//            @Override
//            public void onSuccess(List<Conversation> conversations) {
//                Log.e("-----------------conf",conversations.size()+" ");
//
//                for (int i = 0; i < conversations.size(); i++) {
//                    conversations.get(i).getLatestMessageId();
//                }
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        }, Conversation.ConversationType.PRIVATE);
//        fragement.getConversationList(conversationType, new IHistoryDataResultCallback<List<Conversation>>() {
//            @Override
//            public void onResult(List<Conversation> conversations) {
//
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });


        //不需要启动,静态的
//        Map<String, Boolean> supportedConversation=new HashMap<>();
//        supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(),true); // 会话列表需要显示私聊会话, 第二个参数 true 代表私聊会话需要聚合显示
//        supportedConversation.put(Conversation.ConversationType.GROUP.getName(),false);// 会话列表需要显示群组会话, 第二个参数 false 代表群组会话不需要聚合显示
//        supportedConversation.put(Conversation.ConversationType.PUBLIC_SERVICE.getName(),false);
//        supportedConversation.put(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(),false);
//        supportedConversation.put(Conversation.ConversationType.SYSTEM.getName(),true);



//        RongIM.getInstance().startConversationList(getActivity(),supportedConversation);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
//        layoutManager = new LinearLayoutManager(AppUtils.getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        linearLayout_fensi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AppUtils.getContext(), FensiActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return MessagePresenter.newInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fensi, R.id.zan, R.id.gift, R.id.pinglun, R.id.jiangli,R.id.message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fensi:
                Intent intent = new Intent(AppUtils.getContext(), FensiActivity.class);
                startActivity(intent);
                break;
            case R.id.zan:
                Intent intentz = new Intent(AppUtils.getContext(), ZanActivity.class);
                startActivity(intentz);
                break;
            case R.id.gift:
                Intent intentg = new Intent(AppUtils.getContext(), GiftActivity.class);
                startActivity(intentg);
                break;
            case R.id.pinglun:
                Intent intentp = new Intent(AppUtils.getContext(), PinLenActivity.class);
                startActivity(intentp);
                break;
            case R.id.jiangli:
                Intent intentj = new Intent(AppUtils.getContext(), JiangLiActivity.class);
                startActivity(intentj);
                break;
            case R.id.message:
                Intent intentm = new Intent(AppUtils.getContext(), SysMessageActivity.class);
                startActivity(intentm);
                break;
        }
    }
}

