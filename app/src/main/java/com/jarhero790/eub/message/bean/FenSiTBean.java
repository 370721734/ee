package com.jarhero790.eub.message.bean;

import java.util.List;

public class FenSiTBean {

    /**
     * code : 200
     * data : [{"nickname":"5032","headimgurl":"/static/images/usertouxiang.png","rong_id":5032,"rong_token":"VymYdNrAg1tJrBcEbUoMMTC+AcwG9OuaMU77gxnpvCMxkD1T1b7Lk/mroIXOIJjxL4OSg1toIa2Vo1pe6RwiTQ==","user_id":5032,"is_likeEach":1,"addtime":"2019-08-17 17:05:13"}]
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
         * nickname : 5032
         * headimgurl : /static/images/usertouxiang.png
         * rong_id : 5032
         * rong_token : VymYdNrAg1tJrBcEbUoMMTC+AcwG9OuaMU77gxnpvCMxkD1T1b7Lk/mroIXOIJjxL4OSg1toIa2Vo1pe6RwiTQ==
         * user_id : 5032
         * is_likeEach : 1
         * addtime : 2019-08-17 17:05:13
         */

        private String nickname;
        private String headimgurl;
        private int rong_id;
        private String rong_token;
        private int user_id;
        private int is_likeEach;
        private String addtime;

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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getIs_likeEach() {
            return is_likeEach;
        }

        public void setIs_likeEach(int is_likeEach) {
            this.is_likeEach = is_likeEach;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
