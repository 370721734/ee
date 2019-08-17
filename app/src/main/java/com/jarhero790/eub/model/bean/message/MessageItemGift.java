package com.jarhero790.eub.model.bean.message;


public class MessageItemGift {

    /**
     * id
     */
    private String id;

    /**
     * 赠送的礼物id
     */
    private String gid;

    /**
     * 礼物名称
     */
    private String gift_name;

    /**
     * 抵赞数
     */
    private String zan;

    /**
     * 获得礼物的视频id
     */
    private String vid;

    /**
     * 赠送用户
     */
    private String uid;

    /**
     * 获得礼物的人
     */
    private String tuid;

    /**
     * 添加时间
     */
    private String addtime;

    /**
     * 礼物价格
     */
    private String money;

    /**
     * 1表示已查看；0表示未查看
     */
    private String status;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String headimgurl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGift_name() {
        return gift_name;
    }

    public void setGift_name(String gift_name) {
        this.gift_name = gift_name;
    }

    public String getZan() {
        return zan;
    }

    public void setZan(String zan) {
        this.zan = zan;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTuid() {
        return tuid;
    }

    public void setTuid(String tuid) {
        this.tuid = tuid;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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
}
