package com.jarhero790.eub.bean;


import java.util.ArrayList;

/**

  {
     "mylike": [
       XXXXX
       ],

     "video": [
       XXXXX
     ]
}

 */
public class AttentionUserAndVideo  {
    private   ArrayList<AttentionUser> mylike;
    private   ArrayList<AttentionVideo> video;

    public ArrayList<AttentionUser> getMylike() {
        return mylike;
    }

    public void setMylike(ArrayList<AttentionUser> mylike) {
        this.mylike = mylike;
    }

    public ArrayList<AttentionVideo> getVideo() {
        return video;
    }

    public void setVideo(ArrayList<AttentionVideo> video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "AttentionUserAndVideo{" +
                "mylike=" + mylike +
                ", video=" + video +
                '}';
    }
}
