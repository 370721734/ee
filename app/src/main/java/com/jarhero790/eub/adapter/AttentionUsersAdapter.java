package com.jarhero790.eub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.AttentionUser;
import com.jarhero790.eub.utils.AppUtils;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AttentionUsersAdapter extends RecyclerView.Adapter<AttentionUsersAdapter.CustomViewHolder> {
    private ArrayList<AttentionUser> attentionUsers;

    private Context mcontext;

    public AttentionUsersAdapter(ArrayList<AttentionUser> attentionUsers,Context context){
        this.attentionUsers=attentionUsers;
        mcontext=context;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomViewHolder holder = new CustomViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_attention_users, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.attentionsUserName.setText(attentionUsers.get(position).getNickname());

        if (attentionUsers!=null && attentionUsers.get(position).getHeadimgurl()!=null && attentionUsers.get(position).getHeadimgurl().startsWith("http")){
            Log.e("-------------------headimgurl=",attentionUsers.get(position).getHeadimgurl());
            Glide.with(AppUtils.getContext()).load(attentionUsers.get(position).getHeadimgurl()).apply(new RequestOptions()
                    .placeholder(R.mipmap.edit_tou_icon).error(R.mipmap.edit_tou_icon)).into(holder.attentionsUserIcon);
        }else {

            Glide.with(AppUtils.getContext()).load(Api.TU+attentionUsers.get(position).getHeadimgurl()).apply(new RequestOptions()
                    .placeholder(R.mipmap.edit_tou_icon).error(R.mipmap.edit_tou_icon)).into(holder.attentionsUserIcon);
        }


//        Log.e("--------aaaa","="+attentionUsers.size());
//        if (position>4){
//            Log.e("--------aaaa","b");
//        }else  if (position==4) {
//
////            Glide.with(AppUtils.getContext()).load(Api.TU+attentionUsers.get(position).getHeadimgurl()).apply(new RequestOptions()
////                    .placeholder(R.mipmap.edit_tou_icon).error(R.mipmap.edit_tou_icon)).into(holder.attentionsUserIcon);
//            holder.attentionsUserIcon.setImageDrawable(mcontext.getResources().getDrawable(R.mipmap.atention_more_icon));
//
//        }else {
//            if (attentionUsers.get(position).getHeadimgurl().startsWith("http")){
//                Glide.with(AppUtils.getContext()).load(attentionUsers.get(position).getHeadimgurl()).apply(new RequestOptions()
//                        .placeholder(R.mipmap.edit_tou_icon).error(R.mipmap.edit_tou_icon)).into(holder.attentionsUserIcon);
//            }else {
//                Glide.with(AppUtils.getContext()).load(Api.TU+attentionUsers.get(position).getHeadimgurl()).apply(new RequestOptions()
//                        .placeholder(R.mipmap.edit_tou_icon).error(R.mipmap.edit_tou_icon)).into(holder.attentionsUserIcon);
//            }
//        }



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
