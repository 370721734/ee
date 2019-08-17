package com.jarhero790.eub;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.jarhero790.eub.aop.annotation.LoginFilter;
import com.jarhero790.eub.base.BaseCompatActivity;
import com.jarhero790.eub.record.TCVideoRecordActivity;
import com.jarhero790.eub.ui.attention.child.AttentionFragment;
import com.jarhero790.eub.ui.message.child.MessageFragment;
import com.jarhero790.eub.ui.mine.child.MineFragment;
import com.jarhero790.eub.ui.souye.child.SouyeFragment;
import com.jarhero790.eub.utils.AppUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseCompatActivity implements  View.OnClickListener {

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

    private SupportFragment[] mFragments = new SupportFragment[4];
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;


    public String getSHA1Signature(Context context){
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
        setStatusBarTransparent();
        requestPermissions(this);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, false, GlobalApplication.getContext());
        Log.e("---------","onDestroy");
    }



    public void clickHome(){
        int[] widthHeight=AppUtils.getScreenWidthHeight(this);
        frameLayout.getLayoutParams().height=widthHeight[1];
        indicatorHome.setVisibility(View.VISIBLE);
        indicatorAttention.setVisibility(View.INVISIBLE);
        indicatorMessage.setVisibility(View.INVISIBLE);
        indicatorMine.setVisibility(View.INVISIBLE);
        showHideFragment(mFragments[FIRST]);
    }



    @LoginFilter(loginDefine = 0)
    public void clickAttention(){
        int[] widthHeight=AppUtils.getScreenWidthHeight(this);
        int screenHeight=widthHeight[1];
        /**
         * android 动态设置Framelayout,view,imageView,Layout高度
         */
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                ,screenHeight-(bottomNavigator.getHeight()+40));
        frameLayout.setLayoutParams(params);
        indicatorHome.setVisibility(View.INVISIBLE);
        indicatorAttention.setVisibility(View.VISIBLE);
        indicatorMessage.setVisibility(View.INVISIBLE);
        indicatorMine.setVisibility(View.INVISIBLE);
        showHideFragment(mFragments[SECOND]);
    }

    @LoginFilter(loginDefine = 0)
    public void clickMessage(){
        int[] widthHeight=AppUtils.getScreenWidthHeight(this);
        int screenHeight=widthHeight[1];
        /**
         * android 动态设置Framelayout,view,imageView,Layout高度
         */
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                ,screenHeight-(bottomNavigator.getHeight()+40));
        frameLayout.setLayoutParams(params);
        indicatorHome.setVisibility(View.INVISIBLE);
        indicatorAttention.setVisibility(View.INVISIBLE);
        indicatorMessage.setVisibility(View.VISIBLE);
        indicatorMine.setVisibility(View.INVISIBLE);
        showHideFragment(mFragments[THIRD]);
    }

    @LoginFilter(loginDefine = 0)
    public void clickMine(){
        int[] widthHeight=AppUtils.getScreenWidthHeight(this);
        int screenHeight=widthHeight[1];
        /**
         * android 动态设置Framelayout,view,imageView,Layout高度
         */
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                ,screenHeight-(bottomNavigator.getHeight()+40));

        frameLayout.setLayoutParams(params);
        indicatorHome.setVisibility(View.INVISIBLE);
        indicatorAttention.setVisibility(View.INVISIBLE);
        indicatorMessage.setVisibility(View.INVISIBLE);
        indicatorMine.setVisibility(View.VISIBLE);
        showHideFragment(mFragments[FOURTH]);
    }



    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.home:
              clickHome();
              break;
          case R.id.attention:
              clickAttention();
              break;
          case R.id.record:
              Intent intent=new Intent(MainActivity.this,TCVideoRecordActivity.class);
              startActivity(intent);
              break;
          case R.id.message:
              clickMessage();
              break;
          case R.id.mine:
              clickMine();
              break;
       }
    }
}
