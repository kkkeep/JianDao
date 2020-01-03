package com.jy.jiandao.auth.register;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.User;
import com.jy.jiandao.data.repository.RegisterSetRepository;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.base.BasePresenter;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

/*
 * created by Cherry on 2020-01-02
 **/
public class RegisterSetPresenter extends BasePresenter<RegisterContract.IRegisterSetView> implements RegisterContract.IRegisterSetPresenter {


    private RegisterContract.IRegisterSetMode mRepository;


    public RegisterSetPresenter(){
        mRepository = new RegisterSetRepository();
    }
    @Override
    public void register(String phoneNum, String password, String confirmPassword) {

        Map<String,String> params = ParamsUtils.getCommonParams();

        params.put(AppConstant.RequestKey.AUTH_REGISTER_MOBILE, phoneNum);
        params.put(AppConstant.RequestKey.AUTH_REGISTER_PASSWORD, password);
        params.put(AppConstant.RequestKey.AUTH_REGISTER_CONFIRM_PASSWORD, confirmPassword);

        mRepository.register((LifecycleProvider) mView, params, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                if(mView  != null){
                    mView.onRegisterResult(data, null);
                }
            }

            @Override
            public void onFail(String msg) {
                if(mView  != null){
                    mView.onRegisterResult(null , msg);
                }
            }
        });

    }
}
