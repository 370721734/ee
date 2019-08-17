package com.jarhero790.eub.model.message;

import android.support.annotation.NonNull;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.base.BaseModel;
import com.jarhero790.eub.bean.MessagesBean;
import com.jarhero790.eub.contract.message.MessageContract;
import com.jarhero790.eub.rxjava.RxHelper;
import com.jarhero790.eub.utils.RetrofitCreateHelper;
import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MessageModel extends BaseModel implements MessageContract.IMessageModel {

    @Override
    public Observable<ResponseBody> getMessages(String token) {
        return RetrofitCreateHelper.createApi(Api.class, Api.HOST).getMessages(token)
                .compose(RxHelper.<ResponseBody>rxSchedulerHelper());
    }

    @NonNull
    public static MessageModel newInstance() {
        return new MessageModel();
    }


}

