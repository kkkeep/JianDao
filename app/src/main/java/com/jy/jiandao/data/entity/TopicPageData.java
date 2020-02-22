package com.jy.jiandao.data.entity;

import java.util.List;

public class TopicPageData extends BaseNewsData {

    private List<TopicBanner> banner_list;

    private List<News> list;



    public class TopicBanner extends BaseNews{

    }



    public class News extends BaseNews{

        // 专题列表页目前是没有视频的，加上以防万一

        /**
         *   video_is_sans_href': '视频是否为外链，1是外链，0不是外链(type是视频时出现)',
         *                 'video_url': '视频链接地址(type是视频时出现)'
         */


        private String video_url;
        private int video_is_sans_href;

        private Ad ad;


        @Override
        public String getVideoUrl() {
            return video_url;
        }

        @Override
        public String getVideoTitle() {
            return getTheme();
        }

        @Override
        public String getVideoCoverUrl() {
            return getImageUrl();
        }

        @Override
        public void setVideoUrl(String url) {
            video_url = url;
        }

        @Override
        public Ad getAd() {
            return ad;
        }
    }

}
