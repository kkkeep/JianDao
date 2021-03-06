package com.jy.jiandao.detail.vp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.data.entity.NewsAttributeData;
import com.jy.jiandao.detail.IDetalContract;
import com.jy.jiandao.detail.page.DetailPageFragment;
import com.mr.k.libmvp.base.BaseFragment;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.manager.MvpFragmentManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.jy.jiandao.AppConstant.BundleKey.*;

public class DetailVpFragment extends BaseMvpFragment<IDetalContract.IDetailVpPresenter> implements IDetalContract.IDetailVpView {


    private ViewPager mViewPager;

    private TextView mTvWriteComment;

    private DetailVpAdapter mDetailVpAdapter;

    private ArrayList<? extends BaseNews> mDataList;

    private int position;







    @Override
    public void onNewsAttributeInfoResult(NewsAttributeData data, String msg) {

    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        if(args != null){
            mDataList = args.getParcelableArrayList(DETAIL_NEWS_LIST);
            this.position = args.getInt(DETAIL_NEWS_LIST_POSTION);

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
        mTvWriteComment = findViewById(R.id.newsDetailWriteComment);



        mTvWriteComment.setOnClickListener( (View v) ->{
            // 获取当前显示的fragment,
         DetailPageFragment detailPageFragment = (DetailPageFragment) mDetailVpAdapter.getCurrentFragment(mViewPager);

         detailPageFragment.doCommentNews(mDataList.get(mViewPager.getCurrentItem()));

        });








}


    @Override
    protected void loadData() {
        super.loadData();

        mViewPager.setAdapter((mDetailVpAdapter = new DetailVpAdapter(getChildFragmentManager(),mDataList)));


        mViewPager.setCurrentItem(position);


    }

    @Override
    public IDetalContract.IDetailVpPresenter createPresenter() {
        return null;
    }




    public static void openDetailPage(FragmentActivity activity, BaseFragment currentFragment, ArrayList< ? extends BaseNews> newsList, int position){



        Bundle bundle  = new Bundle();


        bundle.putParcelableArrayList(DETAIL_NEWS_LIST,newsList);

        bundle.putInt(DETAIL_NEWS_LIST_POSTION,position);

        String tag  = DetailVpFragment.class.getName() + System.currentTimeMillis();



        MvpFragmentManager.addOrShowFragment(activity.getSupportFragmentManager(),DetailVpFragment.class,currentFragment,android.R.id.content,tag,bundle);


    }
}
