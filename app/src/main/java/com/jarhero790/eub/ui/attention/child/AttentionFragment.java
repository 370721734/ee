package com.jarhero790.eub.ui.attention.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dueeeke.videoplayer.player.VideoView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.AttentionUsersAndVideosAdapter;
import com.jarhero790.eub.base.BaseMVPCompatFragment;
import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.bean.AttentionUserAndVideoBen;
import com.jarhero790.eub.bean.AttentionVideo;
import com.jarhero790.eub.bean.ShipinDianZanBean;
import com.jarhero790.eub.contract.attention.AttentionContract;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.attention.OnItemClickear;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.presenter.attention.AttentionPresenter;
import com.jarhero790.eub.ui.souye.BottomPingLunDialog;
import com.jarhero790.eub.ui.souye.BottomShareDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AttentionFragment extends BaseMVPCompatFragment<AttentionContract.AttentionPresenter>
        implements AttentionContract.IAttentionView, OnItemClickear {

    //关注的用户所发布的视频
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.recyclerViewAttentionUsers)
    RecyclerView recyclerViewAttentionUsers;

    AttentionUsersAndVideosAdapter attentionUsersAndVideosAdapter;

    private static AttentionFragment attentionFragment;

    public static AttentionFragment newInstance() {
        if(attentionFragment==null){
            attentionFragment = new AttentionFragment();
        }
        return attentionFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return AttentionPresenter.newInstance();
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }




    private ArrayList<AttentionVideo> attentionUsersVideos=new ArrayList<>();


    @Override
    public void displayMyAttentionUsersAndVideos(AttentionUserAndVideoBen attentionUserAndVideoBen) {
        //Log.e("接收的数据",attentionUserAndVideoBen.toString());
        attentionUsersVideos=attentionUserAndVideoBen.getData().getVideo();
        attentionUsersAndVideosAdapter = new AttentionUsersAndVideosAdapter(attentionUserAndVideoBen,getActivity(),this);
        recyclerViewAttentionUsers.setLayoutManager(linearLayoutManager);
        recyclerViewAttentionUsers.setAdapter(attentionUsersAndVideosAdapter);
        /**
        this.attentionUsers=attentionUsers;
        attentionUsersAdapter = new AttentionUsersAdapter(attentionUsers);
        recyclerViewAttentionUsers.setLayoutManager(attentionUsersLinearLayoutManager);
        recyclerViewAttentionUsers.setAdapter(attentionUsersAdapter);
        //点击更多图标 跳转到用户搜索
        moreAttentUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AppUtils.getContext(),AttentionUsersActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("attentionUsersList",attentionUsers);
                intent.putExtra("customBundle",args);
                startActivity(intent);
            }
       });
     **/
    }



    @Override
    public void showNetworkError() {

    }


    /**
     * 懒加载
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        linearLayoutManager=new LinearLayoutManager(AppUtils.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //加载数据
        mPresenter.requestMyAttentionUsersAndUsersVideos();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_attention;
    }



    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {


    }



    public void showShare() {
        BottomShareDialog bottomShareDialog = BottomShareDialog.newInstance();
        bottomShareDialog.show(getChildFragmentManager(), "share");
    }

    //ok
    public void showPingLun(int po) {
//        Log.e("--------token","token");
//        Log.e("--------token",SharePreferenceUtil.getToken(AppUtils.getContext()));
//
//        if (SharePreferenceUtil.getToken(AppUtils.getContext())==null){
//            Log.e("--------token","token1");
//        }

        if (SharePreferenceUtil.getToken(AppUtils.getContext()).equals("")){
//            Log.e("--------token","token2");
            startActivity(new Intent(getActivity(), LoginNewActivity.class));
        }else {
            BottomPingLunDialog bottomPingLunDialog = BottomPingLunDialog.newInstance();
            Bundle args=new Bundle();
            args.putString("vid",attentionUsersVideos.get(po).getVideo_id());
            bottomPingLunDialog.setArguments(args);
            bottomPingLunDialog.show(getChildFragmentManager(), "pinglun");
        }

    }


    @Override
    public void linerck(int position, String type, View view,View view2) {
        Log.e("---------",type+"  "+position);
        if (type.equals("播放")){
            ImageView ivplay = (ImageView) view;
            VideoView videoView= (VideoView) view2;


        }else if (type.equals("分享")){
            showShare();
        }else if (type.equals("评论")){
            showPingLun(position);
        }else if (type.equals("点赞")){
            ImageView ivLike = (ImageView) view;
            RetrofitManager.getInstance().getDataServer().zanorno(attentionUsersVideos.get(position).getVideo_id(),SharePreferenceUtil.getToken(AppUtils.getContext()))
                    .enqueue(new Callback<ShipinDianZanBean>() {
                        @Override
                        public void onResponse(Call<ShipinDianZanBean> call, Response<ShipinDianZanBean> response) {
                            if (response.isSuccessful()){
                                if (response.body()!=null && response.body().getCode().equals("200")){
                                    String value = response.body().getData().getIs();
                                    if (value.equals("1")) {
                                        ivLike.setImageResource(R.drawable.iv_like_selected);
                                    } else {
                                        ivLike.setImageResource(R.drawable.iv_like_unselected);
                                    }


                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ShipinDianZanBean> call, Throwable t) {

                        }
                    });






        }
    }

}

