package com.jarhero790.eub.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.jarhero790.eub.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends Activity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.phoneRegister)
    Button phoneRegister;

    @BindView(R.id.usernameRegister)
    Button usernameRegister;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        phoneRegister.setOnClickListener(this);
        usernameRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.phoneRegister:
                //finish();
                //Toast.makeText(this,"短信接口调试",Toast.LENGTH_LONG).show();
                Intent intent1=new Intent(this,RegisterByPhoneActivity.class);
                startActivity(intent1);
                break;
            case R.id.usernameRegister:
                //finish();
                Intent intent2=new Intent(this,RegisterByUsernameActivity.class);
                startActivity(intent2);
                break;
        }
    }



}
