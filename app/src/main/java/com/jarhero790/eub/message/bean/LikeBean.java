package com.jarhero790.eub.message.bean;

import java.util.List;

public class LikeBean {

    /**
     * code : 200
     * data : [{"id":1135,"uid":5018,"mid":0,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/2021e12bd05770489c0fd3e5c34f1e21.mp4","addtime":"2019-07-04 15:26:24","state":1,"sh_time":"0000-00-00 00:00:00","zan":3,"caifu":0,"visit_val":8,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/2021e12bd05770489c0fd3e5c34f1e21.png","address":"","lng":null,"lat":null,"video_id":1135,"nickname":"5045","headimgurl":"/static/images/usertouxiang.png"}]
     * msg :
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1135
         * uid : 5018
         * mid : 0
         * time : 0
         * title :
         * url : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/2021e12bd05770489c0fd3e5c34f1e21.mp4
         * addtime : 2019-07-04 15:26:24
         * state : 1
         * sh_time : 0000-00-00 00:00:00
         * zan : 3
         * caifu : 0
         * visit_val : 8
         * cate : 0
         * is_tj : 1
         * video_img : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/2021e12bd05770489c0fd3e5c34f1e21.png
         * address :
         * lng : null
         * lat : null
         * video_id : 1135
         * nickname : 5045
         * headimgurl : /static/images/usertouxiang.png
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
        private Object lng;
        private Object lat;
        private int video_id;
        private String nickname;
        private String headimgurl;

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

        public Object getLng() {
            return lng;
        }

        public void setLng(Object lng) {
            this.lng = lng;
        }

        public Object getLat() {
            return lat;
        }

        public void setLat(Object lat) {
            this.lat = lat;
        }

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
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
    }
}