package com.jy.jiandao.home.recommend.page;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.NewsData;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * created by Cherry on 2020-01-14
 **/
public class NewsPageFragment extends BaseMvpFragment<NewsContract.INewsPresenter> implements NewsContract.INewsView {

    private RecyclerView mNewsRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    private String mColumnId;// 频道id
    private int mStart;
    private int mMore;
    private int mNumber;
    private int mPointTime;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_page;
    }

    @Override
    protected void initView(@NotNull View root, @Nullable Bundle savedInstanceState) {
        mNewsRecyclerView = root.findViewById(R.id.home_recommend_news_list);
        mSmartRefreshLayout = root.findViewById(R.id.home_recommend_news_refresh_layout);


    }


    @Override
    protected void loadData() {

        mPresenter.getNews(mColumnId,mStart,mNumber,mPointTime);

    }

    @Override
    public void onNewsResult(NewsData newsData, String msg) {

    }

    @Override
    public NewsContract.INewsPresenter createPresenter() {
        return new NewsPagePresenter();
    }
}
