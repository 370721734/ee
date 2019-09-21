package com.jarhero790.eub;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jarhero790.eub.base.AppManager;
import com.jarhero790.eub.base.BaseCompatActivity;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.bean.AddressBean;
import com.jarhero790.eub.message.bean.Conver;
import com.jarhero790.eub.record.TCVideoRecordActivity;
import com.jarhero790.eub.ui.attention.child.AttentionFragment;
import com.jarhero790.eub.ui.message.child.MessageFragment;
import com.jarhero790.eub.ui.mine.child.MineFragment;
import com.jarhero790.eub.ui.souye.child.SouyeFragment;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseCompatActivity implements View.OnClickListener {

    @BindView(R.id.frameLayoutContainer)
    FrameLayout frameLayout;

    @BindView(R.id.bottomNavigator)
    LinearLayout bottomNavigator;

    @BindView(R.id.indicator_home)
    View indicatorHome;

    @BindView(R.id.indicator_attention)
    View indicatorAttention;

    @BindView(R.id.indicator_message)
    View indicatorMessage;

    @BindView(R.id.indicator_mine)
    View indicatorMine;

    @BindView(R.id.home)
    LinearLayout home;

    @BindView(R.id.attention)
    LinearLayout attention;

    @BindView(R.id.record)
    ImageView record;

    @BindView(R.id.message)
    LinearLayout message;

    @BindView(R.id.mine)
    LinearLayout mine;

    GlobalApplication app;
    @BindView(R.id.souye_home)
    TextView souyeHome;
    @BindView(R.id.souye_attion)
    TextView souyeAttion;
    @BindView(R.id.souye_message)
    TextView souyeMessage;
    @BindView(R.id.souye_my)
    TextView souyeMy;

    private SupportFragment[] mFragments = new SupportFragment[4];
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    //    注册位置监听器
    TencentLocationListener listener;
    TencentLocationRequest request;
    public static boolean isdingweiok = false;//开始没有定位到
    TencentLocationManager locationManager;

    private String islogin = "ddd";

    public String getSHA1Signature(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            byte[] cert = info.signatures[0].toByteArray();

            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void requestPermissions(Activity activity) {
        RxPermissions rxPermission = new RxPermissions(activity);
        //请求权限全部结果
        rxPermission.request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
                            //ToastUtils.showToast("App未能获取全部需要的相关权限，部分功能可能不能正常使用.");
                        }
                        //不管是否获取全部权限，进入主页面

                    }
                });
        //分别请求权限
        //        rxPermission.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
        //                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //                Manifest.permission.READ_CALENDAR,
        //                Manifest.permission.READ_CALL_LOG,
        //                Manifest.permission.READ_CONTACTS,
        //                Manifest.permission.READ_PHONE_STATE,
        //                Manifest.permission.READ_SMS,
        //                Manifest.permission.RECORD_AUDIO,
        //                Manifest.permission.CAMERA,
        //                Manifest.permission.CALL_PHONE,
        //                Manifest.permission.SEND_SMS)
        //注：魅族pro6s-7.0-flyme6权限没有像类似6.0以上手机一样正常的提示dialog获取运行时权限，而是直接默认给了权限。魅族pro6s动态获取权限不会回调下面的方法
        //        rxPermission.requestEach(
        //                Manifest.permission.CAMERA,
        //                Manifest.permission.READ_PHONE_STATE,
        //                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //                Manifest.permission.READ_EXTERNAL_STORAGE,
        //                Manifest.permission.ACCESS_COARSE_LOCATION)
        //                .subscribe(new Consumer<Permission>() {
        //                    @Override
        //                    public void accept(Permission permission) throws Exception {
        //                        if (permission.granted) {
        //                            // 用户已经同意该权限
        //                            Log.d(TAG, permission.name + " is granted.");
        //                        } else if (permission.shouldShowRequestPermissionRationale) {
        //                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
        //                            Log.d(TAG, permission.name + " is denied. More info should
        // be provided.");
        //                        } else {
        //                            // 用户拒绝了该权限，并且选中『不再询问』
        //                            Log.d(TAG, permission.name + " is denied.");
        //                        }
        //                    }
        //                });
    }


    /**
     * 把状态栏设成透明
     */
    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setOnApplyWindowInsetsListener((v, insets) -> {
                WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                return defaultInsets.replaceSystemWindowInsets(
                        defaultInsets.getSystemWindowInsetLeft(),
                        0,
                        defaultInsets.getSystemWindowInsetRight(),
                        defaultInsets.getSystemWindowInsetBottom());
            });
            ViewCompat.requestApplyInsets(decorView);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = SouyeFragment.newInstance();
            mFragments[SECOND] = AttentionFragment.newInstance();
            mFragments[THIRD] = MessageFragment.newInstance();
            mFragments[FOURTH] = MineFragment.newInstance();
            loadMultipleRootFragment(R.id.frameLayoutContainer, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            mFragments[FIRST] = findFragment(SouyeFragment.class);
            mFragments[SECOND] = findFragment(AttentionFragment.class);
            mFragments[THIRD] = findFragment(MessageFragment.class);
            mFragments[FOURTH] = findFragment(MineFragment.class);
        }
        home.setOnClickListener(this);
        attention.setOnClickListener(this);
        record.setOnClickListener(this);
        message.setOnClickListener(this);
        mine.setOnClickListener(this);
    }


    /**
     * 布局文件
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_copy;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.e("签名",getSHA1Signature(AppUtils.getContext()));
        EventBus.getDefault().register(this);
        setStatusBarTransparent();
        requestPermissions(this);
//        String sdkVersionStr = TXLiveBase.getSDKVersionStr();
//        Log.e("---------sdk",sdkVersionStr);//3.0.1185
        locationManager = TencentLocationManager.getInstance(this);
        app = (GlobalApplication) getApplication();
        AppManager.getAppManager().addActivity(this);

        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            if (checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 0);
            } else {
                initdate();
            }
        } else {
            initdate();
        }


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(Conver bean) {
//        Log.e("--------ksksk=>", bean.getName());
        islogin = bean.getName();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, false, GlobalApplication.getContext());
        Log.e("---------", "onDestroy");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (locationManager != null)
            locationManager.removeUpdates(listener);


    }

    @Override
    protected void onStop() {
        super.onStop();
        islogin = "33333333";
        if (isdingweiok) {
            locationManager.removeUpdates(listener);
        }

    }

    public void clickHome() {
        int[] widthHeight = AppUtils.getScreenWidthHeight(this);
        frameLayout.getLayoutParams().height = widthHeight[1];
        indicatorHome.setVisibility(View.VISIBLE);
        indicatorAttention.setVisibility(View.INVISIBLE);
        indicatorMessage.setVisibility(View.INVISIBLE);
        indicatorMine.setVisibility(View.INVISIBLE);
        showHideFragment(mFragments[FIRST]);
    }


    //    @LoginFilter(loginDefine = 0)
    public void clickAttention() {
        int[] widthHeight = AppUtils.getScreenWidthHeight(this);
        int screenHeight = widthHeight[1];
        /**
         * android 动态设置Framelayout,view,imageView,Layout高度
         */
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                , screenHeight - (bottomNavigator.getHeight() + 40));
        frameLayout.setLayoutParams(params);
        indicatorHome.setVisibility(View.INVISIBLE);
        indicatorAttention.setVisibility(View.VISIBLE);
        indicatorMessage.setVisibility(View.INVISIBLE);
        indicatorMine.setVisibility(View.INVISIBLE);
        showHideFragment(mFragments[SECOND]);
    }

    //    @LoginFilter(loginDefine = 0)
    public void clickMessage() {
        int[] widthHeight = AppUtils.getScreenWidthHeight(this);
        int screenHeight = widthHeight[1];
        /**
         * android 动态设置Framelayout,view,imageView,Layout高度
         */
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                , screenHeight - (bottomNavigator.getHeight() + 40));
        frameLayout.setLayoutParams(params);
        indicatorHome.setVisibility(View.INVISIBLE);
        indicatorAttention.setVisibility(View.INVISIBLE);
        indicatorMessage.setVisibility(View.VISIBLE);
        indicatorMine.setVisibility(View.INVISIBLE);
        showHideFragment(mFragments[THIRD]);
    }

    //    @LoginFilter(loginDefine = 0)
    public void clickMine() {
        int[] widthHeight = AppUtils.getScreenWidthHeight(this);
        int screenHeight = widthHeight[1];
        /**
         * android 动态设置Framelayout,view,imageView,Layout高度
         */
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                , screenHeight - (bottomNavigator.getHeight() + 40));

        frameLayout.setLayoutParams(params);
        indicatorHome.setVisibility(View.INVISIBLE);
        indicatorAttention.setVisibility(View.INVISIBLE);
        indicatorMessage.setVisibility(View.INVISIBLE);
        indicatorMine.setVisibility(View.VISIBLE);
        showHideFragment(mFragments[FOURTH]);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:

                souyeHome.setTextColor(Color.parseColor("#FFFFFF"));
                souyeAttion.setTextColor(Color.parseColor("#A5A2A2"));
                souyeMessage.setTextColor(Color.parseColor("#A5A2A2"));
                souyeMy.setTextColor(Color.parseColor("#A5A2A2"));

                clickHome();
                break;
            case R.id.attention:

