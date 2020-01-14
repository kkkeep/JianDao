package com.mr.k.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/*
 * created by Cherry on 2020-01-13
 **/
public class CircleIndicator extends View implements Indicator{

    private static final int MAX_COUNT = 8;
    private Paint mPaint;
    private int radio; // 圆点半径
    private int color;
    private int selectedColor;
    private int width; // view 的 宽度
    private int height; // view 的高度

    private int count; // 有多少个小圆点,但是不超过最大值
    private int currentPosition;
    private int originalCount; // 实际小圆点应该有的个数

    public CircleIndicator(Context context,int radio,int color,int selectedColor) {
        super(context);
        this.radio = radio;
        this.color = color;
        this.selectedColor = selectedColor;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setHeight(int height) {
        this.radio = height / 2;
    }


    public void setCount(int count) {
        this.count = Math.min(count, MAX_COUNT);
        this.originalCount = count;

        width = (this.count * 2 * radio) + (this.count - 1) * radio;
        height = 2 * radio;
        requestLayout();

    }

    /**
     *
     * @param position 表示viewpager 真实的position ,不是取余后的
     * @param index 表示viewpager 真实的position 取余后的，也就是第一次进来显示第几个
     */
    public void setCurrentItem(int position, int index) {
        currentPosition = Integer.MAX_VALUE / 2;
        int m = currentPosition % count;
        int n = m - index;
        currentPosition = currentPosition - n;
        lastPosition = position;
        invalidate();
    }


    private int lastPosition;

    public void onItemSelect(int position) {
        if (position > lastPosition) {
            currentPosition++;
        } else {
            currentPosition--;
        }

        lastPosition = position;

        // init();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 0 * (2 * radio) + 0 (radio) + radio    1 * (2 * radio) + 1* radio + radio,3  2 * (2 * radio) + 2 * radio + radio

       // canvas.drawColor(Color.BLUE);
        int startX = 0;
        for (int i = 0; i < count; i++) {
            startX = i * (2 * radio) + i * radio + radio;

            if (i == currentPosition % count) {
                mPaint.setColor(selectedColor);
            } else {
                mPaint.setColor(color);
            }
            canvas.drawCircle(startX, radio, radio, mPaint);

        }
    }
}
