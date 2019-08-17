package com.jarhero790.eub.bean;


import java.io.Serializable;

/**
 {
 "id": 5031,
 "login_id": 5031,
 "openid": "",
 "token": "f1f14f80ff16e3cafe0093286f6294cd",
 "nickname": "昵称",
 "sex": 0,
 "addr": null,
 "country": "",
 "province": "",
 "city": "",
 "headimgurl": "/static/images/usertouxiang.png",
 "subscribe": 0,
 "money": 10,
 "state": 0,
 "sign": "本宝宝暂时还没想到个性的签名",
 "age": null,
 "freemoney": "0.00",
 "is_admin": 0,
 "type": 0,
 "rong_id": 5032,
 "rong_token": "jXa6\/vIl6dW3VAg4xyvD9jC+AcwG9OuaMU77gxnpvCMxkD1T1b7Lk\/wquEfAJMFwLnjISZiJL8rhATPUaXzOHQ==",
 "username": "15989541552",
 "pwd": "8136c1eb161e6c2ac1347df500f42a62",
 "signtime": null
 }
 */
public class User implements Serializable {
   private  String id;
    private  String login_id;
    private  String token;
    private  String nickname;
    private  String sex;
    private  String addr;
    private  String country;
    private  String province;
    private  String city;
    private  String headimgurl;
    private  String subscribe;
    private  String money;
    private  String state;
    private  String sign;
    private  String age;
    private  String freemoney;
    private  String is_admin;

    private  String type;
    private  String rong_id;
    private  String rong_token;
    private  String username;

    private  String pwd;
    private  String signtime;


 public String getId() {
  return id;
 }

 public void setId(String id) {
  this.id = id;
 }

 public String getLogin_id() {
  return login_id;
 }

 public void setLogin_id(String login_id) {
  this.login_id = login_id;
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
  return "User{" +
          "id='" + id + '\'' +
          ", login_id='" + login_id + '\'' +
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
          ", rong_id='" + rong_id + '\'' +
          ", rong_token='" + rong_token + '\'' +
          ", username='" + username + '\'' +
          ", pwd='" + pwd + '\'' +
          ", signtime='" + signtime + '\'' +
          '}';
 }
}
