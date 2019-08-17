package com.jarhero790.eub.model.souye.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jarhero790.eub.R;


public class DianZanAdapter extends RecyclerView.Adapter<DianZanAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    public interface OnItemClickListener {
        void onItemClick(int position, String Url);
    }

    public DianZanAdapter.OnItemClickListener mOnItemClickListerer;

    public void setmOnItemClickListerer(DianZanAdapter.OnItemClickListener listerer) {
        this.mOnItemClickListerer = listerer;
    }

    public DianZanAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.souye_item_video_commit, parent, false);
        DianZanAdapter.MyViewHolder viewHolder = new DianZanAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 60;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}

