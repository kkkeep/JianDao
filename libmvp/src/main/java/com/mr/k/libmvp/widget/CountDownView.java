package com.mr.k.libmvp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.mr.k.libmvp.R;
import com.mr.k.libmvp.Utils.Logger;

/*
 * created by Cherry on 2019-12-31
 **/
public class CountDownView extends AppCompatTextView {
    private static final String TAG= "CountDownView";

    private CountDownTimer mCountDownTimer;

    private String mText; // 没有开始计时显示的文本
    private String mCountDownText; //开始倒计时 显示的文本

    private int mCount; // 倒计数量
    private int mColor;// 没开开始倒计时字体的颜色
    private int mCountDownColor;// 开始倒计时字体的颜色
    private int mInterval;//每隔多少秒递减

    private int mTempCount;


    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);

        mText = getText().toString();
        mCountDownText = typedArray.getString(R.styleable.CountDownView_countDownText);

        mCount = typedArray.getInt(R.styleable.CountDownView_countDownCount, 60);
        mCountDownColor = typedArray.getColor(R.styleable.CountDownView_countDownTextColor, Color.BLACK);
        mColor = getPaint().getColor();
        mInterval = typedArray.getInt(R.styleable.CountDownView_countDownInterval,1);

        typedArray.recycle();

        setOnClickListener(v -> {
            start();
        });
    }


    public void start() {
        start(mCount);
    }

    public void start(int count) {

        if(!isEnabled()){
            return;
        }

       mTempCount = count;
        if(mCountDownTimer == null){

            mCountDownTimer = new CountDownTimer(mCount * 1000,mInterval * 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    //setText(mCountDownText + millisUntilFinished);
                    Logger.d("%s %s",TAG,millisUntilFinished);

                    setText(mCountDownText + mTempCount-- + "s");
                    setEnabled(false);
                    if(mCountDownColor != 0){
                        setTextColor(mCountDownColor);
                    }

                }

                @Override
                public void onFinish() {
                    Logger.d("%s finis",TAG);
                    setEnabled(true);
                    if(mCountDownColor != 0){
                        setTextColor(mColor);
                    }
                    setText(mText);
                }
            };
        }

        mCountDownTimer.start();

    }


    public void stop() {
        if(!isEnabled()){
            return;
        }

        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
        }
    }

}
