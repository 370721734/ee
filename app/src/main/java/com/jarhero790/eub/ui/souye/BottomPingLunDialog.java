package com.jarhero790.eub.ui.souye;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.adapter.PingLenVideoAdapter;
import com.jarhero790.eub.message.bean.OtherPingLBean;
import com.jarhero790.eub.message.net.RetrofitManager;

import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BottomPingLunDialog extends DialogFragment {

    private View frView;
    private Window window;

    private RecyclerView recyclerView;
    TextView tvnum;
    ImageView imageView;
    private static BottomPingLunDialog instance = null;

    private DialogInterface.OnDismissListener mOnClickListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.mOnClickListener = listener;
    }


    public static BottomPingLunDialog newInstance() {
        if (instance == null) {
            instance = new BottomPingLunDialog();
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String str) {
//        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
//        recyclerView.setLayoutManager(manager);


        Log.e("-------------", "go go go" + str);

        LinearLayoutManager mPerfectCourse = new LinearLayoutManager(getActivity());
        mPerfectCourse.setOrientation(LinearLayoutManager.VERTICAL);// 设置 recyclerview 布局方式为横向布局lvHotCourse.setLayoutManager(mPerfectCourse);
        recyclerView.setLayoutManager(mPerfectCourse);
        adapter = new PingLenVideoAdapter(getActivity(), listBeans, myclick);
        Log.e("------------li", "" + listBeans.size());
        recyclerView.setAdapter(adapter);



        if (listBeans != null && listBeans.size() > 0) {
            tvnum.setText(listBeans.size() + "条评论");
        } else {
            tvnum.setText("0条评论");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        frView = inflater.inflate(R.layout.souye_pinglun, null);
        recyclerView = frView.findViewById(R.id.rlv);
        tvnum = frView.findViewById(R.id.tv_num);
        imageView = frView.findViewById(R.id.iv_exit);

        //默认展开所有回复
//        CommentExpandAdapter adapter = new CommentExpandAdapter(inflater);
//        expandableListView.setAdapter(adapter);


//        LinearLayoutManager mPerfectCourse = new LinearLayoutManager(getActivity());
//        mPerfectCourse.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局lvHotCourse.setLayoutManager(mPerfectCourse);
//        recyclerView.setLayoutManager(mPerfectCourse);
//        adapter=new PingLenVideoAdapter(getActivity(),listBeans,myclick);
//        recyclerView.setAdapter(adapter);


        return frView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String vid = bundle.getString("vid");
            Log.e("------------vid=>", vid);
            page = 1;
            requestdate(vid);
        }


    }

    @Override
    public void onStart() {
        super.onStart();


//        SouyeFragment.newInstance().setPinL(new SouyeFragment.PinL() {
//            @Override
//            public void Clicker(String str) {
//                Log.e("---------=>",str);
//            }
//        });

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
        if (mOnClickListener != null) {
            mOnClickListener.onDismiss(dialog);
        }
    }

    private int page;

    List<OtherPingLBean.DataBean.CommentListBean> listBeans = new ArrayList<>();
    List<OtherPingLBean.DataBean.CommentListBean> itemlistBeans = new ArrayList<>();
    LinearLayoutManager layoutManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

//    CustomProgressDialog dialog = new CustomProgressDialog();

    private void requestdate(String curposition) {
//        dialog.createLoadingDialog(getActivity(), "正在加载...");
//        dialog.show();
        RetrofitManager.getInstance().getDataServer().getotherpinlen(page, curposition, SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new Callback<OtherPingLBean>() {
                    @Override
                    public void onResponse(Call<OtherPingLBean> call, Response<OtherPingLBean> response) {
                        if (response.isSuccessful()) {
//                            dialog.dismiss();
                            if (response.body() != null && response.body().getCode() == 200) {
                                itemlistBeans = response.body().getData().getCommentList();
                                Log.e("----------k", "=" + itemlistBeans.size());

//                                layoutManager=new LinearLayoutManager(getActivity());
//                                recyclerView.setLayoutManager(layoutManager);

                                if (page == 1) {
                                    listBeans.clear();
                                    listBeans.addAll(itemlistBeans);

                                } else {
                                    listBeans.addAll(itemlistBeans);
                                    adapter.notifyDataSetChanged();
                                }
                                EventBus.getDefault().post("ok");

                            }
                        }else {
//                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtherPingLBean> call, Throwable t) {
//                        dialog.dismiss();
                    }
                });
    }

    PingLenVideoAdapter.Myclick myclick = new PingLenVideoAdapter.Myclick() {
        @Override
        public void myClick(int position, View view) {

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
