package com.mr.k.libmvp.base;

/*
 * created by Cherry on 2019-12-20
 **/
public interface IBaseMvpPresenter<V extends IBaseMvpView> {

    void attachView(V view);

    void detachView();
}
