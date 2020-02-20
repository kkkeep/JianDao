package com.jy.jiandao.data.entity;

/*
 * created by Cherry on 2020-01-14
 **/
public class Ad {
    /**
     * 'id': '广告id',
     *             'title': '广告标题',
     *             'target_href': '点击广告打开的链接',
     *             'layout': 'APP广告布局:1图片开屏，2视频开屏，3图片通屏，4图片无标题，5图片有标题，6视频无标题，7视频有标题，8图片栏目插屏',
     *             'width': '广告宽度，单位px(视频时为0)',
     *             'height': '广告高度，单位px(视频时为0)',
     *             'ad_url': '广告资源url',
     */


    private String id;
    private String title;
    private String target_href;
    private int layout;
    private int width;
    private int height;
    private String ad_url;

    private String videoImage = "http://s-dev.seetaoism.com/Public/Uploads/publicity/2019-07-25/y_a0evkrmhk5g1n.png";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTargetHref() {
        return target_href;
    }

    public void setTargetHref(String targetHref) {
        this.target_href = targetHref;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAdUrl(String adUrl) {
        this.ad_url = adUrl;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }
}
