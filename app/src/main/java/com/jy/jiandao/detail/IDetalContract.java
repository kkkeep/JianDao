package com.jy.jiandao.detail;

import com.jy.jiandao.data.entity.Comment;
import com.jy.jiandao.data.entity.CommentListData;
import com.jy.jiandao.data.entity.NewsAttributeData;
import com.jy.jiandao.data.entity.RelativeNewsData;
import com.jy.jiandao.data.entity.Replay;
import com.jy.jiandao.data.entity.ReplayListData;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;

public interface IDetalContract {



    interface  IDetailVpView extends IBaseMvpView<IDetailVpPresenter>{


        void onNewsAttributeInfoResult(NewsAttributeData data,String msg);

        void onDoLike(String data,String msg);

        void onDoCollect(String data,String msg);

    }

    interface IDetailVpPresenter extends IBaseMvpPresenter<IDetailVpView>{


        /**
         * vp 页面，获取新闻熟悉，比如是否点赞，收藏等
         * @param id
         */
        void getNewsAttributeInfo(String id);


        /**
         * 点赞
         * @param id
         */

        void doLike(String id);

        /**
         * 收藏
         * @param id
         */
        void doCollect(String id);


    }



    interface  IDetailPageView extends IBaseMvpView<IDetailPagePresenter>{

        void onRelativeNewsListResult(RelativeNewsData data,String msg);

        void onCommentListResult(CommentListData data, String msg);

        void onCommentRelayListResult(ReplayListData data, String msg);


        void onDoCommentResult(Comment data,String msg);

        void onDoReplayResult(Replay data, String msg);

        void onDoCommentLikeResult(String data,String msg);


    }

    interface IDetailPagePresenter extends IBaseMvpPresenter<IDetailPageView>{


        // 获取当前新闻相关id
        void getRelativeNewsList(String id);



        // 获取评论列表
        void getCommentList(String id,int start,long pointTime);



        // 获取某一条评论的回复列表
        void getCommentRelayList(String newId,String commentId,int start,long pointTime);



        // 评论新闻
        void doCommnet(String newId,String content);


        // 回复别人的评论，
        void doReplay(String newsId,String commentId,String content,String toUserId,int type,String replayId);



        void doCommentLike(String commentId);
    }


}
