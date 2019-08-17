package com.jarhero790.eub.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jarhero790.eub.R;
import com.jarhero790.eub.base.BaseCompatFragment;
import com.jarhero790.eub.ui.mine.child.MineFragment;

public class MineRootFragment extends BaseCompatFragment {

    public static MineRootFragment newInstance() {
        Bundle args = new Bundle();
        MineRootFragment fragment = new MineRootFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_root;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (findChildFragment(MineFragment.class) == null) {
            loadRootFragment(R.id.framelayout_container, MineFragment.newInstance());
        }
    }
}


