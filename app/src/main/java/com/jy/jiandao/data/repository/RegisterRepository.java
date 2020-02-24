package com.jy.jiandao.data.repository;

import com.jy.jiandao.auth.register.RegisterContract;
import com.jy.jiandao.data.HttpResult;
import com.jy.jiandao.data.entity.User;
import com.jy.jiandao.data.ok.JDDataService;
import com.mr.k.libmvp.base.IBaseCallBack;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
 * created by Cherry on 2019-12-27
 **/
public class RegisterRepository extends BaseRepository implements RegisterContract.IRegisterMode {


    @Override
    public void getSmsCode(Map<String, String> params, IBaseCallBack<String> callBack) {

       // observer(JDDataService.getApiService().getVerificationCode(params),this::getConvertObservable,callBack);


    }

    @Override
    public void verifySmsCode(Map<String, String> params, IBaseCallBack<String> callBack) {

      //  observer(JDDataService.getApiService().getCheckVerificationCode(params),this::getConvertObservable,callBack);

    }
}
