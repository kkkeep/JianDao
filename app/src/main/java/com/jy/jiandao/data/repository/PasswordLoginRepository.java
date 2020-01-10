package com.jy.jiandao.data.repository;

import com.jy.jiandao.auth.login.PasswordLoginContract;
import com.jy.jiandao.data.HttpResult;
import com.jy.jiandao.data.entity.User;
import com.jy.jiandao.data.ok.JDDataService;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.exception.ResultException;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
 * created by Cherry on 2020-01-03
 **/
public class PasswordLoginRepository extends BaseRepository implements PasswordLoginContract.IPasswordLoginMode {

    @Override
    public void login(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack) {
        observer(JDDataService.getApiService().login(params), this::getConvertObservable,callBack);
    }




}
