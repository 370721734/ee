package com.jarhero790.eub.message.attention;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.R;
import com.jarhero790.eub.bean.AttentionUserVideosComment;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttPinLAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AttentionUserVideosComment> comment;

    public AttPinLAdapter(Context context, ArrayList<AttentionUserVideosComment> comment) {
        this.context = context;
        this.comment = comment;
    }

    @Override
    public int getCount() {
        return comment == null ? 0 : comment.size();
    }

    @Override
    public AttentionUserVideosComment getItem(int i) {
        return comment.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View contentView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (contentView == null) {
            contentView = View.inflate(context, R.layout.item_attention_pin, null);
            holder=new ViewHolder(contentView);
            contentView.setTag(holder);
        }else {
            holder= (ViewHolder) contentView.getTag();
        }
        holder.bindView(comment.get(i));
        return contentView;
    }

    class ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.benren)
        TextView benren;
        @BindView(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        private void bindView(AttentionUserVideosComment bean){
            name.setText(bean.getNickname());
            content.setText("："+bean.getContent());


            if (bean.getUid().equals(SharePreferenceUtil.getUserid(context))) {
                Log.e("-----userid=",SharePreferenceUtil.getUserid(context));
                benren.setVisibility(View.VISIBLE);
            }else {
                benren.setVisibility(View.GONE);
            }
        }
    }
}
