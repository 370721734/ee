package com.jarhero790.eub.bean;

import java.io.Serializable;

/**

 {
 "code": 200,
 "data": {
 "is": 0,
 "num": 1
 },
 "msg": ""
 }

 */

public class ShipinDianZanBean implements Serializable {
    private String code;
    private String msg;
    private ShipinDianZan data;

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

    public ShipinDianZan getData() {
        return data;
    }

    public void setData(ShipinDianZan data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ShipinDianZanBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
