package com.jy.jiandao.home.mime;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jy.jiandao.R;
import com.mr.k.libmvp.widget.CommonPopView;

public class HeadPicPopView extends CommonPopView {

    private Button mOpenCamera;

    private Button mOpenGallery;

    private TextView mCancel;


    private OnClickListener mOnClickListener;


    public HeadPicPopView(Context context) {
        super(context);
    }

    @Override
    protected void init(Context context) {
        super.init(context);


        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_head_pic_pop,null);



        mOpenCamera = contentView.findViewById(R.id.mine_pop_btn_camera);

        mOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickListener != null){
                    mOnClickListener.onCamera();
                    dismiss();
                }
            }
        });

        setContentView(contentView);

        setTouchOutsideDismiss(true);
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void show(View view){


        showAtLocation(view, Gravity.BOTTOM|Gravity.LEFT,0,0);
    }


    public interface OnClickListener{

        void onCamera();

        void onGallery();
    }
}
