package com.jy.jiandao.data.repository;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.NewsData;
import com.jy.jiandao.data.ok.JDDataService;
import com.jy.jiandao.home.recommend.page.NewsContract;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * created by Cherry on 2020-01-14
 **/
public class NewsPageRepository extends BaseRepository implements NewsContract.INewsMode {


    private HashMap<String, NewsData> mMemoryCache = new HashMap<>();

    @Override
    public void getNews(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<NewsData> callBack,int requestType) {


        if(requestType == AppConstant.REQUEST_FIRST_LOAD){

            // 先看内存里面有没有，如果有，返回内存缓存，没有读取sdcard
            // 如果 sdcard 有，返回sdcard 数据，如果 sdcard 没有，请求网络
        }


        observer(JDDataService.getApiService().getNews(params), this::getConvertObservable, callBack);
    }
}
