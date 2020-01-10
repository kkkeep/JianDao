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

                    tab.select(true); // 让自己变成选中

                    unSelectOther(tab);// 让其他几个变成未选中
                    if(mOnTabSelectListener != null){
                        mOnTabSelectListener.select(v.getId());
                    }
                }
            });
        }
    }

    /**
     * 让指定id 的BottomTab 变为选中
     */
    public void select(int id){
        BottomTab tab = findViewById(id);
        tab.getOnClickListener().onClick(tab);
    }

    /**
     * 为指定 id 的 BottomTab 设置 title
     * @param id
     * @param title
     */
    public void setTitle(int id ,String title){
        BottomTab tab = findViewById(id);
        tab.setTitle(title);
    }


    private void unSelectOther(BottomTab tab) {
        View child;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            // 变量所有是BottomTab 的自view，除开自己以外，并且是选中状态的 view,才让他变成为选中
            if(child instanceof BottomTab && child != tab && ((BottomTab) child).isSelect()){
                ((BottomTab) child).select(false);
            }

        }
    }


    public interface OnTabSelectListener{
        void select(int id);
    }
}
