package com.jy.jiandao.data.repository;

import com.jy.jiandao.data.entity.VideoData;
import com.jy.jiandao.data.ok.JDDataService;
import com.jy.jiandao.home.video.VideoContract;
import com.mr.k.libmvp.base.ICachedCallBack;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import io.reactivex.functions.Consumer;

public class VideoPageRepository extends BaseRepository implements VideoContract.IVideoModel {


    @Override
    public void getDataFistCache(LifecycleProvider provider, Map<String, String> params, ICachedCallBack<VideoData> callBack, int requestType) {


        observer(JDDataService.getApiService().getVideoNews(params), this::getConvertObservable, videoData -> {


            // 去做sdcard 缓存

        },callBack);

    }
}
