package com.jarhero790.eub.message.souye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jarhero790.eub.MainActivity;
import com.jarhero790.eub.R;
import com.jarhero790.eub.base.AppManager;
import com.jarhero790.eub.utils.CommonUtil;


import android.content.Intent;

import android.os.Handler;

import android.os.Message;



public class WelcomeActivity extends AppCompatActivity {


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        CommonUtil.setStatusBarTransparent(this);
        AppManager.getAppManager().addActivity(this);
        Handler handler = new Handler(new Handler.Callback() {

            @Override

            public boolean handleMessage(Message msg) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
                return true;

            }

        });

        handler.sendEmptyMessageDelayed(0,2000);

    }

}
