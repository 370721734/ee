package com.jarhero790.eub.message.presenter;

import android.support.annotation.NonNull;

import com.jarhero790.eub.message.model.QianModel;
import com.jarhero790.eub.message.my.QianDaoActivity;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

public class QianPr extends BasePresenter<QianDaoActivity> {

    QianModel mode;

    public QianPr(@NonNull QianDaoActivity view) {
        super(view);
        mode = new QianModel();
    }

    public void getpinlen() {
        view.onProgress();
        mode.getpin(SharePreferenceUtil.getToken(AppUtils.getContext())).compose(applySchedulers())
                .as(autoDisposable())
                .subscribe(pin -> {
                            if (pin.getCode() == 200) {
                                view.getpinlen(pin);
                            }
                        },
                        throwable -> view.onError(throwable),
                        () -> view.onComplete());
    }


//    public void getpinle() {
//        if (mIModel==null)
//            return;
//        mRxManager.register(mIModel.getpin(SharePreferenceUtil.getToken(AppUtils.getContext()))
//        .subscribe(new Consumer<PinLenBean>() {
//            @Override
//            public void accept(PinLenBean bean) throws Exception {
//                    if (mIView==null)
//                        return;
////                    mIView.getpin(bean);
////                Log.e("----111",bean.getCode()+" "+SharePreferenceUtil.getToken(AppUtils.getContext()));
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Toast.makeText(getContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
//                Log.e("----222",  "");
//            }
//        }));
//
//    }


}
