package com.jarhero790.eub.model.attention;

import android.support.annotation.NonNull;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.AttentionUserAndVideoBen;
import com.jarhero790.eub.contract.attention.AttentionContract;
import com.jarhero790.eub.rxjava.RxHelper;
import com.jarhero790.eub.utils.RetrofitCreateHelper;
import io.reactivex.Observable;

public class AttentionModel implements AttentionContract.IAttentionModel {

    @NonNull
    public static AttentionModel newInstance() {
        return new AttentionModel();
    }

    @Override
    public Observable<AttentionUserAndVideoBen> getMyAttentionUsersAndUsersVideos(String token) {
        return RetrofitCreateHelper.createApi(Api.class, Api.HOST).getAttentionUsersAndVideos(token)
                .compose(RxHelper.<AttentionUserAndVideoBen>rxSchedulerHelper());
    }


}





