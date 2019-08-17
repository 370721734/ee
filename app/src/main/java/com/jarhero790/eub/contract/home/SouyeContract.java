package com.jarhero790.eub.contract.home;

import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.base.IBaseFragment;
import com.jarhero790.eub.base.IBaseModel;
import com.jarhero790.eub.bean.AttentionBean;
import com.jarhero790.eub.bean.ShipinDianZan;
import com.jarhero790.eub.bean.ShipinDianZanBean;
import com.jarhero790.eub.bean.Video;
import com.jarhero790.eub.bean.VideoBean;
import java.util.ArrayList;
import io.reactivex.Observable;

/**
 * 首页Contract
 */
public interface SouyeContract {
    //首页接口
    abstract class SouyePresenter extends BasePresenter<ISouyeModel, ISouyeView> {
        //点赞
        public abstract void zan(String videoID, String tokenValue);

        //获取视频列表
        public abstract void getVideos(String cate, String page);

        //关注用户
        public abstract void attentionUser(String cate, String page);

    }

    interface ISouyeModel extends IBaseModel {
        Observable<ShipinDianZanBean> zan(String videoID, String tokenValue);
        Observable<VideoBean> getVideos(String cate, String page);
        Observable<AttentionBean> attentionUser(String buid, String token);
    }

    interface ISouyeView extends IBaseFragment {
        void updateVideos(ArrayList<Video> list);
        /**
         * 喜欢视频
         */
        void updateLikeVideo(ShipinDianZan shipinDianZan);

        /**
         * 关注了用户之后 按钮会显示 "已关注"
         */
        void updateAttentionUser();
    }
}
