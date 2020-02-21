package com.jy.jiandao.home.video;

import com.jy.jiandao.data.entity.VideoPageData;
import com.jy.jiandao.data.repository.VideoPageRepository;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.MvpManager;
import com.mr.k.libmvp.base.BasePresenter;
import com.mr.k.libmvp.base.ICachedCallBack;
import com.mr.k.libmvp.exception.ResultException;

import java.util.Map;

import static com.jy.jiandao.AppConstant.RequestKey.VIDEO_NEWS_NUMBER;
import static com.jy.jiandao.AppConstant.RequestKey.VIDEO_NEWS_POINT_TIME;
import static com.jy.jiandao.AppConstant.RequestKey.VIDEO_NEWS_START;

public class VideoPresenter extends BasePresenter<VideoContract.IVideoView> implements VideoContract.IVideoPresenter{


    private VideoContract.IVideoModel mRepository;


    public VideoPresenter(){
        mRepository = new VideoPageRepository();
    }

    @Override
    public void getVideoData(int start, int number, long pointTime, int requestType) {

        Map<String,String> params = ParamsUtils.getCommonParams();

        params.put(VIDEO_NEWS_START,String.valueOf(start));
        params.put(VIDEO_NEWS_NUMBER,String.valueOf(number));
        params.put(VIDEO_NEWS_POINT_TIME,String.valueOf(pointTime));

        mRepository.getDataFistCache(getProvider(), params, new ICachedCallBack<VideoPageData>() {
            @Override
            public void onMemoryCacheBack(VideoPageData data) {

            }

            @Override
            public void onDiskCacheBack(VideoPageData data) {
                if(mView != null){
                    mView.onNewsSuccess(data,requestType, MvpManager.RESPONSE_FROM_SDCARD);
                }
            }

            @Override
            public void onSuccess(VideoPageData data) {
                if(mView != null){
                    mView.onNewsSuccess(data,requestType, MvpManager.RESPONSE_FROM_SERVER);
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
