package com.jy.jiandao.home.recommend.page;

import com.jy.jiandao.data.entity.RecommendPageData;
import com.mr.k.libmvp.MvpManager;
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

        void onNewsSuccess(RecommendPageData recommendPageData, @MvpManager.RequestType int requestType, @MvpManager.ResponseType int responseType);
        void onNewsFail(String msg, @MvpManager.RequestType int requestType);
    }

    interface INewsPresenter extends IBaseMvpPresenter<INewsView>{

        void getNews(String id,int start,int number,long pointTime,@MvpManager.RequestType int requestType);
    }

    interface INewsMode {

        void getNews(LifecycleProvider provider, Map<String,String> params, ICachedCallBack<RecommendPageData> callBack, @MvpManager.RequestType int requestType);

    }
}
