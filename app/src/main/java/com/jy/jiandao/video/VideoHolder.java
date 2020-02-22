package com.jy.jiandao.video;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jy.jiandao.data.entity.BaseNews;
import com.mr.k.libmvp.base.BaseAdapterHolder;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public abstract class VideoHolder<T extends BaseNews> extends BaseAdapterHolder<T> {


    private TextView title;
    private GSYVideoOptionBuilder gsyVideoOptionBuilder;
    private JDVideo gsyVideoPlayer;
    private String tag;


    public VideoHolder(String tag, @NonNull View itemView) {
        super(itemView);

        gsyVideoPlayer = getGsyVideoPlayer();
        title = getTitleView();
        this.tag = tag;

        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    public abstract JDVideo getGsyVideoPlayer();

    public TextView getTitleView(){
        return null;
    }


    public String getTitleString(T data){
        return data.getVideoTitle();
    }



    @Override
    public void bindData(T data) {
        super.bindData(data);

        if(title != null){
            if(TextUtils.isEmpty(getTitleString(data))){
                title.setVisibility(View.GONE);
            }else{
                title.setVisibility(View.VISIBLE);
                title.setText(getTitleString(data));
            }

        }

        if(!TextUtils.isEmpty(data.getVideoCoverUrl())){
            gsyVideoPlayer.loadCoverImage(data.getVideoCoverUrl(),0);
        }

        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
               // .setThumbImageView(pic)
                .setUrl(data.getVideoUrl())
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(tag)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(getAdapterPosition())
                .build(gsyVideoPlayer);


        //增加title

        gsyVideoPlayer.setTitleVisible(View.GONE);

        //设置返回键
        gsyVideoPlayer.setBackButtonVisible(View.GONE);

        //设置全屏按键功能
        if(gsyVideoPlayer.getFullscreenButton() != null){
            gsyVideoPlayer.getFullscreenButton().setOnClickListener(v -> resolveFullBtn(gsyVideoPlayer));
        }




    }
    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(itemView.getContext(), true, true);
    }


}
