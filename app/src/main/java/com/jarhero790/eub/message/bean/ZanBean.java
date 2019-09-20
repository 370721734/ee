package com.jarhero790.eub.message.bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ZanBean implements Serializable{

    /**
     * code : 200
     * data : [{"id":690,"uid":5032,"mid":1416,"time":0,"title":"需要的联系我","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/9783635e184c7be7720c917df75ff5a7.mp4","addtime":"2019-06-28 22:55:35","state":1,"sh_time":"0000-00-00 00:00:00","zan":26,"caifu":22,"visit_val":54,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/9783635e184c7be7720c917df75ff5a7.png","address":"","lng":null,"lat":null,"nickname":"老陳","headimgurl":"/static/images/usertouxiang.png","zid":4944}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 690
         * uid : 5032
         * mid : 1416
         * time : 0
         * title : 需要的联系我
         * url : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/9783635e184c7be7720c917df75ff5a7.mp4
         * addtime : 2019-06-28 22:55:35
         * state : 1
         * sh_time : 0000-00-00 00:00:00
         * zan : 26
         * caifu : 22
         * visit_val : 54
         * cate : 0
         * is_tj : 1
         * video_img : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/9783635e184c7be7720c917df75ff5a7.png
         * address :
         * lng : null
         * lat : null
         * nickname : 老陳
         * headimgurl : /static/images/usertouxiang.png
         * zid : 4944
         */

        private int id;
        private int uid;
        private int mid;
        private int time;
        private String title;
        private String url;
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
        private String nickname;
        private String headimgurl;
        private int zid;
        private int is_zan;
        private int commentNum;
        private int is_like;
        private String good_id;

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

        public int getZid() {
            return zid;
        }

        public void setZid(int zid) {
            this.zid = zid;
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

        public String getGood_id() {
            return good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
        }

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
        }
    }
}
