package com.jy.jiandao.home.recommend.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.RecommendPageData;
import com.jy.jiandao.video.JDVideo;
import com.jy.jiandao.video.RecyclerViewVideoScrollListener;
import com.jy.jiandao.video.VideoHolder;
import com.mr.k.libmvp.MvpManager;
import com.mr.k.libmvp.Utils.Logger;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.mr.k.libmvp.MvpManager.*;

/*
 * created by Cherry on 2020-01-14
 **/
public class NewsPageFragment extends BaseMvpFragment<NewsContract.INewsPresenter> implements NewsContract.INewsView {

    public static final String PARAMS_COLUMN_ID = "columnId";

    private RecyclerView mNewsRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    private NewsPageAdapter mPageAdapter;

    private String mColumnId;// 频道id
    private int mStart;
    private int mMore;
    private int mNumber;
    private long mPointTime;

    private String name; // TODO

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_page;
    }

    @Override
    protected void initView(@NotNull View root, @Nullable Bundle savedInstanceState) {
        mNewsRecyclerView = root.findViewById(R.id.home_recommend_news_list);
        mSmartRefreshLayout = root.findViewById(R.id.home_recommend_news_refresh_layout);


        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 上拉加载更多回调
                loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
                // 下拉刷新回调
            }
        });

        mPageAdapter = new NewsPageAdapter();


        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mNewsRecyclerView.setAdapter(mPageAdapter);


        mNewsRecyclerView.addOnScrollListener(new RecyclerViewVideoScrollListener());
    }

    @Override
    public void setArguments(@androidx.annotation.Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            mColumnId = args.getString(PARAMS_COLUMN_ID);
            name = args.getString("name");
        }
    }


    // 第一次进来初始化数据时加载数据
    @Override
    protected void loadData() {
        // 第一次加载需要显示loading 页面
        showFullLoadingView(getRootViewId());
        mPresenter.getNews(mColumnId, mStart, mNumber, mPointTime, REQUEST_FIRST_LOAD);
    }


    // 下拉刷新请求数据

    private void refresh() {

        mPresenter.getNews(mColumnId, 0, 0, 0, REQUEST_REFRESH_LOAD);
    }


    // 上拉加载更多 请求数据
    private void loadMore() {
        mPresenter.getNews(mColumnId, mStart, mNumber, mPointTime, REQUEST_LOAD_MORE_LOAD);
    }

    @Override
    public void onNewsSuccess(RecommendPageData recommendPageData, int requestType, @MvpManager.ResponseType int responseType) {

        if (requestType == REQUEST_FIRST_LOAD) { // 第一次请求数据回来
            mPageAdapter.setData(recommendPageData.getBannerList(), recommendPageData.getFlashList(), recommendPageData.getArticleList());
            closeLoadingView();

            if (responseType == RESPONSE_FROM_SDCARD) {
                mSmartRefreshLayout.autoRefresh(500);
            }

        } else if (requestType == REQUEST_REFRESH_LOAD) { // 刷新加载数据回来
            mPageAdapter.refresh(recommendPageData.getBannerList(), recommendPageData.getFlashList(), recommendPageData.getArticleList());
            mSmartRefreshLayout.finishRefresh();


        } else if (requestType == REQUEST_LOAD_MORE_LOAD) {// 加载更多数据回来
            mPageAdapter.loadMore(recommendPageData.getArticleList());
            mSmartRefreshLayout.finishLoadMore();


        }
        mStart = recommendPageData.getStart();
        mNumber = recommendPageData.getNumber();
        mPointTime = recommendPageData.getPointTime();

        if (recommendPageData.getMore() == 0) {
            mSmartRefreshLayout.setNoMoreData(true); //，这个时候，传入true, 表示没有更多数据了,再去上拉的时候，不会触发下拉更多的回调，会直接显示没有更多数据
        }

    }

    @Override
    public void onNewsFail(String msg, int requestType) {

        if (requestType == REQUEST_FIRST_LOAD) {
            showErrorLoadingView();

        } else if (requestType == REQUEST_REFRESH_LOAD) {
            showToast(getString(R.string.text_error_refresh_fail));
            mSmartRefreshLayout.finishRefresh();

        } else if (requestType == REQUEST_LOAD_MORE_LOAD) {
            showToast(getString(R.string.text_error_load_more_fail));
            mSmartRefreshLayout.finishLoadMore();
        }

    }


    @Override
    public NewsContract.INewsPresenter createPresenter() {
        return new NewsPagePresenter(getContext());
    }


    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("%s", name);

    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d("%s", name);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onPause();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d("%s", name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        GSYVideoManager.releaseAllVideos();
        Logger.d("%s", name);
    }


}
