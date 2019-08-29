package com.jarhero790.eub.bean;

import java.util.ArrayList;

/**

 {
   "id": id,
   "uid": 用户id,
   "mid": 音乐id,
   "time": 时长,
   "title": 标题,
   "url": "视频地址",
   "addtime": "添加时间",
   "state": 0待审核  1通过   2失败,
   "sh_time": "审核通过时间",
   "zan": 获赞数  单独点赞数,
   "caifu": 财富值,
   "visit_val": 浏览量,
   "cate": 分类id,
   "is_tj": 是否首页推荐  1是,
   "video_img": 视频图片,
   "address": 发布地址,
   "lng": 经度,
   "lat": 纬度,
   "comment": [
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
   ]
 }





 */

public class AttentionVideo  {

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

    private String income;

    /**
     * 添加时间
     */
    private String addtime;


    /**
     *0待审核  1通过   2失败,
     */
    private String state;

    /**
     * 审核通过时间
     */
    private String sh_time;

    /**
     * 获赞数  单独点赞数
     */
    private String zan;

    /**
     * 财富值
     */
    private String caifu;


    /**
     * 浏览量
     */
    private String visit_val;

    /**
     * 分类id
     */
    private String cate;


    /**
      是否首页推荐  1是,"
     */
    private String is_tj;

   /**
    * 视频图片
    * */
    private String video_img;

    /**
     * 发布地址
     */
    private String address;

    /**
     * 经度
     */
    private String lng;

    /**
     *纬度
     */
    private String lat;

    private String video_id;

    private String nickname;

    private String headimgurl;

    private String is_zan;


    /**
     * 评论
     */
    private ArrayList<AttentionUserVideosComment> comment;


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

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public ArrayList<AttentionUserVideosComment> getComment() {
        return comment;
    }

    public void setComment(ArrayList<AttentionUserVideosComment> comment) {
        this.comment = comment;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
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

    public String getIs_zan() {
        return is_zan;
    }

    public void setIs_zan(String is_zan) {
        this.is_zan = is_zan;
    }

    @Override
    public String toString() {
        return "AttentionVideo{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", mid='" + mid + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", addtime='" + addtime + '\'' +
                ", state='" + state + '\'' +
                ", sh_time='" + sh_time + '\'' +
                ", zan='" + zan + '\'' +
                ", caifu='" + caifu + '\'' +
                ", visit_val='" + visit_val + '\'' +
                ", cate='" + cate + '\'' +
                ", is_tj='" + is_tj + '\'' +
                ", video_img='" + video_img + '\'' +
                ", address='" + address + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", comment=" + comment +
                '}';
    }
}
