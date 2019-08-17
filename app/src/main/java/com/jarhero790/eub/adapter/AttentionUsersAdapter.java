package com.jarhero790.eub.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.jarhero790.eub.R;
import com.jarhero790.eub.bean.AttentionUser;
import com.jarhero790.eub.utils.AppUtils;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class AttentionUsersAdapter extends RecyclerView.Adapter<AttentionUsersAdapter.CustomViewHolder> {
    private ArrayList<AttentionUser> attentionUsers;

    public AttentionUsersAdapter(ArrayList<AttentionUser> attentionUsers){
        this.attentionUsers=attentionUsers;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomViewHolder holder = new CustomViewHolder(LayoutInflater.from(AppUtils.getContext()).inflate(R.layout.item_attention_users, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.attentionsUserName.setText(attentionUsers.get(position).getNickname());
        Glide.with(AppUtils.getContext()).load(attentionUsers.get(position).getHeadimgurl()).into(holder.attentionsUserIcon);
    }

    @Override
    public int getItemCount() {
        return attentionUsers.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView attentionsUserIcon;
        private TextView attentionsUserName;

        public CustomViewHolder(View view) {
            super(view);
            attentionsUserIcon=view.findViewById(R.id.attentionsUserIcon);
            attentionsUserName=view.findViewById(R.id.attentionsUserName);
        }
    }
}
