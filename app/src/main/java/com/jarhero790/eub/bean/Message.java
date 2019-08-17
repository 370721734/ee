package com.jarhero790.eub.bean;

import java.util.ArrayList;

/**
 * {
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
 }
 */

public class Message {
    private ArrayList<MessageSystem> system;
    private ArrayList<MessageLike> like;

    public ArrayList<MessageSystem> getSystem() {
        return system;
    }

    public void setSystem(ArrayList<MessageSystem> system) {
        this.system = system;
    }

    public ArrayList<MessageLike> getLike() {
        return like;
    }

    public void setLike(ArrayList<MessageLike> like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "Message{" +
                "system=" + system +
                ", like=" + like +
                '}';
    }
}
