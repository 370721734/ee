package com.jarhero790.eub.bean;


import java.io.Serializable;

public class Video implements Serializable {
    /**
     * {
     * "id": 1110,
     "video_id": 1110,
     "uid": 5010,
     "mid": 0,
     "time": 0,
     "title": "俺家小菜园 加上这音乐 还有老大的功夫了得",
     "url": "http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/9c8c9b373288e57a2d6e4ed9a62498be.mp4",
     "addtime": "2019-07-04 14:11:40",
     "state": 1,
     "sh_time": "0000-00-00 00:00:00",
     "zan": 1,
     "caifu": 0,
     "visit_val": 7,
     "cate": 0,
     "is_tj": 1,
     "video_img": "http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/9c8c9b373288e57a2d6e4ed9a62498be.png",
     "address": "",
     "lng": null,
     "lat": null,
     "wurl": null,
     "collect": null,
     "headimgurl": "/static/images/usertouxiang.png",
     "nickname": "成武拉芳",
     "is_zan": 0,
     "commentNum": 0,
     "is_like": 0
     *
     * }
     */

    private String id;
    private String uid;
    private String mid;
    private String good_id;
    private String time;
    private String title;
    private String describe;
    private String url;
    private String income;
    private String addtime;
    private String state;
    private String sh_time;
    private String zan;
    private String caifu;
    private String visit_val;
    private String cate;
    private String is_tj;
    private String video_img;
    private String address;
    private String lng;
    private String lat;
    private String video_id;
    private String wurl;
    private String collect;
    private String headimgurl;
    private String nickname;
    private String is_zan;
    private int commentNum;
    private String is_like;


    public Video(String uid, String url, String zan, String caifu, String video_img) {
        this.uid = uid;
        this.url = url;
        this.zan = zan;
        this.caifu = caifu;
        this.video_img = video_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
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

    public String getWurl() {
        return wurl;
    }

    public void setWurl(String wurl) {
        this.wurl = wurl;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIs_zan() {
        return is_zan;
    }

    public void setIs_zan(String is_zan) {
        this.is_zan = is_zan;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }


    public String getGood_id() {
        return good_id;
    }

    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }


    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", mid='" + mid + '\'' +
                ", good_id='" + good_id + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                ", url='" + url + '\'' +
                ", income='" + income + '\'' +
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
                ", video_id='" + video_id + '\'' +
                ", wurl='" + wurl + '\'' +
                ", collect='" + collect + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", nickname='" + nickname + '\'' +
                ", is_zan='" + is_zan + '\'' +
                ", commentNum=" + commentNum +
                ", is_like='" + is_like + '\'' +
                '}';
    }
}
