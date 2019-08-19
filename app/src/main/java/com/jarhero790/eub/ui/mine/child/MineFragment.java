package com.jarhero790.eub.ui.mine.child;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.base.BaseMVPCompatFragment;
import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.bean.User;
import com.jarhero790.eub.bean.UserBean;
import com.jarhero790.eub.contract.mine.MineMainContract;
import com.jarhero790.eub.eventbus.MessageEventUser;
import com.jarhero790.eub.message.bean.UserCen;
import com.jarhero790.eub.message.my.QianDaoActivity;
import com.jarhero790.eub.presenter.mine.MineMainPresenter;
import com.jarhero790.eub.ui.mine.FragmentLike;
import com.jarhero790.eub.ui.mine.FragmentZuoping;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MineFragment extends BaseMVPCompatFragment<MineMainContract.MineMainPresenter>
        implements MineMainContract.IMineMainView {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.textViewNickName)
    TextView textViewNickName;


    public static MineFragment fragment;


    GlobalApplication app;
    @BindView(R.id.tv_tvhao)
    TextView tvTvhao;
    @BindView(R.id.iv_userimage)
    ImageView ivUserimage;
    Unbinder unbinder;
    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;
    @BindView(R.id.fensi)
    TextView fensi;
    @BindView(R.id.tv_huozai)
    TextView tvHuozai;
    @BindView(R.id.tv_memo)
    TextView tvMemo;

    private String money;//签到金币
    private String signtime;//最后签到时间


    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        if (fragment == null) {
            fragment = new MineFragment();
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);

    }


    /**
     * 在com.zuanshitv.app.activity.UserNameLoginActivity中登录成功后获取到的数据发送
     * 在这里接收
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEventUser messageEvent) {

        User user = messageEvent.getMessage().getData();
        Log.e("-----1", user.getId() + "  " + user.getHeadimgurl());
        textViewNickName.setText(user.getNickname());
        tvTvhao.setText("钻视TV号:" + user.getId());
        Glide.with(getActivity()).load(Api.HOST + user.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.music).error(R.mipmap.music)).into(ivUserimage);

        tvMemo.setText(user.getSign());
        money=user.getMoney();
        signtime=user.getSigntime();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        mPresenter.getTabList();
        mPresenter.getuserinfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        app = (GlobalApplication) getActivity().getApplication();
        qiandao();


        UserBean userBean = app.getUserbean();
        if (userBean != null) {
            Log.e("--------2", userBean.data.getId() + "  " + userBean.data.getHeadimgurl());
        }


    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        tabLayout.addTab(tabLayout.newTab().setText("作 品"));//给TabLayout添加Tab
        tabLayout.addTab(tabLayout.newTab().setText("喜 欢"));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 50, 50);
            }
        });

        tabLayout.getTabAt(0).select();
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, FragmentZuoping.newInstance()); //连接TabLayout下的Fragment需要放置的位置
        transaction.commit();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fm = getChildFragmentManager();
                //开启事务
                FragmentTransaction transaction;
                switch (tab.getPosition()) {
                    case 0:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.container, FragmentZuoping.newInstance()); //连接TabLayout下的Fragment需要放置的位置
                        transaction.commit();
                        break;

                    case 1:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.container, FragmentLike.newInstance()); //连接TabLayout下的Fragment需要放置的位置
                        transaction.commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return MineMainPresenter.newInstance();
    }

    @Override
    public void showTabList(String[] tabs) {
        Log.e("---------11", Arrays.toString(tabs));
    }

    @Override
    public void showuserinfo(ResponseBody response) {
        Log.e("---------", "0");
//        Log.e("-------",response.toString());
        try {
            String data = response.string();


            JSONObject object = JSONObject.parseObject(data);
            Log.e("---------dss", "" + object);

            JSONObject js = JSONObject.parseObject(data);
            UserCen userInfo = JSON.toJavaObject(js, UserCen.class);
            Log.e("qawe", userInfo.getData().getUser().getHeadimgurl());//有了

            if (userInfo.getCode()==200){
                tvMemo.setText(userInfo.getData().getUser().getSign());
                money=userInfo.getData().getUser().getMoney()+"";
                signtime=userInfo.getData().getUser().getSigntime();
            }


            String code = object.getString("code");

            Log.e("---------111", "" + code);

            String msg = object.getString("msg");

            JSONObject jsonObject1 = object.getJSONObject("data");
            String zan = jsonObject1.getString("zan");
            String like = jsonObject1.getString("like");
            String fens = jsonObject1.getString("fensi");
            Log.e("---------2222", "" + zan + "  " + like + "  " + fens);


            if (code.equals("200")) {
                textViewNickName.setText(userInfo.getData().getUser().getNickname());
                tvTvhao.setText("钻视TV号:" + userInfo.getData().getUser().getId());
                Glide.with(getActivity()).load(Api.HOST + userInfo.getData().getUser().getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.music).error(R.mipmap.music)).into(ivUserimage);


                tvHuozai.setText(zan);
                tvGuanzhu.setText(like);
                fensi.setText(fens);

            } else {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            }


            Log.e("123456", data);
//            Log.e("-------22-",response.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.dingwei, R.id.tv_qiandao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dingwei:
                break;
            case R.id.tv_qiandao:
                //签到
                Intent intent=new Intent(getActivity(), QianDaoActivity.class);
                intent.putExtra("money",money);
                intent.putExtra("signtime",signtime);
                startActivity(intent);
                break;
        }
    }

    //签到
    private void qiandao() {


        //通过RequestBody.create 创建requestBody对象
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", SharePreferenceUtil.getToken(AppUtils.getContext()))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Api.HOST + "web/index/signIn").post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("注册异常", e.getMessage());
                Toast.makeText(getActivity(), "异常" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {//{"code":200,"data":15,"msg":""}
                    Log.e("-----1:", result);
                    org.json.JSONObject jsonObject = new org.json.JSONObject(result);
                    int code = jsonObject.optInt("code");
                    String msg = (String) jsonObject.get("msg");
                    Log.e("注册结果msg值", msg);
                    if (code == 200) {
//                        org.json.JSONObject data = jsonObject.optJSONObject("data");
//                        msgid=data.optString("msgId");
//                                Log.e("----------2:",msgid);//ok

//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }

//                Log.e("注册结果", result);
                //Toast.makeText(RegisterByUsernameActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
            }
        });
    }
}

