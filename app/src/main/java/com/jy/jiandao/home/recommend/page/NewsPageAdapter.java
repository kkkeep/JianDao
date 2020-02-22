package com.jy.jiandao.home.recommend.page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import com.jy.jiandao.GlideApp;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.RecommendPageData;
import com.jy.jiandao.ad.NewsAdBannerHolder;
import com.jy.jiandao.ad.NewsAdBigPicHolder;
import com.jy.jiandao.ad.NewsAdVideoHolder;
import com.jy.jiandao.video.VideoHolder;
import com.mr.k.banner.KBanner;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.BaseAdapterHolder;
import com.mr.k.libmvp.base.BaseRecyclerAdapter;
import com.mr.k.libmvp.widget.MarqueeView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import com.jy.jiandao.video.JDVideo;

/*
 * created by Cherry on 2020-01-14
 **/
public class NewsPageAdapter extends BaseRecyclerAdapter<RecommendPageData.News> {

    public static final String VIDEO_PLAY_TAG = "NewsPageAdapter_TAG";

    private static final int NEWS_TYPE_BANER = 0X99; // 顶部banner
    private static final int NEWS_TYPE_LEFT_PIC = 0X100; //  左图
    private static final int NEWS_TYPE_RIGHT_PIC = 0X101; // 右图
    private static final int NEWS_TYPE_BIG_PIC = 0X102; //  中间大图
    private static final int NEWS_TYPE_VIDEO = 0X103; //  视频
    private static final int NEWS_TYPE_TEXT = 0X104; // 即使

    private static final int AD_TYPE_BANER = 0X105; // 通屏广告，就是只有一个小的banner 图片
    private static final int AD_TYPE_BIG_PIC = 0X106; //中间大图广告
    private static final int AD_TYPE_VIDEO = 0X107; // 视频广告


    private List<RecommendPageData.Banner> bannerList;
    private List<RecommendPageData.Flash> flasheList;
    private List<RecommendPageData.News> newsList;


    public void setData(List<RecommendPageData.Banner> banners, List<RecommendPageData.Flash> flashes, List<RecommendPageData.News> news) {
        this.bannerList = banners;
        this.flasheList = flashes;
            this.newsList = news;
            notifyDataSetChanged();
        }


    public void refresh(List<RecommendPageData.Banner> banners, List<RecommendPageData.Flash> flashes, List<RecommendPageData.News> news) {
        setData(banners, flashes, news);
    }


    public void loadMore(List<RecommendPageData.News> news) {
        int start = this.newsList.size();
        this.newsList.addAll(news);
        notifyItemRangeChanged(start,news.size());
    }
/*

    @NonNull
    @Override
    public BaseAdapterHolder<RecommendPageData.News> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        Class<? extends BaseAdapterHolder<RecommendPageData.News>> holderClass = LeftHolder.class;

        int layoutId = R.layout.item_recommend_news_left;

        switch (viewType) {

            case NEWS_TYPE_BANER: {

                holderClass = HeaderHolder.class;
                layoutId = R.layout.item_recommend_news_banner;

                break;
            }

            case NEWS_TYPE_LEFT_PIC: {
                holderClass = LeftHolder.class;
                layoutId = R.layout.item_recommend_news_left;
                break;

            }

            case NEWS_TYPE_RIGHT_PIC: {
                holderClass = RightHolder.class;
                layoutId = R.layout.item_recommend_news_right;
                break;
            }
            case NEWS_TYPE_BIG_PIC: {
                holderClass = BigPicHolder.class;
                layoutId = R.layout.item_recommend_news_big_pic;
                break;
            }
            case NEWS_TYPE_TEXT: {
                holderClass = TextHolder.class;
                layoutId = R.layout.item_recommend_news_text;
                break;
            }
            case NEWS_TYPE_VIDEO: {
                holderClass = NewsVideoHolder.class;
                layoutId = R.layout.item_recommend_news_video;
                break;
            }

            case AD_TYPE_BANER:{

                holderClass = AdBannerHolder.class;
                layoutId = R.layout.item_ad_banner;

                break;
            }

            case AD_TYPE_BIG_PIC:{
                holderClass = AdBigPicHolder.class;
                layoutId = R.layout.item_ad_big_pic;

                break;
            }

            case AD_TYPE_VIDEO:{

                holderClass = AdVideoHolder.class;
                layoutId = R.layout.item_ad_video;
                break;
            }

        }


        try {

            if(viewType != NEWS_TYPE_VIDEO && viewType != AD_TYPE_VIDEO){
                Constructor<? extends BaseAdapterHolder<RecommendPageData.News>> constructor = holderClass.getConstructor(NewsPageAdapter.class, View.class);

                return constructor.newInstance(this, LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
            }else{

                Constructor<? extends BaseAdapterHolder<RecommendPageData.News>> constructor = holderClass.getConstructor(NewsPageAdapter.class,String.class, View.class);

                return constructor.newInstance(this,VIDEO_PLAY_TAG, LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));

            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
*/





