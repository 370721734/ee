package com.jarhero790.eub.model.bean.message;



public class MessageItemLike {
    private String id;

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 被关注用户
     */
    private String build;

    /**
     * 关注时间
     */
    private String addtime;

    /**
     * 是否互相关注：0表示没有互相关注；1表示互相关注,
     */
    private String is_likeEach;


    /**
     * 是否取关
     */
    private String is_cancle;

    /**
     * 1表示已查看；0表示未查看
     */
    private String status;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headimgurl;

    /**
     * 用户ID
     */
    private String user_id;


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

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
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
}
