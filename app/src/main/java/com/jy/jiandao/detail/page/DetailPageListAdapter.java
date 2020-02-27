package com.jy.jiandao.detail.page;

import android.view.View;

import com.jy.jiandao.data.entity.Comment;
import com.jy.jiandao.data.entity.RelativeNewsData;
import com.mr.k.libmvp.base.BaseAdapterHolder;
import com.mr.k.libmvp.base.BaseRecyclerAdapter;

import java.util.List;

public class DetailPageListAdapter extends BaseRecyclerAdapter<Comment> {


    private List<RelativeNewsData.News> mNews;


    public void setData(List<Comment> dataList,List<RelativeNewsData.News> dataLis) {
        mNews = dataLis;
        super.setData(dataList);

    }



    @Override
    public int getItemLayoutId(int viewType) {
        return 0;
    }

    @Override
    public BaseAdapterHolder<Comment> createHolder(View itemView, int viewType) {
        return null;
    }


}
