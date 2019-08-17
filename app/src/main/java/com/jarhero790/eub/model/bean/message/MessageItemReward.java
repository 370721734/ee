package com.jarhero790.eub.model.bean.message;



public class MessageItemReward {
    /**
     {"id": id,
      "uid": 用户id,
      "addtime": "添加时间",
      "money": 单价,
      "type": 奖励类型：0:注册账号奖励；1：邀请码注册将励；2：邀请注册奖励；3：签到奖励,
      "status": 查看状态
      "nickname": "昵称",
      "headimgurl": "用户头像"
     }
     *
     */


    private String id;
    private String uid;
    private String addtime;
    private String money;
    private String type;
    private String status;
    private String nickname;
    private String headimgurl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
