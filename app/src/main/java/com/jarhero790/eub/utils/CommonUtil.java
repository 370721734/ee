package com.jarhero790.eub.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;

import com.jarhero790.eub.GlobalApplication;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;


public class CommonUtil {
    /**
     * 测量View的宽高
     */
    public static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }

    public static String showzannum(int s) {
        if (s > 9999) {
            try {
                float b = Float.valueOf(s) / 10000f;
                String format = new DecimalFormat("#.#").format(b);
                return format+"w";
            } catch (Exception e) {
                return s+"";
            }

        } else {
            return "" + s;
        }
    }


    /**
     * 把状态栏设成透明
     */
    public static void setStatusBarTransparent(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = context.getWindow().getDecorView();
            decorView.setOnApplyWindowInsetsListener((v, insets) -> {
                WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                return defaultInsets.replaceSystemWindowInsets(
                        defaultInsets.getSystemWindowInsetLeft(),
                        0,
                        defaultInsets.getSystemWindowInsetRight(),
                        defaultInsets.getSystemWindowInsetBottom());
            });
            ViewCompat.requestApplyInsets(decorView);
            context.getWindow().setStatusBarColor(context.getResources().getColor(android.R.color.transparent));
        }
    }


    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(bytes, Base64.DEFAULT);

    }

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    public static void connect(String token,Context context){
        if (context.getApplicationInfo().packageName.equals(GlobalApplication.getCurProcessName(context.getApplicationContext()))){
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
//            RongIM.connect(token, new RongIMClient.ConnectCallback() {
//
//
//                /**
//                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的
//                 * Token
//                 */
//                @Override
//                public void onTokenIncorrect() {
//                    Log.e("LoginActivity", "--onTokenIncorrect");
//                }
//
//                /**
//                 * 连接融云成功
//                 *
//                 * @param userid
//                 *            当前 token
//                 */
//                @Override
//                public void onSuccess(String userid) {
////                    EBmessage eb = new EBmessage();
////                    eb.setStatus(true);
////                    eb.setMessage("success");
////                    eb.setFrom("connect");
////                    EventBus.getDefault().post(eb);
//                    Log.e("LoginActivity", "--onSuccess" + userid);
//                }
//
//
//                /**
//                 * 连接融云失败
//                 *
//                 * @param errorCode
//                 *            错误码，可到官网 查看错误码对应的注释
//                 */
//                @Override
//                public void onError(RongIMClient.ErrorCode errorCode) {
//                    Log.e("LoginActivity", "--onError" + errorCode.getMessage());
//                }
//            });
        }



    }

    public  void refresh(){
        /**
         * 刷新用户缓存数据。
         *
         * @param userInfo 需要更新的用户缓存数据。
         */
        RongIM rongIM=RongIM.getInstance();
        rongIM.refreshUserInfoCache(new UserInfo("userId", "啊明", Uri.parse("http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png")));
    }

    public static void callphone(Context context,String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        context.startActivity(intent);
    }

}
