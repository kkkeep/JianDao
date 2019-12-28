package com.jy.jiandao.data.repository;

import com.jy.jiandao.auth.register.RegisterContract;
import com.jy.jiandao.data.HttpResult;
import com.jy.jiandao.data.entity.User;
import com.jy.jiandao.data.ok.JDDataService;
import com.mr.k.libmvp.base.IBaseCallBack;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
 * created by Cherry on 2019-12-27
 **/
public class RegisterRepository implements RegisterContract.IRegisterMode {


    @Override
    public void getSmsCode(Map<String, String> params, IBaseCallBack<String> callBack) {

        JDDataService.getApiService().getVerificationCode(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<String>>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<String> userHttpResult) {
                        if(userHttpResult.code == 1){
                            callBack.onSuccess(userHttpResult.data);
                        }else{
                            callBack.onFail(userHttpResult.message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                            callBack.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void verifySmsCode(Map<String, String> params, IBaseCallBack<String> callBack) {

        JDDataService.getApiService().getCheckVerificationCode(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<String>>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<String> userHttpResult) {
                        if(userHttpResult.code == 0){
                            callBack.onSuccess(userHttpResult.data);
                        }else{
                            callBack.onFail(userHttpResult.message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
