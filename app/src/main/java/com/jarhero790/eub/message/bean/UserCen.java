package com.jarhero790.eub.message.bean;

public class UserCen {

    /**
     * code : 200
     * data : {"fensi":0,"like":0,"myzan":0,"user":{"city":"","country":"","freemoney":"0.00","headimgurl":"/static/images/usertouxiang.png","id":5044,"is_admin":0,"money":10,"nickname":"5044","openid":"","province":"","rong_id":5044,"rong_token":"6a34mMUkLbpLqCOH7kzHD2e7NDDI6gS2aE/afdMaQWwiF5mo1RSLHd4a3r3cubMOxTrpQ2lZSbdx0tHHFX6Z9w==","sex":0,"sign":"本宝宝暂时还没想到个性的签名","state":0,"subscribe":0,"token":"066c402b915703c722edd789df27a987","type":0,"user_id":5044,"username":"13243831328"},"videoList":0,"zan":0}
     * msg :
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

    public static class DataBean {
        /**
         * fensi : 0
         * like : 0
         * myzan : 0
         * user : {"city":"","country":"","freemoney":"0.00","headimgurl":"/static/images/usertouxiang.png","id":5044,"is_admin":0,"money":10,"nickname":"5044","openid":"","province":"","rong_id":5044,"rong_token":"6a34mMUkLbpLqCOH7kzHD2e7NDDI6gS2aE/afdMaQWwiF5mo1RSLHd4a3r3cubMOxTrpQ2lZSbdx0tHHFX6Z9w==","sex":0,"sign":"本宝宝暂时还没想到个性的签名","state":0,"subscribe":0,"token":"066c402b915703c722edd789df27a987","type":0,"user_id":5044,"username":"13243831328"}
         * videoList : 0
         * zan : 0
         */

        private int fensi;
        private int like;
        private int myzan;
        private UserBean user;
        private int videoList;
        private int zan;

        public int getFensi() {
            return fensi;
        }

        public void setFensi(int fensi) {
            this.fensi = fensi;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getMyzan() {
            return myzan;
        }

        public void setMyzan(int myzan) {
            this.myzan = myzan;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public int getVideoList() {
            return videoList;
        }

        public void setVideoList(int videoList) {
            this.videoList = videoList;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public static class UserBean {
            /**
             * city :
             * country :
             * freemoney : 0.00
             * headimgurl : /static/images/usertouxiang.png
             * id : 5044
             * is_admin : 0
             * money : 10
             * nickname : 5044
             * openid :
             * province :
             * rong_id : 5044
             * rong_token : 6a34mMUkLbpLqCOH7kzHD2e7NDDI6gS2aE/afdMaQWwiF5mo1RSLHd4a3r3cubMOxTrpQ2lZSbdx0tHHFX6Z9w==
             * sex : 0
             * sign : 本宝宝暂时还没想到个性的签名
             * state : 0
             * subscribe : 0
             * token : 066c402b915703c722edd789df27a987
             * type : 0
             * user_id : 5044
             * username : 13243831328
             */

            private String city;
            private String country;
            private String freemoney;
            private String headimgurl;
            private int id;
            private int is_admin;
            private int money;
            private String nickname;
            private String openid;
            private String province;
            private int rong_id;
            private String rong_token;
            private int sex;
            private String sign;
            private int state;
            private int subscribe;
            private String token;
            private int type;
            private int user_id;
            private String username;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getFreemoney() {
                return freemoney;
            }

            public void setFreemoney(String freemoney) {
                this.freemoney = freemoney;
            }

            public String getHeadimgurl() {
                return headimgurl;
            }

            public void setHeadimgurl(String headimgurl) {
                this.headimgurl = headimgurl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIs_admin() {
                return is_admin;
            }

            public void setIs_admin(int is_admin) {
                this.is_admin = is_admin;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
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

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getSubscribe() {
                return subscribe;
            }

            public void setSubscribe(int subscribe) {
                this.subscribe = subscribe;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
