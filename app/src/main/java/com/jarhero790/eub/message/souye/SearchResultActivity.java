package com.jarhero790.eub.message.souye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jarhero790.eub.R;
import com.jarhero790.eub.utils.CommonUtil;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        CommonUtil.setStatusBarTransparent(this);
    }
}
