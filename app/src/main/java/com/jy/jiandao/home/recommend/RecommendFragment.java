package com.jy.jiandao.home.recommend;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.ColumnData;
import com.mr.k.libmvp.Utils.DataFileCacheUtils;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.BaseMvpFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

/*
 * created by Cherry on 2020-01-08
 **/
public class RecommendFragment extends BaseMvpFragment<RecommendContract.IRecommendPresenter> implements RecommendContract.IRecommendView {

    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private RecommendViewPagerAdapter mPagerAdapter;


    @Override
    public void onColumnResult(ColumnData data, String msg) {
        if(data != null){
            closeLoadingView();
            mPagerAdapter = new RecommendViewPagerAdapter(getFragmentManager(), data.getList().getMyColumn());
            mViewPager.setAdapter(mPagerAdapter);
            mTabLayout.setViewPager(mViewPager);





        }else{
            showErrorLoadingView(msg, () -> {
                loadData();
            });
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {
        mTabLayout = view.findViewById(R.id.home_recommend_top_tab_layout);
        mViewPager = view.findViewById(R.id.home_recommend_viewpager);



    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected void loadData() {
        showFullLoadingView(getRootViewId());

        mPresenter.getColumnList();
    }

    @Override
    public boolean isAddBackStack() {
        return false;
    }

    @Override
    public boolean isNeedAnimation() {
        return false;
    }

    @Override
    public RecommendContract.IRecommendPresenter createPresenter() {
        return new RecommendPresenter();
    }
}
