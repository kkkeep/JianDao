package com.jy.jiandao.data.entity;

import com.mr.k.libmvp.widget.CommentsView;

public class Replay implements CommentsView.ReplayData {


    /**
     *          'reply_id': '回复id',
     * 			'type': '评论类型，1主评论，2评论的回复',
     * 			'comment_id': '评论id',
     * 			'content': '回复内容',
     * 			'from_name': '回复人名称',
     * 			'to_name': '被回复人名称',
     * 			'from_id': '回复人id',
     * 			'to_id': '被回复人id',
     */


    private String reply_id;
    private int type;
    private String comment_id;
    private String content;
    private String from_name;
    private String to_name;
    private String from_id;
    private String to_id;


    public String getId() {
        return reply_id;
    }

    public int getType() {
        return type;
    }

    public String getCommentId() {
        return comment_id;
    }



    public String getFromUserId() {
        return from_id;
    }

    public String getToUserId() {
        return to_id;
    }

    @Override
    public String getFromUserName() {
        return from_name;
    }

    @Override
    public String getToUserName() {
        return to_name;
    }

    @Override
    public String getContent() {
        return content;
    }
}
