package com.jy.jiandao.home.Topic;

import com.jy.jiandao.data.entity.TopicPageData;
import com.jy.jiandao.data.entity.VideoPageData;
import com.mr.k.libmvp.MvpManager;
import com.mr.k.libmvp.base.IBaseModel;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;

public interface TopicContract {

    public interface ITopicView extends IBaseMvpView<ITopicPresenter>{

        void onNewsSuccess(TopicPageData topicPageData, @MvpManager.RequestType int requestType, int responseType);


        void onNewsFail(String msg, @MvpManager.RequestType int requestType);


    }


    public interface ITopicPresenter extends IBaseMvpPresenter<ITopicView>{

        void getTopicData(int start,int number,long pointTime, int requestType);

    }



}
