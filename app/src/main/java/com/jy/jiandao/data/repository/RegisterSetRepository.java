package com.jy.jiandao.data.repository;

import com.jy.jiandao.auth.register.RegisterContract;
import com.jy.jiandao.data.HttpResult;
import com.jy.jiandao.data.entity.User;
import com.jy.jiandao.data.ok.JDDataService;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
 * created by Cherry on 2020-01-02
 **/
public class RegisterSetRepository implements RegisterContract.IRegisterSetMode {


    @Override
    public void register(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack) {
        JDDataService.getApiService().register(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<User>>(){

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<User> userHttpResult) {
                        if(userHttpResult.code == 1 ){
                            if(userHttpResult.data != null){
                                callBack.onSuccess(userHttpResult.data);
                            }else{
                                callBack.onFail("服务器错误");
                            }

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
