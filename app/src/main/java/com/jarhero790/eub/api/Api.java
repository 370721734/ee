package com.jarhero790.eub.api;

import com.jarhero790.eub.bean.AttentionBean;
import com.jarhero790.eub.bean.AttentionUserAndVideoBen;
import com.jarhero790.eub.bean.MessagesBean;
import com.jarhero790.eub.bean.ShipinDianZanBean;
import com.jarhero790.eub.bean.UserBean;
import com.jarhero790.eub.bean.VideoBean;
import com.jarhero790.eub.message.bean.CurrVedoBean;
import com.jarhero790.eub.message.bean.FenSiTBean;
import com.jarhero790.eub.message.bean.GeRenBean;
import com.jarhero790.eub.message.bean.GiftBean;
import com.jarhero790.eub.message.bean.GuangZuBean;
import com.jarhero790.eub.message.bean.JiangLiBean;
import com.jarhero790.eub.message.bean.LikeBean;
import com.jarhero790.eub.message.bean.MyFaBuBean;
import com.jarhero790.eub.message.bean.MyPL;
import com.jarhero790.eub.message.bean.OtherPingLBean;
import com.jarhero790.eub.message.bean.PinLenBean;
import com.jarhero790.eub.message.bean.SearchBean;
import com.jarhero790.eub.message.bean.SearchResultBean;
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
    public final static String HOST = "http://www.51ayhd.com/zstv/public/index.php/";
//    public final static String HOST = "http://120.79.222.191/zstv/public/index.php/";
    public final static String TU = "http://www.51ayhd.com/zstv/public";
    public final static String GIFT = "http://www.51ayhd.com/zstv/public/";



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
    Call<SysMessageBean> getSysMessages(@Field("token") String tokenValue);


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
    @POST("web/index/zan")
    Call<ResponseBody> zanoter(@Query("vid") String videoID, @Query("token") String tokenValue);


    /**
     * 视频点赞或者取消赞
     * vid	   	视频ID
       token	token*
     */
    @POST("web/index/zan")
    Call<ShipinDianZanBean> zanorno(@Query("vid") String videoID, @Query("token") String tokenValue);


    //短信接口
    @FormUrlEncoded
    @POST("user/login/send_sms")
    Call<ResponseBody> getsms(@Field("mobile") String mobile);


    //短信登陆
    @FormUrlEncoded
    @POST("user/Login/login")
    Observable<ResponseBody> getlogin(@Field("mobile") String mobile, @Field("msgId") String msgId, @Field("smg_code") String smg_code);



    //个人中心
    @FormUrlEncoded
    @POST("web/index/usercenter")
    Observable<ResponseBody> getuserinfo(@Field("token") String token);

    //个人中心
    @FormUrlEncoded
    @POST("web/index/usercenter")
    Call<GeRenBean> getgerenuser(@Field("token") String token);
    //别，个人中心
    @FormUrlEncoded
    @POST("web/index/usercenter")
    Call<GeRenBean> getgerenuserinfos(@Field("token") String token, @Field("uid") String uid);
    //别，个人中心
    @FormUrlEncoded
    @POST("web/index/usercenter")
    Call<ResponseBody> getgerenss(@Field("token") String token, @Field("uid") String uid);


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



    //礼物列表  注意
    @FormUrlEncoded
    @POST("web/index/index")
    Call<GiftBean> mygift(@Field("token") String token);

    //我的礼物  注意
    @FormUrlEncoded
    @POST("web/index/mygift")
    Call<GiftBean> getmygift(@Field("token") String token);


    //赠送礼物
    @FormUrlEncoded
    @POST("web/index/sendGift")
    Call<CurrVedoBean> sendGift(@Field("token") String token, @Field("vid") String vid, @Field("gid") String gid);


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
    Call<ResponseBody> getreward(@Field("token") String token, @Field("type_id") Integer type_id);


    //个人中心我的视频
    @FormUrlEncoded
    @POST("web/index/myfabu")
    Call<MyFaBuBean> myfabu(@Field("token") String token);

    //个人中心我的视频other
    @FormUrlEncoded
    @POST("web/index/myfabu")
    Call<MyFaBuBean> myfabuother(@Field("token") String token,@Field("uid") String uid);


    //个人中心点赞的视频
    @FormUrlEncoded
    @POST("web/index/zanvideo")
    Call<MyFaBuBean> zanvideo(@Field("token") String token);

    //个人中心点赞的视频other
    @FormUrlEncoded
    @POST("web/index/zanvideo")
    Call<MyFaBuBean> zanvideoother(@Field("token") String token,@Field("uid") String uid);

    //我的关注
    @FormUrlEncoded
    @POST("web/index/mylike")
    Call<GuangZuBean> mylike(@Field("token") String token);
    //我的关注
    @FormUrlEncoded
    @POST("web/index/mylike")
    Observable<GuangZuBean> myguangzu(@Field("token") String token);

    //用户拉黑
    @FormUrlEncoded
    @POST("web/index/blacklist")
    Call<ResponseBody> blacklist(@Field("token") String token, @Field("uid") String uid, @Field("type") String type);


    //用户举报
    @FormUrlEncoded
    @POST("web/index/report")
    Call<ResponseBody> report(@Field("token") String token, @Field("buid") String buid, @Field("type") String type, @Field("content") String content, @Field("report_img") String report_img, @Field("report_img1") String report_img1, @Field("report_img2") String report_img2, @Field("report_img3") String report_img3);


    //修改个人信息
    @FormUrlEncoded
    @POST("web/index/editinfo")
    Call<ResponseBody> editinfo(@Field("token") String token, @Field("sign") String sign, @Field("nickname") String nickname, @Field("sex") String sex, @Field("city") String city, @Field("headimgurl") String headimgurl);







    //搜索页面
    @FormUrlEncoded
    @POST("user/index/search")
    Call<SearchBean> search(@Field("page") Integer page, @Field("token") String token);


    //搜索用户
    @FormUrlEncoded
    @POST("user/index/dosearch")
    Call<SearchResultBean> dosearch(@Field("keywords") String keywords, @Field("page") Integer page, @Field("token") String token);






    //获取评论
    @FormUrlEncoded
    @POST("web/index/getComment")
    Call<OtherPingLBean> getotherpinlen(@Field("page") Integer page, @Field("vid") String v, @Field("token") String token);




    //发布的视频进行评论
    @FormUrlEncoded
    @POST("web/index/comment")
    Call<ResponseBody> attention_pinlen(@Field("comment") String comment, @Field("vid") String v, @Field("token") String token);




    //微信登陆
    @FormUrlEncoded
    @POST("user/Login/wechatLogin")
    Call<UserBean> wechatLogin(@Field("openid") String openid,@Field("nickname") String nickname,@Field("sex") String sex,@Field("city") String city,@Field("province") String province,@Field("country") String country,@Field("headimgurl") String headimgurl);



    //绑定手机号码
    @FormUrlEncoded
    @POST("user/Login/binding_mobile")
    Call<UserBean> binding_mobile(@Field("mobile") String mobile,@Field("msgId") String msgId,@Field("smg_code") String smg_code,@Field("openid") String openid);


    //退出登录
    @FormUrlEncoded
    @POST("user/Login/logout")
    Call<ResponseBody> logout(@Field("user_id") String user_id);
}
