package com.jarhero790.eub.contract.message;

import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.base.IBaseFragment;
import com.jarhero790.eub.base.IBaseModel;
import com.jarhero790.eub.bean.Message;
import com.jarhero790.eub.bean.MessagesBean;
import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 消息Contract
 */
public interface MessageContract {
    //主页接口
    abstract class MessagePresenter extends BasePresenter<IMessageModel, IMessageView> {
        public abstract void requestMessages();
    }

    interface IMessageModel extends IBaseModel {
        /**
         * token 用户token
         */
        Observable<ResponseBody> getMessages(String token);
    }

    interface IMessageView extends IBaseFragment {
        public abstract void updateUI(ResponseBody responseBody);
    }
}
