package com.jarhero790.eub.ui.mine.child;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import com.jarhero790.eub.activity.FensiActivity;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.base.BaseMVPCompatFragment;
import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.bean.User;
import com.jarhero790.eub.contract.mine.MineMainContract;
import com.jarhero790.eub.eventbus.MessageEventUser;
import com.jarhero790.eub.message.bean.AddressBean;
import com.jarhero790.eub.message.bean.Conver;
import com.jarhero790.eub.message.bean.GuangZuBean;
import com.jarhero790.eub.message.bean.UserCen;
import com.jarhero790.eub.message.message.ZanActivity;
import com.jarhero790.eub.message.my.GuangZuActivity;
import com.jarhero790.eub.message.my.QianDaoActivity;
import com.jarhero790.eub.message.my.SettingActivity;
import com.jarhero790.eub.presenter.mine.MineMainPresenter;
import com.jarhero790.eub.ui.mine.FragmentLike;
import com.jarhero790.eub.ui.mine.FragmentZuoping;
import com.jarhero790.eub.utils.NetworkConnectionUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
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
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.iv_bot1)
    View ivBot1;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.iv_bot2)
    View ivBot2;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.container)
    FrameLayout container;
//    @BindView(R.id.bingds)
//    AppBarLayout binding;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinator_layout;


    private String money;//签到金币
    private String signtime;//最后签到时间


    List<UserInfo> userInfoList = new ArrayList<>();


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

    public void scrollToTop() {
        //拿到 appbar 的 behavior,让 appbar 滚动
//        ViewGroup.LayoutParams layoutParams = binding.getLayoutParams();

//        View mappbar=binding.getChildAt(0);
//        AppBarLayout.LayoutParams mappbarparams= (AppBarLayout.LayoutParams) mappbar.getLayoutParams();
//        mappbarparams.setScrollFlags(0);


//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) binding.getLayoutParams();
//        AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) params.getBehavior();
//        if (appBarLayoutBehavior!=null)
//        appBarLayoutBehavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
//            @Override
//            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
//                return false;
//            }
//        });
//        CoordinatorLayout.Behavior behavior =params.getBehavior();
//                ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();

//        if (behavior instanceof AppBarLayout.Behavior) {
//            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
//            //拿到下方tabs的y坐标，即为我要的偏移量
//            float y = binding.getY();
//            //注意传递负值
//            appBarLayoutBehavior.setTopAndBottomOffset((int) -y);
//
//            appBarLayoutBehavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
//                @Override
//                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
//                    return false;
//                }
//            });
//        }
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
//        Log.e("-----1", user.getId() + "  " + user.getHeadimgurl());
        textViewNickName.setText(user.getNickname());
        tvTvhao.setText("钻视TV号:" + user.getId());
        Glide.with(getActivity()).load(user.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo).error(R.mipmap.zuanshi_logo)).into(ivUserimage);
