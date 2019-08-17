package com.jarhero790.eub.bean;

import java.io.Serializable;


/**
 {
 "id": id,
 "openid": openid,
 "token": "用户token",
 "nickname": "昵称",
 "sex": 性别,
 "addr": 地址,
 "country": 省,
 "province": 市,
 "city": 区,
 "headimgurl": 头像,
 "subscribe": 值为0时，代表此用户没有关注该公众号,
 "money": 余额,
 "state": 是否拉黑  0否  1是,
 "sign": "个性签名",
 "age": 年级,
 "freemoney": 送的金币,
 "is_admin": 是否后台上传的管理员    1是,
 "type": 用户类型：0：表示普通用户；1：表示超级用户,
 "rong_id": 融云ID,
 "rong_token": 融云token,
 "username": "用户名",
 "pwd": "密码",
 "signtime": 最后签到时间
 }
 *
 */
public class AttentionUser implements Serializable{
    private String id;
    private String openid;
    private String token;
    private String nickname;
    private String sex;
    private String addr;
    private String country;
    private String province;
    private String city;

    private String headimgurl;
    private String subscribe;
    private String money;
    private String state;

    private String sign;
    private String age;
    private String freemoney;
    private String is_admin;

    private String type;
    private String username;
    private String pwd;
    private String signtime;

    private String rong_id;
    private String rong_token;

    public String getRong_id() {
        return rong_id;
    }

    public void setRong_id(String rong_id) {
        this.rong_id = rong_id;
    }

    public String getRong_token() {
        return rong_token;
    }

    public void setRong_token(String rong_token) {
        this.rong_token = rong_token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    public String getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "AttentionUser{" +
                "id='" + id + '\'' +
                ", openid='" + openid + '\'' +
                ", token='" + token + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", addr='" + addr + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", subscribe='" + subscribe + '\'' +
                ", money='" + money + '\'' +
                ", state='" + state + '\'' +
                ", sign='" + sign + '\'' +
                ", age='" + age + '\'' +
                ", freemoney='" + freemoney + '\'' +
                ", is_admin='" + is_admin + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", signtime='" + signtime + '\'' +
                ", rong_id='" + rong_id + '\'' +
                ", rong_token='" + rong_token + '\'' +
                '}';
    }
}

