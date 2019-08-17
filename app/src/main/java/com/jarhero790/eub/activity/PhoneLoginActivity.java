package com.jarhero790.eub.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PhoneLoginActivity extends Activity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.editTextPhone)
    EditText editTextPhone;

    @BindView(R.id.editTextYanZhengCode)
    EditText editTextYanZhengCode;

    @BindView(R.id.buttonGetRegisterCode)
    Button buttonGetRegisterCode;

    @BindView(R.id.buttonLogin)
    Button buttonLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_by_phone);
        ButterKnife.bind(this);
        buttonGetRegisterCode.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        back.setOnClickListener(this);
    }



    public void getRegisterCode(){

    }

    public void login(){
        /**后台接口规定的：
         * 要用手机号注册13699999999   123456
         * 密码不能小于6位
         */

            String phone=editTextPhone.getEditableText().toString();
            String yanZhengCode=editTextYanZhengCode.getEditableText().toString();

            if(phone!=null && phone.equals("")){
                Toast.makeText(this,"手机号不能为空",Toast.LENGTH_LONG).show();
                return;
            }
            if(yanZhengCode!=null && yanZhengCode.equals("")){
                Toast.makeText(this,"验证码不能为空",Toast.LENGTH_LONG).show();
                return;
            }



            //通过RequestBody.create 创建requestBody对象
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("username", editTextPhone.getEditableText().toString())
                    .addFormDataPart("password", editTextPhone.getEditableText().toString())
                    .build();

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(Api.HOST+"user/register/doRegister").post(requestBody).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("登录异常",e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result=response.body().string();
                    Log.e("登录结果",result);
                    //Toast.makeText(RegisterByUsernameActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
                }
            });
        }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.buttonGetRegisterCode:
                getRegisterCode();
                break;
            case R.id.buttonLogin:
                login();
                break;
        }
    }
}
