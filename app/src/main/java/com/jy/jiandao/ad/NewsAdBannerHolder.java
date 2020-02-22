package com.jy.jiandao.ad;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.jy.jiandao.GlideApp;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.Ad;
import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.data.entity.RecommendPageData;
import com.mr.k.libmvp.base.BaseAdapterHolder;

public class NewsAdBannerHolder<T extends BaseNews> extends BaseAdapterHolder<T> {

    ImageView pic;


    public NewsAdBannerHolder(@NonNull View itemView) {
        super(itemView);

        pic = itemView.findViewById(R.id.item_ad_banner_iv_pic);
    }

    @Override
    public void bindData(T news) {
        Ad ad = news.getAd();
        GlideApp.with(itemView).load(ad.getAd_url()).into(pic);

    }
}