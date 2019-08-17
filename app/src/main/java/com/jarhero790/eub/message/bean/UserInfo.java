package com.jarhero790.eub.message.bean;

import com.jarhero790.eub.bean.Message;

public class UserInfo {
    private String code;
    private String msg;
    private Message data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Message getData() {
        return data;
    }

    public void setData(Message data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessagesBean{" +
                "  code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
