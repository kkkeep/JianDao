package com.jy.jiandao.home.Topic;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import com.jy.jiandao.GlideApp;
import com.jy.jiandao.R;
import com.jy.jiandao.ad.NewsAdBannerHolder;
import com.jy.jiandao.ad.NewsAdBigPicHolder;
import com.jy.jiandao.ad.NewsAdVideoHolder;
import com.jy.jiandao.data.entity.RecommendPageData;
import com.jy.jiandao.data.entity.TopicPageData;
import com.jy.jiandao.data.entity.VideoPageData;
import com.jy.jiandao.home.video.VideoAdapter;
import com.mr.k.banner.KBanner;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.BaseAdapterHolder;
import com.mr.k.libmvp.base.BaseRecyclerAdapter;
import com.mr.k.libmvp.widget.MarqueeView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kotlin.jvm.internal.PropertyReference0Impl;

public class TopicAdapter extends BaseRecyclerAdapter<TopicPageData.News> {

    private static final String VIDEO_PAGE_PLAY_TAG = "VideoAdapter_play_tag";


    private static final int NEWS_TYPE_BANER = 0X99; // 顶部banner
    private static final int NEWS_TYPE_LEFT = 0X103; //  视频
    private static final int AD_TYPE_BANER = 0X105; // 通屏广告，就是只有一个小的banner 图片
    private static final int AD_TYPE_BIG_PIC = 0X106; //中间大图广告
    private static final int AD_TYPE_VIDEO = 0X107; // 视频广告


    private List<TopicPageData.TopicBanner> mBannerList;


    public void setData(List<TopicPageData.News> videoList, List<TopicPageData.TopicBanner> bannerList) {
        mBannerList = bannerList;
        super.setData(videoList);

    }


    public void refresh(List<TopicPageData.News> videoList, List<TopicPageData.TopicBanner> bannerList) {
        mBannerList = bannerList;
        super.refresh(videoList);
    }


    @Override
    public int getItemLayoutId(int viewType) {
        if (viewType == NEWS_TYPE_BANER) {
            return R.layout.item_recommend_news_banner;
        } else if (viewType == NEWS_TYPE_LEFT) {

            return R.layout.item_recommend_news_video;
        } else if (viewType == AD_TYPE_BIG_PIC) {
            return R.layout.item_ad_big_pic;

        } else if (viewType == AD_TYPE_VIDEO) {
            return R.layout.item_ad_video;
        } else {
            return R.layout.item_ad_banner;
        }

    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0 && !SystemFacade.isListEmpty(mBannerList)) {
            return NEWS_TYPE_BANER;
        }


        TopicPageData.News news = getDataByPosition(getRealPosition(position));

        if (news.getType() == 7) { // 表示广告

            int adType = news.getAd().getLayout();


            if (adType == 4 || adType == 5) { // 大图广告
                return AD_TYPE_BIG_PIC;
            } else if (adType == 6 || adType == 7) { // 视频广告
                return AD_TYPE_VIDEO;
            } else {
                return AD_TYPE_BANER; // 通屏图片广告
            }

        } else {
            return NEWS_TYPE_LEFT;
        }


    }


    private int getRealPosition(int position) {
        if (!SystemFacade.isListEmpty(mBannerList)) {
            return --position;
        }

        return position;
    }

    @Override
    public BaseAdapterHolder<TopicPageData.News> createHolder(View itemView, int viewType) {
        if (viewType == NEWS_TYPE_BANER) {
            return new HeaderHolder(itemView);

        } else if (viewType == NEWS_TYPE_LEFT) {
            return new LeftHolder(itemView);
        } else if (viewType == AD_TYPE_BIG_PIC) {
            return new NewsAdBigPicHolder<>(itemView);
        } else if (viewType == AD_TYPE_VIDEO) {
            return new NewsAdVideoHolder<>(VIDEO_PAGE_PLAY_TAG, itemView);
        } else {
            return new NewsAdBannerHolder<>(itemView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull BaseAdapterHolder<TopicPageData.News> holder, int position) {
        if (getItemViewType(position) == NEWS_TYPE_BANER) {
            ((HeaderHolder) holder).bindData(mBannerList);
        } else {
            holder.bindData(getDataByPosition(getRealPosition(position)));
        }


    }

    @Override
    public boolean isAdVideo(int position) {
        return getItemViewType(getRealPosition(position)) == AD_TYPE_VIDEO;
    }


    private class LeftHolder extends BaseAdapterHolder<TopicPageData.News> {

        ImageView pic;
        TextView title;
        TextView label;

        public LeftHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.item_news_left_iv_pic);
            title = itemView.findViewById(R.id.item_news_left_tv_title);
            label = itemView.findViewById(R.id.item_news_left_tv_label);
        }


        @Override
        public void bindData(TopicPageData.News news) {
            GlideApp.with(itemView).load(news.getImageUrl()).into(pic);
            title.setText(news.getTheme());
            label.setText(news.getColumnName());

        }
    }

    private class HeaderHolder extends BaseAdapterHolder<TopicPageData.News> {

        KBanner banner;

        MarqueeView marqueeView;


        ImageView more;


        Group group;

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);

            banner = itemView.findViewById(R.id.item_news_top_banner);

            marqueeView = itemView.findViewById(R.id.item_news_top_banner_flash);

            more = itemView.findViewById(R.id.item_news_top_banner_iv_more);

            group = itemView.findViewById(R.id.item_news_top_banner_flash_group);

            group.setVisibility(View.GONE);

        }

        @Override
        public void bindData(TopicPageData.News news) {

        }


        public void bindData(List<TopicPageData.TopicBanner> banners) {

            banner.setData(banners);
            banner.setAdapter(new KBanner.KBannerAdapter<RecommendPageData.Banner>() {
                @Override
                public void fillBannerItemData(KBanner banner, ImageView imageView, RecommendPageData.Banner data, int position) {
                    GlideApp.with(itemView).load(data.getImageUrl()).into(imageView);
                }

                @Override
                public String getTitleString(RecommendPageData.Banner data, int position) {
                    return data.getTheme();
                }
            });

        }


    }
}
