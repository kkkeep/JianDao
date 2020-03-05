package com.mr.k.libmvp.base;

import com.mr.k.libmvp.manager.MvpManager;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

public interface IBaseModel<T> {

    /**
     * 直接取服务器数据
     * @param provider
     * @param params
     * @param callBack
     */

   public default void getData(LifecycleProvider provider, Map<String,String> params, IBaseCallBack<T> callBack){

   }


    /**
     * 先取缓存数据，缓存没有才去服务器取
     * @param provider
     * @param params
     * @param callBack
     * @param requestType
     */
  public default  void getDataFistCache(LifecycleProvider provider, Map<String,String> params, ICachedCallBack<T> callBack, @MvpManager.RequestType int requestType){

   }
}
