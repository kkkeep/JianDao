package com.jy.jiandao.splash;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jy.jiandao.R;
import com.mr.k.libmvp.widget.CommonPopView;

public class AgreementPopView extends CommonPopView {

    private TextView mTvStop;
    private TextView mTvAgree;

    private OnClickListener mOnClickListener;

    public AgreementPopView(Context context) {
        super(context);

        setOnBackKeyDismiss(false);

        setTouchOutsideDismiss(false);

        View view  = LayoutInflater.from(context).inflate(R.layout.layout_splash_pop,null);

        mTvAgree = view.findViewById(R.id.splash_pop_btn_agree);
        mTvStop = view.findViewById(R.id.splash_pop_btn_stop);


        mTvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickListener != null){
                    mOnClickListener.onAgree(true);
                }

                dismiss();

            }
        });

        mTvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mOnClickListener != null){
                    mOnClickListener.onAgree(false);
                }

                dismiss();

            }
        });


        initContent(view);

        setContentView(view);


    }


    private void initContent(View root){

        String content = root.getResources().getString(R.string.text_service_agreement_content);

        String s1 =  root.getResources().getString(R.string.text_service_agreement);
        String s2 =  root.getResources().getString(R.string.text_service_privacy_policy);


        int start  = content.indexOf(s1);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);


        spannableStringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if(mOnClickListener != null){
                    mOnClickListener.onTextClick(1);
                }
               // dismiss();

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#4A90E2"));
                ds.setUnderlineText(false);
            }
        },start,start+s1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        start = content.indexOf(s2);

        spannableStringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if(mOnClickListener != null){
                    mOnClickListener.onTextClick(2);
                }
               // dismiss();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#4A90E2"));
                ds.setUnderlineText(false);
            }
        },start,start+s2.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);


        TextView textView = root.findViewById(R.id.splash_pop_tv_content);
        textView.setMovementMethod(LinkMovementMethod.getInstance());//一定要加上这一行，不然没有点击效果

        textView.setHighlightColor(Color.TRANSPARENT);

        textView.setText(spannableStringBuilder);


    }



    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface OnClickListener {

        void onAgree(boolean agree);

        void onTextClick(int position);
    }
}
