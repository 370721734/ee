package com.jarhero790.eub.bean;


import java.io.Serializable;

/**
 {
 "code": 200,
 "data":XXX
 "msg":XXX
 }

 */
public class AttentionUserAndVideoBen implements Serializable {
    private String code;
    private String msg;
    private AttentionUserAndVideo data;

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

    public AttentionUserAndVideo getData() {
        return data;
    }

    public void setData(AttentionUserAndVideo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AttentionUserAndVideoBen{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
