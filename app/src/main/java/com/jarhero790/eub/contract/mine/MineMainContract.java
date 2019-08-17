package com.jarhero790.eub.contract.mine;

import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.base.IBaseFragment;
import com.jarhero790.eub.base.IBaseModel;

/**
 * 我Contract
 */
public interface MineMainContract {
    //主页接口
    abstract class MineMainPresenter extends BasePresenter<IMineMainModel, IMineMainView> {
        public abstract void getTabList();
    }

    interface IMineMainModel extends IBaseModel {
        String[] getTabs();
    }

    interface IMineMainView extends IBaseFragment {
        void showTabList(String[] tabs);
    }
}
