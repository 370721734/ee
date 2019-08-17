package com.jarhero790.eub.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.UserBean;
import com.jarhero790.eub.eventbus.MessageEventUser;
import com.jarhero790.eub.okhttp.OkHttpUtil;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class UserNameLoginActivity  extends Activity implements View.OnClickListener{

    @BindView(R.id.editTextUseranme)
    EditText editTextUseranme;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.editTextPassword)
    EditText editTextPassword;

    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_by_username);
        ButterKnife.bind(this);
        buttonLogin.setOnClickListener(this);
        back.setOnClickListener(this);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEventUser messageEvent) {

    }


    @Override
    public  void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }



    //测试账号密码 13699999999   123456
    public void login(){
        String username=editTextUseranme.getEditableText().toString();
        String password=editTextPassword.getEditableText().toString();

        if(username!=null && username.equals("")){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if(password!=null && password.equals("")){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, String> bodyParams=new HashMap<>();
        bodyParams.put("username",editTextUseranme.getEditableText().toString());
        bodyParams.put("password",editTextPassword.getEditableText().toString());
        OkHttpUtil.getInstance().postDataAsyn(Api.HOST+"user/login/doLogin",bodyParams,new OkHttpUtil.MyNetCall(){
            @Override
            public void success(Call call, Response response) throws IOException {
                String result=response.body().string();
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    Integer code=(Integer)jsonObject.get("code");
                    if(code==200){
                        //注意 登录成功之后  一定要写上这句代码
                        SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, true, AppUtils.getContext());
                        successBusinese(result);
                        finish();
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void failed(Call call, IOException e) {
                Log.e("注册异常",e.getMessage());
                Toast.makeText(UserNameLoginActivity.this,"异常"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }



    public void successBusinese (String data) throws JSONException {
        Gson gson=new Gson();
        UserBean userBean = gson.fromJson(data, UserBean.class);
        //把token保存在SharePreference
        SharePreferenceUtil.setToken(userBean.getData().getToken(),AppUtils.getContext());
        //发送  在MineFragment中接收
        EventBus.getDefault().post(new MessageEventUser(userBean));
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogin:
                login();
                break;
            case R.id.back:
                finish();
                Intent intent=new Intent(UserNameLoginActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
