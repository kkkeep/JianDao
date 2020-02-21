package com.mr.k.libmvp.base;

import com.trello.rxlifecycle2.LifecycleProvider;

/*
 * created by Cherry on 2019-12-20
 **/
public interface IBaseMvpPresenter<V extends IBaseMvpView> {

    void attachView(V view);

    void detachView();

    LifecycleProvider getProvider();

}
