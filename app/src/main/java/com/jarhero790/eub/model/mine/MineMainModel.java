package com.jarhero790.eub.model.mine;

import android.support.annotation.NonNull;

import com.jarhero790.eub.base.BaseModel;
import com.jarhero790.eub.contract.mine.MineMainContract;

public class MineMainModel extends BaseModel implements MineMainContract.IMineMainModel {

    @NonNull
    public static MineMainModel newInstance() {
        return new MineMainModel();
    }

    @Override
    public String[] getTabs() {
        return new String[]{"知乎日报", "热点新闻", "微信精选"};
    }
}

