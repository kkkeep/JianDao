package com.jy.jiandao.data.entity;

import com.mr.k.libmvp.widget.MarqueeView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/*
 * created by Cherry on 2020-01-14
 **/
public class RecommendPageData extends BaseData {



    private int flash_id;


    private List<Banner> banner_list;
    private List<Flash> flash_list;
    private List<News> article_list;




    public int getFlashId() {
        return flash_id;
    }

    public void setFlashId(int flashId) {
        this.flash_id = flashId;
    }

    public List<Banner> getBannerList() {
        return banner_list;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.banner_list = bannerList;
    }

    public List<Flash> getFlashList() {
        return flash_list;
    }

    public void setFlashList(List<Flash> flashList) {
        this.flash_list = flashList;
    }

    public List<News> getArticleList() {
        return article_list;
    }

    public void setArticleList(List<News> articleList) {
        this.article_list = articleList;
    }



    public class Banner extends BaseNews {

    }

    public class Flash extends BaseNews implements MarqueeView.MarqueeData {

        @NotNull
        @Override
        public String getString() {
            return getTheme();
        }
    }

    public class News extends BaseNews {
        /**
         * id': '文章id',
         * 		'view_type': '视图类型：1左图，2中间大图，3右图，4视频，5即时',
         * 		'type': '文章类型：1新闻，2快讯，3图片，4视频，5期刊，6专题,7广告',
         * 		'column_name': '栏目分类',
         * 		'theme': '文章标题',
         * 		'description': '文章描述',
         * 		'lead': '导语',
         * 		'video_is_sans_href': '视频是否为外链，1是外链，0不是外链(type是视频时出现)',
         * 		'video_url': '视频链接地址(type是视频时出现)',
         * 		'content': '文章内容',
         * 		'edit_time': '文章发布时间',
         * 		'image_url': '文章预览图',
         * 		'is_good': '是否点赞，1已点赞，0未点赞',
         * 		'is_collect': '是否收藏，1已收藏，0未收藏',
         * 		'link': '文章链接',
         * 		'share_link': '文章分享链接',
         */


        private String content;
        private String edit_time;

        private String video_url;
        private String video_is_sans_href;

        private Ad ad;


        @Override
        public Ad getAd() {
            return ad;
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

        @Override
        public void setVideoUrl(String videoUrl) {
            this.video_url = videoUrl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getEditTime() {
            return edit_time;
        }

        public void setEditTime(String editTime) {
            this.edit_time = editTime;
        }


        public String getVideoIsSansHref() {
            return video_is_sans_href;
        }

    }

}
