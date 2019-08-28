package com.jarhero790.eub.message.bean;

import java.util.List;

public class OtherPingLBean {

    /**
     * code : 200
     * data : {"commentList":[{"id":53,"vid":690,"uid":5033,"tuid":5032,"parentId":52,"content":"你猜","addtime":"1个月前","zan":0,"status":1,"comment_id":53,"nickname":"niko","touser":"5032","headimgurl":"/static/images/usertouxiang.png","is_zan":2},{"id":52,"vid":690,"uid":5032,"tuid":4873,"parentId":2,"content":"buyao a","addtime":"1个月前","zan":0,"status":1,"comment_id":52,"nickname":"5032","touser":"成武短视频","headimgurl":"/static/images/usertouxiang.png","is_zan":2},{"id":51,"vid":690,"uid":5032,"tuid":4873,"parentId":2,"content":"你猜啊","addtime":"1个月前","zan":0,"status":1,"comment_id":51,"nickname":"5032","touser":"成武短视频","headimgurl":"/static/images/usertouxiang.png","is_zan":2},{"id":2,"vid":690,"uid":4873,"tuid":null,"parentId":null,"content":"成武短视频友情提示 视频没有水印效果更佳","addtime":"1个月前","zan":0,"status":1,"comment_id":2,"nickname":"成武短视频","touser":null,"headimgurl":"/upload/avatar/5d6131d0bccdb.png","is_zan":2}],"count":4}
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
         * commentList : [{"id":53,"vid":690,"uid":5033,"tuid":5032,"parentId":52,"content":"你猜","addtime":"1个月前","zan":0,"status":1,"comment_id":53,"nickname":"niko","touser":"5032","headimgurl":"/static/images/usertouxiang.png","is_zan":2},{"id":52,"vid":690,"uid":5032,"tuid":4873,"parentId":2,"content":"buyao a","addtime":"1个月前","zan":0,"status":1,"comment_id":52,"nickname":"5032","touser":"成武短视频","headimgurl":"/static/images/usertouxiang.png","is_zan":2},{"id":51,"vid":690,"uid":5032,"tuid":4873,"parentId":2,"content":"你猜啊","addtime":"1个月前","zan":0,"status":1,"comment_id":51,"nickname":"5032","touser":"成武短视频","headimgurl":"/static/images/usertouxiang.png","is_zan":2},{"id":2,"vid":690,"uid":4873,"tuid":null,"parentId":null,"content":"成武短视频友情提示 视频没有水印效果更佳","addtime":"1个月前","zan":0,"status":1,"comment_id":2,"nickname":"成武短视频","touser":null,"headimgurl":"/upload/avatar/5d6131d0bccdb.png","is_zan":2}]
         * count : 4
         */

        private int count;
        private List<CommentListBean> commentList;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public static class CommentListBean {
            /**
             * id : 53
             * vid : 690
             * uid : 5033
             * tuid : 5032
             * parentId : 52
             * content : 你猜
             * addtime : 1个月前
             * zan : 0
             * status : 1
             * comment_id : 53
             * nickname : niko
             * touser : 5032
             * headimgurl : /static/images/usertouxiang.png
             * is_zan : 2
             */

            private int id;
            private int vid;
            private int uid;
            private int tuid;
            private int parentId;
            private String content;
            private String addtime;
            private int zan;
            private int status;
            private int comment_id;
            private String nickname;
            private String touser;
            private String headimgurl;
            private int is_zan;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getVid() {
                return vid;
            }

            public void setVid(int vid) {
                this.vid = vid;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getTuid() {
                return tuid;
            }

            public void setTuid(int tuid) {
                this.tuid = tuid;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getTouser() {
                return touser;
            }

            public void setTouser(String touser) {
                this.touser = touser;
            }

            public String getHeadimgurl() {
                return headimgurl;
            }

            public void setHeadimgurl(String headimgurl) {
                this.headimgurl = headimgurl;
            }

            public int getIs_zan() {
                return is_zan;
            }

            public void setIs_zan(int is_zan) {
                this.is_zan = is_zan;
            }
        }
    }
}
