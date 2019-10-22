package com.jarhero790.eub.message.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.message.bean.SearchBean;
import com.jarhero790.eub.record.BaseRecyclerAdapter;
import com.jarhero790.eub.utils.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTwoAdapter extends BaseRecyclerAdapter<SearchTwoAdapter.VideoVideoHolder> {
    private Context context;
    private List<SearchBean.DataBean.LikeBean> list;

    public SearchTwoAdapter(Context context, List<SearchBean.DataBean.LikeBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindVH(VideoVideoHolder holder, int position) {
        SearchBean.DataBean.LikeBean bean = list.get(position);


        Glide.with(context).load(bean.getVideo_img()).apply(new RequestOptions()
                .placeholder(R.mipmap.zuanshi_logo).error(R.mipmap.zuanshi_logo)).into(holder.ivIcon);
        holder.tvNum.setText(CommonUtil.showzannum(bean.getZan()));
    }

    @Override
    public VideoVideoHolder onCreateVH(ViewGroup parent, int viewType) {
        View layout = View.inflate(context, R.layout.item_search, null);
        return new VideoVideoHolder(layout);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VideoVideoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.rl_all)
        RelativeLayout rlall;

        public VideoVideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
