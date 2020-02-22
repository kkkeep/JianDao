package com.jy.jiandao.ad;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.Ad;
import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.data.entity.RecommendPageData;
import com.jy.jiandao.video.JDVideo;
import com.jy.jiandao.video.VideoHolder;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;

public class NewsAdVideoHolder<T extends BaseNews> extends VideoHolder<T> {


    private CheckBox sound;

    private TextView replay;


    public NewsAdVideoHolder(String tag,@NonNull View itemView) {
        super(tag,itemView);

        sound = itemView.findViewById(R.id.item_ad_video_sound);

        replay = itemView.findViewById(R.id.item_ad_video_replay);



        sound.setOnCheckedChangeListener((buttonView, isChecked) -> GSYVideoManager.instance().setNeedMute(!isChecked));

        replay.setOnClickListener(v -> {

            play();
            replay.setVisibility(View.GONE);

        });
    }

    @Override
    public JDVideo getGsyVideoPlayer() {
        return itemView.findViewById(R.id.item_ad_video);
    }


    @Override
    public TextView getTitleView() {
        return itemView.findViewById(R.id.item_ad_video_tv_title);
    }


    @Override
    public String getTitleString(T data) {
        return data.getAd().getTitle();
    }


    @Override
    public void bindData(T news) {
        Ad ad = news.getAd();
        news.setVideoUrl(ad.getAd_url());
        news.setImageUrl(ad.getVideoImage());
        super.bindData(news);

        sound.setChecked(false);
        replay.setVisibility(View.GONE);

        getGsyVideoPlayer().setVideoAllCallBack(new GSYSampleCallBack(){
            @Override
            public void onPrepared (String url, Object... objects) {
                super.onStartPrepared(url, objects);
                GSYVideoManager.instance().setNeedMute(true); // 开始播放之前，对广告视频进行静音
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);

                // 播放完成后 显示重复播放按钮
                replay.setVisibility(View.VISIBLE);

            }
        });

    }


    public void play(){

        if(!getGsyVideoPlayer().isInPlayingState()){
            getGsyVideoPlayer().startPrepare();
        }

    }
}