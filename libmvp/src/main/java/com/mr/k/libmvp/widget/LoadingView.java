package com.mr.k.libmvp.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IntDef;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;

import com.cunoraz.gifview.library.GifView;
import com.mr.k.libmvp.R;
import com.mr.k.libmvp.Utils.Logger;
import com.mr.k.libmvp.Utils.SystemFacade;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * created by Cherry on 2019-12-23
 **/
public class LoadingView extends ConstraintLayout {

    public static final int MODE_TRANSPARENT_BACKGROUND = 0x100;
    public static final int MODE_WHITE_BACKGROUND = 0x101;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_TRANSPARENT_BACKGROUND, MODE_WHITE_BACKGROUND})
    public @interface Mode {
    }

    public ImageView mGifLayout; // gif 动画的半透明背景
    public GifView mGifView;

    private Group mErrorLayout;


    private static final String TAG = "LoadingView";

    public LoadingView(Context context) {
        super(context);
        Logger.d("%s LoadingView 1", TAG);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Logger.d("%s LoadingView 2", TAG);


    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Logger.d("%s LoadingView 3", TAG);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mGifView = findViewById(R.id.mvp_loading_gif_view);
        mGifLayout = findViewById(R.id.mvp_loading_loading_view_container);
        mErrorLayout = findViewById(R.id.mvp_loading_group_error);
        mErrorLayout = findViewById(R.id.mvp_loading_group_error);

    }

    public void showMode(@Mode int mode) {
        mErrorLayout.setVisibility(GONE);

        if (mode == MODE_WHITE_BACKGROUND) { //  白色背景
            setBackgroundColor(Color.WHITE);
            mGifLayout.setVisibility(View.GONE);

        } else { // 背景透明
            setBackgroundColor(Color.TRANSPARENT);
            mGifLayout.setVisibility(View.VISIBLE);

        }
        resetGifViewSize(mode);
        mGifView.setVisibility(View.VISIBLE);
        mGifView.play();
    }


    public void onError() {
        setBackgroundColor(Color.WHITE);
        mGifView.setVisibility(View.GONE);
        mGifLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(VISIBLE);
    }

    public void close() {

    }


    private void resetGifViewSize(int mode) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        int width = 0;
        if (mode == MODE_TRANSPARENT_BACKGROUND) {
            width = SystemFacade.dp2px(getContext(), 90);
        } else {
            width = SystemFacade.dp2px(getContext(), 133);
        }

        constraintSet.constrainWidth(R.id.mvp_loading_gif_view, width);
        constraintSet.constrainHeight(R.id.mvp_loading_gif_view, width);

        constraintSet.applyTo(this);

    }


}
