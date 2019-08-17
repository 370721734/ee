package com.jarhero790.eub.bean;


import java.io.Serializable;

/**
 *    {
       "code": 200,
       "data": null,
        "msg": "操作成功"
      }
 *
 *
 */
public class AttentionBean implements Serializable {
    private  String  code;
    private  String  data;
    private  String  msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
