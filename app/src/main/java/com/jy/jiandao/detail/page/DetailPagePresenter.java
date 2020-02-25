package com.jy.jiandao.detail.page;

import com.jy.jiandao.detail.IDetalContract;
import com.mr.k.libmvp.base.BasePresenter;

public class DetailPagePresenter extends BasePresenter<IDetalContract.IDetailPageView> implements IDetalContract.IDetailPagePresenter {
    @Override
    public void getRelativeNewsList(String id) {

    }

    @Override
    public void getCommentList(String id, int start, long pointTime) {

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
