package com.jy.jiandao.home.recommend;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.ColumnData;
import com.mr.k.libmvp.base.BaseMvpFragment;

/*
 * created by Cherry on 2020-01-08
 **/
public class RecommendFragment extends BaseMvpFragment<RecommendContract.IRecommendPresenter> implements RecommendContract.IRecommendView {
    @Override
    public void onColumnResult(ColumnData data, String msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }


    @Override
    protected void loadData() {
        super.loadData();

        mPresenter.getColumnList();
    }

    @Override
    public RecommendContract.IRecommendPresenter createPresenter() {
        return new RecommendPresenter();
    }
}
