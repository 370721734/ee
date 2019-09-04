package com.jarhero790.eub.record.bean;

import java.util.List;

public class MusicBean {

    /**
     * code : 200
     * data : [{"nickname":"成武新闻","collect":null,"headimgurl":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/472deda92a776e826dc02e5ae21d4bd82088.png","id":659,"name":"还有多少个十年","music_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/caa6b5deb35181bf18b285a8bdc74727.png","singer":"华语群星","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/caa6b5deb35181bf18b285a8bdc74727.mp3"},{"nickname":"成武新闻","collect":null,"headimgurl":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/472deda92a776e826dc02e5ae21d4bd82088.png","id":891,"name":"暧昧","music_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/8b48dffd8875f1020bcf07740688cd66.png","singer":"冷雪儿","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/8b48dffd8875f1020bcf07740688cd66.mp3"},{"nickname":"成武新闻","collect":null,"headimgurl":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/472deda92a776e826dc02e5ae21d4bd82088.png","id":838,"name":" 包容","music_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/5bb6ee28a4962097df979eb622dc71f4.png","singer":"DJ杨硕","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/5bb6ee28a4962097df979eb622dc71f4.mp3"},{"nickname":"成武新闻","collect":null,"headimgurl":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/472deda92a776e826dc02e5ae21d4bd82088.png","id":773,"name":"四分之三","music_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/351cb131c77693224423afea9212b8b1.png","singer":"獨弦嘆歌","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/351cb131c77693224423afea9212b8b1.mp3"},{"nickname":"成武新闻","collect":null,"headimgurl":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/472deda92a776e826dc02e5ae21d4bd82088.png","id":259,"name":"一百万个可能、","music_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/165dc24b42dce121110e751cd0aa5c3e.png","singer":"无名","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/165dc24b42dce121110e751cd0aa5c3e.mp3"},{"nickname":"成武新闻","collect":null,"headimgurl":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/472deda92a776e826dc02e5ae21d4bd82088.png","id":712,"name":"舞女泪 ","music_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/d092a5b17253c7e67801a18702aefb1c.png","singer":"韩宝仪","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/d092a5b17253c7e67801a18702aefb1c.mp3"},{"nickname":"成武新闻","collect":null,"headimgurl":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/472deda92a776e826dc02e5ae21d4bd82088.png","id":1030,"name":"最好","music_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/8049393b157d643e621235d234507bb9.png","singer":"薛之谦 ","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/8049393b157d643e621235d234507bb9.mp3"},{"nickname":"成武新闻","collect":null,"headimgurl":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/472deda92a776e826dc02e5ae21d4bd82088.png","id":972,"name":"Sexy lady (Original Mix)","music_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/700da1cb8a479932aca9ad4b7822ddb1.png","singer":"安筱冷 ","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/700da1cb8a479932aca9ad4b7822ddb1.mp3"},{"nickname":"成武新闻","collect":null,"headimgurl":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/472deda92a776e826dc02e5ae21d4bd82088.png","id":605,"name":" 小永远","music_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/d402be3905070a93984f9a9b35372830.png","singer":"冯提莫","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/d402be3905070a93984f9a9b35372830.mp3"},{"nickname":"成武新闻","collect":null,"headimgurl":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/472deda92a776e826dc02e5ae21d4bd82088.png","id":994,"name":" 侧脸","music_img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/96cd892c1703f1dd0dacfe7b458f410b.png","singer":"于果 ","url":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/96cd892c1703f1dd0dacfe7b458f410b.mp3"}]
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
         * nickname : 成武新闻
         * collect : null
         * headimgurl : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/472deda92a776e826dc02e5ae21d4bd82088.png
         * id : 659
         * name : 还有多少个十年
         * music_img : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/caa6b5deb35181bf18b285a8bdc74727.png
         * singer : 华语群星
         * url : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/caa6b5deb35181bf18b285a8bdc74727.mp3
         */

        private String nickname;
        private Object collect;
        private String headimgurl;
        private int id;
        private String name;
        private String music_img;
        private String singer;
        private String url;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getCollect() {
            return collect;
        }

        public void setCollect(Object collect) {
            this.collect = collect;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

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

        public String getMusic_img() {
            return music_img;
        }

        public void setMusic_img(String music_img) {
            this.music_img = music_img;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
