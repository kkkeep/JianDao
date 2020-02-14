package com.jy.jiandao.data.repository;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.NewsData;
import com.jy.jiandao.data.ok.JDDataService;
import com.jy.jiandao.home.recommend.page.NewsContract;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/*
 * created by Cherry on 2020-01-14
 **/
public class NewsPageRepository extends BaseRepository implements NewsContract.INewsMode {


    private HashMap<String, NewsData> mMemoryCache = new HashMap<>();


    private static NewsPageRepository mInstance;


    private NewsPageRepository() {

    }


    public static NewsPageRepository getInstance() {

        if (mInstance == null) {

            synchronized (NewsPageRepository.class) {

                if (mInstance == null) {
                    mInstance = new NewsPageRepository();
                }
            }
        }

        return mInstance;
    }


    @Override
    public void getNews(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<NewsData> callBack, int requestType) {


        String cacheKey = params.get(AppConstant.RequestKey.RECOMMOND_NEWS_COLUMN_ID); // 栏目表的id

        if (requestType == AppConstant.REQUEST_FIRST_LOAD) {

            NewsData newsData = mMemoryCache.get(cacheKey);

            if (newsData != null) {
                callBack.onSuccess(cloneData(newsData));
                return;
            }


        }


        // 网络请求
        observer(JDDataService.getApiService().getNews(params), this::getConvertObservable, new Consumer<NewsData>() {
            @Override
            public void accept(NewsData newsData) throws Exception {
                saveToMemoryCache(cacheKey, newsData, requestType);
            }
        }, callBack);
    }


    private NewsData cloneData(NewsData source) {

        NewsData data = new NewsData();


        data.setPointTime(source.getPointTime());
        data.setStart(source.getStart());
        data.setNumber(source.getNumber());

        data.setMore(source.getMore());


        data.setBannerList(new ArrayList<>(source.getBannerList()));

        data.setFlashList(new ArrayList<>(source.getFlashList()));

        data.setArticleList(new ArrayList<>(source.getArticleList()));

        return data;

    }

    private void saveToMemoryCache(String key, NewsData serverData, int requestType) {

        if (requestType != AppConstant.REQUEST_LOAD_MORE_LOAD) {

            mMemoryCache.remove(key); // 删除之前这个key 对应的 新闻数据
            mMemoryCache.put(key, serverData);

        } else { // 加载更多缓存
            NewsData cacheData = mMemoryCache.get(key);
            cacheData.setStart(serverData.getStart());
            cacheData.setNumber(serverData.getNumber());
            cacheData.setPointTime(serverData.getPointTime());
            cacheData.getArticleList().addAll(serverData.getArticleList());
            cacheData.setMore(serverData.getMore());
        }

    }


    private void cleanMemoryCache(){
        if(mMemoryCache != null){
            mMemoryCache.clear();
            mMemoryCache = null;
        }
    }


    public synchronized static void destroy(){
        if(mInstance != null){
            mInstance.cleanMemoryCache();
            mInstance = null;
        }
    }

}
