package com.jarhero790.eub.model.bean.message;



public class MessageItemComment {
    /**
     * id
     */
    private String id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 音乐id
     */
    private String mid;

    /**
     * 时长
     */
    private String time;

    /**
     * 标题
     */
    private String title;

    /**
     * 视频地址
     */
    private String url;

    /**
     *  0待审核  1通过   2失败
     */
    private String state;


    /**
     *  审核通过时间
     */
    private String sh_time;


    /**
     *  赞数
     */
    private String zan;


    /**
     *  财富值
     */
    private String caifu;

    /**
     *  浏览量
     */
    private String visit_val;

    /**
     *  分类id
     */
    private String cate;

    /**
     *  是否首页推荐  1是
     */
    private String is_tj;

    /**
     *  视频图片
     */
    private String video_img;

    /**
     *  评论id
     */
    private String comment_id;

    /**
     *  视频id
     */
    private String vid;

    /**
     *  被评论的评语关联的用户id,没有则为null
     */
    private String tuid;


    /**
     *  parentId
     */
    private String parentId;

    /**
     *  评价内容
     */
    private String content;

    /**
     *  添加时间
     */
    private String addtime;

    /**
     *  1表示已查看；0表示未查看
     */
    private String status;

    /**
     *  昵称
     */
    private String nickname;

    /**
     *  头像图片
     */
    private String headimgurl;

    /**
     *  用户id
     */
    private String cid;

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

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSh_time() {
        return sh_time;
    }

    public void setSh_time(String sh_time) {
        this.sh_time = sh_time;
    }

    public String getZan() {
        return zan;
    }

    public void setZan(String zan) {
        this.zan = zan;
    }

    public String getCaifu() {
        return caifu;
    }

    public void setCaifu(String caifu) {
        this.caifu = caifu;
    }

    public String getVisit_val() {
        return visit_val;
    }

    public void setVisit_val(String visit_val) {
        this.visit_val = visit_val;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getIs_tj() {
        return is_tj;
    }

    public void setIs_tj(String is_tj) {
        this.is_tj = is_tj;
    }

    public String getVideo_img() {
        return video_img;
    }

    public void setVideo_img(String video_img) {
        this.video_img = video_img;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
