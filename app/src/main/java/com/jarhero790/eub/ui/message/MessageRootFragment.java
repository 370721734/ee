package com.jarhero790.eub.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jarhero790.eub.R;
import com.jarhero790.eub.base.BaseCompatFragment;
import com.jarhero790.eub.ui.message.child.MessageFragment;

public class MessageRootFragment extends BaseCompatFragment {

    public static MessageRootFragment newInstance() {
        Bundle args = new Bundle();
        MessageRootFragment fragment = new MessageRootFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message_root;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (findChildFragment(MessageFragment.class) == null) {
            loadRootFragment(R.id.framelayout_container, MessageFragment.newInstance());
        }
    }
}


