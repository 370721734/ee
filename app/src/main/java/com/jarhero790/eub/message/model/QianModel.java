package com.jarhero790.eub.message.model;

import android.support.annotation.NonNull;

import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.bean.PinLenBean;
import com.jarhero790.eub.message.contract.QianDaoContract;
import com.jarhero790.eub.model.attention.AttentionModel;
import com.jarhero790.eub.rxjava.RxHelper;
import com.jarhero790.eub.utils.RetrofitCreateHelper;

import io.reactivex.Observable;

public class QianModel{
    @NonNull
    public static QianModel newInstance() {
        return new QianModel();
    }


    public Observable<PinLenBean> getpin(String token) {
        return RetrofitCreateHelper.createApi(Api.class,Api.HOST).getpinlen(token)
                .compose(RxHelper.rxSchedulerHelper());
    }


}
