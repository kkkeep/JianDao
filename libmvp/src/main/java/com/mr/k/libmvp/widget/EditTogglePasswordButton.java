package com.mr.k.libmvp.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.mr.k.libmvp.R;


/*
 * created by Cherry on 2020-01-02
 **/
public class EditTogglePasswordButton extends AppCompatImageView {

    private EditText mEditText;

    private boolean isShow;


    public EditTogglePasswordButton(Context context) {
        this(context,null);
    }

    public EditTogglePasswordButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EditTogglePasswordButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickListener();
    }


    private void setClickListener(){
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditText != null){
                    //mEditText.setText(null);
                    toggle();
                }
            }
        });
    }

    public void bindEditText(EditText editText){

        mEditText = editText;
        if(TextUtils.isEmpty(mEditText.getText().toString().trim())){
            setVisibility(GONE);
        }else{
            setVisibility(VISIBLE);
        }

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

              //  switchVisible();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(mEditText.getText().toString().trim())){
                    setVisibility(GONE);
                }else{
                    setVisibility(VISIBLE);
                }
            }
        });

       // switchVisible();
    }

    public void toggle(){
        if(isShow){
           mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
           isShow = false;
           setImageResource(R.drawable.ic_mvp_eyes_close);
        }else{
            mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isShow = true;
            setImageResource(R.drawable.ic_mvp_eyes_open);
        }
    }

}
