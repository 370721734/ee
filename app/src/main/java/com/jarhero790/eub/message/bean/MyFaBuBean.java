package com.jarhero790.eub.message.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//我的作品
public class MyFaBuBean implements Serializable {

    /**
     * code : 200
     * data : [{"id":609,"uid":5044,"mid":1333,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/e5e4026ffaff11299d22f085c3485944.mp4","income":0,"addtime":"2019-06-28 15:53:38","state":1,"sh_time":"0000-00-00 00:00:00","zan":0,"caifu":0,"visit_val":3,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/e5e4026ffaff11299d22f085c3485944.png","address":"","lng":null,"lat":null,"video_id":609,"wurl":null,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0,"commentNum":0,"is_like":0},{"id":603,"uid":5044,"mid":1327,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/d87d85f7276331d1323e04929d57f113.mp4","income":0,"addtime":"2019-06-28 15:45:49","state":1,"sh_time":"0000-00-00 00:00:00","zan":0,"caifu":0,"visit_val":5,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/d87d85f7276331d1323e04929d57f113.png","address":"","lng":null,"lat":null,"video_id":603,"wurl":null,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0,"commentNum":0,"is_like":0},{"id":594,"uid":5044,"mid":1318,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/fc2c466430b7c8e4527ed7cd02feece8.mp4","income":0,"addtime":"2019-06-28 15:12:05","state":1,"sh_time":"0000-00-00 00:00:00","zan":2,"caifu":0,"visit_val":46,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/fc2c466430b7c8e4527ed7cd02feece8.png","address":"","lng":null,"lat":null,"video_id":594,"wurl":null,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0,"commentNum":0,"is_like":0},{"id":581,"uid":5044,"mid":1323,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/6fe08b81afe273cfa4fc72d95613648c.mp4","income":0,"addtime":"2019-06-28 14:45:48","state":1,"sh_time":"0000-00-00 00:00:00","zan":4,"caifu":0,"visit_val":45,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/6fe08b81afe273cfa4fc72d95613648c.png","address":"","lng":null,"lat":null,"video_id":581,"wurl":null,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0,"commentNum":0,"is_like":0},{"id":580,"uid":5044,"mid":1322,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/ae60f2bc1a3a2cfcf1c8ccf79a13ed40.mp4","income":0,"addtime":"2019-06-28 14:45:03","state":1,"sh_time":"0000-00-00 00:00:00","zan":1,"caifu":0,"visit_val":45,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/ae60f2bc1a3a2cfcf1c8ccf79a13ed40.png","address":"","lng":null,"lat":null,"video_id":580,"wurl":null,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0,"commentNum":0,"is_like":0}]
     * msg :
     */

    private int code;
    private String msg;
    private ArrayList<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 609
         * uid : 5044
         * mid : 1333
         * time : 0
         * title :
         * url : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/e5e4026ffaff11299d22f085c3485944.mp4
         * income : 0
         * addtime : 2019-06-28 15:53:38
         * state : 1
         * sh_time : 0000-00-00 00:00:00
         * zan : 0
         * caifu : 0
         * visit_val : 3
         * cate : 0
         * is_tj : 1
         * video_img : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/e5e4026ffaff11299d22f085c3485944.png
         * address :
         * lng : null
         * lat : null
         * video_id : 609
         * wurl : null
         * collect : null
         * headimgurl : /upload/avatar/5d5e5e3b451af.png
         * nickname : 5044
         * is_zan : 0
         * commentNum : 0
         * is_like : 0
         */

        private int id;
        private int uid;
        private int mid;
        private int time;
        private String title;
        private String url;
        private int income;
        private String addtime;
        private int state;
        private String sh_time;
        private int zan;
        private int caifu;
        private int visit_val;
        private int cate;
        private int is_tj;
        private String video_img;
        private String address;
        private String lng;
        private String lat;
        private int video_id;
        private String wurl;
        private String collect;
        private String headimgurl;
        private String nickname;
        private int is_zan;
        private int commentNum;
        private int is_like;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
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

        public int getIncome() {
            return income;
        }

        public void setIncome(int income) {
            this.income = income;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getSh_time() {
            return sh_time;
        }

        public void setSh_time(String sh_time) {
            this.sh_time = sh_time;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public int getCaifu() {
            return caifu;
        }

        public void setCaifu(int caifu) {
            this.caifu = caifu;
        }

        public int getVisit_val() {
            return visit_val;
        }

        public void setVisit_val(int visit_val) {
            this.visit_val = visit_val;
        }

        public int getCate() {
            return cate;
        }

        public void setCate(int cate) {
            this.cate = cate;
        }

        public int getIs_tj() {
            return is_tj;
        }

        public void setIs_tj(int is_tj) {
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

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
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

        public int getIs_zan() {
            return is_zan;
        }

        public void setIs_zan(int is_zan) {
            this.is_zan = is_zan;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }
    }
}
