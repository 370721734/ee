package com.jarhero790.eub.presenter.home;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.jarhero790.eub.bean.AttentionBean;
import com.jarhero790.eub.bean.ShipinDianZanBean;
import com.jarhero790.eub.bean.VideoBean;
import com.jarhero790.eub.contract.home.SouyeContract;
import com.jarhero790.eub.model.souye.SouyeModel;

import io.reactivex.functions.Consumer;

import static com.jarhero790.eub.GlobalApplication.getContext;

public class SouyePresenter extends SouyeContract.SouyePresenter {

    @NonNull
    public static SouyePresenter newInstance() {
        return new SouyePresenter();
    }

    @Override
    protected SouyeContract.ISouyeModel getModel() {
        return SouyeModel.newInstance();
    }

    /**
     * 获取视频列表
     */
    @Override
    public void getVideos(String cate, String page) {
        mRxManager.register(mIModel.getVideos(cate, page)
                .subscribe(new Consumer<VideoBean>() {
                    @Override
                    public void accept(VideoBean data) throws Exception {
                        if (mIView == null)
                            return;
                        mIView.updateVideos(data.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }));
    }


    /**
     * 点赞
     * @param videoID
     * @param tokenValue
     */
    @Override
    public void zan(String videoID, String tokenValue) {
        mRxManager.register(mIModel.zan(videoID, tokenValue)
                  .subscribe(new Consumer<ShipinDianZanBean>() {
                    @Override
                    public void accept(ShipinDianZanBean shipinDianZanBean) throws Exception {
                        if (mIView == null) {
                            return;
                        }
                       if(shipinDianZanBean.getCode().equals("200")){
                           mIView.updateLikeVideo(shipinDianZanBean.getData());
                       }

                    }
                 }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }






    @Override
    public void attentionUser(String buid, String token) {
        mRxManager.register(mIModel.attentionUser(buid, token)
                .subscribe(new Consumer<AttentionBean>() {
                    @Override
                    public void accept(AttentionBean attentionBean) throws Exception {

                        if (mIView == null){
                            return;
                        }
                        /**
                            {
                              "code": 200,
                              "data": null,
                              "msg": "操作成功"
                             }
                         */
                        String code=attentionBean.getCode();
                        if(code.equals("200")){
                            mIView.updateAttentionUser();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }




    @Override
    public void onStart() {


    }
}

