package com.jarhero790.eub.message.net;

//ok
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.okhttp.CacheInterceptor;
import com.jarhero790.eub.okhttp.HttpCache;
import com.jarhero790.eub.okhttp.TrustManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
//    public static final String SERVER_ADDRESS="http://api.kabaodian.cn";
    public static final String SERVER_ADDRESS="http://120.79.222.191/zstv/public/index.php/";
    private Retrofit retrofit;
    private Api dataServer;
    private Context context;
    private static RetrofitManager instance;



    private static final int TIMEOUT_READ = 20;
    private static final int TIMEOUT_CONNECTION = 10;
    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static CacheInterceptor cacheInterceptor = new CacheInterceptor();


    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            //SSL证书
            .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
            .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
            //打印日志
            .addInterceptor(interceptor)
            //设置Cache拦截器
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(cacheInterceptor)
            .cache(HttpCache.getCache())
            //time out
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            //失败重连
            .retryOnConnectionFailure(true)
            .build();

    private RetrofitManager() {
    }
    public void init(Context context){
        this.context=context;
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        retrofit=new Retrofit.Builder()
                .baseUrl(SERVER_ADDRESS)
                .client(okHttpClient)
//                .client(new OkHttpClient.Builder().addInterceptor(new MyInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    public static RetrofitManager getInstance(){
        if (instance==null){
            instance=new RetrofitManager();
        }
        return instance;
    }

    public Api getDataServer(){
        if (null==dataServer){
            dataServer=retrofit.create(Api.class);
        }
        return dataServer;
    }
}
