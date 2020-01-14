package com.jy.jiandao.home.recommend.page;

import com.jy.jiandao.data.entity.NewsData;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

/*
 * created by Cherry on 2020-01-14
 **/
public interface NewsContract {

    interface INewsView extends IBaseMvpView<INewsPresenter> {

        void onNewsResult(NewsData newsData,String msg);
    }

    interface INewsPresenter extends IBaseMvpPresenter<INewsView>{

        void getNews(String id,int start,int number,int pointTime);
    }

    interface INewsMode {

        void getNews(LifecycleProvider provider, Map<String,String> params, IBaseCallBack<NewsData> callBack);

    }
}
