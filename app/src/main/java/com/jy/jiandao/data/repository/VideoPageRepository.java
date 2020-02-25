package com.jy.jiandao.data.repository;

import android.content.Context;

import com.jy.jiandao.data.entity.RecommendPageData;
import com.jy.jiandao.data.entity.VideoPageData;
import com.jy.jiandao.data.ok.JDDataService;
import com.jy.jiandao.home.video.VideoContract;
import com.mr.k.libmvp.Utils.DataFileCacheUtils;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.ICachedCallBack;
import com.mr.k.libmvp.base.ParamsMap;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.util.Map;

import io.reactivex.functions.Consumer;


public class VideoPageRepository extends BaseRepository implements VideoContract.IVideoModel {


    private static final String NEWS_CACHE_FILE_PREFIX = "VIDEO_NEWSDATA_CHACHE_";
    private Context mApplicationContext;


    public VideoPageRepository(Context context) {
        this.mApplicationContext = context.getApplicationContext();
    }




    public void getDataFistCache(LifecycleProvider provider, ParamsMap params, ICachedCallBack<VideoPageData> callBack, int requestType) {

      /*  observer(provider,JDDataService.getApiService().getVideoNews(params), this::getConvertObservable, new Consumer<VideoPageData>() {
            @Override
            public void accept(VideoPageData videoPageData) throws Exception {
                saveToSdcard(videoPageData);
            }
        }, callBack);



        observer(provider,JDDataService.getApiService().getVideoNews(params), this::getConvertObservable, videoData -> {

            saveToSdcard(videoData);

        },callBack);


*/


        get(provider,this::saveToSdcard,params,callBack);

        //observer(provider,JDDataService.getApiService().getVideoNews(params), this::getConvertObservable, this::saveToSdcard, callBack);
    }





    /**
     * 保存服务器数据到sdcard,采用替换策略，每次保存时替换之前的
     */
    private void saveToSdcard(VideoPageData serverData) {

        File file = SystemFacade.getExternalCacheDir(mApplicationContext, NEWS_CACHE_FILE_PREFIX );

        DataFileCacheUtils.saveDataToFile(file, serverData);
    }


    /**
     * 从 sdcard 中读取之前缓存的数据
     *
     * @return
     */
    private RecommendPageData readFromSdcard() {

        File file = SystemFacade.getExternalCacheDir(mApplicationContext, NEWS_CACHE_FILE_PREFIX);

        return DataFileCacheUtils.getDataFromFile(file, RecommendPageData.class);
    }




}