//        if (user.getHeadimgurl().startsWith("http")) {
//
//        } else {
//            Glide.with(getActivity()).load(Api.TU + user.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.music).error(R.mipmap.music)).into(ivUserimage);
//        }

        tvMemo.setText(user.getSign());
        money = user.getMoney();
        signtime = user.getSigntime();


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

        if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
            mPresenter.getuserinfo();
            mPresenter.getmyguangzu();
        } else {
            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    float mPosX;
    float mPosY;
    float mCurPosX;
    float mCurPosY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        scrollToTop();

        coordinator_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
//                        if ((mCurPosY-mPosY>0 && (Math.abs(mCurPosY-mPosY)>25)) || (mCurPosY-mPosY<0 && (Math.abs(mCurPosY-mPosY)>25))){
//                            Log.e("----------------","向上向下滑动了");
//                        }else {
//                            Log.e("----------------","向上向下滑动了000");
//                        }

                        if (mCurPosY - mPosY > 0 && (Math.abs(mCurPosY - mPosY) > 25)) {
//                            Log.e("----------------","向上向下滑动了1");
                        } else if (mCurPosY - mPosY < 0 && (Math.abs(mCurPosY - mPosY) > 25)) {
//                            Log.e("----------------","向上向下滑动了2");
                        }else {
//                            Log.e("----------------","向上向下滑动了3");
                        }


                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        tabLayout.addTab(tabLayout.newTab().setText("作 品"));//给TabLayout添加Tab
        tabLayout.addTab(tabLayout.newTab().setText("喜 欢"));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 80, 80);

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
//        Log.e("---------11", Arrays.toString(tabs));
    }

    @Override
    public void showuserinfo(ResponseBody response) {
//        Log.e("---------", "0");
//        Log.e("-------",response.toString());
        try {
            String data = response.string();


            JSONObject object = JSONObject.parseObject(data);
//            Log.e("---------dss", "" + object);


            int code = object.getInteger("code");
            String msg = object.getString("msg");
            if (code == 400) {
//                Log.e("---------555", "" + code);
                EventBus.getDefault().post(new Conver("400"));
                //注意 登录成功之后  一定要写上这句代码
//                SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, false, AppUtils.getContext());
//                ILoginFilter abd=new ILoginFilter() {
//                    @Override
//                    public void login(Context applicationContext, int loginDefine) {
//                        Log.e("---------login=>",loginDefine+"");
//                    }
//
//                    @Override
//                    public boolean isLogin(Context applicationContext) {
//                        return false;
//                    }
//
//                    @Override
//                    public void clearLoginStatus(Context applicationContext) {
//                        SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, false, applicationContext);
//                    }
//                };
//                LoginAssistant.getInstance().setILoginFilter(abd);

//                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

//                ILoginFilter iLoginFilter = LoginAssistant.getInstance().getILoginFilter();
//                if (iLoginFilter == null){
//                    throw new RuntimeException("LoginManger没有初始化");
//                }
//                iLoginFilter.login(mContext,0);


            } else {
                EventBus.getDefault().post(new Conver("200"));
            }


            JSONObject js = JSONObject.parseObject(data);
            UserCen userInfo = JSON.toJavaObject(js, UserCen.class);
            app.setUserCen(userInfo);
            if (userInfo.getData().getUser().getHeadimgurl().equals("")) {
//                Log.e("-----------", "来了1");
                userInfo.getData().getUser().setHeadimgurl("http://www.51ayhd.com/static/images/usertouxiang.png");
            }
//            if (userInfo.getData().getUser().getHeadimgurl()==null){
//                Log.e("-----------","来了");
//            }
//            Log.e("qawe", userInfo.getData().getUser().getHeadimgurl());//有了
            if (code == 200) {

                JSONObject jsonObject1 = object.getJSONObject("data");
                String zan = jsonObject1.getString("zan");
                String like = jsonObject1.getString("like");
                String fens = jsonObject1.getString("fensi");
                textViewNickName.setText(userInfo.getData().getUser().getNickname());
                tvTvhao.setText("钻视TV号:" + userInfo.getData().getUser().getId());
                Glide.with(getActivity()).load(userInfo.getData().getUser().getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo).error(R.mipmap.zuanshi_logo)).into(ivUserimage);
//                if (userInfo.getData().getUser().getHeadimgurl().startsWith("http")) {
//
//                } else {
//                    Glide.with(getActivity()).load(Api.TU + userInfo.getData().getUser().getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.music).error(R.mipmap.music)).into(ivUserimage);
//                }
                tvHuozai.setText(zan);
                tvGuanzhu.setText(like);
                fensi.setText(fens);
                //先添加自己的
                userInfoList.add(new UserInfo(userInfo.getData().getUser().getRong_id() + "", userInfo.getData().getUser().getNickname(), Uri.parse(userInfo.getData().getUser().getHeadimgurl())));//"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1253139285,1661865494&fm=26&gp=0.jpg"
//                Log.e("----------ddata=", userInfo.getData().getUser().getRong_id() + "      " + userInfo.getData().getUser().getHeadimgurl());


            }
            if (userInfo.getCode() == 200) {
                app.setUserzhong(userInfo.getData().getUser());

                SharePreferenceUtil.setuserid(userInfo.getData().getUser().getUser_id() + "", getActivity());
                tvMemo.setText(userInfo.getData().getUser().getSign());
                money = userInfo.getData().getUser().getMoney() + "";
                signtime = userInfo.getData().getUser().getSigntime();

//                Log.e("----------token", userInfo.getData().getUser().getRong_token());


                //连接融
                connect(userInfo.getData().getUser().getRong_token(), getActivity());
                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public UserInfo getUserInfo(String userid) {
//                        Log.e("--------who2:", userid);
                        //用户信息,应该从粉丝一列表中获取
//                        initUserInfo(userid);
//                         UserInfo userInfo1=new UserInfo(userid,"名字不一样",Uri.parse("不一样的地址"));
//                         UserInfo userInfo1=new UserInfo(userid+"",userInfo.getData().getUser().getUsername(), Uri.parse("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1253139285,1661865494&fm=26&gp=0.jpg"));
//
//                         userInfoList.add(userInfo1);
//                         E/--------who2:: 5040
//                         2019-08-21 19:39:14.438 23314-23314/com.jarhero790.eub E/--------who2:: 5040
//                         2019-08-21 19:39:14.439 23314-23314/com.jarhero790.eub E/--------who2:: 5040
//                         2019-08-21 19:39:14.440 23314-23314/com.jarhero790.eub E/--------who2:: 5032
//                         2019-08-21 19:39:14.440 23314-23314/com.jarhero790.eub E/--------who2:: 5032
//                         2019-08-21 19:39:14.440 23314-23314/com.jarhero790.eub E/--------who2:: 5044
//                         --who2:: 5044
//                         2019-08-21 19:40:03.616 23314-23314/com.jarhero790.eub E/--------who2:: 5040
//                         2019-08-21 19:40:03.622 23314-23314/com.jarhero790.eub E/--------who2:: 5044
//                         2019-08-21 19:40:03.635 23314-23314/com.jarhero790.eub E/--------who2:: 5040
//                         RongIM.getInstance().setCurrentUserInfo(new UserInfo(userid,userInfo.getData().getUser().getUsername(),Uri.parse(Api.TU+userInfo.getData().getUser().getHeadimgurl())));
//                         return new UserInfo(userInfo.getData().getUser().getId()+"",userInfo.getData().getUser().getUsername(), Uri.parse("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1253139285,1661865494&fm=26&gp=0.jpg"));//Api.TU+userInfo.getData().getUser().getHeadimgurl()

                        if (userInfoList != null && userInfoList.size() > 0) {

                            for (UserInfo info : userInfoList) {
                                if (info.getUserId().equals(userid)) {
//                                    Log.e("--------------zhong-", userInfoList.size() + " =  " + info.getPortraitUri() + "   =" + info);

//                                    if (info.getPortraitUri())
//
//                                        if (userInfo.getData().getUser().getHeadimgurl().equals("")){
//                                            Log.e("-----------","来了1");
//                                            userInfo.getData().getUser().setHeadimgurl("http://www.51ayhd.com/static/images/usertouxiang.png");
//                                        }
                                    return new UserInfo(info.getUserId(), info.getName(), info.getPortraitUri());
                                }
                            }
                        }


                        return null;


                    }
                }, false);