//              if (islogin.equals("ddd")){
//                  return;
//              }
//              if (islogin.equals("400")){
//
//              }else {
//
//              }


                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("") || islogin.equals("400")) {
                    Intent intent = new Intent(this, LoginNewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {

                    souyeHome.setTextColor(Color.parseColor("#A5A2A2"));
                    souyeAttion.setTextColor(Color.parseColor("#FFFFFF"));
                    souyeMessage.setTextColor(Color.parseColor("#A5A2A2"));
                    souyeMy.setTextColor(Color.parseColor("#A5A2A2"));


                    clickAttention();


                }


                break;
            case R.id.record:
//              if (islogin.equals("ddd")){
//                  return;
//              }
//              if (islogin.equals("400")){
//
//              }else {
//
//              }

                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("") || islogin.equals("400")) {
                    Intent intent = new Intent(this, LoginNewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, TCVideoRecordActivity.class);
                    startActivity(intent);
                }


                break;
            case R.id.message:
//              if (islogin.equals("ddd")){
//                  Log.e("-------------","ddd");
//                  return;
//              }
//              if (islogin.equals("400")){
//
//              }else {
//
//              }

                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("") || islogin.equals("400")) {
                    Intent intent = new Intent(this, LoginNewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {

                    souyeHome.setTextColor(Color.parseColor("#A5A2A2"));
                    souyeAttion.setTextColor(Color.parseColor("#A5A2A2"));
                    souyeMessage.setTextColor(Color.parseColor("#FFFFFF"));
                    souyeMy.setTextColor(Color.parseColor("#A5A2A2"));

                    clickMessage();
                }

                break;
            case R.id.mine:
//              if (islogin.equals("ddd")){
//                  return;
//              }
//              if (islogin.equals("400")){
//
//              }else {
//
//              }


                if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("") || islogin.equals("400")) {
                    Intent intent = new Intent(this, LoginNewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    souyeHome.setTextColor(Color.parseColor("#A5A2A2"));
                    souyeAttion.setTextColor(Color.parseColor("#A5A2A2"));
                    souyeMessage.setTextColor(Color.parseColor("#A5A2A2"));
                    souyeMy.setTextColor(Color.parseColor("#FFFFFF"));
                    clickMine();
                }

                break;
        }
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                AppManager.getAppManager().finishActivity();
                AppManager.getAppManager().AppExit(this);
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //定位
    private void initdate() {

        //腾讯
//        tencentMap = mMapView.getMap();
//        tencentMap.setMapType(TencentMap.MAP_TYPE_NORMAL);//普通地图

//        UiSettings类 对地图手势及SDK提供的控件的控制
//        UiSettings mapUiSettings = tencentMap.getUiSettings();
//        mapUiSettings.setCompassEnabled(true);//指南针
//        mapUiSettings.setScrollGesturesEnabled(true);//平移


        //定位
//        setInterval	设置定位周期(位置监听器回调周期), 单位为 ms (毫秒)
//        setRequestLevel	设置定位的 request level
//        setAllowCache	设置是否允许使用缓存, 连续多次定位时建议允许缓存
//        setQQ	设置 QQ 号
//        Request level 决定定位结果中包含哪些信息，分为以下几类：
//
//        Request Level	值	含义
//        REQ_LEVEL_GEO	0	包含经纬度
//        REQ_LEVEL_NAME	1	包含经纬度, 位置名称, 位置地址
//        REQ_LEVEL_ADMIN_AREA	3	包含经纬度，位置所处的中国大陆行政区划
//        REQ_LEVEL_POI	4	包含经纬度，位置所处的中国大陆行政区划及周边POI列表
        request = TencentLocationRequest.create();
        request.setAllowCache(false);
        request.setAllowGPS(true);
        request.setInterval(100000);


        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);


//
        listener = new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation location, int error, String reason) {
//                Log.e("---location:1", location.getAddress() + "," + error + "," + reason + "," + location.getLatitude());


//                Log.e("---location:2",location.getCity()+location.getProvince());//深圳市
                if (TencentLocation.ERROR_OK == error) {
                    isdingweiok = true;
//                    Log.e("------", "成1功"+location.getCity());
                    if (location != null && location.getCity() != null && location.getCity().length() > 0) {
                        app.setCITY(location.getCity());
                        EventBus.getDefault().post(new AddressBean((location.getCity()).replace("市", "")));
                    }
//
//                    //-: 成1功
//                    //location:: 广东省深圳市宝安区沙井镇上星村河宾南路上星大厦正东方向196米,0,OK
//
//                    //新的缩放级别 ,//俯仰角 0~45° (垂直地图时为0),//偏航角 0~360° (正北方为0)   new LatLng(39.977290,116.337000
//                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 0, 0));
//                    tencentMap.moveCamera(cameraUpdate);//移动地图
//
//                    //标注坐标
//                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    if (marker == null) {
//                        marker = tencentMap.addMarker(new MarkerOptions().position(latLng)
//                                .title("我的位置").snippet("DefaultMarker"));
//
//                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dingweijing));
//                    } else {
//                        Log.e("------", "mmmmmm");
//                    }
                } else {
//                    Log.e("------", "失1败"+error+"   "+reason);
                    isdingweiok = false;

                }


            }

            @Override
            public void onStatusUpdate(String name, int status, String desc) {
                Log.e("-----name,", name + "," + status + "," + desc);
                if (status == STATUS_DISABLED) {
                    /* 检测到定位权限被内置或第三方的权限管理或安全软件禁用, 导致当前应用**很可能无法定位**
                     * 必要时可对这种情况进行特殊处理, 比如弹出提示或引导
                     */
//                    Toast.makeText(CheDetailsActivity.this, "定位权限被禁用!", Toast.LENGTH_SHORT).show();
//                    premissiongo();
                    requestPermissions(MainActivity.this);
                }
//                if (name.equals("wifi")){
//
//                }


            }
        };

//        TencentLocationManager locationManager = TencentLocationManager.getInstance(this);


        int error = locationManager.requestLocationUpdates(request, listener);
        if (error == 0) {
            Log.e("------", "成功");
        } else {
            Log.e("------", "失败" + error);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        initdate();
    }


}
