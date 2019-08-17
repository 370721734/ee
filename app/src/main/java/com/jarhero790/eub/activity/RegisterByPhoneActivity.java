package com.jarhero790.eub.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class RegisterByPhoneActivity extends Activity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.editTextYanZhengCode)
    EditText editTextYanZhengCode;

    @BindView(R.id.editTextPhone)
    EditText editTextPhone;

    @BindView(R.id.buttonGetRegisterCode)
    Button buttonGetRegisterCode;

    @BindView(R.id.buttonRegister)
    Button buttonRegister;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_by_phone);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
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
                register();
                break;
        }
    }



    public void register(){
        String phone=editTextPhone.getEditableText().toString();
        String yanZhengCode=editTextYanZhengCode.getEditableText().toString();


        if(phone!=null && phone.equals("")){
            Toast.makeText(this,"手机不能为空",Toast.LENGTH_LONG).show();
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
                .addFormDataPart("nickname", editTextYanZhengCode.getEditableText().toString())
                .build();

        //创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
        Request request = new Request.Builder().url(Api.HOST+"user/login/doLogin").post(requestBody).build();

        //创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);

        //请求加入调度,重写回调方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(RegisterByPhoneActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Toast.makeText(RegisterByPhoneActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
