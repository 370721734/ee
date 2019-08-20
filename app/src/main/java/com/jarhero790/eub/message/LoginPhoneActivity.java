package com.jarhero790.eub.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.MainActivity;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.bean.UserBean;
import com.jarhero790.eub.eventbus.MessageEventUser;
import com.jarhero790.eub.message.bean.Userbean;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginPhoneActivity extends AppCompatActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.ivpass)
    ImageView ivpass;
    @BindView(R.id.tv_ma)
    TextView tvMa;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.submit)
    TextView submit;

    private TimeCount time;//定时

    private String msgid;//ma id

    GlobalApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        CommonUtil.setStatusBarTransparent(LoginPhoneActivity.this);
        ButterKnife.bind(this);
        time = new TimeCount(60000, 1000);
        app= (GlobalApplication) getApplication();

    }

    @OnClick({R.id.back, R.id.tv_ma, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_ma:
                String userphone = etPhone.getEditableText().toString();
                if (userphone != null && userphone.equals("")) {
                    Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (time != null) {
                    time.start();
                }


                //通过RequestBody.create 创建requestBody对象
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("mobile", etPhone.getEditableText().toString())
                        .build();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(Api.HOST + "user/login/send_sms").post(requestBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("注册异常", e.getMessage());
                        Toast.makeText(LoginPhoneActivity.this, "异常" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        try {
//                            Log.e("-----1:", result);//{"code":200,"data":{"msgId":"19081613443425770"},"msg":"\u77ed\u4fe1\u5df2\u53d1\u9001\uff0c\u8bf7\u6ce8\u610f\u67e5\u6536"}
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.optInt("code");
                            String msg = (String) jsonObject.get("msg");
                            Log.e("注册结果msg值", msg);
                            if (code == 200) {
                                JSONObject data = jsonObject.optJSONObject("data");
                                msgid=data.optString("msgId");
//                                Log.e("----------2:",msgid);//ok

                                Toast.makeText(LoginPhoneActivity.this, msg, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginPhoneActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {

                        }

                        Log.e("注册结果", result);
                        //Toast.makeText(RegisterByUsernameActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.submit:
                String userphone2 = etPhone.getEditableText().toString();
                if (userphone2 != null && userphone2.equals("")) {
                    Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                String ma = etPassword.getEditableText().toString();
                if (ma != null && ma.equals("")) {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (msgid==null || msgid.equals("")){
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                Log.e("---------3",userphone2+"  "+ma+"  "+ msgid);
                //通过RequestBody.create 创建requestBody对象
                RequestBody requestBody2 = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("mobile", etPhone.getEditableText().toString())
                        .addFormDataPart("msgId",msgid)
                        .addFormDataPart("smg_code",etPassword.getEditableText().toString())
                        .build();
                OkHttpClient okHttpClient2 = new OkHttpClient();
                Request request2 = new Request.Builder().url(Api.HOST + "user/Login/login").post(requestBody2).build();
                Call call2 = okHttpClient2.newCall(request2);
                call2.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("注册异常", e.getMessage());
                        Toast.makeText(LoginPhoneActivity.this, "异常" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        try {
                            Log.e("-----1:", result);
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.optInt("code");
                            String msg = (String) jsonObject.get("msg");
                            Log.e("注册结果msg值", msg);
                            if (code == 200) {
//                                JSONObject data = jsonObject.optJSONObject("data");
//                                String id=data.optString("id");
//                                String openid=data.optString("openid");
//                                String token=data.optString("token");
//                                String nickname=data.optString("nickname");
//                                String sex=data.optString("sex");
//                                String addr=data.optString("addr");
//                                String country=data.optString("country");
//                                String province=data.optString("province");
//                                String city=data.optString("city");
//                                String headimgurl=data.optString("headimgurl");
//                                String subscribe=data.optString("subscribe");
//                                String money=data.optString("money");
//                                String state=data.optString("state");
//                                String sign=data.optString("sign");
//                                String age=data.optString("age");
//                                String freemoney=data.optString("freemoney");
//                                String is_admin=data.optString("is_admin");
//                                String type=data.optString("type");
//                                String rong_id=data.optString("rong_id");
//                                String rong_token=data.optString("rong_token");
//                                String username=data.optString("username");
//                                String pwd=data.optString("pwd");
//                                String signtime=data.optString("signtime");
//                                String login_id=data.optString("login_id");
//
//                                Userbean userbean=new Userbean(id,openid,token,nickname,sex,addr,country,province,city,headimgurl,subscribe,money,state,sign,age,freemoney,is_admin,type,rong_id,rong_token,username,pwd,signtime,login_id);


                                //注意 登录成功之后  一定要写上这句代码
                                SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, true, AppUtils.getContext());
                                successBusinese(result);

                                startActivity(new Intent(LoginPhoneActivity.this, MainActivity.class));

//                                Toast.makeText(LoginPhoneActivity.this, msg, Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(LoginPhoneActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {

                        }

                        Log.e("注册结果", result);
                        //Toast.makeText(RegisterByUsernameActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
                    }
                });

                break;
        }


    }

    private void successBusinese(String data) {
        Gson gson=new Gson();
        UserBean userBean = gson.fromJson(data, UserBean.class);
        //把token保存在SharePreference
        SharePreferenceUtil.setToken(userBean.getData().getToken(),AppUtils.getContext());
        app.setUserbean(userBean);
        //发送  在MineFragment中接收
        EventBus.getDefault().post(new MessageEventUser(userBean));
    }


    class TimeCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            tvGetMa.setBackgroundDrawable(getResources().getDrawable(R.drawable.item_regist_circle));
            tvMa.setClickable(false);
            tvMa.setText(millisUntilFinished / 1000 + "");
        }

        @Override
        public void onFinish() {
            tvMa.setText("重新获取验证码");
            tvMa.setClickable(true);
//            tvGetMa.setBackgroundDrawable(getResources().getDrawable(R.drawable.item_regin_str));
        }
    }
}
