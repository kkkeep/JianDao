package com.jy.jiandao.home.video;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.VideoData;
import com.mr.k.libmvp.MvpManager;
import com.mr.k.libmvp.Utils.Logger;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.umeng.commonsdk.debug.I;

public class VideoFragment extends BaseMvpFragment<VideoContract.IVideoPresenter> implements VideoContract.IVideoView {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_video_page;
    }


    @Override
    protected void loadData() {

        mPresenter.getVideoData(0,0,0, MvpManager.REQUEST_FIRST_LOAD);
    }

    @Override
    public VideoContract.IVideoPresenter createPresenter() {
        return new VideoPresenter();
    }

    @Override
    public void onNewsSuccess(VideoData videoData, int requestType, int responseType) {
        Logger.d("%s" ,"onNewsSuccess");
    }

    @Override
    public void onNewsFail(String msg, int requestType) {
        Logger.d("%s" ,"onNewsFail");

    }
}
