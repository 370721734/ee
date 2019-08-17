package com.jarhero790.eub.message.net;

import android.support.annotation.NonNull;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by silladus on 2018/3/20/0020.
 * GitHub: https://github.com/silladus
 * Description:
 */

public final class MyInterceptor implements Interceptor {


    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        // #userid 传入当前登陆用户的ID，未登录传入0（加密后的）
        // #type 网站 web 苹果 ios 安卓 android 微信小程序 small
        // #platform OEM客户编号 写死到程序中（我们写1）



        Request request = chain.request()
                .newBuilder()
//                .addHeader("token", MyApplication.getInstance().getToken())
//                .addHeader("proxy",MyApplication.PROXY)
                .build();
        return chain.proceed(request);
    }
}
