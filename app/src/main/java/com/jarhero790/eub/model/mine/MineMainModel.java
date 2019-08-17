package com.jarhero790.eub.model.mine;

import android.support.annotation.NonNull;

import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.base.BaseModel;
import com.jarhero790.eub.bean.UserBean;
import com.jarhero790.eub.contract.mine.MineMainContract;
import com.jarhero790.eub.rxjava.RxHelper;
import com.jarhero790.eub.utils.RetrofitCreateHelper;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class MineMainModel extends BaseModel implements MineMainContract.IMineMainModel {

    @NonNull
    public static MineMainModel newInstance() {
        return new MineMainModel();
    }

    @Override
    public String[] getTabs() {
        return new String[]{"知乎日报", "热点新闻", "微信精选"};
    }

    @Override
    public Observable<ResponseBody> getuserinfo(String token) {
        return RetrofitCreateHelper.createApi(Api.class,Api.HOST).getuserinfo(token)
                .compose(RxHelper.rxSchedulerHelper());
    }


}

