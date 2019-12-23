package com.mr.k.libmvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

/*
 * created by Cherry on 2019-12-20
 **/
public abstract class BaseMvpFragment<P extends IBaseMvpPresenter> extends BaseFragment implements IBaseMvpView<P>{

    protected  P mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();

        if(mPresenter != null){
            mPresenter.attachView(this);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(mPresenter != null){
            mPresenter.detachView();
        }
    }



}
