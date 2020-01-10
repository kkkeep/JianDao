package com.jy.jiandao;

import android.app.Application;

import com.jy.jiandao.data.ok.ApiService;
import com.jy.jiandao.data.ok.converter.MyGsonConverterFactory;
import com.mr.k.libmvp.oknet.DataService;

/*
 * created by Cherry on 2019-12-26
 **/
public class JDApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DataService.init(ApiService.class, AppConstant.BASE_URL, MyGsonConverterFactory.create());

    }
}
