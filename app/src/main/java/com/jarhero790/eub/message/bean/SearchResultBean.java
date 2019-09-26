package com.jarhero790.eub.message.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResultBean implements Serializable {

    /**
     * code : 200
     * data : {"user":{"id":5044,"openid":"","token":"e4428294c22777d78c4cbe337a340982","nickname":"5044","sex":1,"addr":null,"country":"","province":"","city":"北京市东城区","headimgurl":"/upload/avatar/5d5e5e3b451af.png","subscribe":0,"money":60,"state":0,"sign":"本天上午，人告诉你们","age":null,"freemoney":"0.00","is_admin":0,"type":0,"rong_id":5044,"rong_token":"6a34mMUkLbpLqCOH7kzHD2e7NDDI6gS2aE/afdMaQWwiF5mo1RSLHd4a3r3cubMOxTrpQ2lZSbdx0tHHFX6Z9w==","username":"13243831328","pwd":null,"signtime":"2019-08-28 08:21:15","user_id":5044,"myzan":7,"like":3,"fensi":2},"video":[{"id":609,"uid":5044,"mid":1333,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/e5e4026ffaff11299d22f085c3485944.mp4","income":0,"addtime":"2019-06-28 15:53:38","state":1,"sh_time":"0000-00-00 00:00:00","zan":0,"caifu":0,"visit_val":3,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/e5e4026ffaff11299d22f085c3485944.png","address":"","lng":null,"lat":null,"video_id":609,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0},{"id":603,"uid":5044,"mid":1327,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/d87d85f7276331d1323e04929d57f113.mp4","income":0,"addtime":"2019-06-28 15:45:49","state":1,"sh_time":"0000-00-00 00:00:00","zan":0,"caifu":0,"visit_val":5,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/d87d85f7276331d1323e04929d57f113.png","address":"","lng":null,"lat":null,"video_id":603,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0},{"id":594,"uid":5044,"mid":1318,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/fc2c466430b7c8e4527ed7cd02feece8.mp4","income":0,"addtime":"2019-06-28 15:12:05","state":1,"sh_time":"0000-00-00 00:00:00","zan":2,"caifu":0,"visit_val":46,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/fc2c466430b7c8e4527ed7cd02feece8.png","address":"","lng":null,"lat":null,"video_id":594,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0},{"id":581,"uid":5044,"mid":1323,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/6fe08b81afe273cfa4fc72d95613648c.mp4","income":0,"addtime":"2019-06-28 14:45:48","state":1,"sh_time":"0000-00-00 00:00:00","zan":4,"caifu":0,"visit_val":45,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/6fe08b81afe273cfa4fc72d95613648c.png","address":"","lng":null,"lat":null,"video_id":581,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0},{"id":580,"uid":5044,"mid":1322,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/ae60f2bc1a3a2cfcf1c8ccf79a13ed40.mp4","income":0,"addtime":"2019-06-28 14:45:03","state":1,"sh_time":"0000-00-00 00:00:00","zan":1,"caifu":0,"visit_val":45,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/ae60f2bc1a3a2cfcf1c8ccf79a13ed40.png","address":"","lng":null,"lat":null,"video_id":580,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0}]}
     * msg : 成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean  implements Serializable{
        /**
         * user : {"id":5044,"openid":"","token":"e4428294c22777d78c4cbe337a340982","nickname":"5044","sex":1,"addr":null,"country":"","province":"","city":"北京市东城区","headimgurl":"/upload/avatar/5d5e5e3b451af.png","subscribe":0,"money":60,"state":0,"sign":"本天上午，人告诉你们","age":null,"freemoney":"0.00","is_admin":0,"type":0,"rong_id":5044,"rong_token":"6a34mMUkLbpLqCOH7kzHD2e7NDDI6gS2aE/afdMaQWwiF5mo1RSLHd4a3r3cubMOxTrpQ2lZSbdx0tHHFX6Z9w==","username":"13243831328","pwd":null,"signtime":"2019-08-28 08:21:15","user_id":5044,"myzan":7,"like":3,"fensi":2}
         * video : [{"id":609,"uid":5044,"mid":1333,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/e5e4026ffaff11299d22f085c3485944.mp4","income":0,"addtime":"2019-06-28 15:53:38","state":1,"sh_time":"0000-00-00 00:00:00","zan":0,"caifu":0,"visit_val":3,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/e5e4026ffaff11299d22f085c3485944.png","address":"","lng":null,"lat":null,"video_id":609,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0},{"id":603,"uid":5044,"mid":1327,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/d87d85f7276331d1323e04929d57f113.mp4","income":0,"addtime":"2019-06-28 15:45:49","state":1,"sh_time":"0000-00-00 00:00:00","zan":0,"caifu":0,"visit_val":5,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/d87d85f7276331d1323e04929d57f113.png","address":"","lng":null,"lat":null,"video_id":603,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0},{"id":594,"uid":5044,"mid":1318,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/fc2c466430b7c8e4527ed7cd02feece8.mp4","income":0,"addtime":"2019-06-28 15:12:05","state":1,"sh_time":"0000-00-00 00:00:00","zan":2,"caifu":0,"visit_val":46,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/fc2c466430b7c8e4527ed7cd02feece8.png","address":"","lng":null,"lat":null,"video_id":594,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0},{"id":581,"uid":5044,"mid":1323,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/6fe08b81afe273cfa4fc72d95613648c.mp4","income":0,"addtime":"2019-06-28 14:45:48","state":1,"sh_time":"0000-00-00 00:00:00","zan":4,"caifu":0,"visit_val":45,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/6fe08b81afe273cfa4fc72d95613648c.png","address":"","lng":null,"lat":null,"video_id":581,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0},{"id":580,"uid":5044,"mid":1322,"time":0,"title":"","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/ae60f2bc1a3a2cfcf1c8ccf79a13ed40.mp4","income":0,"addtime":"2019-06-28 14:45:03","state":1,"sh_time":"0000-00-00 00:00:00","zan":1,"caifu":0,"visit_val":45,"cate":0,"is_tj":1,"video_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/ae60f2bc1a3a2cfcf1c8ccf79a13ed40.png","address":"","lng":null,"lat":null,"video_id":580,"collect":null,"headimgurl":"/upload/avatar/5d5e5e3b451af.png","nickname":"5044","is_zan":0}]
         */

        private UserBean user;
        private ArrayList<VideoBean> video;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public ArrayList<VideoBean> getVideo() {
            return video;
        }

        public void setVideo(ArrayList<VideoBean> video) {
            this.video = video;
        }

        public static class UserBean {
            /**
             * id : 5044
             * openid :
             * token : e4428294c22777d78c4cbe337a340982
             * nickname : 5044
             * sex : 1
             * addr : null
             * country :
             * province :
             * city : 北京市东城区
             * headimgurl : /upload/avatar/5d5e5e3b451af.png
             * subscribe : 0
             * money : 60
             * state : 0
             * sign : 本天上午，人告诉你们
             * age : null
             * freemoney : 0.00
             * is_admin : 0
             * type : 0
             * rong_id : 5044
             * rong_token : 6a34mMUkLbpLqCOH7kzHD2e7NDDI6gS2aE/afdMaQWwiF5mo1RSLHd4a3r3cubMOxTrpQ2lZSbdx0tHHFX6Z9w==
             * username : 13243831328
             * pwd : null
             * signtime : 2019-08-28 08:21:15
             * user_id : 5044
             * myzan : 7
             * like : 3
             * fensi : 2
             */

            private int id;
            private String openid;
            private String token;
            private String nickname;
            private int sex;
            private Object addr;
            private String country;
            private String province;
            private String city;
            private String headimgurl;
            private int subscribe;
            private int money;
            private int state;
            private String sign;
            private Object age;
            private String freemoney;
            private int is_admin;
            private int type;
            private int rong_id;
            private String rong_token;
            private String username;
            private Object pwd;
            private String signtime;
            private int user_id;
            private int myzan;
            private int like;
            private int fensi;
            private int is_like;
            private int is_likeEach;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public Object getAddr() {
                return addr;
            }

            public void setAddr(Object addr) {
                this.addr = addr;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getHeadimgurl() {
                return headimgurl;
            }

            public void setHeadimgurl(String headimgurl) {
                this.headimgurl = headimgurl;
            }

            public int getSubscribe() {
                return subscribe;
            }

            public void setSubscribe(int subscribe) {
                this.subscribe = subscribe;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public Object getAge() {
                return age;
            }

            public void setAge(Object age) {
                this.age = age;
            }

            public String getFreemoney() {
                return freemoney;
            }

            public void setFreemoney(String freemoney) {
                this.freemoney = freemoney;
            }

            public int getIs_admin() {
                return is_admin;
            }

            public void setIs_admin(int is_admin) {
                this.is_admin = is_admin;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getRong_id() {
                return rong_id;
            }

            public void setRong_id(int rong_id) {
                this.rong_id = rong_id;
            }

            public String getRong_token() {
                return rong_token;
            }

            public void setRong_token(String rong_token) {
                this.rong_token = rong_token;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public Object getPwd() {
                return pwd;
            }

            public void setPwd(Object pwd) {
                this.pwd = pwd;
            }

            public String getSigntime() {
                return signtime;
            }

            public void setSigntime(String signtime) {
                this.signtime = signtime;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getMyzan() {
                return myzan;
            }

            public void setMyzan(int myzan) {
                this.myzan = myzan;
            }

            public int getLike() {
                return like;
            }

            public void setLike(int like) {
                this.like = like;
            }

            public int getFensi() {
                return fensi;
            }

            public void setFensi(int fensi) {
                this.fensi = fensi;
            }

            public int getIs_like() {
                return is_like;
            }

            public void setIs_like(int is_like) {
                this.is_like = is_like;
            }

            public int getIs_likeEach() {
                return is_likeEach;
            }

            public void setIs_likeEach(int is_likeEach) {
                this.is_likeEach = is_likeEach;
            }
        }

        public static class VideoBean  implements Serializable{
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
             * collect : null
             * headimgurl : /upload/avatar/5d5e5e3b451af.png
             * nickname : 5044
             * is_zan : 0
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
            private Object lng;
            private Object lat;
            private int video_id;
            private Object collect;
            private String headimgurl;
            private String nickname;
            private String click_num;
            private int is_zan;
            private String is_like;
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

            public Object getCollect() {
                return collect;
            }

            public void setCollect(Object collect) {
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

            public String getClick_num() {
                return click_num;
            }

            public void setClick_num(String click_num) {
                this.click_num = click_num;
            }
        }
    }
}
