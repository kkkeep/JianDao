package com.jy.jiandao.home.recommend.page;

import android.content.Context;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.NewsData;
import com.jy.jiandao.data.repository.NewsPageRepository;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.base.BasePresenter;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.ICachedCallBack;
import com.mr.k.libmvp.exception.ResultException;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;


/*
 * created by Cherry on 2020-01-14
 **/
public class NewsPagePresenter extends BasePresenter<NewsContract.INewsView> implements NewsContract.INewsPresenter {

    private NewsContract.INewsMode mRepository;


    public NewsPagePresenter(Context context) {
        this.mRepository = NewsPageRepository.getInstance(context);
    }

    @Override
    public void getNews(String id, int start, int number, long pointTime,@AppConstant.RequestType int requestType) {
        Map<String,String> params = ParamsUtils.getCommonParams();


        params.put(AppConstant.RequestKey.RECOMMOND_NEWS_COLUMN_ID,id);
        params.put(AppConstant.RequestKey.RECOMMOND_NEWS_START_,String.valueOf(start));
        params.put(AppConstant.RequestKey.RECOMMOND_NEWS_NUMBER,String.valueOf(number));
        params.put(AppConstant.RequestKey.RECOMMOND_NEWS_POINT_TIME,String.valueOf(pointTime));

        mRepository.getNews((LifecycleProvider) mView, params, new ICachedCallBack<NewsData>() {
            @Override
            public void onMemoryCacheBack(NewsData data) {
                if(mView != null){
                    mView.onNewsSuccess(data, requestType,AppConstant.RESPONSE_FROM_MEMORY);
                }
            }

            @Override
            public void onDiskCacheBack(NewsData data) {
                if(mView != null){
                    mView.onNewsSuccess(data, requestType,AppConstant.RESPONSE_FROM_SDCARD);
                }
            }

            @Override
            public void onSuccess(NewsData data) {
                if(mView != null){
                    mView.onNewsSuccess(data, requestType,AppConstant.RESPONSE_FROM_SERVER);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onNewsFail(e.getMessage(),requestType);
                }
            }
        },requestType);
    }
}
