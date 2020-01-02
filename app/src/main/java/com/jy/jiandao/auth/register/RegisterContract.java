package com.jy.jiandao.auth.register;

import com.jy.jiandao.data.entity.User;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;
import com.trello.rxlifecycle2.LifecycleProvider;

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


    // -------------  注册 设置密码 ------------------

    public interface IRegisterSetView extends IBaseMvpView<IRegisterSetPresenter>{

        void onRegisterResult(User user,String msg);
    }


    public interface  IRegisterSetPresenter extends IBaseMvpPresenter<IRegisterSetView>{

        void register(String phoneNum,String password,String confirmPassword);
    }


    public interface IRegisterSetMode {

        void register(LifecycleProvider provider,Map<String,String> params,IBaseCallBack<User> callBack);
    }
    // -------------  注册 设置密码 ------------------
}
