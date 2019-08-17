package com.jarhero790.eub.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jarhero790.eub.R;
import com.jarhero790.eub.bean.AttentionUser;
import java.util.List;

public class AttentionUsersRecyclerViewAdapter extends BaseQuickAdapter<AttentionUser, BaseViewHolder> {

    public AttentionUsersRecyclerViewAdapter(@LayoutRes int layoutResId, @Nullable List<AttentionUser> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AttentionUser item) {
        //可链式调用赋值
        helper.setText(R.id.tv_username, item.getNickname())
                .setText(R.id.tv_sign, item.getSign());


        ImageView imageView = helper.getView(R.id.userHeadIcon);
        Glide.with(mContext).load(item.getHeadimgurl()).into(imageView);

        //获取当前条目position
        //int position = helper.getLayoutPosition();
    }
}