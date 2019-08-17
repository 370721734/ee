package com.jarhero790.eub.bean;

import java.io.Serializable;

/**
 * {
 "id": 2,
 "comment_id": 2,
 "vid": 690,
 "uid": 4873,
 "tuid": null,
 "parentId": null,
 "content": "成武短视频友情提示 视频没有水印效果更佳",
 "addtime": "1周前",
 "zan": 0,
 "status": 1,
 "nickname": "成武短视频",
 "touser": null,
 "headimgurl": "http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/269ed0037255c9d844a99cc38a772dc54873.png",
 "is_zan": 2
 }
 *
 */

public class Comment implements Serializable {
    private  String id;
    private  String comment_id;
    private  String vid;
    private  String uid;
    private  String tuid;

    private  String parentId;
    private  String content;
    private  String addtime;
    private  String zan;
    private  String status;

    private  String nickname;
    private  String touser;
    private  String headimgurl;
    private  String is_zan;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getZan() {
        return zan;
    }

    public void setZan(String zan) {
        this.zan = zan;
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

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getIs_zan() {
        return is_zan;
    }

    public void setIs_zan(String is_zan) {
        this.is_zan = is_zan;
    }
}
