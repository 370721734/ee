package com.jarhero790.eub.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.MainActivity;
import com.jarhero790.eub.R;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPhoneActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.ivpass)
    ImageView ivpass;
    @BindView(R.id.tv_ma)
    TextView tvMa;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.s)
    TextView s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(LoginPhoneActivity.this);
    }

    @OnClick(R.id.s)
    public void onClick() {
        SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, true, GlobalApplication.getContext());
        startActivity(new Intent(this, MainActivity.class));
    }
}
