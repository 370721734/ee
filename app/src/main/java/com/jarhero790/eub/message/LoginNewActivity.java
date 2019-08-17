package com.jarhero790.eub.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginNewActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.phoneLogin)
    Button phoneLogin;
    @BindView(R.id.qq_login)
    ImageView qqLogin;
    @BindView(R.id.weixin_login)
    ImageView weixinLogin;
    @BindView(R.id.zhifubao_login)
    ImageView zhifubaoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        ButterKnife.bind(this);

        CommonUtil.setStatusBarTransparent(LoginNewActivity.this);
    }


    @OnClick({R.id.phoneLogin, R.id.qq_login, R.id.weixin_login, R.id.zhifubao_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phoneLogin:
                startActivity(new Intent(this,LoginPhoneActivity.class));
                break;
            case R.id.qq_login:
                break;
            case R.id.weixin_login:
                break;
            case R.id.zhifubao_login:
                break;
        }
    }
}
