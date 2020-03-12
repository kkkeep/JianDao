package com.jy.jiandao.detail.vp;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.detail.page.DetailPageFragment;
import com.umeng.socialize.media.Base;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailVpAdapter extends FragmentStatePagerAdapter {



    private ArrayList<? extends BaseNews> list;

    private FragmentManager fragmentManager;

    private String tag;

    public DetailVpAdapter(@NonNull FragmentManager fm, ArrayList<? extends BaseNews> data) {

        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentManager = fm;

        list = data;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        DetailPageFragment detailPageFragment = new DetailPageFragment();

        Bundle bundle = new Bundle();

        bundle.putParcelable(AppConstant.BundleKey.DETAIL_NEWS,list.get(position));

        detailPageFragment.setArguments(bundle);
        return detailPageFragment;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return  list == null ? 0 : list.size();
    }


    public Fragment getCurrentFragment(ViewPager viewPager){

        int position = viewPager.getCurrentItem();
        if(this instanceof FragmentStatePagerAdapter){
            return (Fragment) instantiateItem(viewPager,position);
        }else{
            return fragmentManager.findFragmentByTag("android:switcher:" + viewPager.getId() + ":" + position);
        }



        //

    }

}
