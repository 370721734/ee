package com.jarhero790.eub.contract.mine;

import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.base.IBaseFragment;
import com.jarhero790.eub.base.IBaseModel;
import com.jarhero790.eub.bean.UserBean;
import com.jarhero790.eub.message.bean.GuangZuBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 我Contract
 */
public interface MineMainContract {
    //主页接口
    abstract class MineMainPresenter extends BasePresenter<IMineMainModel, IMineMainView> {
        public abstract void getTabList();

        public abstract void getuserinfo();

        public abstract void getmyguangzu();
    }

    interface IMineMainModel extends IBaseModel {
        String[] getTabs();

        Observable<ResponseBody> getuserinfo(String token);

        Observable<GuangZuBean> getmyguangzu(String token);
    }

    interface IMineMainView extends IBaseFragment {
        void showTabList(String[] tabs);

         void showuserinfo(ResponseBody bean);

         void showguangzu(GuangZuBean bean);

    }


}
