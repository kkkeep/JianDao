package com.jy.jiandao.home.recommend;

import com.jy.jiandao.data.entity.ColumnData;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

/*
 * created by Cherry on 2020-01-08
 **/
public interface RecommendContract {


    interface  IRecommendView extends IBaseMvpView<IRecommendPresenter>{
        void onColumnResult(ColumnData data,String msg);
    }

    interface IRecommendPresenter extends IBaseMvpPresenter<IRecommendView>{
       void  getColumnList();
    }


    interface IRecommendMode{
        void getColumnList(LifecycleProvider provider, Map<String,String> params, IBaseCallBack<ColumnData> callBack);
    }
}
