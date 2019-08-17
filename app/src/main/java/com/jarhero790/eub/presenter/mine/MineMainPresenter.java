package com.jarhero790.eub.presenter.mine;

import android.support.annotation.NonNull;

import com.jarhero790.eub.contract.mine.MineMainContract;
import com.jarhero790.eub.model.mine.MineMainModel;

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
    protected MineMainContract.IMineMainModel getModel() {
        return MineMainModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}

