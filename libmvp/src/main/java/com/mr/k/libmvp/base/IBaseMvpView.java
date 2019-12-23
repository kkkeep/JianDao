package com.mr.k.libmvp.base;

/*
 * created by Cherry on 2019-12-20
 **/
public interface IBaseMvpView<P extends  IBaseMvpPresenter> {

    P createPresenter();
}
