package com.jy.jiandao.detail.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jy.jiandao.widgets.CommonPopView;

public class CommentPopView extends CommonPopView {


    public CommentPopView(Context context) {
        super(context);
    }

    @Override
    protected void init(Context context) {
        super.init(context);


        ImageView contentView = new ImageView(context);



       // contentView.setLayoutParams(new ViewGroup.LayoutParams(400, ViewGroup.Layou));


        contentView.setBackgroundColor(Color.RED);

        setContentView(contentView);



        contentView.requestLayout();

    }
}
