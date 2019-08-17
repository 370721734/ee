package com.jarhero790.eub.ui.attention.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.AttentionUsersAndVideosAdapter;
import com.jarhero790.eub.base.BaseMVPCompatFragment;
import com.jarhero790.eub.base.BasePresenter;
import com.jarhero790.eub.bean.AttentionUserAndVideoBen;
import com.jarhero790.eub.contract.attention.AttentionContract;
import com.jarhero790.eub.presenter.attention.AttentionPresenter;
import com.jarhero790.eub.utils.AppUtils;
import butterknife.BindView;


public class AttentionFragment extends BaseMVPCompatFragment<AttentionContract.AttentionPresenter>
        implements AttentionContract.IAttentionView{

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




    @Override
    public void displayMyAttentionUsersAndVideos(AttentionUserAndVideoBen attentionUserAndVideoBen) {
        //Log.e("接收的数据",attentionUserAndVideoBen.toString());
        attentionUsersAndVideosAdapter = new AttentionUsersAndVideosAdapter(attentionUserAndVideoBen);
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






}

