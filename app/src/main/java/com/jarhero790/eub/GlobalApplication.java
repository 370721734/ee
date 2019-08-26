package com.jarhero790.eub;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jarhero790.eub.bean.UserBean;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.bean.UserCen;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.tencent.ugc.TXUGCBase;
import com.jarhero790.eub.aop.logincore.ILoginFilter;
import com.jarhero790.eub.aop.logincore.LoginManger;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import io.rong.imkit.RongIM;


public class GlobalApplication extends Application {
    protected static Context context;
    protected static Handler handler;
    protected static int mainThreadId;
    private static GlobalApplication mApp;

    String ugcLicenceUrl = "http://license.vod2.myqcloud.com/license/v1/37cad01a3224fd473ab7591458a616bc/TXUgcSDK.licence"; //您从控制台申请的 licence url
    String ugcKey = "d511730b581038234dae207be1d4b3e2";

    private UserBean userbean;

    private UserCen userCen;

    public  String TOKEN;

    public static synchronized GlobalApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();
        RetrofitManager.getInstance().init(context);

        LoginManger.getInstance().init(this,iLoginFilter);
        IjkPlayerFactory ijkPlayerFactory=IjkPlayerFactory.create(this);
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                //使用使用IjkPlayer解码
                .setPlayerFactory(ijkPlayerFactory)
                //.setPlayerFactory(ExoMediaPlayerFactory.create(this))
                //.setEnableMediaCodec(true)
                //.setUsingSurfaceView(true)
                //.setEnableParallelPlay(true)
                //.setEnableAudioFocus(false)
                .setPlayOnMobileNetwork(true)
                //.setAutoRotate(true)
                .build());

        Fresco.initialize(this);
        //腾讯短视频
        TXUGCBase.getInstance().setLicence(context, ugcLicenceUrl, ugcKey);
        //融云初始化
        RongIM.init(this);


    }





    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }



    ILoginFilter iLoginFilter = new ILoginFilter() {
        @Override
        public void login(Context applicationContext, int loginDefine) {
            switch (loginDefine){
                case 0:
                    Intent intent = new Intent(applicationContext, LoginNewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity(intent);
                    break;
                case 1:
                    Toast.makeText(applicationContext, "您还没有登录，请登陆后执行", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(applicationContext, "执行失败，因为您还没有登录！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public boolean isLogin(Context applicationContext) {
            return SharePreferenceUtil.getBooleanSp(SharePreferenceUtil.IS_LOGIN, applicationContext);
        }


        @Override
        public void clearLoginStatus(Context applicationContext) {
            SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, false, applicationContext);
        }
    };


    public UserBean getUserbean() {
        return userbean;
    }

    public void setUserbean(UserBean userbean) {
        this.userbean = userbean;
    }


    public UserCen getUserCen() {
        return userCen;
    }

    public void setUserCen(UserCen userCen) {
        this.userCen = userCen;
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
