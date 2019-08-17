package com.jarhero790.eub.api;

import com.jarhero790.eub.bean.AttentionBean;
import com.jarhero790.eub.bean.AttentionUserAndVideoBen;
import com.jarhero790.eub.bean.ShipinDianZanBean;
import com.jarhero790.eub.bean.VideoBean;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
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
}
