package com.jarhero790.eub.bean;

import java.io.Serializable;

/**
 *
 *   {
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
 *
 */

public class MessageLike implements Serializable{
    private String viewType;
    private String  id;
    private String  uid;
    private String  buid;
    private String  addtime;
    private String  is_likeEach;
    private String  is_cancle;
    private String  status;
    private String  nickname;
    private String  headimgurl;
    private String  user_id;

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBuid() {
        return buid;
    }

    public void setBuid(String buid) {
        this.buid = buid;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getIs_likeEach() {
        return is_likeEach;
    }

    public void setIs_likeEach(String is_likeEach) {
        this.is_likeEach = is_likeEach;
    }

    public String getIs_cancle() {
        return is_cancle;
    }

    public void setIs_cancle(String is_cancle) {
        this.is_cancle = is_cancle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "MessageLike{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", buid='" + buid + '\'' +
                ", addtime='" + addtime + '\'' +
                ", is_likeEach='" + is_likeEach + '\'' +
                ", is_cancle='" + is_cancle + '\'' +
                ", status='" + status + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
