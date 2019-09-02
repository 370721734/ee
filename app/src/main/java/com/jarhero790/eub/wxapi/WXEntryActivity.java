package com.jarhero790.eub.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.MainActivity;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.base.AppManager;
import com.jarhero790.eub.bean.User;
import com.jarhero790.eub.bean.UserBean;
import com.jarhero790.eub.eventbus.MessageEventUser;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.message.souye.BindPhoneActivity;
import com.jarhero790.eub.okhttp.OkHttpUtil;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by arvinljw on 17/7/6 14:43
 * Function：
 * Desc：
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    GlobalApplication app;
    public static String WX_APP_ID = "wx8fe099e16a3b7fcf";
    private String WX_APP_SECRET = "f3ef3ebd98f2d4faab14028ff2c57f41";
    // 获取第一步的code后，请求以下链接获取access_token
    private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 获取用户个人信息
    private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_wx);
        CommonUtil.setStatusBarTransparent(this);
        //处理微信传回的Intent,当然你也可以在别的地方处理
        app = (GlobalApplication) getApplication();
        app.api.handleIntent(getIntent(), this);//这句不能少
        AppManager.getAppManager().addActivity(this);
//
//
//        Log.e("-----winxin-",app.isIsup()+"");

    }

    //微信请求相应
    @Override
    public void onReq(BaseReq baseReq) {
//        Log.e("--1----", baseReq.openId + "," + baseReq.transaction + ",");
//        Intent intent = new Intent(this, xxx.class);
//        intent.putExtra("errCode", resp.errCode);
//        intent.putExtra("errStr", resp.transaction);
//        startActivity(intent);
//        finish();
    }

    //发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
//        Log.e("--2----", resp.errCode + ",");
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
//                Log.e("WXTest", "onResp OK");

                if (resp instanceof SendAuth.Resp) {
                    SendAuth.Resp newResp = (SendAuth.Resp) resp;
                    //获取微信传回的code
                    String code = newResp.code;
                    String get_access_token = getCodeRequest(code);
//                    Log.e("--------ato=>", get_access_token);
                    Map<String, String> reqBody = new ConcurrentSkipListMap<>();
                    OkHttpUtil.getInstance().postDataAsyn(get_access_token, reqBody, new OkHttpUtil.MyNetCall() {

                        @Override
                        public void success(okhttp3.Call call, okhttp3.Response response) throws IOException {
                                 if (response.isSuccessful()){
                                     String json= null;
                                     try {
                                         json = response.body().string();
//                                         Log.e("--------json=",json);

                                         JSONObject object=new JSONObject(json);
                                         String access_token=object.optString("access_token");
                                         String openid=object.optString("openid");
//                                         Log.e("--------json=",access_token+" "+openid);
                                         String userInfoUrl = getUserInfoUrl(access_token, openid);
                                         getUserInfo(userInfoUrl);
                                     } catch (Exception e) {
                                         e.printStackTrace();
                                     }

                                 }
                        }

                        @Override
                        public void failed(okhttp3.Call call, IOException e) {

                        }
                    });
//                    //onResp code = 081rQ9d810vePL1LU9d81Z90d81rQ9dr   CN   wx8fe099e16a3b7fcf://oauth?code=081rQ9d810vePL1LU9d81Z90d81rQ9dr&state=wechat_sdk_demo_test
//                    initRetrofit(code);
                }
                finish();
                Log.e("-----", "ww");

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.e("WXTest", "onResp ERR_USER_CANCEL ");
                //发送取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.e("WXTest", "onResp ERR_AUTH_DENIED");
                //发送被拒绝
                break;
            default:
                Log.e("WXTest", "onResp default errCode " + resp.errCode);
                //发送返回
                break;
        }
//        finish();
    }

    private void getUserInfo(String str) {
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        OkHttpUtil.getInstance().postDataAsyn(str, reqBody, new OkHttpUtil.MyNetCall() {
            @Override
            public void success(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()){
                    try {
                        String str = response.body().string();
//                        Log.e("-------------str=",str);
                        JSONObject object=new JSONObject(str);
                        String openid=object.optString("openid");
                        String nickname=object.optString("nickname");
                        String sex=object.optString("sex");
                        String city=object.optString("city");
                        String province=object.optString("province");
                        String country=object.optString("country");
                        String headimgurl=object.optString("headimgurl");

                        initRetrofit(openid,nickname,(sex.equals("1")?"男":"女"),city,province,country,headimgurl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failed(okhttp3.Call call, IOException e) {

            }
        });

    }

    private String getUserInfoUrl(String access_token,String openid){
        String result=null;
        GetUserInfo=GetUserInfo.replace("ACCESS_TOKEN",urlEnodeUTF8(access_token));
        GetUserInfo=GetUserInfo.replace("OPENID",urlEnodeUTF8(openid));
        result=GetUserInfo;
        return result;
  }

    private String getCodeRequest(String code) {
        String result = null;
        GetCodeRequest = GetCodeRequest.replace("APPID", urlEnodeUTF8(WX_APP_ID));
        GetCodeRequest = GetCodeRequest.replace("SECRET", urlEnodeUTF8(WX_APP_SECRET));
        GetCodeRequest = GetCodeRequest.replace("CODE", urlEnodeUTF8(code));
        result = GetCodeRequest;
        return result;
    }

    private CharSequence urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void initRetrofit(String openid,String nickname,String sex,String city,String province,String country,String headimgurl) {
        RetrofitManager.getInstance().getDataServer().wechatLogin(openid,nickname,sex,city,province,country,headimgurl).enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                if (response.isSuccessful()) {
                    try {
//                        String json = response.body().string();
//                        Log.e("------result==", json);//result=: {"code":200,"data":null,"msg":"\u6210\u529f"}
//                        JSONObject object = new JSONObject(json);
//                        int code = object.optInt("code");
//                        String msg = object.optString("msg");
//                        String data = object.optString("data");
                        if (response.body()!=null){
                            String code = response.body().getCode();
                            String msg=response.body().getMsg();
                            UserBean data = response.body();
                            if (code.equals("200")){
                                if (data==null){
                                    Intent intent = new Intent(WXEntryActivity.this, BindPhoneActivity.class);
                                    intent.putExtra("openid",openid);
                                    startActivity(intent);
                                }else {
                                    //把token保存在SharePreference
                                    SharePreferenceUtil.setToken(data.getData().getToken(), AppUtils.getContext());
                                    SharePreferenceUtil.setuserid(data.getData().getId(),AppUtils.getContext());
                                    //注意 登录成功之后  一定要写上这句代码
                                    SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, true, AppUtils.getContext());
                                    app.setUserbean(data);
                                    //发送  在MineFragment中接收
                                    EventBus.getDefault().post(new MessageEventUser(data));
                                    startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
                                }
                            }else {
                                Toast.makeText(WXEntryActivity.this,msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {

            }
        });
    }

}
