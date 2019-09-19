package com.jarhero790.eub.message.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
