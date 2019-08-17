package com.jarhero790.eub.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *   {
 "code": 200,
 "data": [
 {
 "id": 9,
 "name": "鼓励",
 "money": "1.00",
 "caifu": 1,
 "is_show": 1,
 "img": "default\/20190508\/e080698e691c5b63a64587f9054b1a50.png",
 "sort": 7,
 "gift_id": 9
 }
 ],
 "msg": ""
 }
 *
 */
public class GiftBean implements Serializable {
 private  String code;
    private  String msg;
    private ArrayList<Gift> data;

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

    public ArrayList<Gift> getData() {
        return data;
    }

    public void setData(ArrayList<Gift> data) {
        this.data = data;
    }
}
