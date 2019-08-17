package com.jarhero790.eub.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.jarhero790.eub.R;

import me.yokeyword.fragmentation.SupportFragment;

public class FragmentLike extends SupportFragment {
    private View view;
    private GridView gridView;
    private static FragmentLike instance=null;

    public static FragmentLike newInstance() {
        if(instance==null){
            instance= new FragmentLike();
        }
        return instance;
    }

    public FragmentLike(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            Toast.makeText(getContext(),"可见",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(),"不可见",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_like, container, false);
        gridView=view.findViewById(R.id.mine_gridview_like);
        return view;
    }
}
