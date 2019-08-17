package com.jarhero790.eub.ui.souye;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.CommentExpandAdapter;


public class BottomPingLunDialog extends DialogFragment {

    private View frView;
    private Window window;

    private ExpandableListView expandableListView;
    private static BottomPingLunDialog instance=null;

    private DialogInterface.OnDismissListener mOnClickListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener listener){
        this.mOnClickListener = listener;
    }



    public static BottomPingLunDialog newInstance() {
        if(instance==null){
            instance= new BottomPingLunDialog();
        }
        return instance;
    }


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
        expandableListView= frView.findViewById(R.id.expandableListView);
        //默认展开所有回复
        CommentExpandAdapter adapter = new CommentExpandAdapter(inflater);
        expandableListView.setAdapter(adapter);
        return frView;
    }


    @Override
    public void onStart() {
        super.onStart();
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


}
