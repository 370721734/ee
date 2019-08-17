package com.jarhero790.eub.bean;


import java.io.Serializable;
import java.util.ArrayList;

public class VideoBean implements Serializable {
    private String code;
    private String msg;
    private ArrayList<Video> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Video> getData() {
        return data;
    }

    public void setData(ArrayList<Video> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "VideoBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
