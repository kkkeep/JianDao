package com.jy.jiandao.data.entity;

import java.util.List;

public class RelativeNewsData {

    /**
     * {
     * 'code': '1',
     * 'message': '成功提示',
     * 'data': {
     * 	'access_article_list': [
     * 	{//文章列表
     * 		'id': '文章id',
     * 		'theme': '文章标题',
     * 		'description': '文章描述',
     * 		'type': '文章类型：1新闻，2快讯，3图片，4视频，5期刊，6专题',
     * 		'column_name': '栏目分类',
     * 		'is_good': '是否点赞，1已点赞，0未点赞',
     * 		'is_collect': '是否收藏，1已收藏，0未收藏',
     * 		'link': '文章链接',
     * 		'share_link': '文章分享链接',
     * 		'image_url': '文章预览图(系统会根据不同文章类型，返回不同大小的图片)',
     *        },...],
     * 	'type': '文章类型：1新闻，2快讯，3图片，4视频，5期刊，6专题',
     * }
     * }
     */

    private List<News>access_article_list;

    private int type;


    public List<News> getList() {
        return access_article_list;
    }

    public int getType() {
        return type;
    }

    public class News extends BaseNews{



    }




}
