package com.jarhero790.eub.message.bean;
//当前视频 赠送礼物后返回的当前视频值
public class CurrVedoBean {

    /**
     * code : 200
     * data : {"zan":30,"caifu":26,"money":51}
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
         * zan : 30
         * caifu : 26
         * money : 51
         */

        private int zan;
        private int caifu;
        private int money;

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

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }
    }
}
