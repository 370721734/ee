package com.jarhero790.eub.model.souye;

import android.support.annotation.NonNull;

import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.base.BaseModel;
import com.jarhero790.eub.bean.AttentionBean;
import com.jarhero790.eub.bean.ShipinDianZanBean;
import com.jarhero790.eub.bean.VideoBean;
import com.jarhero790.eub.contract.home.SouyeContract;
import com.jarhero790.eub.rxjava.RxHelper;
import com.jarhero790.eub.utils.RetrofitCreateHelper;

import io.reactivex.Observable;


public class SouyeModel extends BaseModel implements SouyeContract.ISouyeModel {

    @NonNull
    public static SouyeModel newInstance() {
        return new SouyeModel();
    }



    @Override
    public Observable<ShipinDianZanBean> zan(String videoID, String tokenValue) {
        return RetrofitCreateHelper.createApi(Api.class, Api.HOST).zan(videoID,tokenValue)
                                   .compose(RxHelper.<ShipinDianZanBean>rxSchedulerHelper());
    }


    @Override
    public Observable<VideoBean> getVideos(String cate, String page) {
        return RetrofitCreateHelper.createApi(Api.class, Api.HOST).getVideos(cate,page)
                                   .compose(RxHelper.<VideoBean>rxSchedulerHelper());
    }

    @Override
    public Observable<VideoBean> getVideos(String cate, String page, String token) {
        return RetrofitCreateHelper.createApi(Api.class,Api.HOST).getVideos(cate,page,token)
                .compose(RxHelper.rxSchedulerHelper());
    }


    @Override
    public Observable<AttentionBean> attentionUser(String buid, String token) {
        return RetrofitCreateHelper.createApi(Api.class, Api.HOST).attentionUser(buid,token)
                                   .compose(RxHelper.<AttentionBean>rxSchedulerHelper());
    }




}

