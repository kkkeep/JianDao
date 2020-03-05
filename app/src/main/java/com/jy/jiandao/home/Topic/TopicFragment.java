package com.jy.jiandao.home.Topic;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.data.entity.TopicPageData;
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

import static com.mr.k.libmvp.manager.MvpManager.REQUEST_FIRST_LOAD;

public class TopicFragment extends BaseMvpFragment<TopicContract.ITopicPresenter> implements TopicContract.ITopicView{


    private SmartRefreshLayout mSmartRefreshLayout;

    private RecyclerView mRecyclerView;


    private TopicAdapter mTopicAdapter;

    private int mStart;
    private int mNumber;
    private long mPointTime;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_topic_page;
    }


    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {

        mSmartRefreshLayout = findViewById(R.id.home_topic_refresh_layout);
        mRecyclerView = findViewById(R.id.home_topic_list);


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

        mRecyclerView.setAdapter((mTopicAdapter = new TopicAdapter()));

        mTopicAdapter.setItemClickListener(new OnItemClickListener<TopicPageData.News>() {
            @Override
            public void onNewsClick(List<TopicPageData.News> news, int position) {
                DetailVpFragment.openDetailPage(getActivity(),null, (ArrayList<? extends BaseNews>) news,position);
            }
        });
    }

    @Override
    protected void loadData() {
        showFullLoadingView(getRootViewId());
        mPresenter.getTopicData(mStart,mNumber,mPointTime,REQUEST_FIRST_LOAD);
    }


    private void refresh(){

        mPresenter.getTopicData(0,0,0, MvpManager.REQUEST_REFRESH_LOAD);
    }

    private void loadMore(){
        mPresenter.getTopicData(mStart,mNumber,mPointTime, MvpManager.REQUEST_LOAD_MORE_LOAD);
    }


    @Override
    public TopicContract.ITopicPresenter createPresenter() {
        return new TopicPresenter();
    }

    @Override
    public void onNewsSuccess(TopicPageData topicPageData, @MvpManager.RequestType int requestType, @MvpManager.ResponseType int responseType) {

        if(requestType == MvpManager.REQUEST_FIRST_LOAD){
            closeLoadingView();
            mTopicAdapter.setData(topicPageData.getList(),topicPageData.getBannerList());
            if(responseType == MvpManager.RESPONSE_FROM_SDCARD){
                mSmartRefreshLayout.autoRefresh(1000);
            }
        }else if(requestType == MvpManager.REQUEST_REFRESH_LOAD){
            mSmartRefreshLayout.finishRefresh();
            mTopicAdapter.refresh(topicPageData.getList(), topicPageData.getBannerList());
        }else if(requestType == MvpManager.REQUEST_LOAD_MORE_LOAD){
            mSmartRefreshLayout.finishLoadMore();

            mTopicAdapter.loadMore(topicPageData.getList());
        }

        mStart = topicPageData.getStart();
        mNumber = topicPageData.getNumber();
        mPointTime = topicPageData.getPointTime();
        mSmartRefreshLayout.setNoMoreData( topicPageData.getMore() == 0);

    }

    @Override
    public void onNewsFail(String msg, int requestType) {
        if(requestType == MvpManager.REQUEST_FIRST_LOAD){

            showErrorLoadingView(msg, this::loadData);
        }else if(requestType == MvpManager.REQUEST_REFRESH_LOAD){
            showToast(msg);
            mSmartRefreshLayout.finishRefresh();
        }else if(requestType == MvpManager.REQUEST_LOAD_MORE_LOAD){
            showToast(msg);
            mSmartRefreshLayout.finishLoadMore();

        }
    }


    @Override
    public boolean isAddBackStack() {
        return false;
    }

    @Override
    public int getEnter() {
        return 0;
    }

    @Override
    public boolean isNeedAnimation() {
        return false;
    }

}

