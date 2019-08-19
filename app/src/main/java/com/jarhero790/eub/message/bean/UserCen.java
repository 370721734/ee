package com.jarhero790.eub.message.bean;
//个人中心
public class UserCen {


    /**
     * code : 200
     * data : {"user":{"id":5045,"openid":"","token":"f4c3571af9126a8e3a2f69872c487c16","nickname":"5045","sex":0,"addr":null,"country":"","province":"","city":"","headimgurl":"/static/images/usertouxiang.png","subscribe":0,"money":10,"state":0,"sign":"本宝宝暂时还没想到个性的签名","age":null,"freemoney":"0.00","is_admin":0,"type":0,"rong_id":5045,"rong_token":"rS3sE2HuWuqKRjg7iDHnXqRjDx9LbphsWShi8V/xTexkai91peZay5MXn4rHDxOdn7FXceXd49ekQLhxOPI9Uw==","username":"13480893622","pwd":null,"signtime":null,"user_id":5045},"videoList":0,"zan":0,"like":0,"fensi":0,"myzan":0}
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
         * user : {"id":5045,"openid":"","token":"f4c3571af9126a8e3a2f69872c487c16","nickname":"5045","sex":0,"addr":null,"country":"","province":"","city":"","headimgurl":"/static/images/usertouxiang.png","subscribe":0,"money":10,"state":0,"sign":"本宝宝暂时还没想到个性的签名","age":null,"freemoney":"0.00","is_admin":0,"type":0,"rong_id":5045,"rong_token":"rS3sE2HuWuqKRjg7iDHnXqRjDx9LbphsWShi8V/xTexkai91peZay5MXn4rHDxOdn7FXceXd49ekQLhxOPI9Uw==","username":"13480893622","pwd":null,"signtime":null,"user_id":5045}
         * videoList : 0
         * zan : 0
         * like : 0
         * fensi : 0
         * myzan : 0
         */

        private UserBean user;
        private int videoList;
        private int zan;
        private int like;
        private int fensi;
        private int myzan;

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

        public int getMyzan() {
            return myzan;
        }

        public void setMyzan(int myzan) {
            this.myzan = myzan;
        }

        public static class UserBean {
            /**
             * id : 5045
             * openid :
             * token : f4c3571af9126a8e3a2f69872c487c16
             * nickname : 5045
             * sex : 0
             * addr : null
             * country :
             * province :
             * city :
             * headimgurl : /static/images/usertouxiang.png
             * subscribe : 0
             * money : 10
             * state : 0
             * sign : 本宝宝暂时还没想到个性的签名
             * age : null
             * freemoney : 0.00
             * is_admin : 0
             * type : 0
             * rong_id : 5045
             * rong_token : rS3sE2HuWuqKRjg7iDHnXqRjDx9LbphsWShi8V/xTexkai91peZay5MXn4rHDxOdn7FXceXd49ekQLhxOPI9Uw==
             * username : 13480893622
             * pwd : null
             * signtime : null
             * user_id : 5045
             */

            private int id;
            private String openid;
            private String token;
            private String nickname;
            private int sex;
            private String addr;
            private String country;
            private String province;
            private String city;
            private String headimgurl;
            private int subscribe;
            private int money;
            private int state;
            private String sign;
            private String age;
            private String freemoney;
            private int is_admin;
            private int type;
            private int rong_id;
            private String rong_token;
            private String username;
            private String pwd;
            private String signtime;
            private int user_id;

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

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
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

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
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

            public String getPwd() {
                return pwd;
            }

            public void setPwd(String pwd) {
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
        }
    }
}
