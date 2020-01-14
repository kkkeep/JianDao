package com.mr.k.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ProgressBar;

/*
 * created by Cherry on 2020-01-14
 **/
public class ProgressIndicator extends ProgressBar implements Indicator {

    private int height;
    public ProgressIndicator(Context context,int color,int selectedColor) {
        super(context,null,android.R.style.Widget_DeviceDefault_Light_ProgressBar_Horizontal);
        setBackgroundColor(color);
        setProgressDrawable(new ClipDrawable(new ColorDrawable(selectedColor), Gravity.LEFT, ClipDrawable.HORIZONTAL));
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    public void setHeight(int height) {
        this.height = height;

        requestLayout();
    }



    private int count;
    @Override
    public void setCount(int count) {
        this.count = count;
        setMax(count);
    }

    @Override
    public void setCurrentItem(int position, int index) {

    }

    @Override
    public void onItemSelect(int position) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N){
            setProgress(position % count + 1,true);
        }else{
            setProgress(position % count + 1);
        }
    }
}
