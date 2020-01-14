package com.jy.jiandao.home.recommend;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.jy.jiandao.data.entity.ColumnData;
import com.jy.jiandao.home.recommend.page.NewsPageFragment;

import java.util.List;

/*
 * created by Cherry on 2020-01-14
 **/
public class RecommendViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<ColumnData.Column> mList;

    public RecommendViewPagerAdapter(@NonNull FragmentManager fm, List<ColumnData.Column> list) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mList = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new NewsPageFragment();
    }

    @Override
    public int getCount() {
        return  mList == null ? 0 : mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getName();

    }
}
