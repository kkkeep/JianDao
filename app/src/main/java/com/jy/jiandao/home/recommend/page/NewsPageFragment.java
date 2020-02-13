package com.jy.jiandao.home.recommend.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.NewsData;
import com.mr.k.libmvp.Utils.Logger;
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

    public static final String PARAMS_COLUMN_ID = "columnId";

    private RecyclerView mNewsRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    private NewsPageAdapter mPageAdapter;

    private String mColumnId;// 频道id
    private int mStart;
    private int mMore;
    private int mNumber;
    private int mPointTime;

    private String name; // TODO

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_page;
    }

    @Override
    protected void initView(@NotNull View root, @Nullable Bundle savedInstanceState) {
        mNewsRecyclerView = root.findViewById(R.id.home_recommend_news_list);
        mSmartRefreshLayout = root.findViewById(R.id.home_recommend_news_refresh_layout);

        mPageAdapter = new NewsPageAdapter();


        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mNewsRecyclerView.setAdapter(mPageAdapter);


    }

    @Override
    public void setArguments(@androidx.annotation.Nullable Bundle args) {
        super.setArguments(args);
        if(args != null){
            mColumnId = args.getString(PARAMS_COLUMN_ID);
            name = args.getString("name");
        }
    }

    @Override
    protected void loadData() {

        mPresenter.getNews(mColumnId,mStart,mNumber,mPointTime);

    }

    @Override
    public void onNewsResult(NewsData newsData, String msg) {

        if(newsData != null){

            mPageAdapter.setData(newsData.getBannerList(),newsData.getFlashList(),newsData.getArticleList());

        }else{
            showToast(msg);
        }

    }

    @Override
    public NewsContract.INewsPresenter createPresenter() {
        return new NewsPagePresenter();
    }


    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("%s",name);

    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d("%s",name);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d("%s",name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("%s",name);
    }


}
