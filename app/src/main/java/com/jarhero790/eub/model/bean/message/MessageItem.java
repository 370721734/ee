package com.jarhero790.eub.model.bean.message;


import java.util.ArrayList;

public class MessageItem {
    private ArrayList<MessageItemLike> like;

    private ArrayList<MessageItemZan> zan;

    private ArrayList<MessageItemGift> gift;

    private ArrayList<MessageItemComment> comment;

    private ArrayList<MessageItemReward> reward;

    public ArrayList<MessageItemLike> getLike() {
        return like;
    }

    public void setLike(ArrayList<MessageItemLike> like) {
        this.like = like;
    }

    public ArrayList<MessageItemZan> getZan() {
        return zan;
    }

    public void setZan(ArrayList<MessageItemZan> zan) {
        this.zan = zan;
    }

    public ArrayList<MessageItemGift> getGift() {
        return gift;
    }

    public void setGift(ArrayList<MessageItemGift> gift) {
        this.gift = gift;
    }

    public ArrayList<MessageItemComment> getComment() {
        return comment;
    }

    public void setComment(ArrayList<MessageItemComment> comment) {
        this.comment = comment;
    }

    public ArrayList<MessageItemReward> getReward() {
        return reward;
    }

    public void setReward(ArrayList<MessageItemReward> reward) {
        this.reward = reward;
    }
}
