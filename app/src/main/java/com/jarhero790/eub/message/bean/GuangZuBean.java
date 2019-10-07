package com.jarhero790.eub.message.bean;

import java.util.List;

public class GuangZuBean {

    /**
     * code : 200
     * data : [{"id":5032,"openid":"oBFjqv7pArp2BHJGo2MwJIwjSx89","token":"c600814ee459afd23ef4f2b8410f4ae2","nickname":"5032","sex":0,"addr":null,"country":"","province":"","city":"","headimgurl":"/static/images/usertouxiang.png","subscribe":0,"money":10,"state":0,"sign":"本宝宝暂时还没想到个性的签名","age":null,"freemoney":"0.00","is_admin":0,"type":0,"rong_id":5032,"rong_token":"VymYdNrAg1tJrBcEbUoMMTC+AcwG9OuaMU77gxnpvCMxkD1T1b7Lk/mroIXOIJjxL4OSg1toIa2Vo1pe6RwiTQ==","username":"15989541552","pwd":null,"signtime":null,"is_likeEach":1}]
     * msg : 成功
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
         * id : 5032
         * openid : oBFjqv7pArp2BHJGo2MwJIwjSx89
         * token : c600814ee459afd23ef4f2b8410f4ae2
         * nickname : 5032
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
         * rong_id : 5032
         * rong_token : VymYdNrAg1tJrBcEbUoMMTC+AcwG9OuaMU77gxnpvCMxkD1T1b7Lk/mroIXOIJjxL4OSg1toIa2Vo1pe6RwiTQ==
         * username : 15989541552
         * pwd : null
         * signtime : null
         * is_likeEach : 1
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
        private int is_like;
        private int is_likeEach;

        public DataBean(int id, String nickname, String headimgurl, String sign, int is_like, int is_likeEach) {
            this.id = id;
            this.nickname = nickname;
            this.headimgurl = headimgurl;
            this.sign = sign;
            this.is_like = is_like;
            this.is_likeEach = is_likeEach;
        }

        public DataBean() {
        }

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

        public int getIs_likeEach() {
            return is_likeEach;
        }

        public void setIs_likeEach(int is_likeEach) {
            this.is_likeEach = is_likeEach;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }
    }
}
