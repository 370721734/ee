package com.jarhero790.eub.eventbus;

import com.jarhero790.eub.bean.UserBean;

public class MessageEventUser{
    private UserBean userBean;

    public  MessageEventUser(UserBean userBean){
        this.userBean=userBean;
    }

    public UserBean getMessage() {
        return userBean;
    }

    public void setMessage(UserBean userBean) {
        this.userBean = userBean;
    }
}
