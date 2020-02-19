package com.jy.jiandao.home.recommend.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.NewsData;
import com.mr.k.libmvp.Utils.Logger;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.jy.jiandao.AppConstant.*;

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



        mNewsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mNewsRecyclerView.getLayoutManager();
                firstVisibleItem   = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(NewsPageAdapter.VIDEO_PLAY_TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {

                        //如果滑出去了上面和下面就是否，和今日头条一样


                        //是否全屏
                        if(!GSYVideoManager.isFullState(getActivity())) {
                            GSYVideoManager.releaseAllVideos();
                            mNewsRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                }
            }
        });

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
    public void onNewsSuccess(NewsData newsData, int requestType,@AppConstant.ResponseType int responseType) {

        if (requestType == REQUEST_FIRST_LOAD) { // 第一次请求数据回来
            mPageAdapter.setData(newsData.getBannerList(), newsData.getFlashList(), newsData.getArticleList());
            closeLoadingView();

            if(responseType == RESPONSE_FROM_SDCARD){
                mSmartRefreshLayout.autoRefresh(500);
            }

        } else if (requestType == REQUEST_REFRESH_LOAD) { // 刷新加载数据回来
            mPageAdapter.refresh(newsData.getBannerList(), newsData.getFlashList(), newsData.getArticleList());
            mSmartRefreshLayout.finishRefresh();


        } else if (requestType == REQUEST_LOAD_MORE_LOAD) {// 加载更多数据回来
            mPageAdapter.loadMore(newsData.getArticleList());
            mSmartRefreshLayout.finishLoadMore();



        }
        mStart = newsData.getStart();
        mNumber = newsData.getNumber();
        mPointTime = newsData.getPointTime();

        if(newsData.getMore() == 0){
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

    /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){

        }
    }*/

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
