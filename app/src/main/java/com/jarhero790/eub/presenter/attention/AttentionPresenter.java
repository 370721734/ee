package com.jarhero790.eub.presenter.attention;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.jarhero790.eub.bean.AttentionUserAndVideoBen;
import com.jarhero790.eub.contract.attention.AttentionContract;
import com.jarhero790.eub.model.attention.AttentionModel;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import io.reactivex.functions.Consumer;
import static com.jarhero790.eub.GlobalApplication.getContext;


public class AttentionPresenter extends AttentionContract.AttentionPresenter {



    @NonNull
    public static AttentionPresenter newInstance() {
        return new AttentionPresenter();
    }


    @Override
    protected AttentionContract.IAttentionModel getModel() {
        return AttentionModel.newInstance();
    }

    @Override
    public void onStart() {

    }



    @Override
    public void requestMyAttentionUsersAndUsersVideos() {
        if (mIModel == null)
            return;

        mRxManager.register(mIModel.getMyAttentionUsersAndUsersVideos(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .subscribe(new Consumer<AttentionUserAndVideoBen>() {
                    @Override
                    public void accept(AttentionUserAndVideoBen attentionUserAndVideoBen) throws Exception {
                        if (mIView == null){
                            return;
                        }
                        mIView.displayMyAttentionUsersAndVideos(attentionUserAndVideoBen);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }));




    }




}

