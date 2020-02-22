package com.jy.jiandao.data.entity;

public class BaseNewsData {

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








}