    @Override
    public void onBindViewHolder(@NonNull BaseAdapterHolder<RecommendPageData.News> holder, int position) {
        int type = getItemViewType(position);
        if (type == NEWS_TYPE_BANER) {
            ((HeaderHolder) holder).bindData(bannerList, flasheList);
        } else {
            holder.bindData(newsList.get(getRealPosition(position)));
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (!SystemFacade.isListEmpty(bannerList)) {
            count++;
        }

        if (newsList != null) {
            count = count + newsList.size();
        }
        return count;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        int layoutId = R.layout.item_recommend_news_left;

        switch (viewType) {

            case NEWS_TYPE_BANER: {

                layoutId = R.layout.item_recommend_news_banner;

                break;
            }

            case NEWS_TYPE_LEFT_PIC: {
                layoutId = R.layout.item_recommend_news_left;
                break;

            }

            case NEWS_TYPE_RIGHT_PIC: {
                layoutId = R.layout.item_recommend_news_right;
                break;
            }
            case NEWS_TYPE_BIG_PIC: {
                layoutId = R.layout.item_recommend_news_big_pic;
                break;
            }
            case NEWS_TYPE_TEXT: {
                layoutId = R.layout.item_recommend_news_text;
                break;
            }
            case NEWS_TYPE_VIDEO: {
                layoutId = R.layout.item_recommend_news_video;
                break;
            }

            case AD_TYPE_BANER:{

                layoutId = R.layout.item_ad_banner;

                break;
            }

            case AD_TYPE_BIG_PIC:{
                layoutId = R.layout.item_ad_big_pic;

                break;
            }

            case AD_TYPE_VIDEO:{
                layoutId = R.layout.item_ad_video;
                break;
            }

        }

        return  layoutId;
    }

    @Override
    public BaseAdapterHolder<RecommendPageData.News> createHolder(View itemView,int viewType) {

        BaseAdapterHolder<RecommendPageData.News> holder = null;
        switch (viewType) {

            case NEWS_TYPE_BANER: {

                holder = new HeaderHolder(itemView);

                break;
            }

            case NEWS_TYPE_LEFT_PIC: {
                holder = new LeftHolder(itemView);
                break;

            }

            case NEWS_TYPE_RIGHT_PIC: {
                holder = new RightHolder(itemView);

                break;
            }
            case NEWS_TYPE_BIG_PIC: {
                holder = new BigPicHolder(itemView);
                break;
            }
            case NEWS_TYPE_TEXT: {
                holder = new TextHolder(itemView);

                break;
            }
            case NEWS_TYPE_VIDEO: {

                holder = new NewsVideoHolder(VIDEO_PLAY_TAG,itemView);

                break;
            }

            case AD_TYPE_BANER:{
                holder = new NewsAdBannerHolder<>(itemView);

                break;
            }

            case AD_TYPE_BIG_PIC:{
                holder = new NewsAdBigPicHolder<>(itemView);
                break;
            }

            case AD_TYPE_VIDEO:{
                holder = new NewsAdVideoHolder<>(VIDEO_PLAY_TAG,itemView);
                break;
            }

        }

        if(holder == null){
            holder = new LeftHolder(itemView);
        }

        return holder;
    }

    @Override
    public boolean isAdVideo(int position){
       return getItemViewType(position) == AD_TYPE_VIDEO;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && !SystemFacade.isListEmpty(bannerList)) {
            return NEWS_TYPE_BANER;
        }


        RecommendPageData.News news = newsList.get(getRealPosition(position));


        int type = news.getType(); // 1 ~  6 是非广告，7 表示广告


        if (type == 7) { // 广告
            // 广告又分为三种类型

            int adType = news.getAd().getLayout();

            if (adType == 4 || adType == 5) {
                return AD_TYPE_BIG_PIC;
            } else if (adType == 6 || adType == 7) {
                return AD_TYPE_VIDEO;
            } else {
                return AD_TYPE_BANER;
            }

        } else { // 非广告

            int newsType = news.getViewType();

            if (newsType == 2) {
                return NEWS_TYPE_BIG_PIC;
            } else if (newsType == 3) {
                return NEWS_TYPE_RIGHT_PIC;
            } else if (newsType == 4) {
                return NEWS_TYPE_VIDEO;
            } else if (newsType == 5) {
                return NEWS_TYPE_TEXT;
            } else {
                return NEWS_TYPE_LEFT_PIC;
            }

        }


    }


