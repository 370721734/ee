package com.jarhero790.eub.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jarhero790.eub.R;

import me.yokeyword.fragmentation.SupportFragment;

public class FragmentZuoping extends SupportFragment {
    private View view;
    private static FragmentZuoping instance=null;

    public static FragmentZuoping newInstance() {
        if(instance==null){
            instance= new FragmentZuoping();
        }
        return instance;
    }
    public FragmentZuoping(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_by_phone, container, false);
        return view;
    }
}

