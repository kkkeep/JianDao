package com.mr.k.libmvp.base;

import com.mr.k.libmvp.exception.ResultException;

/*
 * created by Cherry on 2019-12-20
 **/
public interface IBaseCallBack<T> {

    void onSuccess(T data);

    void onFail(ResultException e);
}
