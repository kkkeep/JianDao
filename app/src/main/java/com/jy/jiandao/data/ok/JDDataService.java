package com.jy.jiandao.data.ok;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.BuildConfig;
import com.mr.k.libmvp.oknet.DataService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/*
 * created by Cherry on 2019-12-26
 **/
public class JDDataService {

    public static ApiService getApiService(){
       return (ApiService) DataService.getApiService();
    }


}
