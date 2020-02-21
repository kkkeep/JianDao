package com.jy.jiandao.home.video;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.VideoPageData;
import com.jy.jiandao.video.JDVideo;
import com.jy.jiandao.video.VideoHolder;
import com.mr.k.libmvp.base.BaseAdapterHolder;
import com.mr.k.libmvp.base.BaseRecyclerAdapter;

public class VideoAdapter extends BaseRecyclerAdapter<VideoPageData.News> {

    private static final String VIDEO_PAGE_PLAY_TAG = "VideoAdapter_play_tag";



    @Override
    public int getItemLayoutId(int viewType) {
         return R.layout.item_recommend_news_video;
    }

    @Override
    public BaseAdapterHolder<VideoPageData.News> createHolder(View itemView) {
        return new VideoNewsHolder(VIDEO_PAGE_PLAY_TAG,itemView);
    }



    public class VideoNewsHolder extends VideoHolder<VideoPageData.News> {


        private TextView description;


        public VideoNewsHolder(String tag, @NonNull View itemView) {
            super(tag, itemView);

            description = itemView.findViewById(R.id.item_news_video_tv_label);
        }

        @Override
        public JDVideo getGsyVideoPlayer() {
            return itemView.findViewById(R.id.item_news_video_gsy_player);
        }

        @Override
        public TextView getTitleView() {
            return itemView.findViewById(R.id.item_news_video_tv_title);
        }


        @Override
        public void bindData(VideoPageData.News data) {
            super.bindData(data);
            description.setText(data.getDescription());
        }
    }
}
