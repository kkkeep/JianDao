package com.mr.k.libmvp.base;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseAdapterHolder<T> extends RecyclerView.ViewHolder {


    public BaseAdapterHolder(@NonNull View itemView) {
        super(itemView);


    }

    public void bindData(T data){

    }


    public <V extends View> V  findViewById(@IdRes int id){

       return itemView.findViewById(id);
    }


}
