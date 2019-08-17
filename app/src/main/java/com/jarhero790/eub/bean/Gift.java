package com.jarhero790.eub.bean;

import java.io.Serializable;

/**
 {
 "id": 9,
 "name": "鼓励",
 "money": "1.00",
 "caifu": 1,
 "is_show": 1,
 "img": "default\/20190508\/e080698e691c5b63a64587f9054b1a50.png",
 "sort": 7,
 "gift_id": 9
  }

 */
public class Gift implements Serializable {
    private String  id;
    private String  name;
    private String  money;
    private String  caifu;
    private String  is_show;
    private String  img;
    private String  sort;
    private String  gift_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCaifu() {
        return caifu;
    }

    public void setCaifu(String caifu) {
        this.caifu = caifu;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getGift_id() {
        return gift_id;
    }

    public void setGift_id(String gift_id) {
        this.gift_id = gift_id;
    }
}
