package com.jy.jiandao.auth.login;

import com.jy.jiandao.data.entity.User;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

/*
 * created by Cherry on 2020-01-03
 **/
public interface PasswordLoginContract {


    interface IPasswordLoginView extends IBaseMvpView<IPasswordLoginPresenter> {

        void onLoginResult(User user,String msg);

    }
    interface IPasswordLoginPresenter extends IBaseMvpPresenter<IPasswordLoginView> {


        void login(String phoneNum,String password);
    }


    interface IPasswordLoginMode  {

        void login(LifecycleProvider provider, Map<String,String> params, IBaseCallBack<User> callBack);
    }
}
