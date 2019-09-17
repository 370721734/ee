package com.jarhero790.eub.message.bean;

import java.util.List;

public class TongKuanBean {

    /**
     * code : 200
     * data : {"video":{"id":"视频ID","mid":"音乐ID","title":"标题","describe":"视频描述"},"music_count":"音乐使用人数","identical":[{"id":"id","uid":"用户ID","mid":"音乐ID","good_id":"商品ID","time":"时长","title":"标题","describe":"视频描述","url":"视频地址","income":"视频收入","addtime":"添加时间","state":"0待审核  1通过   2失败","sh_time":"审核通过时间","zan":"赞数","caifu":"财富数量","visit_val":"浏览量","cate":"分类","is_tj":"是否推荐","video_img":"视频脱","address":"深圳","lng":null,"lat":null}]}
     * msg : 成功
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
         * video : {"id":"视频ID","mid":"音乐ID","title":"标题","describe":"视频描述"}
         * music_count : 音乐使用人数
         * identical : [{"id":"id","uid":"用户ID","mid":"音乐ID","good_id":"商品ID","time":"时长","title":"标题","describe":"视频描述","url":"视频地址","income":"视频收入","addtime":"添加时间","state":"0待审核  1通过   2失败","sh_time":"审核通过时间","zan":"赞数","caifu":"财富数量","visit_val":"浏览量","cate":"分类","is_tj":"是否推荐","video_img":"视频脱","address":"深圳","lng":null,"lat":null}]
         */

        private VideoBean video;
        private String music_count;
        private List<IdenticalBean> identical;

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        public String getMusic_count() {
            return music_count;
        }

        public void setMusic_count(String music_count) {
            this.music_count = music_count;
        }

        public List<IdenticalBean> getIdentical() {
            return identical;
        }

        public void setIdentical(List<IdenticalBean> identical) {
            this.identical = identical;
        }

        public static class VideoBean {
            /**
             * id : 视频ID
             * mid : 音乐ID
             * title : 标题
             * describe : 视频描述
             */

            private String id;
            private String mid;
            private String video_img;
            private String title;
            private String describe;
            private String music_count;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public String getVideo_img() {
                return video_img;
            }

            public void setVideo_img(String video_img) {
                this.video_img = video_img;
            }

            public String getMusic_count() {
                return music_count;
            }

            public void setMusic_count(String music_count) {
                this.music_count = music_count;
            }
        }

        public static class IdenticalBean {
            /**
             * id : id
             * uid : 用户ID
             * mid : 音乐ID
             * good_id : 商品ID
             * time : 时长
             * title : 标题
             * describe : 视频描述
             * url : 视频地址
             * income : 视频收入
             * addtime : 添加时间
             * state : 0待审核  1通过   2失败
             * sh_time : 审核通过时间
             * zan : 赞数
             * caifu : 财富数量
             * visit_val : 浏览量
             * cate : 分类
             * is_tj : 是否推荐
             * video_img : 视频脱
             * address : 深圳
             * lng : null
             * lat : null
             */

            private String id;
            private String uid;
            private String mid;
            private String good_id;
            private String time;
            private String title;
            private String describe;
            private String url;
            private String income;
            private String addtime;
            private String state;
            private String sh_time;
            private String zan;
            private String caifu;
            private String visit_val;
            private String cate;
            private String is_tj;
            private String video_img;
            private String address;
            private String lng;
            private String lat;

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

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getGood_id() {
                return good_id;
            }

            public void setGood_id(String good_id) {
                this.good_id = good_id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getIncome() {
                return income;
            }

            public void setIncome(String income) {
                this.income = income;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getSh_time() {
                return sh_time;
            }

            public void setSh_time(String sh_time) {
                this.sh_time = sh_time;
            }

            public String getZan() {
                return zan;
            }

            public void setZan(String zan) {
                this.zan = zan;
            }

            public String getCaifu() {
                return caifu;
            }

            public void setCaifu(String caifu) {
                this.caifu = caifu;
            }

            public String getVisit_val() {
                return visit_val;
            }

            public void setVisit_val(String visit_val) {
                this.visit_val = visit_val;
            }

            public String getCate() {
                return cate;
            }

            public void setCate(String cate) {
                this.cate = cate;
            }

            public String getIs_tj() {
                return is_tj;
            }

            public void setIs_tj(String is_tj) {
                this.is_tj = is_tj;
            }

            public String getVideo_img() {
                return video_img;
            }

            public void setVideo_img(String video_img) {
                this.video_img = video_img;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }
        }
    }
}
