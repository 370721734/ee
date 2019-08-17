package com.jarhero790.eub.bean;

import java.util.ArrayList;

/**

 {
 "code": 200,
 "data": [
 {
 "nickname": "王凯",
 "headimgurl": "http:\/\/aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com\/f0218aaa27320e60a19337f963c90f134872.png",
 "user_id": 4872,
 "is_likeEach": 1,
 "rong_id": 5032,
 "rong_token": "1qxxNd8VopXcMWm1gBlgnGe7NDDI6gS2aE\/afdMaQWwNZdDDqeAnCcq4Voky31W7MyvYQCwxSMTbsDohiSvjEg==",
 "addtime":2019-07-15 10:19:18
 }
 ],
 "msg": ""
 }

 */

public class FenSiBean {
    private String code;
    private String msg;
    private ArrayList<FenSi> data;

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

    public ArrayList<FenSi> getData() {
        return data;
    }

    public void setData(ArrayList<FenSi> data) {
        this.data = data;
    }
}