//                RongIM.connect(userInfo.getData().getUser().getRong_token(), new RongIMClient.ConnectCallback() {
//                    @Override
//                    public void onSuccess(String userid) {
//                        Log.e("------LoginActivity", "--onSuccess" + userid);
//
//
//                    }
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                        Log.e("------LoginActivity", "--f" + errorCode.getMessage());
//                    }
//
//                    @Override
//                    public void onTokenIncorrect() {
//                        Log.e("------LoginActivity", "--3" );
//                    }
//                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    List<GuangZuBean.DataBean> dataBeanList = new ArrayList<>();

    @Override
    public void showguangzu(GuangZuBean bean) {
        dataBeanList.addAll(bean.getData());
        //关注用户的信息
        if (dataBeanList != null && dataBeanList.size() > 0) {
            for (int i = 0; i < dataBeanList.size(); i++) {
                userInfoList.add(new UserInfo(dataBeanList.get(i).getRong_id() + "", dataBeanList.get(i).getNickname(), Uri.parse(dataBeanList.get(i).getHeadimgurl())));
//                Log.e("---------------data=", dataBeanList.get(i).getHeadimgurl());

            }
        }
    }

//    //关注用户的信息
//    private void initUserInfo(String userid) {
//        if (dataBeanList!=null && dataBeanList.size()>0){
//            for (int i = 0; i < dataBeanList.size(); i++) {
//                if ((dataBeanList.get(i).getRong_id()+"").equals(userid)){
//                    userInfoList.add(new UserInfo(dataBeanList.get(i).getRong_id()+"",dataBeanList.get(i).getNickname(),Uri.parse(dataBeanList.get(i).getHeadimgurl())));
//                }
//            }
//        }
//
//    }


    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        LinearLayout llTab = null;
        try {
            if (tabStrip != null) {
                llTab = (LinearLayout) tabStrip.get(tabs);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        if (llTab != null) {
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

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void city(AddressBean bean) {
//        Log.e("--------cicici", bean.getCity());

        if (bean != null && bean.getCity() != null) {
            city.setText(bean.getCity());
        } else {
            city.setText("深圳");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (NetworkConnectionUtils.isNetworkConnected(getActivity())) {
            mPresenter.getuserinfo();
            mPresenter.getmyguangzu();
            app = (GlobalApplication) getActivity().getApplication();
//            UserBean userBean = app.getUserbean();
//            if (userBean != null) {
//                Log.e("--------2", userBean.data.getId() + "  " + userBean.data.getHeadimgurl());
//            }
//            String city = app.getCITY();
//            Log.e("------------city",city);

        } else {
            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
        }

//        Log.e("---", "onResume");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!isAdded())
            return;
    }

    @OnClick({R.id.dingwei, R.id.tv_qiandao, R.id.iv_edit, R.id.myguanzu, R.id.myfensi, R.id.myzan, R.id.ll1, R.id.ll2})
    public void onViewClicked(View view) {
        FragmentManager fm = getChildFragmentManager();
        //开启事务
        FragmentTransaction transaction;

        switch (view.getId()) {
            case R.id.dingwei:
                break;
            case R.id.tv_qiandao:
                //签到
                Intent intent = new Intent(getActivity(), QianDaoActivity.class);
                intent.putExtra("money", money);
                intent.putExtra("signtime", signtime);
                startActivity(intent);
                break;
            case R.id.iv_edit:
                if (app != null && app.getUserCen() != null && app.getUserCen().getData() != null && app.getUserCen().getData().getUser() != null) {
                    Intent intent1 = new Intent(getActivity(), SettingActivity.class);
                    if (app.getUserCen().getData().getUser().getNickname() != null)
                        intent1.putExtra("name", app.getUserCen().getData().getUser().getNickname());
                    if (app.getUserCen().getData().getUser().getSign() != null)
                        intent1.putExtra("sign", app.getUserCen().getData().getUser().getSign());
                    intent1.putExtra("sex", app.getUserCen().getData().getUser().getSex());
                    if (app.getUserCen().getData().getUser().getHeadimgurl() != null)
                        intent1.putExtra("heading", app.getUserCen().getData().getUser().getHeadimgurl());
                    if (app.getUserCen().getData().getUser().getCity() != null)
                        intent1.putExtra("city", app.getUserCen().getData().getUser().getCity());
                    startActivity(intent1);
                }

                break;

            case R.id.myguanzu:
                startActivity(new Intent(getActivity(), GuangZuActivity.class));
                break;
            case R.id.myfensi:
                startActivity(new Intent(getActivity(), FensiActivity.class));
                break;
            case R.id.myzan:
                startActivity(new Intent(getActivity(), ZanActivity.class));
                break;
            case R.id.ll1:
                ivBot1.setVisibility(View.VISIBLE);
                ivBot2.setVisibility(View.INVISIBLE);
//                vp.setCurrentItem(0);

                transaction = fm.beginTransaction();
                transaction.replace(R.id.container, FragmentZuoping.newInstance()); //连接TabLayout下的Fragment需要放置的位置
                transaction.commit();
                break;
            case R.id.ll2:
                ivBot1.setVisibility(View.INVISIBLE);
                ivBot2.setVisibility(View.VISIBLE);
//                vp.setCurrentItem(1);

                transaction = fm.beginTransaction();
                transaction.replace(R.id.container, FragmentLike.newInstance()); //连接TabLayout下的Fragment需要放置的位置
                transaction.commit();
                break;
        }
    }


    //签到  不能用
//    private void qiandao() {
//
//
//        //通过RequestBody.create 创建requestBody对象
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("token", SharePreferenceUtil.getToken(AppUtils.getContext()))
//                .build();
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(Api.HOST + "web/index/signIn").post(requestBody).build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("注册异常", e.getMessage());
//                Toast.makeText(getActivity(), "异常" + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//                try {//{"code":200,"data":15,"msg":""}
//                    Log.e("-----1:", result);
//                    org.json.JSONObject jsonObject = new org.json.JSONObject(result);
//                    int code = jsonObject.optInt("code");
//                    String msg = (String) jsonObject.get("msg");
//                    Log.e("注册结果msg值", msg);
//                    if (code == 200) {
////                        org.json.JSONObject data = jsonObject.optJSONObject("data");
////                        msgid=data.optString("msgId");
////                                Log.e("----------2:",msgid);//ok
//
////                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//
//                }
//
////                Log.e("注册结果", result);
//                //Toast.makeText(RegisterByUsernameActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
//            }
//        });
//    }


    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #init(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @param callback 连接回调。
     * @return RongIM  客户端核心类的实例。
     */
//    private void connect(String token) {
//
//        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {
//
//            RongIM.connect(token, new RongIMClient.ConnectCallback() {
//
//                /**
//                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
//                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
//                 */
//                @Override
//                public void onTokenIncorrect() {
//
//                }
//
//                /**
//                 * 连接融云成功
//                 * @param userid 当前 token 对应的用户 id
//                 */
//                @Override
//                public void onSuccess(String userid) {
//                    Log.d("LoginActivity", "--onSuccess" + userid);
////                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
////                    finish();
//                }
//
//                /**
//                 * 连接融云失败
//                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
//                 */
//                @Override
//                public void onError(RongIMClient.ErrorCode errorCode) {
//
//                }
//            });
//        }
//    }


    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    public void connect(String token, Context context) {
        if (context.getApplicationInfo().packageName.equals(GlobalApplication.getCurProcessName(context.getApplicationContext()))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             *
             *
             */


            RongIM.connect(token, new RongIMClient.ConnectCallback() {


                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的
                 * Token
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.e("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 *
                 * @param userid
                 *            当前 token
                 */
                @Override
                public void onSuccess(String userid) {
//                    EBmessage eb = new EBmessage();
//                    eb.setStatus(true);
//                    eb.setMessage("success");
//                    eb.setFrom("connect");
//                    EventBus.getDefault().post(eb);
//                    Log.e("LoginActivity", "--onSuccess" + userid);
//                    LoginActivity: --onSuccess5044
                }


                /**
                 * 连接融云失败
                 *
                 * @param errorCode
                 *            错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
//                    Log.e("LoginActivity", "--onError" + errorCode.getMessage());
                }
            });
        }
    }


}

