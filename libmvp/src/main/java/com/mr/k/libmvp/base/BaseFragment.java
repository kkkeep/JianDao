package com.mr.k.libmvp.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.mr.k.libmvp.manager.MvpFragmentManager;
import com.trello.rxlifecycle2.components.support.RxFragment;

/*
 * created by Cherry on 2019-12-20
 **/
public class BaseFragment extends RxFragment {





    protected void showToast(@StringRes int id) {
        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String content) {
        Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();

    }

    public boolean isAddBackStack(){
        return true;
    }
    public Action getAction(){
        return Action.Hide;
    }

    // 显示一个fragment 时，对上一个fragment 做的处理
    public enum Action{
        Remove,Detach,Hide
    }



    protected void showPopLoadingView(){

    }

    protected void showFullLoadingView(){

    }


    protected void closeLoadingView(){

    }

    protected void showErrorLoadingView(){

    }


}
