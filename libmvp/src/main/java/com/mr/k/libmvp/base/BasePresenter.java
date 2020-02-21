package com.mr.k.libmvp.base;

import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

/*
 * created by Cherry on 2019-12-20
 **/
public class BasePresenter<V extends IBaseMvpView> implements IBaseMvpPresenter<V> {

    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public LifecycleProvider getProvider() {
        return (LifecycleProvider) mView;
    }




}
