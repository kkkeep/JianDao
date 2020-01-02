package com.jy.jiandao.auth.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jy.jiandao.R;
import com.jy.jiandao.auth.BaseAuthFragment;
import com.jy.jiandao.auth.login.PasswordLoginFragment;
import com.jy.jiandao.auth.login.VerificationLoginFragment;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.manager.MvpFragmentManager;
import com.mr.k.libmvp.widget.EditCleanButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * created by Cherry on 2019-12-27
 **/
public class RegisterFragment extends BaseAuthFragment<RegisterContract.IRegisterPresenter> implements RegisterContract.IRegisterView, View.OnClickListener {


    private EditText mEdtPhoneNum;
    private EditCleanButton mBtnCleanPhoneNum;
    private EditText mEdtVerification;
    private Button mBtnNext;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }


    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {
        mBtnCleanPhoneNum = bindView(R.id.auth_register_iv_clean);
        mEdtPhoneNum = bindView(R.id.auth_register_edt_phone_num);
        mEdtVerification = bindView(R.id.auth_register_edt_verification_code);
        mBtnNext = bindView(R.id.auth_register_btn_next_step, this);
        bindView(R.id.auth_register_tv_get_verification_code, this);
        bindView(R.id.auth_register_tv_code_login, this);
        bindView(R.id.auth_register_tv_psd_login, this);


        mBtnCleanPhoneNum.bindEditText(mEdtPhoneNum);
        mEdtVerification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBtnNext.setEnabled(!TextUtils.isEmpty(mEdtVerification.getText().toString().trim()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auth_register_tv_get_verification_code: {
                String phoneNum = mEdtPhoneNum.getText().toString().trim();

                if(SystemFacade.isValidPhoneNumber(phoneNum)){
                    mPresenter.getSmsCode(phoneNum);
                }else{
                    showToast(R.string.error_invalid_phone_num);
                }

                break;
            }
            case R.id.auth_register_btn_next_step: {
                onVerifySmsCodeResult("", true);

               /* String phoneNum = mEdtPhoneNum.getText().toString().trim();
                if(SystemFacade.isValidPhoneNumber(phoneNum)){
                    mPresenter.getSmsCode(phoneNum);
                }else{
                    showToast(R.string.error_invalid_phone_num);
                }

                String code = mEdtVerification.getText().toString().trim();
                if(SystemFacade.isValidSmsCodeNumber(code)){
                    mPresenter.verifySmsCode(phoneNum,code);
                }*/

                break;
            }
            case R.id.auth_register_tv_code_login: {
                MvpFragmentManager.addOrShowFragment(getFragmentManager(), VerificationLoginFragment.class, this, android.R.id.content);
                break;
            }
            case R.id.auth_register_tv_psd_login: {
                MvpFragmentManager.addOrShowFragment(getFragmentManager(), PasswordLoginFragment.class, this, android.R.id.content);
                break;
            }

        }
    }

    @Override
    public RegisterContract.IRegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }


    @Override
    public boolean isAddBackStack() {
        return false;
    }

    @Override
    public int getEnter() {
        return 0;
    }



    @Override
    public void onSmsCodeResult(String msg, boolean success) {
        if (success) {
            showToast("获取验证码成功");
        } else {
            showToast("获取验证码失败" + msg);
        }
    }

    @Override
    public void onVerifySmsCodeResult(String msg, boolean success) {
        if (success) {
            Bundle bundle = new Bundle();
            bundle.putString("phone", mEdtPhoneNum.getText().toString().trim());
            MvpFragmentManager.addOrShowFragment(getFragmentManager(), SetPsdFragment.class, this, android.R.id.content,bundle);
        } else {
            showToast("验证码失败" + msg);
        }
    }
}
