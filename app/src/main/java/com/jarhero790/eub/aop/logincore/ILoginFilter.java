package com.jarhero790.eub.aop.logincore;

import android.content.Context;

/**
 * 登录拦截接口
 */

public interface ILoginFilter {
    void login(Context applicationContext, int loginDefine);
    boolean isLogin(Context applicationContext);
    void clearLoginStatus(Context applicationContext);
}
