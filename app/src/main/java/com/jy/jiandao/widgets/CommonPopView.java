package com.jy.jiandao.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;


/**
 * popupwindow 底层实际上是通过  windowmanager 添加 一个 叫 DecorView（FrameLayout）的方式实现的。
 *
 * popupwindow 一般设计到三个 view,{1.mDecorView(FrameLayout),2.mBackgroundView(FrameLayout),3.mContentView(我们要显示的layout)}
 *
 * 1 mDecorView： popupWindow 的根view，它的大小就是通过 设置popouWindow 的 宽高来决定的（setHeight，setWidth）
 *
 * 2 mBackgroundView：它不一定会创建，取决于 是否调用了 setBackgroundDrawable 给  popupWindow 设置背景，如果设置了那么会创建一个 mBackgroundView
 * 然后添加到mDecorView 上，如下：
 *
 *  if (mBackground != null) { // 是否设置里背景
 *             mBackgroundView = createBackgroundView(mContentView);
 *             mBackgroundView.setBackground(mBackground);
 *         } else {
 *             mBackgroundView = mContentView;
 *   }
 *
 *   //把我们的 mContentView 的传进来 创建 mBackgroundView
 *
 *    private PopupBackgroundView createBackgroundView(View contentView) {
 *         final ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
 *         final int height;
 *         if (layoutParams != null && layoutParams.height == WRAP_CONTENT) {
 *             height = WRAP_CONTENT;
 *         } else {
 *             height = MATCH_PARENT;
 *         }
 *
 *         final PopupBackgroundView backgroundView = new PopupBackgroundView(mContext);
 *         final PopupBackgroundView.LayoutParams listParams = new PopupBackgroundView.LayoutParams(
 *                 MATCH_PARENT, height);
 *
 *         // 把我们的 mContentView 添加到 mBackgroundView上面,
 *         // mContentView的宽度是 MATCH_PARENT（和mBackgroundView的宽度一致）。
 *         // mContentView的高度 取决于  mContentView.getLayoutParams() ，要么是MATCH_PARENT，要么是 WRAP_CONTENT
 *         backgroundView.addView(contentView, listParams);
 *
 *         return backgroundView;
 *     }
 *
 *   mDecorView = createDecorView(mBackgroundView);
 *
 *  // 通过把mBackgroundView 传进来 创建 mDecorView，
 *   private PopupDecorView createDecorView(View contentView) {
 *         final ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
 *         final int height;
 *
 *         // 如果 mBackgroundView 的 layoutParams ！= null && layoutParams.height == WRAP_CONTENT 时，高度为 wrap_content,否则为 match_parent
 *         if (layoutParams != null && layoutParams.height == WRAP_CONTENT) {
 *             height = WRAP_CONTENT;
 *         } else {
 *             height = MATCH_PARENT;
 *         }
 *
 *         final PopupDecorView decorView = new PopupDecorView(mContext);
 *         // 把 mBackgroundView添加到了 mDecorView上
 *         // mBackgroundView 的宽度是 MATCH_PARENT（和 mDecorView 一样宽，也就是说等于 setWidth的值）
 *
 *         //mBackgroundView  的高度 取决于mBackgroundView.getLayoutParams(),要么是MATCH_PARENT，要么是 WRAP_CONTENT
 *
 *         decorView.addView(contentView, MATCH_PARENT, height);
 *         decorView.setClipChildren(false);
 *         decorView.setClipToPadding(false);
 *
 *         return decorView;
 *     }
 *
 *
 *
 *
 * 3 mContentView，这就是我们调用popupwindow 的 setContentView 方法传进去我们要显示的内容view
 *
 *
 * 如果设置了pop 的backgrounddrawable 那么创建顺序是
 *
 *  根据 contentview 创建 backgroundview,再根据backgroundview 创建 decorview，最后 windowmanager add  decorview
 *
 *
 * 如果没有设置pop 的backgrounddrawable的背景，那么创建顺序是
 *
 * 直接把 contentview 赋值给 backgroundview。 更具 backgroundview 创建 decorview ，最后 windowmanager add  decorview
 *
 *
 */

public class CommonPopView extends PopupWindow {



    public CommonPopView(Context context) {
        super(context);



        init(context);
    }


    protected void init(Context context){

        //setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        //setWidth(WindowManager.LayoutParams.MATCH_PARENT);


        /**
         * 设置 popupwindow 的宽度和高度
         *
         * 注意： popupwindow 实际是上通过 windowmanager 添加 一个 叫 DecorView（FrameLayout）的方式实现的。
         *
         *  如果 setBackgroundDrawable 设置了 背景，那么 还会创建一个 backgroundView(FrameLayout)，然后再把我们自己的 contentView 添加到
         *   backgroundView 上，最后把 backgroundView 添加到DecorView。backgroundView 的宽度是 Match_parent,也就是说和 DecorView 一样宽，
         *   高度由 contentView 的 layoutparams 参数决定，如果 contentView layoutparams ！= null &&  layoutparams.height == wrap_content，
         *   那么 backgroundView 的高度就是 wrap_content, 否则就是 match_parent.
         *
         * private PopupBackgroundView createBackgroundView(View contentView) {
         *         final ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
         *         final int height;
         *         if (layoutParams != null && layoutParams.height == WRAP_CONTENT) {
         *             height = WRAP_CONTENT;
         *         } else {
         *             height = MATCH_PARENT;
         *         }
         *
         *         final PopupBackgroundView backgroundView = new PopupBackgroundView(mContext);
         *         final PopupBackgroundView.LayoutParams listParams = new PopupBackgroundView.LayoutParams(
         *                 MATCH_PARENT, height);
         *         backgroundView.addView(contentView, listParams);
         *
         *         return backgroundView;
         *     }
         *
         *
         *   如果没有设置setBackgroundDrawable，那么不会创建 backgroundView(FrameLayout)，直接把 contentView 添加到DecorView（FrameLayout）
         *
         *
         *   我们调用setHeight 和 setWidth 就是给 DecorView 这只的宽高。
         */

        setHeight(500); //
        setWidth(500);

        setBackgroundDrawable(new ColorDrawable());

        backgroundAlpha((Activity) context,0.9f);

        // 默认 popwindow 是不获取焦点的，因此按返回键是没有效果。但是点击 popupwindow 以外的区域 会自动关闭pop
        setFocusable(true);





    }






    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
