package com.jarhero790.eub.ui.souye;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.activity.FensiActivity;
import com.jarhero790.eub.adapter.CommentExpandAdapter;
import com.jarhero790.eub.message.adapter.PingLenVideoAdapter;
import com.jarhero790.eub.message.bean.OtherPingLBean;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BottomPingLunDialog extends DialogFragment {

    private View frView;
    private Window window;

    private RecyclerView recyclerView;
    private static BottomPingLunDialog instance=null;

    private DialogInterface.OnDismissListener mOnClickListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener listener){
        this.mOnClickListener = listener;
    }

    private String curposition="-1";
    public void setCuposition(String cuposition){
        curposition=cuposition;
    }

    public static BottomPingLunDialog newInstance() {
        if(instance==null){
            instance= new BottomPingLunDialog();
        }
        return instance;
    }

    PingLenVideoAdapter adapter;

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getContext(),"BottomPingLunDialog onPause",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();

        //Toast.makeText(getContext(),"BottomPingLunDialog onResume",Toast.LENGTH_LONG).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        frView = inflater.inflate(R.layout.souye_pinglun, null);
        recyclerView= frView.findViewById(R.id.rlv);
        //默认展开所有回复
//        CommentExpandAdapter adapter = new CommentExpandAdapter(inflater);
//        expandableListView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(manager);
        adapter=new PingLenVideoAdapter(getActivity(),listBeans,myclick);
        recyclerView.setAdapter(adapter);


        return frView;
    }


    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
        if (curposition.equals("-1")){
            page=1;
            Log.e("------","来了没有");
            requestdate(curposition);
        }
        // 下面这些设置必须在此方法(onStart())中才有效
        window = getDialog().getWindow();
        // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置动画
        window.setWindowAnimations(R.style.bottomDialog);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }


    //做一些弹框的初始化，以及创建一个弹框

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(mOnClickListener != null) {
            mOnClickListener.onDismiss(dialog);
        }
    }

    private int page;

    List<OtherPingLBean.DataBean.CommentListBean> listBeans=new ArrayList<>();
    List<OtherPingLBean.DataBean.CommentListBean> itemlistBeans=new ArrayList<>();
    LinearLayoutManager layoutManager;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    private void requestdate(String curposition){
        RetrofitManager.getInstance().getDataServer().getotherpinlen(page,curposition, SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<OtherPingLBean>() {
                    @Override
                    public void onResponse(Call<OtherPingLBean> call, Response<OtherPingLBean> response) {
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getCode()==200){
                                itemlistBeans=response.body().getData().getCommentList();
//                                layoutManager=new LinearLayoutManager(getActivity());
//                                recyclerView.setLayoutManager(layoutManager);

//                                if (page==1){
//                                    listBeans.clear();
//                                    listBeans.addAll(itemlistBeans);
//
//                                }else {
//                                    listBeans.addAll(itemlistBeans);
//                                    adapter.notifyDataSetChanged();
//                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<OtherPingLBean> call, Throwable t) {

                    }
                });
    }

    PingLenVideoAdapter.Myclick myclick=new PingLenVideoAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {

        }
    };

}
