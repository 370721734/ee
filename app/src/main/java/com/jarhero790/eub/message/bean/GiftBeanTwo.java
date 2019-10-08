package com.jarhero790.eub.message.bean;

import java.util.List;

public class GiftBeanTwo {

    /**
     * code : 200
     * data : [{"id":9,"name":"鼓励","money":"1.00","caifu":1,"is_show":1,"img":"default/20190508/e080698e691c5b63a64587f9054b1a50.png","sort":7,"gift_id":9},{"id":14,"name":"加油哦","money":"20.00","caifu":20,"is_show":1,"img":"default/20190508/a3df2fb39850606fce3da9f6e6ddd1dc.png","sort":6,"gift_id":14},{"id":12,"name":"你好棒哦","money":"60.00","caifu":60,"is_show":1,"img":"default/20190508/ae94160f5c58d5644d499227b9b6a104.png","sort":5,"gift_id":12},{"id":16,"name":"好像在哪见过你","money":"99.00","caifu":99,"is_show":1,"img":"default/20190508/13fb757b39388622701fa1f2386fc6b6.png","sort":5,"gift_id":16},{"id":13,"name":"君若花火","money":"299.00","caifu":299,"is_show":1,"img":"default/20190508/b722856ab168f126d6ec80062c4dcaf7.png","sort":3,"gift_id":13},{"id":10,"name":"一见钟情","money":"999.00","caifu":999,"is_show":1,"img":"default/20190508/5d388f693f3ace88c860b1f8670fd5c5.png","sort":2,"gift_id":10},{"id":11,"name":"一眼万年","money":"1999.00","caifu":1999,"is_show":1,"img":"default/20190508/779f2f9f234b6a89da0e7b3ef5349140.png","sort":1,"gift_id":11},{"id":17,"name":"长长久久","money":"9999.00","caifu":9999,"is_show":1,"img":"default/20180911/4bf9654369bdb0337af1ecd598ee11bb.png","sort":0,"gift_id":17}]
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
         * id : 9
         * name : 鼓励
         * money : 1.00
         * caifu : 1
         * is_show : 1
         * img : default/20190508/e080698e691c5b63a64587f9054b1a50.png
         * sort : 7
         * gift_id : 9
         */

        private int id;
        private int gid;
        private String gift_name;
        private String zan;
        private String vid;
        private String uid;
        private String tuid;
        private String addtime;
        private String money;
        private String status;
        private String nickname;
        private String headimgurl;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getGift_name() {
            return gift_name;
        }

        public void setGift_name(String gift_name) {
            this.gift_name = gift_name;
        }

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTuid() {
            return tuid;
        }

        public void setTuid(String tuid) {
            this.tuid = tuid;
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
}
