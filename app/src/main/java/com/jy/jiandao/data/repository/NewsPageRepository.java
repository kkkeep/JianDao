package com.jy.jiandao.data.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.NewsData;
import com.jy.jiandao.data.ok.JDDataService;
import com.jy.jiandao.home.recommend.page.NewsContract;
import com.mr.k.libmvp.Utils.DataFileCacheUtils;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.ICachedCallBack;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*
 * created by Cherry on 2020-01-14
 **/
public class NewsPageRepository extends BaseRepository implements NewsContract.INewsMode {


    private static final String NEWS_CACHE_FILE_PREFIX = "NEWSDATA_CHACHE_";

    private HashMap<String, NewsData> mMemoryCache = new HashMap<>();


    private static NewsPageRepository mInstance;

    private Context mApplicationContext;


    private NewsPageRepository(Context context) {
        mApplicationContext = context.getApplicationContext();
    }


    public static NewsPageRepository getInstance(Context context) {

        if (mInstance == null) {

            synchronized (NewsPageRepository.class) {

                if (mInstance == null) {
                    mInstance = new NewsPageRepository(context);
                }
            }
        }

        return mInstance;
    }


    @SuppressLint("CheckResult")
    @Override
    public void getNews(LifecycleProvider provider, Map<String, String> params, ICachedCallBack<NewsData> callBack, int requestType) {


        String cacheKey = params.get(AppConstant.RequestKey.RECOMMOND_NEWS_COLUMN_ID); // 栏目表的id

        if (requestType == AppConstant.REQUEST_FIRST_LOAD) { // 第一次加载，非刷新和加载更多时候

            //  step1: 先从内存中查找

            NewsData newsData = mMemoryCache.get(cacheKey);

            if (newsData != null) { // 如果内存缓存里面有数据，那么返回内存里面缓存的给 Presenter 层
                callBack.onMemoryCacheBack(newsData);
                return;

            } else {
                // step2 : 如果内存没有缓存，那么从sdcard 中读取


                Observable.create((ObservableOnSubscribe<NewsData>) emitter -> {

                    NewsData newsData1 = readFromSdcard(cacheKey);
                    if (newsData1 != null) {
                        emitter.onNext(newsData1);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NullPointerException());
                    }

                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

                        .subscribe(callBack::onDiskCacheBack, throwable -> {
                            // 网络请求
                            observer(JDDataService.getApiService().getNews(params), NewsPageRepository.this::getConvertObservable, newsData13 -> {
                                saveToMemoryCache(cacheKey, newsData13, requestType);

                                saveToSdcard(cacheKey, newsData13);
                            }, callBack);

                        });
                return;
            }

        }


        // 网络请求
        observer(JDDataService.getApiService().getNews(params), this::getConvertObservable, newsData -> {
            saveToMemoryCache(cacheKey, newsData, requestType);

            saveToSdcard(cacheKey, newsData);
        }, callBack);
    }


    private void requestToServer() {

    }

    /**
     * 根据已有对象克隆一个新的对象（这儿相当于java的 浅克隆）
     *
     * @param source 目标对象
     * @return 返回克隆后的对象
     */

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


    /**
     * 保持服务器返回的数据到 内存中
     *
     * @param key         ， 栏目的 id
     * @param serverData  : 服务器返回的对象
     * @param requestType ： 请求类型
     */

    private void saveToMemoryCache(String key, NewsData serverData, @AppConstant.RequestType int requestType) {

        if (requestType != AppConstant.REQUEST_LOAD_MORE_LOAD) { // 不是加载更多，也就是说是 第一加载和刷新时，采用先删除原来的 key 对象，在存储

            mMemoryCache.remove(key); // 删除之前这个key 对应的 新闻数据
            mMemoryCache.put(key, serverData);

        } else { // 加载更多缓存，在原来的缓存之上追加数据
            NewsData cacheData = mMemoryCache.get(key);
            cacheData.setStart(serverData.getStart());
            cacheData.setNumber(serverData.getNumber());
            cacheData.setPointTime(serverData.getPointTime());
            cacheData.getArticleList().addAll(serverData.getArticleList());
            cacheData.setMore(serverData.getMore());
        }

    }


    /**
     * 保存服务器数据到sdcard,采用替换策略，每次保存时替换之前的
     */
    private void saveToSdcard(String key, NewsData serverData) {

        File file = SystemFacade.getExternalCacheDir(mApplicationContext, NEWS_CACHE_FILE_PREFIX + key);

        DataFileCacheUtils.saveDataToFile(file, serverData);
    }


    /**
     * 从 sdcard 中读取之前缓存的数据
     *
     * @param key
     * @return
     */
    private NewsData readFromSdcard(String key) {

        File file = SystemFacade.getExternalCacheDir(mApplicationContext, NEWS_CACHE_FILE_PREFIX + key);

        return DataFileCacheUtils.getDataFromFile(file, NewsData.class);
    }


    // 清空内存缓存
    private void cleanMemoryCache() {
        if (mMemoryCache != null) {
            mMemoryCache.clear();
            mMemoryCache = null;
        }
    }


    // 释放单利，在我们 HomeActivity 关闭时调用
    public synchronized static void destroy() {
        if (mInstance != null) {
            mInstance.cleanMemoryCache();
            mInstance = null;
        }
    }

}
