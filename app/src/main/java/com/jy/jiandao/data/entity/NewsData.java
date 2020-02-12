package com.jy.jiandao.data.entity;

import java.util.List;

/*
 * created by Cherry on 2020-01-14
 **/
public class NewsData {
    /**
     * 'start': '下次请求文章开始位置',
     * 	'number': '下次请求文章次数',
     * 	'point_time': '下次请求使用的节点时间',
     * 	'more': '是否有更多数据，1有，0没有',
     */

    private long point_time;
    private int start;
    private int number;
    private int more;
    private int flash_id;


    private List<Banner> banner_list;
    private List<Flash> flash_list;
    private List<News> article_list;


    public long getPointTime() {
        return point_time;
    }

    public void setPointTime(long point_time) {
        this.point_time = point_time;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }

    public int getFlashId() {
        return flash_id;
    }

    public void setFlashId(int flashId) {
        this.flash_id = flashId;
    }

    public List getBannerList() {
        return banner_list;
    }

    public void setBannerList(List bannerList) {
        this.banner_list = bannerList;
    }

    public List getFlashList() {
        return flash_list;
    }

    public void setFlashList(List flashList) {
        this.flash_list = flashList;
    }

    public List getArticleList() {
        return article_list;
    }

    public void setArticleList(List articleList) {
        this.article_list = articleList;
    }

    public class BaseNews {

        /**
         * id': '文章id',
         * 		'theme': '文章标题',
         * 		'description': '文章描述',
         * 		'image_url': '文章预览图',
         * 		'is_good': '是否点赞，1已点赞，0未点赞',
         * 		'is_collect': '是否收藏，1已收藏，0未收藏',
         * 		'link': '文章链接',
         * 		'share_link': '文章分享链接',
         */

        private  String id;
        private  String theme;
        private  String description;
        private  String image_url;
        private  String link;
        private  String share_link;
        private  int is_good;
        private  int is_collect;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImageUrl() {
            return image_url;
        }

        public void setImageUrl(String imageUrl) {
            this.image_url = imageUrl;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getShareLink() {
            return share_link;
        }

        public void setShareLink(String shareLink) {
            this.share_link = shareLink;
        }

        public int getIsGood() {
            return is_good;
        }

        public void setIsGood(int isGood) {
            this.is_good = isGood;
        }

        public int getIsCollect() {
            return is_collect;
        }

        public void setIsCollect(int isCollect) {
            this.is_collect = isCollect;
        }
    }


    public class Banner extends BaseNews {

    }

    public class Flash extends BaseNews {

    }


    public class News extends BaseNews{
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

        private String column_name;
        private String lead;
        private String video_url;
        private String content;
        private String edit_time;

        private int view_type;
        private int type;
        private int video_is_sans_href;

        private Ad ad;

        public Ad getAd() {
            return ad;
        }

        public void setAd(Ad ad) {
            this.ad = ad;
        }

        public String getColumnName() {
            return column_name;
        }



        public String getLead() {
            return lead;
        }

        public void setLead(String lead) {
            this.lead = lead;
        }

        public String getVideoUrl() {
            return video_url;
        }

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

        public int getViewType() {
            return view_type;
        }



        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getVideoIsSansHref() {
            return video_is_sans_href;
        }

    }

}
