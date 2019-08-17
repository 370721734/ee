package com.jarhero790.eub.bean;


import java.io.Serializable;

/**
 *  {
     "id": 1,
     "admin": "钻视TV",
     "content": "内容",
     "status": 1,
     "img": "头像",
     "addtime": "添加时间"
    }
 *
 *
 */
public class MessageSystem implements Serializable {
    private String viewType;
    private String id;
    private String admin;
    private String content;
    private String status;
    private String img;
    private String addtime;

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    @Override
    public String toString() {
        return "MessageSystem{" +
                "viewType=" + viewType +
                ", id='" + id + '\'' +
                ", admin='" + admin + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", img='" + img + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }
}
