package com.jy.jiandao.detail.page;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.CommentListData;
import com.jy.jiandao.data.entity.RelativeNewsData;
import com.jy.jiandao.data.repository.BaseRepository;
import com.jy.jiandao.detail.IDetalContract;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.base.BasePresenter;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.ParamsMap;
import com.mr.k.libmvp.exception.ResultException;

public class DetailPagePresenter extends BasePresenter<IDetalContract.IDetailPageView> implements IDetalContract.IDetailPagePresenter {


    private BaseRepository mBaseRepository;


    public DetailPagePresenter() {

        mBaseRepository = new BaseRepository();
    }

    @Override
    public void getRelativeNewsList(String id) {

        ParamsMap paramsMap = new ParamsMap(AppConstant.Url.GET_DETAIL_RELATIVE_NEWS);

        paramsMap.put(AppConstant.RequestKey.DETAIL_NEWS_ID,id);

        paramsMap.putAll(ParamsUtils.getCommonParams());


        mBaseRepository.get(getProvider(), paramsMap, new IBaseCallBack<RelativeNewsData>() {

            @Override
            public void onSuccess(RelativeNewsData data) {

                if(mView != null){
                    mView.onRelativeNewsListResult(data,null);
                }

            }

            @Override
            public void onFail(ResultException e) {

                if(mView != null){
                    mView.onRelativeNewsListResult(null,e.getMessage());
                }

            }
        });

    }

    @Override
    public void getCommentList(String id, int start, long pointTime) {
        ParamsMap paramsMap = new ParamsMap(AppConstant.Url.GET_DETAIL_COMMNETS);

        paramsMap.put(AppConstant.RequestKey.DETAIL_COMMENT_NEWS_ID,id);
        paramsMap.put(AppConstant.RequestKey.DETAIL_COMMENT_START,String.valueOf(start));
        paramsMap.put(AppConstant.RequestKey.DETAIL_COMMENT_POINT_TIME,String.valueOf(pointTime));

        paramsMap.putAll(ParamsUtils.getCommonParams());


        mBaseRepository.get(getProvider(), paramsMap, new IBaseCallBack<CommentListData>() {

            @Override
            public void onSuccess(CommentListData data) {

                if(mView != null){
                    mView.onCommentListResult(data,null);
                }

            }

            @Override
            public void onFail(ResultException e) {

                if(mView != null){
                    mView.onCommentListResult(null,e.getMessage());
                }

            }
        });
    }

    @Override
    public void getCommentRelayList(String newId, String commentId, int start, int pointTime) {

    }

    @Override
    public void doCommnet(String newId, String content) {

    }

    @Override
    public void doReplay(String newsId, String commentId, String content, String toUserId, int type, String replayId) {

    }
}
