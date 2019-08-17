package com.jarhero790.eub.message.contract;

import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.base.IBaseFragment;
import com.jarhero790.eub.base.IBaseModel;
import com.jarhero790.eub.bean.AttentionUserAndVideoBen;
import com.jarhero790.eub.message.bean.PinLenBean;
import com.jarhero790.eub.message.net.BaseView;

import io.reactivex.Observable;


public interface QianDaoContract {

//    abstract class QianPr extends BasePresenter<Iqian, IView> {
//        public abstract void getpinle();
//    }
//
//
//    interface Iqian extends IBaseModel {
//        Observable<PinLenBean> getpin(String token);
//    }
//
//    interface IView extends IBaseFragment {
//        void getp(PinLenBean bean);
//    }

    interface View extends BaseView{
        void getpinlen(PinLenBean bean);
    }
}
