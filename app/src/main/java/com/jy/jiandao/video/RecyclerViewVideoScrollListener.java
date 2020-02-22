package com.jy.jiandao.video;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.jiandao.ad.NewsAdVideoHolder;
import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.home.recommend.page.NewsPageAdapter;
import com.mr.k.libmvp.base.BaseRecyclerAdapter;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

public class RecyclerViewVideoScrollListener extends RecyclerView.OnScrollListener {

    int firstVisibleItem, lastVisibleItem;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        // 在 recycler view 滑动停止后，去遍历屏幕上所有可见的item，看这些 item 中是否有视频广告，如果有，并且视频广告view 全部出现在可见区域内，那么播放广告

        if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 滑动停止后
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition(); // 第一个看见item 的 position
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition(); // 第最后一个可见 item 的 position


            // 遍历第一个可见 item 和 最后一个可见item 之间的 是否有 视频广告item


            for (int i = firstVisibleItem; i <= lastVisibleItem; i++) {

                BaseRecyclerAdapter adapter = (BaseRecyclerAdapter) recyclerView.getAdapter();

                if (adapter.isAdVideo(i)) { // 如果是视频广告

                    // 判断视频广告的item 是否全部出现在可见范围内

                    View itemView = linearLayoutManager.findViewByPosition(i);// 根据 position  找到 item view;


                    RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(itemView);

                    if (holder instanceof NewsAdVideoHolder) {


                        int itemViewTop = itemView.getTop(); // item 在 recycler view 中的 top

                        JDVideo jdVideo = ((NewsAdVideoHolder) holder).getGsyVideoPlayer();

                        int videoHeight = jdVideo.getHeight();// 视频空间的高度

                        int videoTopYInRecyclerView = itemViewTop + jdVideo.getTop(); // video view 在 recycler view （爷爷容器） 的 top 值

                        if (videoTopYInRecyclerView < 0) { // 如果video view 顶部有一部分不在recycler view 里面
                            if (Math.abs(videoTopYInRecyclerView) <= videoHeight / 3) { // 顶部不在 recycler  view 连的部分 小于 video view 高度的 三分之一
                                ((NewsAdVideoHolder) holder).play();
                            }
                        } else {

                            int videoBottomYInRecyclerView = itemView.getTop() + jdVideo.getBottom(); // video view 在 recycler vie 中的 bottom

                            int excess = videoBottomYInRecyclerView - recyclerView.getHeight(); // video view 在 recycler view 中超出部分

                            if (excess < videoHeight / 3) { //  如果video view 整个都在recycler view  里面或者 video 有一部分已经超出了 recycler view 下面一部分,但是超出部分不足 video view 高度的三分之一
                                ((NewsAdVideoHolder) holder).play();
                            }

                        }

                    }


                }
            }


        }


    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        //大于0说明有播放
        if (GSYVideoManager.instance().getPlayPosition() >= 0) {
            //当前播放的位置
            int position = GSYVideoManager.instance().getPlayPosition();


            //对应的播放列表TAG
            if (GSYVideoManager.instance().getPlayTag().equals(NewsPageAdapter.VIDEO_PLAY_TAG)
                    && (position < firstVisibleItem || position > lastVisibleItem)) { // video item  已经不在屏幕上可见了

                if (!GSYVideoManager.isFullState((Activity) recyclerView.getContext())) {
                    GSYVideoManager.releaseAllVideos();
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            } else if (position == firstVisibleItem || position == lastVisibleItem) { // video item  是第一个可见或者最后一个可见item,那么就要确保 video view 有三分之2 在里面

                View itemView = linearLayoutManager.findViewByPosition(position);// 根据 position  找到 item view;
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(itemView);

                int itemViewTop = itemView.getTop(); // item 在 recycler view 中的 top

                JDVideo jdVideo = ((VideoHolder) holder).getGsyVideoPlayer();

                int videoHeight = jdVideo.getHeight();// 视频空间的高度

                int videoTopYInRecyclerView = itemViewTop + jdVideo.getTop(); // video view 在 recycler view （爷爷容器） 的 top 值

                if (videoTopYInRecyclerView < 0) {  // 如果video view 顶部有一部分不在recycler view 里面
                    if (Math.abs(videoTopYInRecyclerView) > videoHeight / 3) { // 顶部不在 recycler  view 连的部分 大于 video view 高度的 三分之一
                        GSYVideoManager.releaseAllVideos();
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                } else {
                    int videoBottomYInRecyclerView = itemView.getTop() + jdVideo.getBottom(); // video view 在 recycler vie 中的 bottom

                    int excess = videoBottomYInRecyclerView - recyclerView.getHeight(); // video view 在 recycler view 中超出部分

                    if (excess > videoHeight / 3) { //  video 有一部分已经超出了 recycler view 下面一部分,并且超出部分 大于了三分之一一，说明留在屏幕里面可见的部分补助 三分之二
                        GSYVideoManager.releaseAllVideos();
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }

            }
        }

    }
}