package com.mr.k.libmvp.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.IntDef;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import androidx.viewpager.widget.PagerAdapter;

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
    public static final int MODE_ERROR = 0x102;
    private static final String TAG = "LoadingView";

    private ViewGroup mParent;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_TRANSPARENT_BACKGROUND, MODE_WHITE_BACKGROUND})
    public @interface Mode {
    }

    public ImageView mGifLayout; // gif 动画的半透明背景
    public GifView mGifView;

    private Group mErrorLayout;

    private int mCurrentMode;


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


    /**
     * 循环遍历parent 里面的所有子控件，如果发现loadingview 已经是 parent 这个 viewGroup 的孩子，
     * 那么就直接把loading view  return 出去，如果找遍了所有子控件，没有loading view 那么就
     * 通过 LayoutInflater  inflate 一个出来，然后保存期 parent 到自己属性里面，并返回
     *
     * @param parent
     * @return
     */

    public static LoadingView inject(ViewGroup parent) {
        String parentName = parent.getClass().getSimpleName();

        if (parentName.equals(FrameLayout.class.getSimpleName()) ||
                parentName.equals(RelativeLayout.class.getSimpleName()) ||
                parentName.equals(ConstraintLayout.class.getSimpleName()) ||
                parentName.equals(ContentFrameLayout.class.getSimpleName())) {

            View child;
            for (int i = parent.getChildCount()-1; i >= 0; i--) {
                child = parent.getChildAt(i);
                if (child instanceof LoadingView) {
                    return (LoadingView) child;
                }
            }

            LoadingView loadingView = (LoadingView) LayoutInflater.from(parent.getContext()).inflate(R.layout.mvp_layout_loading, parent, false);

            loadingView.mParent = parent;

            return loadingView;
        } else {
            throw new IllegalArgumentException("Loading view 必须被添加到 FrameLayout,RelativeLayout 或者 ConstraintLayout 上");
        }
    }


    public void showMode(@Mode int mode) {

        if (this.getParent() == null) {
            String parentName = mParent.getClass().getSimpleName();
            if (parentName.equals(FrameLayout.class.getSimpleName()) || parentName.equals(RelativeLayout.class.getSimpleName()) || parentName.equals(ContentFrameLayout.class.getSimpleName())) {
                mParent.addView(this);
            } else {
                mParent.addView(this);
                ConstraintSet constraintSet = new ConstraintSet();
                this.setId(100000);
                constraintSet.clone((ConstraintLayout) mParent);
                constraintSet.connect(this.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(this.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                constraintSet.connect(this.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                constraintSet.connect(this.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                constraintSet.constrainWidth(this.getId(), ConstraintSet.MATCH_CONSTRAINT);
                constraintSet.constrainHeight(this.getId(), ConstraintSet.MATCH_CONSTRAINT);
                constraintSet.applyTo((ConstraintLayout) mParent);
            }
        }


        if(mCurrentMode == mode){
            return;
        }

        mCurrentMode = mode;

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

        mIsShow = true;

    }

    private boolean mIsShow;
    protected boolean isShow(){
        return mIsShow;
    }


    public void onError() {
        mCurrentMode = MODE_ERROR;
        setBackgroundColor(Color.WHITE);
        mGifView.setVisibility(View.GONE);
        mGifLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(VISIBLE);
    }

    public void close() {
        if(this.getParent() != null){
            ((ViewGroup) getParent()).removeView(this);
            mIsShow = false;
        }
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
