package com.mr.k.libmvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

/*
 * created by Cherry on 2019-12-20
 **/
public abstract class BaseMvpActivity<P extends IBaseMvpPresenter> extends BaseActivity implements IBaseMvpView<P>{

    protected P mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if(mPresenter != null){
            mPresenter.attachView(this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mPresenter != null){
            mPresenter.detachView();
        }

    }
}
