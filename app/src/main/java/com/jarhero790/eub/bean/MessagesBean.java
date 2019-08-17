package com.jarhero790.eub.bean;

import java.io.Serializable;

/**
 *
 *   {
 "code": 200,
 "data": {
 "system": [
 {
 "id": 1,
 "admin": "钻视TV",
 "content": "内容",
 "status": 1,
 "img": "头像",
 "addtime": "添加时间"
 }
 ],
 "like": [
 {
 "id": id,
 "uid": 用户ID,
 "buid": 被关注用户,
 "addtime": "关注时间",
 "is_likeEach": 是否互相关注：0表示没有互相关注；1表示互相关注,
 "is_cancle": 是否取关,
 "status": 1表示已查看；0表示未查看,
 "nickname": "昵称",
 "headimgurl": "头像",
 "user_id": 用户ID
 }
 ]
 },
 "msg": "成功"
 }
 *
 *
 *
 */

public class MessagesBean implements Serializable{
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
