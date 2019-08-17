package com.jarhero790.eub.contract.attention;

import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.base.IBaseFragment;
import com.jarhero790.eub.base.IBaseModel;
import com.jarhero790.eub.bean.AttentionUser;
import com.jarhero790.eub.bean.AttentionUserAndVideoBen;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * 关注Contract
 */
public interface AttentionContract {
    abstract class AttentionPresenter extends BasePresenter<IAttentionModel, IAttentionView> {
        /**
         * 加载
         */
        public abstract void requestMyAttentionUsersAndUsersVideos();


    }

    interface IAttentionModel extends IBaseModel {
        /**
         * 获取关注用户以及被关注用户的视频
         * token 用户token
         */
        Observable<AttentionUserAndVideoBen> getMyAttentionUsersAndUsersVideos(String token);
    }

    interface IAttentionView extends IBaseFragment {
        /**
         * 更新界面list
         */
        void displayMyAttentionUsersAndVideos(AttentionUserAndVideoBen attentionUserAndVideoBen);
        /**
         * 显示网络错误
         */
        void showNetworkError();

    }
}
