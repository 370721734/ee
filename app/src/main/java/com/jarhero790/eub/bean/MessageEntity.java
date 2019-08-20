package com.jarhero790.eub.bean;


import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MessageEntity implements MultiItemEntity {

    public static final int TYPE_SYSTEM = 1;
    public static final int TYPE_LIKE = 2;
    public MessageSystem messageSystem;
    public MessageLike messageLike;

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public void setMessageSystem(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageLike getMessageLike() {
        return messageLike;
    }

    public void setMessageLike(MessageLike messageLike) {
        this.messageLike = messageLike;
    }



    @Override
    public String toString() {
        return "MessageEntity{" +
                "messageSystem=" + messageSystem +
                ", messageLike=" + messageLike +
                '}';
    }


    //这里怎么判断是什么布局
    @Override
    public int getItemType() {
        if (messageSystem!=null && messageSystem.getViewType()!=null){
            if(messageSystem.getViewType().equals("1")){
                return TYPE_SYSTEM;
            }
        }

        if (messageLike!=null && messageLike.getNickname()!=null){
            if(!messageLike.getNickname().equals("")){
                return TYPE_LIKE;
            }
        }



        return 0;
    }


}
