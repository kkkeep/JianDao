package com.jy.jiandao.home.recommend;

import com.jy.jiandao.data.entity.ColumnData;
import com.jy.jiandao.data.repository.RecommendRepository;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.base.BasePresenter;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.exception.ResultException;
import com.trello.rxlifecycle2.LifecycleProvider;

/*
 * created by Cherry on 2020-01-08
 **/
public class RecommendPresenter extends BasePresenter<RecommendContract.IRecommendView> implements RecommendContract.IRecommendPresenter {

    private RecommendRepository mRepository;


    public RecommendPresenter(){
        mRepository = new RecommendRepository();
    }
    @Override
    public void getColumnList() {

        mRepository.getColumnList((LifecycleProvider) mView, ParamsUtils.getCommonParams(),new IBaseCallBack<ColumnData>() {
            @Override
            public void onSuccess(ColumnData data) {
                if(mView != null){
                    mView.onColumnResult(data, null);
                }
            }

            @Override
            public void onFail(ResultException msg) {
                if(mView != null){
                    mView.onColumnResult(null, msg.getMessage());
                }
            }
        });
    }
}
