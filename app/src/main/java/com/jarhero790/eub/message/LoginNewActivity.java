package com.jarhero790.eub.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.R;
import com.jarhero790.eub.base.AppManager;
import com.jarhero790.eub.message.bean.souyelookone;
import com.jarhero790.eub.message.bean.souyelookthree;
import com.jarhero790.eub.message.bean.souyelooktwo;
import com.jarhero790.eub.message.my.XieYiActivity;
import com.jarhero790.eub.message.my.YiSiXieYiActivity;
import com.jarhero790.eub.utils.CommonUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    GlobalApplication app;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.tv_user_xiyi)
    TextView tvUserXiyi;
    @BindView(R.id.tv_yisi_xiyi)
    TextView tvYisiXiyi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        ButterKnife.bind(this);
        app = (GlobalApplication) getApplication();
        CommonUtil.setStatusBarTransparent(LoginNewActivity.this);
        AppManager.getAppManager().addActivity(this);
        EventBus.getDefault().register(this);
    }


    @OnClick({R.id.phoneLogin, R.id.qq_login, R.id.weixin_login, R.id.zhifubao_login, R.id.back, R.id.tv_user_xiyi, R.id.tv_yisi_xiyi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phoneLogin:
                if (checkbox.isChecked()) {
                    startActivity(new Intent(this, LoginPhoneActivity.class));
                } else {
                    Toast.makeText(this, "请先确认用户协议和隐私协议", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.qq_login:
                break;
            case R.id.weixin_login:
                //微信登录
                if (checkbox.isChecked()) {
                    if (app.api != null && app.api.isWXAppInstalled()) {
                        SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "wechat_sdk_demo_test";
                        app.api.sendReq(req);
                    } else {
                        Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请先确认用户协议和隐私协议", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.zhifubao_login:

                break;
            case R.id.back:
                if (app.isIslookthree()) {
                    EventBus.getDefault().post(new souyelookthree("ok"));
                } else if (app.isIslooktwo()) {
                    EventBus.getDefault().post(new souyelooktwo("ok"));
                } else {
                    EventBus.getDefault().post(new souyelookone("ok"));
                }

                finish();
                break;
            case R.id.tv_user_xiyi:
                startActivity(new Intent(this, XieYiActivity.class)
                        .putExtra("name", "用户协议")
                        .putExtra("url","http://www.51ayhd.com/user/index/agreement.html"));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;
            case R.id.tv_yisi_xiyi:
                startActivity(new Intent(this, XieYiActivity.class)
                        .putExtra("name", "隐私协议")
                        .putExtra("url","http://www.51ayhd.com/user/index/privacy.html"));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ee1(souyelookone bean) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (app.isIslookthree()) {
                EventBus.getDefault().post(new souyelookthree("ok"));
            } else if (app.isIslooktwo()) {
                EventBus.getDefault().post(new souyelooktwo("ok"));
            } else {
                EventBus.getDefault().post(new souyelookone("ok"));
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
