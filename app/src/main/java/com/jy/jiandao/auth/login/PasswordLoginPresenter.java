package com.jy.jiandao.auth.login;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.User;
import com.jy.jiandao.data.repository.PasswordLoginRepository;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.base.BasePresenter;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.exception.ResultException;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import static com.jy.jiandao.AppConstant.RequestKey.PASSWORD_LOGIN_PASSWORD;
import static com.jy.jiandao.AppConstant.RequestKey.PASSWORD_LOGIN_USER_NAME;

/*
 * created by Cherry on 2020-01-03
 **/
public class PasswordLoginPresenter extends BasePresenter<PasswordLoginContract.IPasswordLoginView> implements PasswordLoginContract.IPasswordLoginPresenter {


    private PasswordLoginContract.IPasswordLoginMode mRepository;


    public PasswordLoginPresenter() {
        this.mRepository = new PasswordLoginRepository();
    }

    @Override
    public void login(String phoneNum, String password) {

        Map<String,String> params = ParamsUtils.getCommonParams();

        params.put(PASSWORD_LOGIN_USER_NAME, phoneNum);
        params.put(PASSWORD_LOGIN_PASSWORD, password);

        mRepository.login((LifecycleProvider) mView, params, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                if(mView != null){
                    mView.onLoginResult(data, null);
                }
            }

            @Override
            public void onFail(ResultException msg) {
                if(mView != null){
                    mView.onLoginResult(null, msg.getMessage());
                }
            }
        });
    }
}
