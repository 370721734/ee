package com.jarhero790.eub.presenter.mine;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.UserBean;
import com.jarhero790.eub.contract.mine.MineMainContract;
import com.jarhero790.eub.message.bean.GuangZuBean;
import com.jarhero790.eub.model.mine.MineMainModel;
import com.jarhero790.eub.rxjava.RxHelper;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.RetrofitCreateHelper;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

import static com.jarhero790.eub.GlobalApplication.getContext;

public class MineMainPresenter extends MineMainContract.MineMainPresenter {

    @NonNull
    public static MineMainPresenter newInstance() {
        return new MineMainPresenter();
    }

    @Override
    public void getTabList() {
        if (mIView == null || mIModel == null)
            return;

        mIView.showTabList(mIModel.getTabs());
    }

    @Override
    public void getuserinfo() {
        if (mIModel == null)
            return;
//        Log.e("-------44-",SharePreferenceUtil.getToken(AppUtils.getContext()));
        mRxManager.register(mIModel.getuserinfo(SharePreferenceUtil.getToken(AppUtils.getContext())).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody bean) throws Exception {
                try {

                    if (mIView==null){
                        return;
                    }
                    mIView.showuserinfo(bean);
//                    Log.e("-------33-",bean.string());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
//                Toast.makeText(getContext(),"请求异常",Toast.LENGTH_LONG).show();
            }
        }));

    }

    @Override
    public void getmyguangzu() {
        if (mIModel==null)
            return;
        mRxManager.register(mIModel.getmyguangzu(SharePreferenceUtil.getToken(AppUtils.getContext())).subscribe(new Consumer<GuangZuBean>() {
            @Override
            public void accept(GuangZuBean bean) throws Exception {
                if (mIView==null)
                    return;
                mIView.showguangzu(bean);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
//                Toast.makeText(getContext(),"请求异常",Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    protected MineMainContract.IMineMainModel getModel() {
        return MineMainModel.newInstance();
    }

    @Override
    public void onStart() {
    }


//    @Override
//    public Observable<ResponseBody> getMessages(String token) {
//        return RetrofitCreateHelper.createApi(Api.class, Api.HOST).getMessages(token)
//                .compose(RxHelper.<ResponseBody>rxSchedulerHelper());
//    }
//
//    public Observable<ResponseBody> getuser
}

