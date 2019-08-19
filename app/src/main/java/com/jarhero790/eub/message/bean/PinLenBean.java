package com.jarhero790.eub.message.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
//个人签到页面
public class PinLenBean implements Serializable{

    /**
     * code : 200
     * data : {"sign":["2019-08-19"],"task_type":[{"id":1,"name":"评论","money":5,"addtime":"2019-07-31 13:46:28","type_id":1},{"id":2,"name":"分享","money":5,"addtime":"2019-07-31 13:46:46","type_id":2},{"id":3,"name":"发布作品","money":5,"addtime":"2019-07-31 13:47:12","type_id":3},{"id":4,"name":"赠送礼物","money":5,"addtime":"2019-07-31 13:47:39","type_id":4}],"comment":0,"share":1,"video":0,"give":0,"comment_task":2,"share_task":2,"video_task":2,"give_task":2,"continuity_sign":{"1t":"2019-08-19 13:25:41","2t":null,"3t":null,"4t":null,"5t":null,"6t":null,"7t":null}}
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
         * sign : ["2019-08-19"]
         * task_type : [{"id":1,"name":"评论","money":5,"addtime":"2019-07-31 13:46:28","type_id":1},{"id":2,"name":"分享","money":5,"addtime":"2019-07-31 13:46:46","type_id":2},{"id":3,"name":"发布作品","money":5,"addtime":"2019-07-31 13:47:12","type_id":3},{"id":4,"name":"赠送礼物","money":5,"addtime":"2019-07-31 13:47:39","type_id":4}]
         * comment : 0
         * share : 1
         * video : 0
         * give : 0
         * comment_task : 2
         * share_task : 2
         * video_task : 2
         * give_task : 2
         * continuity_sign : {"1t":"2019-08-19 13:25:41","2t":null,"3t":null,"4t":null,"5t":null,"6t":null,"7t":null}
         */

        private int comment;
        private int share;
        private int video;
        private int give;
        private int comment_task;
        private int share_task;
        private int video_task;
        private int give_task;
        private ContinuitySignBean continuity_sign;
        private List<String> sign;
        private List<TaskTypeBean> task_type;

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getShare() {
            return share;
        }

        public void setShare(int share) {
            this.share = share;
        }

        public int getVideo() {
            return video;
        }

        public void setVideo(int video) {
            this.video = video;
        }

        public int getGive() {
            return give;
        }

        public void setGive(int give) {
            this.give = give;
        }

        public int getComment_task() {
            return comment_task;
        }

        public void setComment_task(int comment_task) {
            this.comment_task = comment_task;
        }

        public int getShare_task() {
            return share_task;
        }

        public void setShare_task(int share_task) {
            this.share_task = share_task;
        }

        public int getVideo_task() {
            return video_task;
        }

        public void setVideo_task(int video_task) {
            this.video_task = video_task;
        }

        public int getGive_task() {
            return give_task;
        }

        public void setGive_task(int give_task) {
            this.give_task = give_task;
        }

        public ContinuitySignBean getContinuity_sign() {
            return continuity_sign;
        }

        public void setContinuity_sign(ContinuitySignBean continuity_sign) {
            this.continuity_sign = continuity_sign;
        }

        public List<String> getSign() {
            return sign;
        }

        public void setSign(List<String> sign) {
            this.sign = sign;
        }

        public List<TaskTypeBean> getTask_type() {
            return task_type;
        }

        public void setTask_type(List<TaskTypeBean> task_type) {
            this.task_type = task_type;
        }

        public static class ContinuitySignBean {
            /**
             * 1t : 2019-08-19 13:25:41
             * 2t : null
             * 3t : null
             * 4t : null
             * 5t : null
             * 6t : null
             * 7t : null
             */

            @SerializedName("1t")
            private String _$1t;
            @SerializedName("2t")
            private String _$2t;
            @SerializedName("3t")
            private String _$3t;
            @SerializedName("4t")
            private String _$4t;
            @SerializedName("5t")
            private String _$5t;
            @SerializedName("6t")
            private String _$6t;
            @SerializedName("7t")
            private String _$7t;

            public String get_$1t() {
                return _$1t;
            }

            public void set_$1t(String _$1t) {
                this._$1t = _$1t;
            }

            public String get_$2t() {
                return _$2t;
            }

            public void set_$2t(String _$2t) {
                this._$2t = _$2t;
            }

            public String get_$3t() {
                return _$3t;
            }

            public void set_$3t(String _$3t) {
                this._$3t = _$3t;
            }

            public String get_$4t() {
                return _$4t;
            }

            public void set_$4t(String _$4t) {
                this._$4t = _$4t;
            }

            public String get_$5t() {
                return _$5t;
            }

            public void set_$5t(String _$5t) {
                this._$5t = _$5t;
            }

            public String get_$6t() {
                return _$6t;
            }

            public void set_$6t(String _$6t) {
                this._$6t = _$6t;
            }

            public String get_$7t() {
                return _$7t;
            }

            public void set_$7t(String _$7t) {
                this._$7t = _$7t;
            }
        }

        public static class TaskTypeBean {
            /**
             * id : 1
             * name : 评论
             * money : 5
             * addtime : 2019-07-31 13:46:28
             * type_id : 1
             */

            private int id;
            private String name;
            private int money;
            private String addtime;
            private int type_id;

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

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public int getType_id() {
                return type_id;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }
        }
    }
}
