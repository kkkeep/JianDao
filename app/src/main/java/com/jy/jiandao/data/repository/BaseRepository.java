package com.jy.jiandao.data.repository;

import androidx.annotation.IntDef;

import com.jy.jiandao.data.HttpResult;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.exception.ResultException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/*
 * created by Cherry on 2020-01-03
 **/
public class BaseRepository {


    public  <D> void observer(Observable<HttpResult<D>> observable, Function<HttpResult<D>, ObservableSource<D>> function, IBaseCallBack<D> callBack) {
        observable
                .flatMap(function)
                .subscribeOn(Schedulers.io())
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
                        if (e instanceof ResultException) {
                            callBack.onFail((ResultException) e);
                        } else {
                            callBack.onFail(new ResultException(e));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public <D> void observer(Observable<HttpResult<D>> observable, Function<HttpResult<D>, ObservableSource<D>> function, Consumer<D> consumer, IBaseCallBack<D> callBack) {
        Observable<D> observable1 = observable.flatMap(function);

        if (consumer != null) {
            observable1 = observable1.doOnNext(consumer);
        }

        observable1.subscribeOn(Schedulers.io())
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
                        if (e instanceof ResultException) {
                            callBack.onFail((ResultException) e);
                        } else {
                            callBack.onFail(new ResultException(e));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public  <D> Observable<D> getConvertObservable(HttpResult<D> httpResult) {
        if (httpResult.code == 1) {
            if (httpResult.data != null) {
                return Observable.just(httpResult.data);
            } else {
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        } else {
            return Observable.error(new ResultException(httpResult.message));
        }
    }
}
