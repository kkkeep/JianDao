package com.jy.jiandao.auth.register;

import com.jy.jiandao.data.entity.User;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;

import java.util.Map;

/*
 * created by Cherry on 2019-12-27
 **/
public interface RegisterContract {

    // -------------- 注册 -------------------
    public interface IRegisterView extends IBaseMvpView<IRegisterPresenter> {


        void onSmsCodeResult(String msg,boolean success);

        void onVerifySmsCodeResult(String msg,boolean success);

    }


    public interface IRegisterPresenter extends IBaseMvpPresenter<IRegisterView> {

        void getSmsCode(String phoneNumber);

        void verifySmsCode(String phoneNumber,String code);



    }


    public interface IRegisterMode  {

        void getSmsCode(Map<String,String> params, IBaseCallBack<String> callBack);

        void verifySmsCode(Map<String,String> params, IBaseCallBack<String> callBack);



    }
    // -------------- 注册 -------------------
}
