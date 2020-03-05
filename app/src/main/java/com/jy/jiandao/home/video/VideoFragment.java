package com.jy.jiandao.home.video;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.data.entity.VideoPageData;
import com.jy.jiandao.detail.vp.DetailVpFragment;
import com.jy.jiandao.video.RecyclerViewVideoScrollListener;
import com.mr.k.libmvp.manager.MvpManager;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.base.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends BaseMvpFragment<VideoContract.IVideoPresenter> implements VideoContract.IVideoView {


    private SmartRefreshLayout mSmartRefreshLayout;

    private RecyclerView mRecyclerView;


    private VideoAdapter mVideoAdapter;

    private int mStart;
    private int mNumber;
    private long mPointTime;



    @Override
    public int getLayoutId() {
        return R.layout.fragment_video_page;
    }

    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {
        mSmartRefreshLayout = findViewById(R.id.home_video_refresh_layout);
        mRecyclerView = findViewById(R.id.home_video_list);


        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                refresh();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerViewVideoScrollListener());


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setAdapter((mVideoAdapter = new VideoAdapter()));



        mVideoAdapter.setItemClickListener(new OnItemClickListener<VideoPageData.News>() {
            @Override
            public void onNewsClick(List<VideoPageData.News> news, int position) {
                DetailVpFragment.openDetailPage(getActivity(),null,(ArrayList<? extends BaseNews>) news,position);
            }
        });
    }

    @Override
    protected void loadData() {
        showFullLoadingView(getRootViewId());
        mPresenter.getVideoData(mStart,mNumber,mPointTime, MvpManager.REQUEST_FIRST_LOAD);
    }


    private void refresh(){

        mPresenter.getVideoData(0,0,0, MvpManager.REQUEST_REFRESH_LOAD);
    }

    private void loadMore(){
        mPresenter.getVideoData(mStart,mNumber,mPointTime, MvpManager.REQUEST_LOAD_MORE_LOAD);
    }





    @Override
    public VideoContract.IVideoPresenter createPresenter() {
        return new VideoPresenter(getContext());
    }

    @Override
    public void onNewsSuccess(VideoPageData videoPageData, int requestType, int responseType) {
        if(requestType == MvpManager.REQUEST_FIRST_LOAD){
            closeLoadingView();
            mVideoAdapter.setData(videoPageData.getList());
            if(responseType == MvpManager.RESPONSE_FROM_SDCARD){
                mSmartRefreshLayout.autoRefresh(1000);
            }
        }else if(requestType == MvpManager.REQUEST_REFRESH_LOAD){
            mSmartRefreshLayout.finishRefresh();
            mVideoAdapter.refresh(videoPageData.getList());
        }else if(requestType == MvpManager.REQUEST_LOAD_MORE_LOAD){
            mSmartRefreshLayout.finishLoadMore();

            mVideoAdapter.loadMore(videoPageData.getList());
        }

        mStart = videoPageData.getStart();
        mNumber = videoPageData.getNumber();
        mPointTime = videoPageData.getPointTime();
        mSmartRefreshLayout.setNoMoreData( videoPageData.getMore() == 0);
    }

    @Override
    public void onNewsFail(String msg, int requestType) {

        if(requestType == MvpManager.REQUEST_FIRST_LOAD){

            showErrorLoadingView(msg, () -> loadData());
        }else if(requestType == MvpManager.REQUEST_REFRESH_LOAD){
            showToast(msg);
            mSmartRefreshLayout.finishRefresh();
        }else if(requestType == MvpManager.REQUEST_LOAD_MORE_LOAD){
            showToast(msg);
            mSmartRefreshLayout.finishLoadMore();

        }
    }

    @Override
    public boolean isNeedAnimation() {
        return false;
    }
    @Override
    public boolean isAddBackStack() {
        return false;
    }



}
