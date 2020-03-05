package com.jy.jiandao.home.Topic;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.TopicPageData;
import com.jy.jiandao.data.repository.BaseRepository;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.manager.MvpManager;
import com.mr.k.libmvp.base.BasePresenter;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.ParamsMap;
import com.mr.k.libmvp.exception.ResultException;

import java.util.Map;



import static com.jy.jiandao.AppConstant.RequestKey.*;

public class TopicPresenter extends BasePresenter<TopicContract.ITopicView> implements TopicContract.ITopicPresenter {

    private BaseRepository mBaseRepository;


    public TopicPresenter() {

        mBaseRepository= new BaseRepository();
    }

    @Override
    public void getTopicData(int start, int number, long pointTime, int requestType) {


        ParamsMap paramsMap = new ParamsMap(AppConstant.Url.GET_TOPIC_NEWS);

        Map<String,String> params = ParamsUtils.getCommonParams();

        params.put(TOPIC_NEWS_START,String.valueOf(start));
        params.put(TOPIC_NEWS_NUMBER,String.valueOf(number));
        params.put(TOPIC_NEWS_POINT_TIME,String.valueOf(pointTime));

        paramsMap.putAll(params);


        mBaseRepository.get(getProvider(), paramsMap, new IBaseCallBack<TopicPageData>() {
            @Override
            public void onSuccess(TopicPageData data) {

                if(mView != null){
                    mView.onNewsSuccess(data,requestType, MvpManager.RESPONSE_FROM_SERVER);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onNewsFail(e.getMessage(), requestType);
                }
            }
        });


/*

        mBaseRepository.observer(getProvider(), JDDataService.getApiService().getTopicNews(params), mBaseRepository::getConvertObservable, new IBaseCallBack<TopicPageData>() {
            @Override
            public void onSuccess(TopicPageData data) {

                if(mView != null){
                    mView.onNewsSuccess(data,requestType, MvpManager.RESPONSE_FROM_SERVER);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onNewsFail(e.getMessage(), requestType);
                }
            }
        });
*/

    }
}
