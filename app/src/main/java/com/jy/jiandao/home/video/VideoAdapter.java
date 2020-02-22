package com.jy.jiandao.home.video;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jy.jiandao.R;
import com.jy.jiandao.ad.NewsAdBannerHolder;
import com.jy.jiandao.ad.NewsAdBigPicHolder;
import com.jy.jiandao.ad.NewsAdVideoHolder;
import com.jy.jiandao.data.entity.VideoPageData;
import com.jy.jiandao.video.JDVideo;
import com.jy.jiandao.video.VideoHolder;
import com.mr.k.libmvp.base.BaseAdapterHolder;
import com.mr.k.libmvp.base.BaseRecyclerAdapter;

public class VideoAdapter extends BaseRecyclerAdapter<VideoPageData.News> {

    private static final String VIDEO_PAGE_PLAY_TAG = "VideoAdapter_play_tag";



    private static final int NEWS_TYPE_VIDEO = 0X103; //  视频
    private static final int AD_TYPE_BANER = 0X105; // 通屏广告，就是只有一个小的banner 图片
    private static final int AD_TYPE_BIG_PIC = 0X106; //中间大图广告
    private static final int AD_TYPE_VIDEO = 0X107; // 视频广告



    @Override
    public int getItemLayoutId(int viewType) {

        if(viewType == NEWS_TYPE_VIDEO){

            return R.layout.item_recommend_news_video;
        }else if(viewType == AD_TYPE_BIG_PIC){
            return R.layout.item_ad_big_pic;

        }else if(viewType == AD_TYPE_VIDEO){
                return R.layout.item_ad_video;
        }else{
            return R.layout.item_ad_banner;
        }

    }

    @Override
    public BaseAdapterHolder<VideoPageData.News> createHolder(View itemView,int viewType) {
        if(viewType == NEWS_TYPE_VIDEO){
            return new VideoNewsHolder(VIDEO_PAGE_PLAY_TAG,itemView);
        }else if(viewType == AD_TYPE_BIG_PIC){
            return new NewsAdBigPicHolder<>(itemView);
        }else if(viewType == AD_TYPE_VIDEO){
            return new NewsAdVideoHolder<>(VIDEO_PAGE_PLAY_TAG,itemView);
        }else{
            return new NewsAdBannerHolder<>(itemView);
        }


    }


    @Override
    public boolean isAdVideo(int position) {
        return getItemViewType(position) == AD_TYPE_VIDEO;
    }

    @Override
    public int getItemViewType(int position) {
        VideoPageData.News news = getDataByPosition(position);

        if(news.getType() == 7){ // 表示广告

            int adType = news.getAd().getLayout();


            if (adType == 4 || adType == 5) { // 大图广告
                return AD_TYPE_BIG_PIC;
            } else if (adType == 6 || adType == 7) { // 视频广告
                return AD_TYPE_VIDEO;
            } else {
                return AD_TYPE_BANER; // 通屏图片广告
            }

        }else{
            return NEWS_TYPE_VIDEO;
        }

    }

    public class VideoNewsHolder extends VideoHolder<VideoPageData.News> {


        private TextView description;


        public VideoNewsHolder(String tag, @NonNull View itemView) {
            super(tag, itemView);

            description = itemView.findViewById(R.id.item_news_video_tv_label);
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
        public void bindData(VideoPageData.News data) {
            super.bindData(data);
            description.setText(data.getDescription());
        }
    }

/*

    public class AdBannerHolder extends NewsAdBannerHolder<VideoPageData.News>{

        public AdBannerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public class AdBigPicHolder extends NewsAdBigPicHolder<VideoPageData.News>{

        public AdBigPicHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public class AdVideoHolder extends NewsAdVideoHolder<VideoPageData.News>{

        public AdVideoHolder(String tag, @NonNull View itemView) {
            super(tag, itemView);
        }
    }*/
}
