package com.jy.jiandao.detail.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;

import com.jy.jiandao.R;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.widget.CommonPopView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CommentPopView extends CommonPopView {


    private OnSendActionListener mActionListener;

    private EditText mEtContent;
    private TextView mTvCancel;
    private TextView mTvSent;
    private String mHintContent;



    public CommentPopView(Context context,String hintContent) {

        super(context);

        mHintContent = hintContent;
    }

    public CommentPopView(Context context) {

        super(context);

        mHintContent = null;
    }
    @Override
    protected void init(Context context) {
        super.init(context);


        View contentView  = LayoutInflater.from(context).inflate(R.layout.layout_comment,null);

        mEtContent = contentView.findViewById(R.id.commentEtContent);
        mTvCancel = contentView.findViewById(R.id.commentTvCancel);
        mTvSent = contentView.findViewById(R.id.commentTvSent);


        mEtContent.requestFocus();




        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        mTvSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = mEtContent.getText().toString();

                if(TextUtils.isEmpty(content)){
                    Toast.makeText(v.getContext(),"内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mActionListener != null){
                    mActionListener.onClick(content);
                    dismiss();
                }
            }
        });

        setContentView(contentView);


        setTouchOutsideDismiss(true);

        setOnBackKeyDismiss(true);



    }

    public void setActionListener(OnSendActionListener actionListener) {
        this.mActionListener = actionListener;
    }

    @Override
    public void dismiss() {
        hideKeyboard(getContentView().getContext());
        super.dismiss();

    }

    public void show(View view){
        if(mHintContent != null){

            mEtContent.setHint("回复：" + mHintContent );
        }else{
            mEtContent.setHint("写评论");
        }

        showAtLocation(view, Gravity.LEFT|Gravity.BOTTOM,0,0);
        showKeyboard(view.getContext());
    }

    private void hideKeyboard (Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
    private void showKeyboard (Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        //imm.showSoftInput(mEtContent,SHOW_FORCED)
    }


    public interface OnSendActionListener{

        void onClick(String content);
    }
}
