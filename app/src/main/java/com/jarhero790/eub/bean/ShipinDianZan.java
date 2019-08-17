package com.jarhero790.eub.bean;


import java.io.Serializable;

/**
 *
 * 参数名	类型	说明
 is	int	返回当前观看视频的用户是否点赞当前视频，1表示已点赞；0表示未点赞或者取消点赞
 num	int	当前视频总点赞数
 *
 *
 * {"is": 0,
    "num": 1
   }
 */

public class ShipinDianZan implements Serializable {
   private String is;
    private String num;

    public String getIs() {
        return is;
    }

    public void setIs(String is) {
        this.is = is;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ShipinDianZan{" +
                "is='" + is + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
