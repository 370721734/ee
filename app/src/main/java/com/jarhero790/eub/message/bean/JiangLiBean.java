package com.jarhero790.eub.message.bean;

import java.util.List;

public class JiangLiBean {

    /**
     * code : 200
     * data : [{"id":281,"uid":5032,"addtime":"2019-07-12 15:48:53","money":5,"type":3,"status":1,"nickname":"龙哥","headimgurl":"/static/images/usertouxiang.png"}]
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
         * id : 281
         * uid : 5032
         * addtime : 2019-07-12 15:48:53
         * money : 5
         * type : 3
         * status : 1
         * nickname : 龙哥
         * headimgurl : /static/images/usertouxiang.png
         */

        private int id;
        private int uid;
        private String addtime;
        private int money;
        private int type;
        private int status;
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

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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
}
