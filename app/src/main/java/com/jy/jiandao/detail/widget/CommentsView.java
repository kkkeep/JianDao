package com.jy.jiandao.detail.widget;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.util.List;

/*
 * created by Cherry on 2019-11-10
 **/
public class CommentsView extends LinearLayout {

    private Context mContext;
    private List<? extends ReplayData> mDatas;
    private onItemClickListener listener;

    public CommentsView(Context context) {
        this(context, null);
    }

    public CommentsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        this.mContext = context;
    }

    /**
     * 设置评论列表信息
     *
     * @param list
     */
    public void setList(List<? extends ReplayData> list) {
        mDatas = list;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void notifyDataSetChanged() {
        removeAllViews();
        if (mDatas == null || mDatas.size() <= 0) {
            return;
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 10);
        for (int i = 0; i < mDatas.size(); i++) {
            View view = getView(i);
            if (view == null) {
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }
            addView(view, i, layoutParams);
        }
    }

    private View getView(final int position) {
        final ReplayData  item = mDatas.get(position);

        boolean hasReply = true;   // 是否有回复

        TextView textView = new TextView(mContext);
        textView.setTextSize(15);
        textView.setTextColor(0xff686868);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        String name =  item.getFromUserName();
        if (hasReply) {
            builder.append(setClickableSpan(name, item));
            builder.append(" 回复 ");
            builder.append(setClickableSpan(item.getToUserName(),item));

        } else {
            builder.append(setClickableSpan(name, item));
        }
        builder.append(" : ");
        builder.append(item.getContent());
        builder.append(" ");
        textView.setText(builder);
        // 设置点击背景色
        textView.setHighlightColor(getResources().getColor(android.R.color.transparent));
       textView.setHighlightColor(0xff000000);


        final CircleMovementMethod method = new CircleMovementMethod(0xffcccccc, 0xffcccccc);
        textView.setMovementMethod(method);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (method.isParseTv()) {
                    if (listener != null) {
                        listener.onItemClick(position, item);
                    }
               // }
            }
        });

        return textView;
    }

    /**
     * 设置评论内容点击事件
     *
     * @param item
     * @param position
     * @return
     */
    public SpannableString setClickableSpanContent(final String item, final int position) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO: 2017/9/3 评论内容点击事件
                //Toast.makeText(mContext, "position: " + position + " , content: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置显示的内容文本颜色
                ds.setColor(0xff686868);
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    /**
     * 设置评论用户名字点击事件
     *
     * @param item
     * @param bean
     * @return
     */
    public SpannableString setClickableSpan(final String item, final ReplayData bean) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO: 2017/9/3 评论用户名字点击事件
                Toast.makeText(mContext, bean.getToUserName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置显示的用户名文本颜色
                ds.setColor(0xff58658D);
                ds.setUnderlineText(false);
            }
        };

        string.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    /**
     * 定义一个用于回调的接口
     */
    public interface onItemClickListener {
        void onItemClick(int position, ReplayData bean);
    }



    public interface ReplayData{

        String getFromUserName();//  谁回复的,比如这条回复是张三 回复的李四，那么返回的就是张三的名字


        String getToUserName(); // 回复的谁，比如这条回复是张三 回复的李四，那么返回的就是李四的名字

        String getContent(); // 回复的内容


    }
}