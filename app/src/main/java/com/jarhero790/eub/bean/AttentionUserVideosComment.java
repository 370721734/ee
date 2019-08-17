package com.jarhero790.eub.bean;

/**

 {
 "id": id,
 "vid": 视频id,
 "uid": 用户id,
 "tuid": 评论用户,
 "parentId": 评论用户的id,
 "content": "评论内容",
 "addtime": "评论时间",
 "zan": 赞数,
 "status": 1表示已查看；0表示未查看,
 "comment_id": 评论id,
 "nickname": "昵称",
 "touser": 评论用户昵称,
 "headimgurl": "用户头像",
 "is_zan": 是否取消赞
 }


 */

public class AttentionUserVideosComment {
    private String id;

    /**
     * 视频id
     */
    private String vid;

    /**
     * 用户id
     */
    private String uid;

    /**
     *评论用户
     */
    private String tuid;


    /**
     *评论用户的id
     */
    private String parentId;


    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private String addtime;


    /**
     *赞数
     */
    private String zan;


    /**
     * 1表示已查看；0表示未查看,
     */
    private String status;

    /**
     * 评论id
     */
    private String comment_id;


    /**
     * 昵称
     */
    private String nickname;

    /**
     *评论用户昵称
     */
    private String touser;

    /**
     *用户头像
     */
    private String headimgurl;

    /**
     *是否取消赞
     */
    private String is_zan;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
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
