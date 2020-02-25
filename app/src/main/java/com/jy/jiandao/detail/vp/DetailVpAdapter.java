package com.jy.jiandao.detail.vp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.detail.page.DetailPageFragment;
import com.umeng.socialize.media.Base;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailVpAdapter extends FragmentStatePagerAdapter {



    private ArrayList<? extends BaseNews> list;



    public DetailVpAdapter(@NonNull FragmentManager fm, ArrayList<? extends BaseNews> data) {

        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        list = data;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        DetailPageFragment detailPageFragment = new DetailPageFragment();

        Bundle bundle = new Bundle();

        bundle.putSerializable("news",list.get(position));

        detailPageFragment.setArguments(bundle);
        return detailPageFragment;
    }

    @Override
    public int getCount() {
        return  list == null ? 0 : list.size();
    }
}