    private int getRealPosition(int position) {
        if (!SystemFacade.isListEmpty(bannerList)) {
            return --position;
        }

        return position;
    }




    private class HeaderHolder extends BaseAdapterHolder<RecommendPageData.News> {

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



        }

        @Override
        public void bindData(RecommendPageData.News news) {

        }


        public void bindData(List<RecommendPageData.Banner> banners, List<RecommendPageData.Flash> flashes) {

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


            if(SystemFacade.isListEmpty(flashes)){

                group.setVisibility(View.GONE);

            }else{
                group.setVisibility(View.VISIBLE);
                marqueeView.setClickableText(flashes);

                marqueeView.setOnMarqueeTextClickListener(new MarqueeView.OnMarqueeTextClickListener<MarqueeView.MarqueeData>() {
                    @Override
                    public void onClick(@NotNull MarqueeView.MarqueeData data, int position) {

                        RecommendPageData.Flash flash= (RecommendPageData.Flash) data;


                    }
                });
            }



        }
    }

    private class LeftHolder extends BaseAdapterHolder<RecommendPageData.News>  {

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
        public void bindData(RecommendPageData.News news) {
            GlideApp.with(itemView).load(news.getImageUrl()).into(pic);
            title.setText(news.getTheme());
            label.setText(news.getColumnName());

        }
    }

    private class RightHolder extends LeftHolder {

        public RightHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.item_news_right_iv_pic);
            title = itemView.findViewById(R.id.item_news_right_tv_title);
            label = itemView.findViewById(R.id.item_news_right_tv_label);
        }


    }

    private class BigPicHolder extends LeftHolder {

        public BigPicHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.item_news_big_pic_iv_pic);
            title = itemView.findViewById(R.id.item_news_big_pic_tv_title);
            label = itemView.findViewById(R.id.item_news_big_pic_tv_label);
        }

    }



    private class TextHolder extends BaseAdapterHolder<RecommendPageData.News>  {

        TextView title;
        TextView content;
        TextView time;
        ImageView share;

        public TextHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_news_text_tv_title);
            content = itemView.findViewById(R.id.item_news_text_tv_content);
            time = itemView.findViewById(R.id.item_news_text_tv_time);
            share = itemView.findViewById(R.id.item_news_text_iv_share);
        }

        @Override
        public void bindData(RecommendPageData.News news) {

            title.setText(news.getTheme());
            content.setText(news.getContent());
            time.setText(news.getEditTime());

        }
    }


    private class NewsVideoHolder extends VideoHolder<RecommendPageData.News>{

        private TextView label;

        public NewsVideoHolder(String tag, @NonNull View itemView) {
            super(tag, itemView);

           label =  itemView.findViewById(R.id.item_news_video_tv_label);
        }

        @Override
        public JDVideo getGsyVideoPlayer() {
            return itemView.findViewById(R.id.item_news_video_gsy_player);
        }

        @Override
        public TextView getTitleView() {
            return itemView.findViewById(R.id.item_news_video_tv_title);
        }


        @Override
        public void bindData(RecommendPageData.News data) {
            super.bindData(data);

            label.setText(data.getColumnName());
        }
    }

/*

    private class AdBannerHolder extends NewsAdBannerHolder<RecommendPageData.News>{

        public AdBannerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    private class AdBigPicHolder extends NewsAdBigPicHolder<RecommendPageData.News>{

        public AdBigPicHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    private class AdVideoHolder extends NewsAdVideoHolder<RecommendPageData.News>{

        public AdVideoHolder(String tag, @NonNull View itemView) {
            super(tag, itemView);
        }
    }

*/




}
