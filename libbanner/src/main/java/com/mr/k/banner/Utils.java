package com.mr.k.banner;

import android.content.Context;

/*
 * created by Cherry on 2020-01-13
 **/
 class Utils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
