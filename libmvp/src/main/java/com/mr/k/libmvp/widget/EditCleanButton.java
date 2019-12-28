package com.mr.k.libmvp.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/*
 * created by Cherry on 2019-12-26
 **/
public class EditCleanButton extends AppCompatImageView {

    private EditText mEditText;
    public EditCleanButton(Context context) {
        this(context,null);
    }

    public EditCleanButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EditCleanButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickListener();
    }



    private void setClickListener(){
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditText != null){
                    mEditText.setText(null);
                }
            }
        });
    }


    public void bindEditText(EditText editText){

        mEditText = editText;

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switchVisible();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        switchVisible();
    }

    private void switchVisible(){
        if(TextUtils.isEmpty(mEditText.getText().toString())){
            EditCleanButton.this.setVisibility(GONE);
        }else{
            EditCleanButton.this.setVisibility(VISIBLE);
        }
    }

}
