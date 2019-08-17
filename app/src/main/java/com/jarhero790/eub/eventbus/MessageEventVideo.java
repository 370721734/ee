package com.jarhero790.eub.eventbus;

import com.jarhero790.eub.bean.Video;

import java.util.List;

public class MessageEventVideo {
    private List<Video> mVideoList;

    public  MessageEventVideo(List<Video> mVideoList){
        this.mVideoList=mVideoList;
    }

    public List<Video> getMessage() {
        return mVideoList;
    }

    public void setMessage(List<Video> mVideoList) {
        this.mVideoList = mVideoList;
    }


}
