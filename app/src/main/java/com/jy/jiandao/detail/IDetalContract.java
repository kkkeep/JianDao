package com.jy.jiandao.detail;

import com.jy.jiandao.data.entity.Comment;
import com.jy.jiandao.data.entity.CommentListData;
import com.jy.jiandao.data.entity.NewsAttributeData;
import com.jy.jiandao.data.entity.RelativeNewsData;
import com.jy.jiandao.data.entity.Relay;
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


        void getNewsAttributeInfo(String id);

        void doLike(String id);

        void doCollect(String id);


    }



    interface  IDetailPageView extends IBaseMvpView<IDetailPagePresenter>{

        void onRelativeNewsListResult(RelativeNewsData data,String msg);

        void onCommentListResult(CommentListData data, String msg);

        void onCommentRelayListResult(ReplayListData data, String msg);


        void onDoCommentResult(Comment data,String msg);

        void onDoReplayResult(Relay data,String msg);


    }

    interface IDetailPagePresenter extends IBaseMvpPresenter<IDetailPageView>{


        void getRelativeNewsList(String id);

        void getCommentList(String id,int start,long pointTime);


        void getCommentRelayList(String newId,String commentId,int start,int pointTime);


        void doCommnet(String newId,String content);


        void doReplay(String newsId,String commentId,String content,String toUserId,int type,String replayId);

    }


}
