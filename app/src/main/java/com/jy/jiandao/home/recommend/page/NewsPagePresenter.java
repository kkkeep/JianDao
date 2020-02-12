package com.jy.jiandao.home.recommend.page;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.NewsData;
import com.jy.jiandao.data.repository.NewsPageRepository;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.base.BasePresenter;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.exception.ResultException;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;


/*
 * created by Cherry on 2020-01-14
 **/
public class NewsPagePresenter extends BasePresenter<NewsContract.INewsView> implements NewsContract.INewsPresenter {

    private NewsContract.INewsMode mRepository;


    public NewsPagePresenter() {
        this.mRepository = new NewsPageRepository();
    }

    @Override
    public void getNews(String id, int start, int number, int pointTime) {
        Map<String,String> params = ParamsUtils.getCommonParams();


        params.put(AppConstant.RequestKey.RECOMMOND_NEWS_COLUMN_ID,id);
        params.put(AppConstant.RequestKey.RECOMMOND_NEWS_START_,String.valueOf(start));
        params.put(AppConstant.RequestKey.RECOMMOND_NEWS_NUMBER,String.valueOf(number));
        params.put(AppConstant.RequestKey.RECOMMOND_NEWS_POINT_TIME,String.valueOf(pointTime));

        mRepository.getNews((LifecycleProvider) mView, params, new IBaseCallBack<NewsData>() {
            @Override
            public void onSuccess(NewsData data) {
                if(mView != null){
                    mView.onNewsResult(data, null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onNewsResult(null, e.getMessage());
                }
            }
        });
    }
}
