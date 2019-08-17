package com.jarhero790.eub.presenter.message;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.jarhero790.eub.bean.MessagesBean;
import com.jarhero790.eub.contract.message.MessageContract;
import com.jarhero790.eub.model.message.MessageModel;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import io.reactivex.functions.Consumer;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.jarhero790.eub.GlobalApplication.getContext;


public class MessagePresenter extends MessageContract.MessagePresenter {

    @Override
    public void requestMessages() {
        if (mIModel == null)
            return;

        mRxManager.register(mIModel.getMessages(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        if (mIView == null)
                            return;
                        mIView.updateUI(responseBody);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }));


    }

    @NonNull
    public static MessagePresenter newInstance() {
        return new MessagePresenter();
    }

    @Override
    protected MessageContract.IMessageModel getModel() {
        return MessageModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}

