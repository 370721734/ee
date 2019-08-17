package com.jarhero790.eub.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.jarhero790.eub.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends Activity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.phoneLogin)
    Button phoneLogin;

    @BindView(R.id.zhanghaomimaLogin)
    Button zhanghaomimaLogin;

    @BindView(R.id.registerGo)
    TextView registerGo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        String str="没账号?<font color='#FFFF00'>现在去注册</font>";
        registerGo.setTextSize(15);
        registerGo.setText(Html.fromHtml(str));

        registerGo.setOnClickListener(this);
        phoneLogin.setOnClickListener(this);
        zhanghaomimaLogin.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerGo:
                finish();
                Intent intent1=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent1);
                break;

            case R.id.phoneLogin:
                finish();
                Intent intent2=new Intent(LoginActivity.this,PhoneLoginActivity.class);
                startActivity(intent2);
                break;

            case R.id.zhanghaomimaLogin:
                finish();
                Intent intent3=new Intent(LoginActivity.this,UserNameLoginActivity.class);
                startActivity(intent3);
                break;

            case R.id.back:
                finish();
                break;
        }
    }
}
