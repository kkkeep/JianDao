package com.jy.jiandao.home.recommend.page;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.NewsData;
import com.jy.jiandao.data.repository.BaseRepository;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;
import com.mr.k.libmvp.base.ICachedCallBack;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

/*
 * created by Cherry on 2020-01-14
 **/
public interface NewsContract {

    interface INewsView extends IBaseMvpView<INewsPresenter> {

        void onNewsSuccess(NewsData newsData, @AppConstant.RequestType int requestType, @AppConstant.ResponseType int responseType);
        void onNewsFail(String msg, @AppConstant.RequestType int requestType);
    }

    interface INewsPresenter extends IBaseMvpPresenter<INewsView>{

        void getNews(String id,int start,int number,long pointTime,@AppConstant.RequestType int requestType);
    }

    interface INewsMode {

        void getNews(LifecycleProvider provider, Map<String,String> params, ICachedCallBack<NewsData> callBack, @AppConstant.RequestType int requestType);

    }
}
