package com.jy.jiandao.data.repository;

import com.jy.jiandao.data.HttpResult;
import com.mr.k.libmvp.base.IBaseCallBack;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/*
 * created by Cherry on 2020-01-03
 **/
public class BaseRepository {


    protected <D> void observer(Observable<HttpResult<D>> observable, Function<HttpResult<D>, ObservableSource<D>> function, IBaseCallBack<D> callBack) {
        observable.flatMap(function).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<D>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(D d) {
                        callBack.onSuccess(d);
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
