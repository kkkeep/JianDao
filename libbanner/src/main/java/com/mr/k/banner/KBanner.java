package com.mr.k.banner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.List;

/*
 * created by Cherry on 2020-01-13
 **/
public class KBanner extends ConstraintLayout {

    private static final int INTERVAL = 3000; //  每个多少秒切换一夜
    private static final int DURATION = 500;  // 每一页切换动画执行的时间

    private ViewPager mViewPager;
    private KBannerAdapter mAdapter;
    CircleIndicator mIndicator;

    private List mBannerData;

    private boolean mIsManualScroll; // 用来表示手动滑动
    int id = 10000;
    public KBanner(Context context) {
        this(context, null);
    }

    public KBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }



    public  void  setData(List data){
        mBannerData = data;
        mIndicator.setCount(data.size());

    }

    public <T> void setAdapter(KBannerAdapter<T> adapter){
        mAdapter = adapter;

        mViewPager.setAdapter(new BannerPagerAdapter());

        int count = mViewPager.getAdapter().getCount();

        int currentItem = count/2;

        int m = currentItem % mBannerData.size();

        currentItem = currentItem -m;

        mViewPager.setCurrentItem(currentItem);
        mIndicator.setCurrentItem(currentItem,currentItem % mBannerData.size()); // 选中第 0 个
    }


    private void initView() {
        // 第一步先把viewpager 搞出来
        initViewPager();

        initIndicator();
    }


    private void initIndicator(){
         mIndicator = new CircleIndicator(getContext());

        mIndicator.setId(id++);
        ConstraintSet constraintSet = new ConstraintSet();

        constraintSet.clone(this);


        constraintSet.connect(mIndicator.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END,Utils.dip2px(getContext(), 15));
        constraintSet.connect(mIndicator.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM,Utils.dip2px(getContext(), 15));
        addView(mIndicator);

        constraintSet.applyTo(this);
    }


    private void initViewPager() {


        mViewPager = new ViewPager(getContext());
        mViewPager.setId(id++);
        mViewPager.setBackgroundColor(Color.RED);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.connect(mViewPager.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.constrainWidth(mViewPager.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainHeight(mViewPager.getId(), ConstraintSet.MATCH_CONSTRAINT);
        addView(mViewPager);
        constraintSet.applyTo(this);

        setViewPagerScroller();


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndicator.onItemSelect(position  );
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE){
                    mIsManualScroll = false;
                }
            }
        });

    }


    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility == VISIBLE){
            startLoop();
        }else{
            stopLoop();
        }
    }

    // 开启循环轮播
    private void startLoop(){
        Log.d("Test", "start");
       getHandler().removeCallbacks(mLoopRunnable);
       getHandler().postDelayed(mLoopRunnable, INTERVAL);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoop();
    }

    // 停止循环轮播
    private void stopLoop(){
        Log.d("Test", "stop");
        getHandler().removeCallbacks(mLoopRunnable);
    }

    private Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d("Test", "switch");
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            getHandler().postDelayed(this, INTERVAL);

        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                // 手指按下是停止轮播
                stopLoop();
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                mIsManualScroll = true;
            }
            case MotionEvent.ACTION_UP:{
                // 手指抬起时继续轮播
                startLoop();
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 通过反射修改 viewpager 自动切换下一页的时间，因为默认的时间只有200ms,太短
     */
    private void setViewPagerScroller() {

        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");

            scrollerField.setAccessible(true);

            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");


            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(getContext(), new LinearInterpolator()) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    int newDuration;
                    // 如果这次是手滑，那么剩下的那一部分滑动的时间我们不修改。就用默认的。
                    if (mIsManualScroll) {
                        newDuration = Math.min(DURATION, duration);
                    } else {
                        // 自动切换时，修改默认切换的时间
                        newDuration = DURATION;
                    }
                    Log.d("Test", "duration = " + newDuration);
                    super.startScroll(startX, startY, dx, dy, newDuration);    // 这里是关键，将duration变长或变短
                }
            };
            scrollerField.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }

    public class BannerPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return object == view;
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            if(mAdapter != null){
                mAdapter.fillBannerItemData(KBanner.this, imageView, mBannerData.get(position % mBannerData.size()), position);
            }
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView)object);
        }
    }



    public interface KBannerAdapter<T>{

        void fillBannerItemData(KBanner banner,ImageView imageView,T data,int position);
    }

}
