package com.jarhero790.eub.message.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
    }


    @OnClick({R.id.back, R.id.tv_acctent_find, R.id.tv_user_xiyi, R.id.tv_yisi_xiyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_acctent_find:
                startActivity(new Intent(this, XieYiActivity.class)
                        .putExtra("name", "账号找回")
                        .putExtra("url","http://www.51ayhd.com/user/index/recovery.html"));
                break;
            case R.id.tv_user_xiyi:
                startActivity(new Intent(this, XieYiActivity.class)
                        .putExtra("name", "用户协议")
                        .putExtra("url","http://www.51ayhd.com/user/index/agreement.html"));
                break;
            case R.id.tv_yisi_xiyi:
                startActivity(new Intent(this, XieYiActivity.class)
                        .putExtra("name", "隐私协议")
                        .putExtra("url","http://www.51ayhd.com/user/index/privacy.html"));
                break;
        }
    }
}
