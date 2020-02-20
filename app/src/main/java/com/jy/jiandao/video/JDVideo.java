package com.jy.jiandao.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.jy.jiandao.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class JDVideo extends StandardGSYVideoPlayer {

    ImageView mCoverImage;

    String mCoverOriginUrl;

    int mDefaultRes;
    public JDVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public JDVideo(Context context) {
        super(context);
    }

    public JDVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void init(Context context) {
        super.init(context);
        mCoverImage = (ImageView) findViewById(R.id.thumbImage);

        if (mThumbImageViewLayout != null &&
                (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)) {
            mThumbImageViewLayout.setVisibility(VISIBLE);
        }

        startPrepare();
    }

    public void loadCoverImage(String url, int res) {
        if(mCoverImage == null){
            return;
        }
        mCoverOriginUrl = url;
        mDefaultRes = res;
        Glide.with(getContext().getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                                .error(res)
                                .placeholder(res))
                .load(url)
                .into(mCoverImage);




    }


    public void setTitleVisible(int visibility){

        if(mTitleTextView != null){
            mTitleTextView.setVisibility(visibility);
        }

    }

    public void setBackButtonVisible(int visibility){

        if(mBackButton != null){
            mBackButton.setVisibility(visibility);
        }

    }


    @Override
    public void startPrepare() {
        super.startPrepare();
    }
}
