package com.jarhero790.eub.bean;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 *   {
 "list": [
 {
 "id": 2,
 "comment_id": 2,
 "vid": 690,
 "uid": 4873,
 "tuid": null,
 "parentId": null,
 "content": "成武短视频友情提示 视频没有水印效果更佳",
 "addtime": "1周前",
 "zan": 0,
 "status": 1,
 "nickname": "成武短视频",
 "touser": null,
 "headimgurl": "http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/269ed0037255c9d844a99cc38a772dc54873.png",
 "is_zan": 2
 }
 ],
 "count": 1
 }
 *
 */

public class CommentBean implements Serializable {

    private ArrayList<Comment> list;

    private String count;

    public ArrayList<Comment> getList() {
        return list;
    }

    public void setList(ArrayList<Comment> list) {
        this.list = list;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
