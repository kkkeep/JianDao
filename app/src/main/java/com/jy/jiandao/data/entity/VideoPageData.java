package com.jy.jiandao.data.entity;

import java.util.List;

public class VideoPageData extends NewsData {




    private List<News> list; //  视频列表


    public List<News> getList() {
        return list;
    }

    public class News extends BaseNews{


        /**
         * 'video_is_sans_href': '视频是否为外链，1是外链，0不是外链',
         * 		'video_url': '视频链接地址',
         */


        private String video_is_sans_href;
        private String video_url;


        public String getVideoIsSansHref() {
            return video_is_sans_href;
        }

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
    }




}
