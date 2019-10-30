package com.jarhero790.eub.record.bean;

import java.util.List;

public class SearchMusicBean {

    /**
     * code : 200
     * data : [{"id":599,"name":"七月上、","uid":2088,"singer":"Jam ","addtime":"2019-06-14 19:01:12","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/bfb9c079cde6471f8a5f2220c17c5b56.mp3","music_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/bfb9c079cde6471f8a5f2220c17c5b56.png","state":0}]
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
         * id : 599
         * name : 七月上、
         * uid : 2088
         * singer : Jam
         * addtime : 2019-06-14 19:01:12
         * url : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/bfb9c079cde6471f8a5f2220c17c5b56.mp3
         * music_img : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/bfb9c079cde6471f8a5f2220c17c5b56.png
         * state : 0
         */

        private int id;
        private String name;
        private int uid;
        private String singer;
        private String addtime;
        private String url;
        private String music_img;
        private int state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMusic_img() {
            return music_img;
        }

        public void setMusic_img(String music_img) {
            this.music_img = music_img;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
