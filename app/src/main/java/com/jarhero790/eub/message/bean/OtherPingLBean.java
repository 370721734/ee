package com.jarhero790.eub.message.bean;

import java.util.List;

public class OtherPingLBean {


    /**
     * code : 200
     * data : {"commentList":[{"id":5,"vid":1286,"uid":5044,"tuid":null,"parentId":null,"content":"12233875858585745868","addtime":"2019-09-17 19:25:40","zan":0,"status":0,"comment_id":5,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2,"ucomment":[{"id":9,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:26","zan":0,"status":1,"comment_id":9,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":8,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:24","zan":0,"status":1,"comment_id":8,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":7,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866464848175849191","addtime":"2019-09-17 19:26:17","zan":0,"status":1,"comment_id":7,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":6,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  58664648481","addtime":"2019-09-17 19:26:12","zan":0,"status":1,"comment_id":6,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2}]},{"id":4,"vid":1286,"uid":5044,"tuid":null,"parentId":null,"content":"12233875858585775","addtime":"2019-09-17 19:25:37","zan":0,"status":0,"comment_id":4,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2,"ucomment":[{"id":9,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:26","zan":0,"status":1,"comment_id":9,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":8,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:24","zan":0,"status":1,"comment_id":8,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":7,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866464848175849191","addtime":"2019-09-17 19:26:17","zan":0,"status":1,"comment_id":7,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":6,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  58664648481","addtime":"2019-09-17 19:26:12","zan":0,"status":1,"comment_id":6,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2}]},{"id":3,"vid":1286,"uid":5044,"tuid":null,"parentId":null,"content":"12233","addtime":"2019-09-17 19:25:33","zan":0,"status":0,"comment_id":3,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2,"ucomment":[{"id":9,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:26","zan":0,"status":1,"comment_id":9,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":8,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:24","zan":0,"status":1,"comment_id":8,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":7,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866464848175849191","addtime":"2019-09-17 19:26:17","zan":0,"status":1,"comment_id":7,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":6,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  58664648481","addtime":"2019-09-17 19:26:12","zan":0,"status":1,"comment_id":6,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2}]}],"count":3}
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
         * commentList : [{"id":5,"vid":1286,"uid":5044,"tuid":null,"parentId":null,"content":"12233875858585745868","addtime":"2019-09-17 19:25:40","zan":0,"status":0,"comment_id":5,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2,"ucomment":[{"id":9,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:26","zan":0,"status":1,"comment_id":9,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":8,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:24","zan":0,"status":1,"comment_id":8,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":7,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866464848175849191","addtime":"2019-09-17 19:26:17","zan":0,"status":1,"comment_id":7,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":6,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  58664648481","addtime":"2019-09-17 19:26:12","zan":0,"status":1,"comment_id":6,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2}]},{"id":4,"vid":1286,"uid":5044,"tuid":null,"parentId":null,"content":"12233875858585775","addtime":"2019-09-17 19:25:37","zan":0,"status":0,"comment_id":4,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2,"ucomment":[{"id":9,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:26","zan":0,"status":1,"comment_id":9,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":8,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:24","zan":0,"status":1,"comment_id":8,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":7,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866464848175849191","addtime":"2019-09-17 19:26:17","zan":0,"status":1,"comment_id":7,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":6,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  58664648481","addtime":"2019-09-17 19:26:12","zan":0,"status":1,"comment_id":6,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2}]},{"id":3,"vid":1286,"uid":5044,"tuid":null,"parentId":null,"content":"12233","addtime":"2019-09-17 19:25:33","zan":0,"status":0,"comment_id":3,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2,"ucomment":[{"id":9,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:26","zan":0,"status":1,"comment_id":9,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":8,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:24","zan":0,"status":1,"comment_id":8,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":7,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866464848175849191","addtime":"2019-09-17 19:26:17","zan":0,"status":1,"comment_id":7,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":6,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  58664648481","addtime":"2019-09-17 19:26:12","zan":0,"status":1,"comment_id":6,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2}]}]
         * count : 3
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
             * id : 5
             * vid : 1286
             * uid : 5044
             * tuid : null
             * parentId : null
             * content : 12233875858585745868
             * addtime : 2019-09-17 19:25:40
             * zan : 0
             * status : 0
             * comment_id : 5
             * nickname : 5044
             * headimgurl : http://www.51ayhd.com/upload/avatar/5d78d705671a2.png
             * is_zan : 2
             * ucomment : [{"id":9,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:26","zan":0,"status":1,"comment_id":9,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":8,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866664648573","addtime":"2019-09-17 19:26:24","zan":0,"status":1,"comment_id":8,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":7,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  5866464848175849191","addtime":"2019-09-17 19:26:17","zan":0,"status":1,"comment_id":7,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2},{"id":6,"vid":1286,"uid":5044,"tuid":5044,"parentId":5044,"content":"@5044  58664648481","addtime":"2019-09-17 19:26:12","zan":0,"status":1,"comment_id":6,"nickname":"5044","headimgurl":"http://www.51ayhd.com/upload/avatar/5d78d705671a2.png","is_zan":2}]
             */

            private int id;
            private int vid;
            private int uid;
            private String tuid;
            private String parentId;
            private String content;
            private String addtime;
            private int zan;
            private int status;
            private int comment_id;
            private String nickname;
            private String headimgurl;
            private int is_zan;
            private List<UcommentBean> ucomment;

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

            public String getTuid() {
                return tuid;
            }

            public void setTuid(String tuid) {
                this.tuid = tuid;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
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

            public List<UcommentBean> getUcomment() {
                return ucomment;
            }

            public void setUcomment(List<UcommentBean> ucomment) {
                this.ucomment = ucomment;
            }

            public static class UcommentBean {
                /**
                 * id : 9
                 * vid : 1286
                 * uid : 5044
                 * tuid : 5044
                 * parentId : 5044
                 * content : @5044  5866664648573
                 * addtime : 2019-09-17 19:26:26
                 * zan : 0
                 * status : 1
                 * comment_id : 9
                 * nickname : 5044
                 * headimgurl : http://www.51ayhd.com/upload/avatar/5d78d705671a2.png
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
}
