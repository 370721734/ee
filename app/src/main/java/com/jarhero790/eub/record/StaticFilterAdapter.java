package com.jarhero790.eub.record;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.jarhero790.eub.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hans on 2017/11/6.
 */

public class StaticFilterAdapter extends BaseRecyclerAdapter<StaticFilterAdapter.FilterViewHolder> {
    private List<Integer> mFilterList;
    private List<String> str=new ArrayList<>();
    private int mCurrentSelectedPos;

    public StaticFilterAdapter(List<Integer> list) {
        mFilterList = list;
        str.add("原图");
        str.add("标准");
        str.add("樱红");
        str.add("云裳");
        str.add("纯真");
        str.add("白兰");
        str.add("元气");
        str.add("超脱");
        str.add("香氛");
        str.add("浪漫");
        str.add("清新");
        str.add("唯美");
        str.add("粉嫩");
        str.add("怀旧");
        str.add("蓝调");
        str.add("清凉");
        str.add("日系");
        str.add("日系");
        str.add("日系");

    }


    public void setCurrentSelectedPos(int pos) {
        int tPos = mCurrentSelectedPos;
        mCurrentSelectedPos = pos;
        this.notifyItemChanged(tPos);
        this.notifyItemChanged(mCurrentSelectedPos);
    }

    @Override
    public void onBindVH(FilterViewHolder holder, int position) {
        //Glide.with(holder.itemView.getContext()).load(mFilterList.get(position)).into(holder.ivImage);
        holder.ivImage.setImageResource(mFilterList.get(position));
        holder.textView.setText(str.get(position));
        if (mCurrentSelectedPos == position) {
            holder.ivImageTint.setVisibility(View.VISIBLE);
        } else {
            holder.ivImageTint.setVisibility(View.GONE);
        }
    }


    @Override
    public FilterViewHolder onCreateVH(ViewGroup parent, int viewType) {
        return new FilterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_layout, parent, false));
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }

    public static class FilterViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivImage, ivImageTint;
        TextView textView;

        public FilterViewHolder(View itemView) {
            super(itemView);
            ivImage = (CircleImageView) itemView.findViewById(R.id.filter_image);
            ivImageTint = (CircleImageView) itemView.findViewById(R.id.filter_image_tint);
            textView=itemView.findViewById(R.id.tv_text);
        }
    }
}
