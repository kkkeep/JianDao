package com.jy.jiandao.detail.page;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.Comment;
import com.jy.jiandao.data.entity.CommentListData;
import com.jy.jiandao.data.entity.RelativeNewsData;
import com.jy.jiandao.data.entity.Replay;
import com.jy.jiandao.data.entity.ReplayListData;
import com.jy.jiandao.data.repository.BaseRepository;
import com.jy.jiandao.detail.IDetalContract;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.base.BasePresenter;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.ParamsMap;
import com.mr.k.libmvp.exception.ResultException;

import static com.jy.jiandao.AppConstant.RequestKey.*;
import static com.jy.jiandao.AppConstant.RequestKey.DETAIL_DO_COMMENT_LIKE_COMMENT_ID;
import static com.jy.jiandao.AppConstant.Url.*;
import static com.jy.jiandao.AppConstant.Url.DO_DETAIL_COMMNET_RELAY;

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

        paramsMap.put(DETAIL_COMMENT_NEWS_ID,id);
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
    public void getCommentRelayList(String newId, String commentId, int start, long pointTime) {


        ParamsMap paramsMap = new ParamsMap(AppConstant.Url.GET_DETAIL_COMMNET_REPLAY);

        paramsMap.put(AppConstant.RequestKey.DETAIL_COMMENT_REPLAY_NEWS_ID,newId);
        paramsMap.put(AppConstant.RequestKey.DETAIL_COMMENT_REPLAY_COMMENT_ID,commentId);
        
        paramsMap.put(AppConstant.RequestKey.DETAIL_COMMENT_REPLAY_START,String.valueOf(start));
        paramsMap.put(AppConstant.RequestKey.DETAIL_COMMENT_REPLAY_POINT_TIME,String.valueOf(pointTime));
        

        paramsMap.putAll(ParamsUtils.getCommonParams());
        
        mBaseRepository.get(getProvider(), paramsMap, new IBaseCallBack< ReplayListData>() {
            @Override
            public void onSuccess(ReplayListData data) {
                if(mView != null){
                    mView.onCommentRelayListResult(data,null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onCommentRelayListResult(null,e.getMessage());
                }
            }
        });

    }

    @Override
    public void doCommnet(String newId, String content) {
        ParamsMap paramsMap = new ParamsMap(AppConstant.Url.DO_DETAIL_COMMNET_NEWS);
        paramsMap.put(DETAIL_COMMENT_NEWS_ID,newId);
        paramsMap.put(AppConstant.RequestKey.DETAIL_DO_COMMENT_NEWS_CONTENT,content);
        paramsMap.putAll(ParamsUtils.getCommonParams());

        mBaseRepository.post(getProvider(), paramsMap, new IBaseCallBack<Comment>() {
            @Override
            public void onSuccess(Comment data) {
                if(mView != null){
                    mView.onDoCommentResult(data,null);
                }

            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onDoCommentResult(null,e.getMessage());
                }
            }
        });



    }

    @Override
    public void doReplay(String newsId, String commentId, String content, String toUserId, int type, String replayId) {

        ParamsMap paramsMap = new ParamsMap(DO_DETAIL_COMMNET_RELAY);
        paramsMap.put(DETAIL_COMMENT_NEWS_ID,newsId);
        paramsMap.put(DETAIL_COMMENT_REPLAY_COMMENT_ID,commentId);
        paramsMap.put(DETAIL_DO_COMMENT_REPLAY_CONTENT,content);
        paramsMap.put(DETAIL_DO_COMMENT_REPLAY_TO_USER_ID,toUserId);
        paramsMap.put(DETAIL_DO_COMMENT_REPLAY_TYPE,String.valueOf(type));
        paramsMap.put(DETAIL_DO_COMMENT_REPLAY_REPLAY_ID,replayId);
        paramsMap.putAll(ParamsUtils.getCommonParams());

        mBaseRepository.post(getProvider(), paramsMap, new IBaseCallBack<Replay>() {
            @Override
            public void onSuccess(Replay data) {
                if(mView != null){
                    mView.onDoReplayResult(data,null);
                }

            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onDoReplayResult(null,e.getMessage());
                }
            }
        });
    }

    @Override
    public void doCommentLike(String commentId) {

        ParamsMap paramsMap = new ParamsMap(DO_DETAIL_COMMNET_LIKE);
        paramsMap.put(DETAIL_DO_COMMENT_LIKE_COMMENT_ID,commentId);

        paramsMap.putAll(ParamsUtils.getCommonParams());




        mBaseRepository.post(getProvider(), paramsMap, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                if(mView != null){
                    mView.onDoCommentLikeResult(data,null);
                }

            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onDoCommentLikeResult(null,e.getMessage());
                }
            }
        });
    }
}
