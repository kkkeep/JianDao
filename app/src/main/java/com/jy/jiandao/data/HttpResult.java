package com.jy.jiandao.data;

import com.mr.k.libmvp.base.IEntity;

import org.jetbrains.annotations.Nullable;

/*
 * created by Cherry on 2019-12-26
 **/
public class HttpResult<D> implements IEntity<D> {

    public String message;
    public int code;
    public D data;


    @Override
    public int getCode() {
        return code;
    }

    @Nullable
    @Override
    public String getErrorMessage() {
        return message;
    }

    @Nullable
    @Override
    public D getData() {
        return data;
    }
}



