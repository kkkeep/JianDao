package com.jy.jiandao.detail.vp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.data.entity.NewsAttributeData;
import com.jy.jiandao.data.entity.RecommendPageData;
import com.jy.jiandao.detail.IDetalContract;
import com.mr.k.libmvp.base.BaseFragment;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.manager.MvpFragmentManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DetailVpFragment extends BaseMvpFragment<IDetalContract.IDetailVpPresenter> implements IDetalContract.IDetailVpView {


    private ViewPager mViewPager;

    private ArrayList<? extends BaseNews> mDataList;

    int position;

    @Override
    public void onNewsAttributeInfoResult(NewsAttributeData data, String msg) {

    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        if(args != null){
            mDataList = (ArrayList<? extends BaseNews>) args.getSerializable("newsDataList");

            this.position = args.getInt("position");

        }
    }

    @Override
    public void onDoLike(String data, String msg) {

    }

    @Override
    public void onDoCollect(String data, String msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail_vp;
    }


    @Override
    protected void initView(@NotNull View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        mViewPager = findViewById(R.id.newsDetailVp);


    }


    @Override
    protected void loadData() {
        super.loadData();

        mViewPager.setAdapter(new DetailVpAdapter(getChildFragmentManager(),mDataList));


        mViewPager.setCurrentItem(position);


    }

    @Override
    public IDetalContract.IDetailVpPresenter createPresenter() {
        return null;
    }




    public static void openDetailPage(FragmentActivity activity, BaseFragment currentFragment, ArrayList< ? extends BaseNews> newsList, int position){



        Bundle bundle  = new Bundle();

        bundle.putSerializable("newsDataList",newsList);

        bundle.putInt("position",position);

        MvpFragmentManager.addOrShowFragment(activity.getSupportFragmentManager(),DetailVpFragment.class,currentFragment,android.R.id.content,bundle);


    }
}
