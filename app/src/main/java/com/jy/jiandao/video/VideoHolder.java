package com.jy.jiandao.video;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jy.jiandao.GlideApp;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.NewsData;
import com.mr.k.libmvp.base.BaseAdapterHolder;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public abstract class VideoHolder extends BaseAdapterHolder<NewsData.News> {


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


    public String getTitleString(NewsData.News data){
        return data.getTheme();
    }



    @Override
    public void bindData(NewsData.News data) {
        super.bindData(data);

        if(title != null){
            if(TextUtils.isEmpty(getTitleString(data))){
                title.setVisibility(View.GONE);
            }else{
                title.setVisibility(View.VISIBLE);
                title.setText(getTitleString(data));
            }

        }

        if(!TextUtils.isEmpty(data.getImageUrl())){
            gsyVideoPlayer.loadCoverImage(data.getImageUrl(),0);
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
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                          //  GSYVideoManager.instance().setNeedMute(true);
                        }

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                       // GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String)objects[0]);
                    }
                }).build(gsyVideoPlayer);


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
