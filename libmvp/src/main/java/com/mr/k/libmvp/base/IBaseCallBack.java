package com.mr.k.libmvp.base;

/*
 * created by Cherry on 2019-12-20
 **/
public interface IBaseCallBack<T> {

    void onSuccess(T data);

    void onFail(String msg);
}
