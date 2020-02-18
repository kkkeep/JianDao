package com.jy.jiandao.video;

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

public class VideoHolder extends BaseAdapterHolder<NewsData.News> {

   // private ImageView pic;

    private TextView title;
    private TextView label;

    private String tag;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    private SampleCoverVideo gsyVideoPlayer;


    public VideoHolder(String tag, @NonNull View itemView) {
        super(itemView);

       // pic = itemView.findViewById(R.id.item_news_video_iv_pic);
        title = itemView.findViewById(R.id.item_news_video_tv_title);
        label = itemView.findViewById(R.id.item_news_video_tv_label);

        gsyVideoPlayer = itemView.findViewById(R.id.item_news_video_gsy_player);

        this.tag = tag;

        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    @Override
    public void bindData(NewsData.News data) {
        super.bindData(data);
        //GlideApp.with(itemView).load(data.getImageUrl()).into(pic);
        title.setText(data.getTheme());
        label.setText(data.getColumnName());

        gsyVideoPlayer.loadCoverImage(data.getImageUrl(),0);

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
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(gsyVideoPlayer);
            }
        });



    }
    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(itemView.getContext(), true, true);
    }


}
