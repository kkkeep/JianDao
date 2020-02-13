package com.jy.jiandao.data.repository;

import com.jy.jiandao.data.entity.NewsData;
import com.jy.jiandao.data.ok.JDDataService;
import com.jy.jiandao.home.recommend.page.NewsContract;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

/*
 * created by Cherry on 2020-01-14
 **/
public class NewsPageRepository extends BaseRepository implements NewsContract.INewsMode {

    @Override
    public void getNews(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<NewsData> callBack,int requestType) {
        observer(JDDataService.getApiService().getNews(params), this::getConvertObservable, callBack);
    }
}
