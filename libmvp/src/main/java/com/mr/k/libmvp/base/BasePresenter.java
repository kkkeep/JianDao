package com.mr.k.libmvp.base;

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
}
