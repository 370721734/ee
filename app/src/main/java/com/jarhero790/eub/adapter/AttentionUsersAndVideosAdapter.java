package com.jarhero790.eub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jarhero790.eub.R;
import com.jarhero790.eub.bean.AttentionUser;
import com.jarhero790.eub.bean.AttentionUserAndVideoBen;
import com.jarhero790.eub.bean.AttentionVideo;
import com.jarhero790.eub.message.attention.OnItemClickear;
import com.jarhero790.eub.utils.AppUtils;
import java.util.ArrayList;

public class AttentionUsersAndVideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //这是从服务端请求回来的数据
    private AttentionUserAndVideoBen attentionUserAndVideoBen;

    //关注的用户
    ArrayList<AttentionUser> attentionUsers;

    //关注的用户所发布的视频
    ArrayList<AttentionVideo> attentionUsersVideos;

    private Context mcontext;
    private OnItemClickear onItemClickear;

    public final static int TYPE_1= 10000;//横向列表的viewType
    public final static int TYPE_2= 20000;//垂直列表的viewType
    int firstVisibleItem, lastVisibleItem, visibleCount;


    public AttentionUsersAndVideosAdapter(AttentionUserAndVideoBen attentionUserAndVideoBen,Context context,OnItemClickear onItemClickear) {
        this.attentionUserAndVideoBen = attentionUserAndVideoBen;
        attentionUsers=attentionUserAndVideoBen.getData().getMylike();
        attentionUsersVideos=attentionUserAndVideoBen.getData().getVideo();
        mcontext=context;
        this.onItemClickear = onItemClickear;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_1:
                //Log.e("哈哈1","TYPE_1");
                view = LayoutInflater.from(AppUtils.getContext()).inflate(R.layout.attentions_users, parent, false);
                return new AttentionsUsersViewHolder(view);
            case TYPE_2:
                //Log.e("哈哈2","TYPE_2");
                view = LayoutInflater.from(AppUtils.getContext()).inflate(R.layout.attentions_users_videos, parent, false);
                return new AttentionsUsersVideosViewHolder(view);
        }
        return null;
    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof AttentionsUsersViewHolder) {
              AttentionsUsersViewHolder attentionsUsersViewHolder=(AttentionsUsersViewHolder)viewHolder;
              AttentionUsersAdapter attentionUsersAdapter=new AttentionUsersAdapter(attentionUsers,mcontext);
              LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AppUtils.getContext(),LinearLayoutManager.HORIZONTAL,false);
              attentionsUsersViewHolder.recyclerViewAttentionsUsers.setLayoutManager(linearLayoutManager);
              attentionsUsersViewHolder.recyclerViewAttentionsUsers.setAdapter(attentionUsersAdapter);

        }

        if (viewHolder instanceof AttentionsUsersVideosViewHolder) {
            AttentionsUsersVideosViewHolder attentionsUsersVideosViewHolder=(AttentionsUsersVideosViewHolder)viewHolder;
            AttentionVideosAdapter attentionVideosAdapter=new AttentionVideosAdapter(attentionUsersVideos,mcontext,onItemClickear);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AppUtils.getContext(),LinearLayoutManager.VERTICAL,false);
            attentionsUsersVideosViewHolder.recyclerViewAttentionsUsersVideos.setLayoutManager(linearLayoutManager);
            attentionsUsersVideosViewHolder.recyclerViewAttentionsUsersVideos.setAdapter(attentionVideosAdapter);



            attentionsUsersVideosViewHolder.recyclerViewAttentionsUsersVideos.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    switch (newState){
                        case RecyclerView.SCROLL_STATE_IDLE:
//                            autoPlayVideo(recyclerView);//滚动停止
                            Log.e("--------------0","滚动停止");
                            break;
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    firstVisibleItem=linearLayoutManager.findFirstVisibleItemPosition();
                    lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
                    visibleCount=lastVisibleItem-firstVisibleItem;//记录可视区域item个数
                    Log.e("--------------0",firstVisibleItem+"  "+lastVisibleItem+"  "+visibleCount);
                }
            });

        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_1;
        }else{
            return TYPE_2;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


    private class AttentionsUsersViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerViewAttentionsUsers;
        public AttentionsUsersViewHolder(View itemView) {
            super(itemView);
            recyclerViewAttentionsUsers = itemView.findViewById(R.id.attentionuser_recyclerview);
        }
    }

    private class AttentionsUsersVideosViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerViewAttentionsUsersVideos;
        public AttentionsUsersVideosViewHolder(View itemView) {
            super(itemView);
            recyclerViewAttentionsUsersVideos = itemView.findViewById(R.id.attentionuservideos_recyclerview);
        }
    }




 }


