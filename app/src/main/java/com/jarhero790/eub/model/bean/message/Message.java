package com.jarhero790.eub.model.bean.message;



public class Message {
    private String code;
    private String msg;
    private MessageItem data;

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

    public MessageItem getData() {
        return data;
    }

    public void setData(MessageItem data) {
        this.data = data;
    }
}
