package com.mr.k.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

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
    private static final int INDICATOR_MODE_CIRCLE = 1;
    private static final int INDICATOR_MODE_PROGRESS = 2;

    private static final boolean INDICATOR_ENABLE = true; // 默认显示 指示器
    private static final int INDICATOR_COLOR = Color.WHITE; //
    private static final int INDICATOR_HEIGHT_DP = 6; //
    private static final int INDICATOR_SELECT_COLOR = Color.BLACK;
    private static final int INDICATOR_MARGIN_END_DP = 15;

    private static final int TITLE_SIZE_DP = 16;
    private static final int TITLE_COLOR = Color.WHITE;

    private static final int TITLE_MARGIN_START_DP = 15;
    private static final int TITLE_MARGIN_END_DP = 15;
    private static final int TITLE_MARGIN_TOP_DP = 12;
    private static final int TITLE_MARGIN_BOTTOM_DP = 12;


    private ViewPager mViewPager;
    private KBannerAdapter mAdapter;
    private OnItemClickListener mItemClickListener;
    private Indicator mIndicator;
    private TextView mTitle;
    private ImageView mMask;
    private List mBannerData;

    private int mIndicatorStyle;

    private int mIndicatorColor; //
    private int mIndicatorSelectColor; //
    private int mIndicatorHeight; //
    private int mIndicatorMarginEnd;


    private int mTitleColor;
    private int mTitleSize;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private int mTitleMarginEnd;
    private int mTitleMarginBottom;
    int id = 10000;


    private boolean mIsManualScroll; // 用来表示手动滑动
    private boolean mIndicatorEnable; // 是否显示指示器

    public KBanner(Context context) {
        this(context, null);
    }

    public KBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KBanner);


        mIndicatorStyle = typedArray.getInt(R.styleable.KBanner_bannerIndicatorStyle,INDICATOR_MODE_CIRCLE);

        mIndicatorEnable = typedArray.getBoolean(R.styleable.KBanner_bannerIndicatorEnable, INDICATOR_ENABLE);
        mIndicatorHeight = (int) typedArray.getDimension(R.styleable.KBanner_bannerIndicatorHeight, Utils.dip2px(context, INDICATOR_HEIGHT_DP));
        mIndicatorColor = typedArray.getColor(R.styleable.KBanner_bannerIndicatorColor, INDICATOR_COLOR);
        mIndicatorSelectColor = typedArray.getColor(R.styleable.KBanner_bannerIndicatorSelectColor, INDICATOR_SELECT_COLOR);
        mIndicatorMarginEnd = (int) typedArray.getDimension(R.styleable.KBanner_bannerIndicatorMarginEnd, dp2pX(INDICATOR_MARGIN_END_DP));


        mTitleColor = typedArray.getColor(R.styleable.KBanner_bannerTitleTextColor, TITLE_COLOR);
        mTitleSize = (int) typedArray.getDimension(R.styleable.KBanner_bannerTitleTextSize, Utils.dip2px(context, TITLE_SIZE_DP));

        mTitleMarginStart = (int) typedArray.getDimension(R.styleable.KBanner_bannerTitleMarginStart, dp2pX(TITLE_MARGIN_START_DP));
        mTitleMarginEnd = (int) typedArray.getDimension(R.styleable.KBanner_bannerTitleMarginEnd, dp2pX(TITLE_MARGIN_END_DP));
        mTitleMarginBottom = (int) typedArray.getDimension(R.styleable.KBanner_bannerTitleMarginBottom, dp2pX(TITLE_MARGIN_BOTTOM_DP));
        mTitleMarginTop = (int) typedArray.getDimension(R.styleable.KBanner_bannerTitleMarginTop, dp2pX(TITLE_MARGIN_TOP_DP));

        typedArray.recycle();
        initView();

    }

    private int dp2pX(int dp){
        return Utils.dip2px(getContext(), dp);
    }

    public void setData(List data) {
        mBannerData = data;
        mIndicator.setCount(data.size());

    }

    public <T> void setAdapter(KBannerAdapter<T> adapter) {
        mAdapter = adapter;

        mViewPager.setAdapter(new BannerPagerAdapter());

        int count = mViewPager.getAdapter().getCount();

        int currentItem = count / 2; // 取最大值的一半

        int m = currentItem % mBannerData.size(); // 用最大值的一半对size 去余数

        currentItem = currentItem - m; // 减去余数，让第一次进来定位到 0 个

        mViewPager.setCurrentItem(currentItem);
        mIndicator.setCurrentItem(currentItem, currentItem % mBannerData.size()); // 选中第 0 个
    }


    public void setOnItemClickListener(OnItemClickListener clickListener){
        mItemClickListener = clickListener;

    }
    private void initView() {
        // 第一步先把viewpager 搞出来
        initViewPager();

        initTitleMask();

        initIndicator();

        initTitle();
    }

    private void initTitleMask(){
        mMask = new ImageView(getContext());
        mMask.setId(id++);



       // 计算 mask  高度

        Paint paint = new Paint();
        paint.setTextSize(mTitleSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        int titleContentHeight = (int) Math.abs(fm.ascent);


        mMask.setBackgroundColor(Color.parseColor("#40000000"));
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.connect(mMask.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(mMask.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(mMask.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.constrainWidth(mMask.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainHeight(mMask.getId(), titleContentHeight + mTitleMarginBottom + mTitleMarginTop);
        addView(mMask);

        constraintSet.applyTo(this);

    }

    private void initTitle(){

        mTitle = new TextView(getContext());
        mTitle.setId(id++);

        mTitle.setTextColor(mTitleColor);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        mTitle.setMaxLines(1);
        mTitle.setEllipsize(TextUtils.TruncateAt.END);


        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        constraintSet.connect(mTitle.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START,mTitleMarginStart);
        if(mIndicatorStyle == INDICATOR_MODE_CIRCLE){
            constraintSet.connect(mTitle.getId(), ConstraintSet.END, ((View)mIndicator).getId(), ConstraintSet.START,mTitleMarginEnd);
        }else{
            constraintSet.connect(mTitle.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END,mTitleMarginEnd);
        }
        constraintSet.connect(mTitle.getId(), ConstraintSet.BOTTOM,mMask.getId()  , ConstraintSet.BOTTOM);
        constraintSet.connect(mTitle.getId(), ConstraintSet.TOP,mMask.getId()  , ConstraintSet.TOP);

        constraintSet.constrainWidth(mTitle.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainHeight(mTitle.getId(), ConstraintSet.WRAP_CONTENT);


        addView(mTitle);

        constraintSet.applyTo(this);

        mTitle.setText("Title.......");


    }

    private void initIndicator() {

        if(mIndicatorStyle == INDICATOR_MODE_CIRCLE){
            mIndicator = new CircleIndicator(getContext(), mIndicatorHeight/2,mIndicatorColor,mIndicatorSelectColor);
        }else {
           mIndicator = new ProgressIndicator(getContext(),mIndicatorColor,mIndicatorSelectColor);
           mIndicator.setHeight(mIndicatorHeight);
        }


        mIndicator.setId(id++);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);


        if(mIndicatorStyle == INDICATOR_MODE_CIRCLE){
            constraintSet.connect(mIndicator.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, mIndicatorMarginEnd);
            constraintSet.connect(mIndicator.getId(), ConstraintSet.BOTTOM, mMask.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(mIndicator.getId(), ConstraintSet.TOP, mMask.getId(), ConstraintSet.TOP);
            constraintSet.constrainWidth(mIndicator.getId(), ConstraintSet.WRAP_CONTENT);// 宽高没有意义，但是不设置的话约束条件不可用
            constraintSet.constrainHeight(mIndicator.getId(), ConstraintSet.WRAP_CONTENT);// 宽高没有意义，但是不设置的话约束条件不可用
        }else{
            constraintSet.connect(mIndicator.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            constraintSet.connect(mIndicator.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            constraintSet.constrainWidth(mIndicator.getId(), ConstraintSet.MATCH_CONSTRAINT);
            constraintSet.constrainHeight(mIndicator.getId(), ConstraintSet.WRAP_CONTENT);
        }



        addView(((View)mIndicator));

        constraintSet.applyTo(this);

        if(!mIndicatorEnable){
            ((View)mIndicator).setVisibility(GONE);
        }
    }


    private void initViewPager() {


        mViewPager = new ViewPager(getContext());
        mViewPager.setId(id++);
       // mViewPager.setBackgroundColor(Color.RED);
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
                mIndicator.onItemSelect(position);
                if(mAdapter != null){
                    mTitle.setText(mAdapter.getTitleString(mBannerData.get(getDataPosition(position)),getDataPosition(position)));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mIsManualScroll = false;
                }
            }
        });


    }


    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startLoop();
        } else {
            stopLoop();
        }
    }

    private int getDataPosition(int position){
        return position % mBannerData.size();
    }

    // 开启循环轮播
    private void startLoop() {
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
    private void stopLoop() {
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
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                // 手指按下是停止轮播
                stopLoop();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                mIsManualScroll = true;
            }
            case MotionEvent.ACTION_UP: {
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

    public class BannerPagerAdapter extends PagerAdapter {

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
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            if (mAdapter != null) {
                mAdapter.fillBannerItemData(KBanner.this, imageView, mBannerData.get(position % mBannerData.size()), position % mBannerData.size());
            }

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mItemClickListener != null){
                        mItemClickListener.onClick(mBannerData.get(position % mBannerData.size()),position % mBannerData.size());
                    }
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }


    public interface KBannerAdapter<T> {

        void fillBannerItemData(KBanner banner, ImageView imageView, T data, int position);
        String getTitleString(T data,int position);
    }

    public interface OnItemClickListener<T>{
        void onClick(T data, int position);
    }

}
