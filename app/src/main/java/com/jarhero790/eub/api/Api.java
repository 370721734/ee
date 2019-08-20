package com.jarhero790.eub.api;

import com.jarhero790.eub.bean.AttentionBean;
import com.jarhero790.eub.bean.AttentionUserAndVideoBen;
import com.jarhero790.eub.bean.MessagesBean;
import com.jarhero790.eub.bean.ShipinDianZanBean;
import com.jarhero790.eub.bean.UserBean;
import com.jarhero790.eub.bean.VideoBean;
import com.jarhero790.eub.message.bean.FenSiTBean;
import com.jarhero790.eub.message.bean.GiftBean;
import com.jarhero790.eub.message.bean.JiangLiBean;
import com.jarhero790.eub.message.bean.LikeBean;
import com.jarhero790.eub.message.bean.MyFaBuBean;
import com.jarhero790.eub.message.bean.MyPL;
import com.jarhero790.eub.message.bean.PinLenBean;
import com.jarhero790.eub.message.bean.SysMessageBean;
import com.jarhero790.eub.message.bean.ZanBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
//    public final static String HOST = "http://49.234.23.127/";
    public final static String HOST = "http://120.79.222.191/zstv/public/index.php/";


    /**
     * 视频列表接口
     * 参数：
       cate	    	视频分类：0为最新；1为推荐；2为长视频
       page	     	页数
     *
     **/
    @FormUrlEncoded
    @POST("user/index/getVideo")
    Observable<VideoBean> getVideos(@Field("cate") String cateValue,
                                    @Field("page") String pageValue);




/**
    @FormUrlEncoded
    @POST("user/index/getVideo")
    Observable<ResponseBody> getVideos(@Field("cate")String cateValue,
                                   @Field("page")String pageValue);

     **/


    /**
     * 获取自己关注了的用户以及该用户发布的视频
     * token值
     */

    @POST("web/index/mylike_list")
    @FormUrlEncoded
    Observable<AttentionUserAndVideoBen> getAttentionUsersAndVideos(@Field("token") String tokenValue);



    /**
     * 关注用户
     * buid	    被关注的用户id
       token	用户TOKEN
     *
     *
     */
    @POST("web/index/like")
    @FormUrlEncoded
    Observable<AttentionBean> attentionUser(@Field("buid") String buidValue,
                                            @Field("token") String tokenValue);


    /**
     * 消息
     */
    @POST("web/index/messages")
    @FormUrlEncoded
    Observable<ResponseBody> getMessages(@Field("token") String tokenValue);

    /**
     * 消息
     */
    @POST("web/index/messages")
    @FormUrlEncoded
    Call<MessagesBean> getSysMessages(@Field("token") String tokenValue);


    /**
     * 用户注册
     * POST
     * username	是	string	用户名
       nickname	是	string	昵称
       password	是	string	密码
     *
     */
    @FormUrlEncoded
    @POST("user/register/doRegister")
    Observable<ResponseBody> register(@Field("username") String usernameValue,
                                      @Field("nickname") String nicknameValue,
                                      @Field("password") String passwordValue
    );
    /**
     * 用户登录
     */
    @GET("user/login/doLogin")
    Observable<ResponseBody> login();





    /**
     * 视频点赞或者取消赞
     * vid	   	视频ID
       token	token*
     */
    @POST("web/index/zan")
    Observable<ShipinDianZanBean> zan(@Query("vid") String videoID, @Query("token") String tokenValue);


    //短信接口
    @FormUrlEncoded
    @POST("user/login/send_sms")
    Observable<ResponseBody> getsms(@Field("mobile") String mobile);


    //短信登陆
    @FormUrlEncoded
    @POST("user/Login/login")
    Observable<ResponseBody> getlogin(@Field("mobile") String mobile, @Field("msgId") String msgId, @Field("smg_code") String smg_code);



    //个人中心
    @FormUrlEncoded
    @POST("web/index/usercenter")
    Observable<ResponseBody> getuserinfo(@Field("token") String token);


    //个人签到页面
    @FormUrlEncoded
    @POST("web/index/sign_in")
    Observable<PinLenBean> getpinlen(@Field("token") String token);

    //我的粉丝
    @FormUrlEncoded
    @POST("web/index/myfensi")
    Call<FenSiTBean> getfensi(@Field("token") String token);


    //关注用户接口
    @FormUrlEncoded
    @POST("web/index/like")
    Call<ResponseBody> getguanzu(@Field("buid") String buid, @Field("token") String token);

    //用户签到
    @FormUrlEncoded
    @POST("web/index/signIn")
    Call<ResponseBody> qiandao(@Field("token") String token);


    //我的评论
    @FormUrlEncoded
    @POST("web/index/mycomment")
    Call<MyPL> mypinlen(@Field("token") String token);



    //我的礼物列表
    @FormUrlEncoded
    @POST("web/index/index")
    Call<GiftBean> mygift(@Field("token") String token);

    //我的点赞
    @FormUrlEncoded
    @POST("web/index/myzan")
    Call<ZanBean> myzan(@Field("token") String token);

    //我的奖励
    @FormUrlEncoded
    @POST("web/index/myreward")
    Call<JiangLiBean> myreward(@Field("token") String token);


    //签到页面领取奖励
    @FormUrlEncoded
    @POST("web/index/reward")
    Call<ResponseBody> getreward(@Field("token") String token,@Field("type_id") Integer type_id);


    //个人中心我的视频
    @FormUrlEncoded
    @POST("web/index/myfabu")
    Call<MyFaBuBean> myfabu(@Field("token") String token);


    //个人中心点赞的视频
    @FormUrlEncoded
    @POST("web/index/zanvideo")
    Call<LikeBean> zanvideo(@Field("token") String token);







    //获取评论
    @FormUrlEncoded
    @POST("web/index/getComment")
    Call<ResponseBody> getpinlen(@Field("page") Integer page,@Field("vid") String v, @Field("token") String token);
}
