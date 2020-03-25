package com.jy.umeng.share;

public class LinkData {

    public String url;
    public String title;
    public Object thumb; // 缩略图
    public String description;


    public String permisssonMsg ; // 如果没有权限，分享失败提示信息

    public LinkData(String url, String title, Object thumb, String description) {
        this.url = url;
        this.title = title;
        this.thumb = thumb;
        this.description = description;

        this.permisssonMsg = "QQ 分享需要文件读写权限";

    }

    public LinkData(String url, String title, Object thumb) {
        this.url = url;
        this.title = title;
        this.thumb = thumb;
    }

    public LinkData(String url, String title) {
        this.url = url;
        this.title = title;
    }
    public LinkData(String url) {
        this.url = url;
        this.title = title;
    }


    public void setPermisssonMsg(String permisssonMsg) {
        this.permisssonMsg = permisssonMsg;
    }
}
