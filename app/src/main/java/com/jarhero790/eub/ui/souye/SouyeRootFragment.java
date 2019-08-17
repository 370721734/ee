package com.jarhero790.eub.ui.souye;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.base.BaseCompatFragment;
import com.jarhero790.eub.ui.souye.child.SouyeFragment;


public class SouyeRootFragment extends BaseCompatFragment {

    public static SouyeRootFragment newInstance() {
        Bundle args = new Bundle();
        SouyeRootFragment fragment = new SouyeRootFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_souye_root;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (findChildFragment(SouyeFragment.class) == null) {
            loadRootFragment(R.id.framelayout_container, SouyeFragment.newInstance());
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {

        if(hidden){
            Toast.makeText(getContext(),"videoView.pause()",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"videoView.resume()",Toast.LENGTH_LONG).show();
        }
    }


}

