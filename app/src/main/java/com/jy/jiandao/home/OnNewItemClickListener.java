package com.jy.jiandao.home;

import com.jy.jiandao.data.entity.BaseNews;

import java.util.List;

public interface OnNewItemClickListener  {

    void onNewsClick(List<? extends BaseNews> news, int position);
}
