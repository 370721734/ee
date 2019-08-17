package com.jarhero790.eub.message.net;

//ok
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jarhero790.eub.api.Api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
//    public static final String SERVER_ADDRESS="http://api.kabaodian.cn";
    public static final String SERVER_ADDRESS="http://120.79.222.191/zstv/public/index.php/";
    private Retrofit retrofit;
    private Api dataServer;
    private Context context;
    private static RetrofitManager instance;

    private RetrofitManager() {
    }
    public void init(Context context){
        this.context=context;
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        retrofit=new Retrofit.Builder()
                .baseUrl(SERVER_ADDRESS)
                .client(new OkHttpClient.Builder().addInterceptor(new MyInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create(gson))
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
