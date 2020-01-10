package com.jy.jiandao.auth.register;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.repository.RegisterRepository;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.base.BasePresenter;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.exception.ResultException;

import java.util.HashMap;

import static com.jy.jiandao.AppConstant.RequestKey.AUTH_REGISTER_MOBILE;
import static com.jy.jiandao.AppConstant.RequestKey.AUTH_REGISTER_TYPE;
import static com.jy.jiandao.AppConstant.RequestKey.VERIFICATION_CODE;

/*
 * created by Cherry on 2019-12-27
 **/
public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterView> implements RegisterContract.IRegisterPresenter {

    private RegisterContract.IRegisterMode mRepository;


    public RegisterPresenter(){
        mRepository = new RegisterRepository();
    }

    @Override
    public void getSmsCode(String phoneNumber) {
        HashMap<String,String> hashMap = ParamsUtils.getCommonParams();
        hashMap.put(AUTH_REGISTER_MOBILE, phoneNumber);
        hashMap.put(AUTH_REGISTER_TYPE,"1");
        mRepository.getSmsCode(hashMap, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                if(mView != null){
                    mView.onSmsCodeResult(data, true);
                }
            }

            @Override
            public void onFail(ResultException msg) {
                if(mView != null){
                    mView.onSmsCodeResult(msg.getMessage(), false);
                }
            }
        });


    }

    @Override
    public void verifySmsCode(String phoneNumber, String code) {
        HashMap<String,String> hashMap = ParamsUtils.getCommonParams();
        hashMap.put(AUTH_REGISTER_MOBILE, phoneNumber);
        hashMap.put(AUTH_REGISTER_TYPE,"1");
        hashMap.put(VERIFICATION_CODE,code);
        mRepository.verifySmsCode(hashMap, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                if(mView != null){
                    mView.onVerifySmsCodeResult(data, true);
                }
            }

            @Override
            public void onFail(ResultException msg) {
                if(mView != null){
                    mView.onVerifySmsCodeResult(msg.getMessage(), false);
                }
            }
        });
    }
}
