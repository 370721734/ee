package com.jarhero790.eub.message.bean;

import java.util.List;

public class SysMessageBean {

    /**
     * code : 200
     * data : {"system":[{"id":1,"admin":"钻视TV","content":"在美国的政治体制中，政治是常态，战争是例外，而中国恰恰相反\u201d，这一观点荒唐得令人喷饭","status":1,"img":"http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/06e160f141261abb8c6a8b7ae9009be14910.png","addtime":"2019-07-27 13:55:17"}]}
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
        private List<SystemBean> system;

        public List<SystemBean> getSystem() {
            return system;
        }

        public void setSystem(List<SystemBean> system) {
            this.system = system;
        }

        public static class SystemBean {
            /**
             * id : 1
             * admin : 钻视TV
             * content : 在美国的政治体制中，政治是常态，战争是例外，而中国恰恰相反”，这一观点荒唐得令人喷饭
             * status : 1
             * img : http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/06e160f141261abb8c6a8b7ae9009be14910.png
             * addtime : 2019-07-27 13:55:17
             */

            private int id;
            private String admin;
            private String content;
            private int status;
            private String img;
            private String addtime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAdmin() {
                return admin;
            }

            public void setAdmin(String admin) {
                this.admin = admin;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
        }
    }
}
