package com.mr.k.libmvp.oknet;

import com.mr.k.libmvp.BuildConfig;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/*
 * created by Cherry on 2019-12-26
 **/
public class DataService {


    private static final long DEFAULT_TIMEOUT = 20000; // 默认超时20s
    private static Object mApiService;

    private static ArrayList<Interceptor> mInterceptors;
    private static Converter.Factory mFactor;
    private static Class mServiceClass;
    private static String mBaseUrl;

    public static void init(Class aClass, String baseUrl, ArrayList<Interceptor> interceptor, Converter.Factory factor) {
        mServiceClass = aClass;
        mBaseUrl = baseUrl;
        mInterceptors = interceptor;
        mFactor = factor;

    }

    public static void init(Class aClass, String baseUrl, ArrayList<Interceptor> interceptor) {
        init(aClass, baseUrl, interceptor, null);

    }

    public static void init(Class aClass, String baseUrl, Converter.Factory factor) {
        init(aClass, baseUrl, null, factor);

    }

    public static void init(Class aClass, String baseUrl) {
        init(aClass, baseUrl, null, null);

    }


    public static synchronized Object getApiService() {

        if (mServiceClass == null) {
            throw new NullPointerException("在调用 DataService 的 getApiService 之前必须调用 init 方法惊喜初始化");
        }


        if (mApiService == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            /**
             * 注意，如果有大文件下载，或者 response 里面的body 很大，要么不加HttpLoggingInterceptor 拦截器
             * 如果非要加，日志级别不能是 BODY,否则容易内存溢出。
             */
            if (BuildConfig.DEBUG) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                logging.setLevel(HttpLoggingInterceptor.Level.NONE);
            }


            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(logging);
            if (mInterceptors != null && mInterceptors.size() > 0) {
                for (Interceptor interceptor : mInterceptors) {
                    builder.addInterceptor(interceptor);
                }
            }

            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(mFactor == null ? GsonConverterFactory.create() : mFactor) // 帮我们把json 窜转为 entity 对象
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 结合 rxjava 使用
                    .baseUrl(mBaseUrl)
                    .build();

            mApiService = retrofit.create(mServiceClass);

        }

        return mApiService;
    }
}
