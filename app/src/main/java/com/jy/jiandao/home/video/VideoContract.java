package com.jy.jiandao.home.video;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.RecommendData;
import com.jy.jiandao.data.entity.VideoData;
import com.mr.k.libmvp.MvpManager;
import com.mr.k.libmvp.base.IBaseModel;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;

public interface VideoContract {






    public interface IVideoView extends IBaseMvpView<IVideoPresenter>{

        void onNewsSuccess(VideoData videoData, @MvpManager.RequestType int requestType, @MvpManager.ResponseType int responseType);
        void onNewsFail(String msg, @MvpManager.RequestType int requestType);

    }


    public interface IVideoPresenter extends IBaseMvpPresenter<IVideoView>{


        void getVideoData(int start,int number,long pointTime,@MvpManager.RequestType int requestType);

    }



    public interface IVideoModel extends IBaseModel<VideoData> {


    }



}
