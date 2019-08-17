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
import org.json.JSONObject;
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

public class RegisterByUsernameActivity extends Activity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.editTextUseranme)
    EditText editTextUseranme;

    @BindView(R.id.editTextNickName)
    EditText editTextNickName;

    @BindView(R.id.editTextPassword)
    EditText editTextPassword;

    @BindView(R.id.buttonRegister)
    Button register;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_by_username);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        register.setOnClickListener(this);
        /**
        editTextUseranme.addTextChangedListener(new TextWatcher() {
            private String mBefore;// 用于记录变化前的文字
            private int mCursor;// 用于记录变化时光标的位置

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                }
            });
            **/
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.buttonRegister:
                //用户注册
                register();
                break;
        }
    }


    /**后台接口规定的：
     * 要用手机号注册
     * 密码不能小于6位
     */
    public void register(){
        String username=editTextUseranme.getEditableText().toString();
        String nickName=editTextNickName.getEditableText().toString();
        String password=editTextPassword.getEditableText().toString();

        if(username!=null && username.equals("")){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if(nickName!=null && nickName.equals("")){
            Toast.makeText(this,"昵称不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if(password!=null && password.equals("")){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show();
            return;
        }


        //通过RequestBody.create 创建requestBody对象
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", editTextUseranme.getEditableText().toString())
                .addFormDataPart("nickname", editTextNickName.getEditableText().toString())
                .addFormDataPart("password", editTextPassword.getEditableText().toString())
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Api.HOST+"user/register/doRegister").post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("注册异常",e.getMessage());
                Toast.makeText(RegisterByUsernameActivity.this,"异常"+e.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               String result=response.body().string();
               try{
                   JSONObject jsonObject = new JSONObject(result);
                   String msg=(String)jsonObject.get("msg");
                   Log.e("注册结果msg值",msg);
                }catch (Exception e){

               }

               Log.e("注册结果",result);
                //Toast.makeText(RegisterByUsernameActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
