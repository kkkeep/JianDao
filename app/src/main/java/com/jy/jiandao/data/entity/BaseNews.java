package com.jy.jiandao.data.entity;

public class BaseNews implements VideoNews {



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


        private  String id;
        private  String theme;
        private  String description;
        private  String image_url;
        private  String link;
        private  String share_link;
        private  int is_good;
        private  int is_collect;
        private int view_type;
        private int type;

        private String column_name;
        private String lead;


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

        public int getViewType() {
            return view_type;
        }



        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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


        public Ad getAd(){
            return null;
        }


}
