package com.mr.k.libmvp.widget.bottomtab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.appcompat.widget.AppCompatImageView;

/*
 * created by Cherry on 2020-01-06
 **/
public class TabIcon extends AppCompatImageView implements Checkable {

    int[] CHECKED_STATE_SET = { android.R.attr.state_checked};

    private boolean isChecked;


    public TabIcon(Context context) {
        super(context);
    }

    public TabIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public int[] onCreateDrawableState(int extraSpace) {


        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;


    }

    @Override
    public void setChecked(boolean checked) {
        if(isChecked != checked){
            isChecked = checked;
            refreshDrawableState();
        }

    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
