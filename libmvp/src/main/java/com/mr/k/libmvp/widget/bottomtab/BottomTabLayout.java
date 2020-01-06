package com.mr.k.libmvp.widget.bottomtab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

/*
 * created by Cherry on 2020-01-06
 **/
public class BottomTabLayout extends ConstraintLayout {

    private OnTabSelectListener mOnTabSelectListener;

    public BottomTabLayout(Context context) {
        super(context);
    }

    public BottomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.mOnTabSelectListener = onTabSelectListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View child;
        for(int i = 0; i < getChildCount(); i++){
            child = getChildAt(i);

            if(!(child instanceof BottomTab)){
                continue;
            }

            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomTab tab = (BottomTab) v;
                    tab.select(true);
                    unSelectOther(tab);
                    if(mOnTabSelectListener != null){
                        mOnTabSelectListener.select(indexOfChild(v));
                    }
                }
            });
        }
    }

    /**
     * position 必须重1 开始
     * @param position
     */
    public void select(int position){
        BottomTab tab = (BottomTab) getChildAt(position);
        tab.getOnClickListener().onClick(tab);
    }



    private void unSelectOther(BottomTab tab) {
        View child;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            if(child instanceof BottomTab && child != tab && ((BottomTab) child).isSelect()){
                ((BottomTab) child).select(false);
            }

        }
    }


    public interface OnTabSelectListener{
        void select(int position);
    }
}
