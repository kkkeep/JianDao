package com.jy.jiandao.home.recommend.page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.jiandao.GlideApp;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.NewsData;
import com.mr.k.banner.KBanner;
import com.mr.k.libmvp.Utils.SystemFacade;

import org.w3c.dom.Text;

import java.lang.reflect.Constructor;
import java.util.List;

/*
 * created by Cherry on 2020-01-14
 **/
public class NewsPageAdapter extends RecyclerView.Adapter<NewsPageAdapter.BaseHolder> {


    private static final int NEWS_TYPE_BANER = 0X99; // 顶部banner
    private static final int NEWS_TYPE_LEFT_PIC = 0X100; //  左图
    private static final int NEWS_TYPE_RIGHT_PIC = 0X101; // 右图
    private static final int NEWS_TYPE_BIG_PIC = 0X102; //  中间大图
    private static final int NEWS_TYPE_VIDEO = 0X103; //  视频
    private static final int NEWS_TYPE_TEXT = 0X104; // 即使

    private static final int AD_TYPE_BANER = 0X105; // 通屏广告，就是只有一个小的banner 图片
    private static final int AD_TYPE_BIG_PIC = 0X106; //中间大图广告
    private static final int AD_TYPE_VIDEO = 0X107; // 视频广告


    private List<NewsData.Banner> bannerList;
    private List<NewsData.Flash> flasheList;
    private List<NewsData.News> newsList;


    public void setData(List<NewsData.Banner> banners, List<NewsData.Flash> flashes, List<NewsData.News> news) {
        this.bannerList = banners;
        this.flasheList = flashes;
        this.newsList = news;
        notifyDataSetChanged();
    }


    public void refresh(List<NewsData.Banner> banners, List<NewsData.Flash> flashes, List<NewsData.News> news) {
        setData(banners, flashes, news);
    }


    public void loadMore(List<NewsData.News> news) {
        int start = this.newsList.size();
        this.newsList.addAll(news);
        notifyItemRangeInserted(start, news.size());
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        Class<? extends BaseHolder> holderClass = LeftHolder.class;

        int layoutId = R.layout.item_news_left;

        switch (viewType) {

            case NEWS_TYPE_BANER: {

                holderClass = HeaderHolder.class;
                layoutId = R.layout.item_news_banner;

                break;
            }

            case NEWS_TYPE_LEFT_PIC: {
                holderClass = LeftHolder.class;
                layoutId = R.layout.item_news_left;
                break;

            }

            case NEWS_TYPE_RIGHT_PIC: {
                holderClass = RightHolder.class;
                layoutId = R.layout.item_news_right;
                break;
            }
            case NEWS_TYPE_BIG_PIC: {
                holderClass = BigPicHolder.class;
                layoutId = R.layout.item_news_big_pic;
                break;
            }
            case NEWS_TYPE_TEXT: {
                holderClass = TextHolder.class;
                layoutId = R.layout.item_news_text;
                break;
            }
            case NEWS_TYPE_VIDEO: {
                holderClass = VideoHolder.class;
                layoutId = R.layout.item_news_video;
                break;
            }

        }


        try {

            Constructor constructor = holderClass.getConstructor(NewsPageAdapter.class, View.class);
            BaseHolder baseHolder = (BaseHolder) constructor.newInstance(this, LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));

            return baseHolder;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
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
    public int getItemViewType(int position) {
        if (position == 0 && !SystemFacade.isListEmpty(bannerList)) {
            return NEWS_TYPE_BANER;
        }


        NewsData.News news = newsList.get(getRealPosition(position));


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


    public abstract class BaseHolder extends RecyclerView.ViewHolder {


        public BaseHolder(@NonNull View itemView) {
            super(itemView);

        }


        public abstract void bindData(NewsData.News news);
    }


    private class HeaderHolder extends BaseHolder {

        KBanner banner;

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);

            banner = itemView.findViewById(R.id.item_news_top_banner);
        }

        @Override
        public void bindData(NewsData.News news) {

        }


        public void bindData(List<NewsData.Banner> banners, List<NewsData.Flash> flashes) {

            banner.setData(banners);
            banner.setAdapter(new KBanner.KBannerAdapter<NewsData.Banner>() {
                @Override
                public void fillBannerItemData(KBanner banner, ImageView imageView, NewsData.Banner data, int position) {
                    GlideApp.with(itemView).load(data.getImageUrl()).into(imageView);
                }

                @Override
                public String getTitleString(NewsData.Banner data, int position) {
                    return data.getTheme();
                }
            });

        }
    }

    private class LeftHolder extends BaseHolder {

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
        public void bindData(NewsData.News news) {
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

    private class VideoHolder extends BaseHolder {

        ImageView pic;
        TextView title;
        TextView label;


        public VideoHolder(@NonNull View itemView) {
            super(itemView);

            pic = itemView.findViewById(R.id.item_news_video_iv_pic);
            title = itemView.findViewById(R.id.item_news_video_tv_title);
            label = itemView.findViewById(R.id.item_news_video_tv_label);
        }

        @Override
        public void bindData(NewsData.News news) {

            GlideApp.with(itemView).load(news.getImageUrl()).into(pic);
            title.setText(news.getTheme());
            label.setText(news.getColumnName());

        }
    }

    private class TextHolder extends BaseHolder {

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
        public void bindData(NewsData.News news) {

            title.setText(news.getTheme());
            content.setText(news.getContent());
            time.setText(news.getEditTime());

        }
    }

}
