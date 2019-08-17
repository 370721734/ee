package com.jarhero790.eub.aop.logincore;

import android.content.Context;


public class LoginManger {
    private static LoginManger instance;
    private LoginManger(){}

    public static LoginManger getInstance(){
        if (null == instance){
            synchronized (LoginManger.class){
                if (null == instance){
                    instance = new LoginManger();
                }
            }
        }
        return instance;
    }


    public void init(Context context, ILoginFilter iLoginFilter){
        LoginAssistant.getInstance().setApplicationContext(context);
        LoginAssistant.getInstance().setILoginFilter(iLoginFilter);
    }

}
