package com.jy.jiandao.ad;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jy.jiandao.GlideApp;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.Ad;
import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.data.entity.RecommendPageData;
import com.mr.k.libmvp.base.BaseAdapterHolder;

public class NewsAdBigPicHolder<T extends BaseNews> extends BaseAdapterHolder<T> {

    private ImageView pic;

    private TextView title;


    public NewsAdBigPicHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.item_ad_tv_title);
        pic = itemView.findViewById(R.id.item_ad_iv_pic_big);

    }

    @Override
    public void bindData(T news) {
        Ad ad = news.getAd();

        GlideApp.with(itemView).load(ad.getAd_url()).into(pic);


        if (TextUtils.isEmpty(ad.getTitle())) {
            title.setVisibility(View.GONE);
        } else {
            title.setVisibility(View.VISIBLE);
            title.setText(ad.getTitle());
        }

    }
}