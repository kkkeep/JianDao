package com.mr.k.libmvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

/*
 * created by Cherry on 2019-12-20
 **/
public abstract class BaseMvpFragment<P extends IBaseMvpPresenter> extends BaseFragment implements IBaseMvpView<P>{

    protected  P mPresenter;


    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if(mPresenter != null){
            mPresenter.attachView(this);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(mPresenter != null){
            mPresenter.detachView();
        }
    }



}
