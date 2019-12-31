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
import com.jy.jiandao.auth.login.PasswordLoginFragment;
import com.jy.jiandao.auth.login.VerificationLoginFragment;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;
import com.mr.k.libmvp.manager.MvpFragmentManager;
import com.mr.k.libmvp.widget.EditCleanButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * created by Cherry on 2019-12-27
 **/
public class RegisterFragment extends BaseMvpFragment<RegisterContract.IRegisterPresenter> implements RegisterContract.IRegisterView {


    private EditText mEdtPhoneNum;
    private EditCleanButton mBtnCleanPhoneNum;

    private EditText mEdtVerification;
    private Button mBtnNext;

    private TextView mTvGetCode;

    private TextView mTvVCLogin;
    private TextView mTvPsdLogin;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }


    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {
        mBtnCleanPhoneNum = view.findViewById(R.id.auth_register_iv_clean);
        mEdtPhoneNum   = view.findViewById(R.id.auth_register_edt_phone_num);
        mBtnCleanPhoneNum.bindEditText(mEdtPhoneNum);

        mEdtVerification = view.findViewById(R.id.auth_register_edt_verification_code);
        mBtnNext = view.findViewById(R.id.auth_register_btn_next_step);

        mTvGetCode = view.findViewById(R.id.auth_register_tv_get_verification_code);

       /* mTvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getSmsCode(mEdtPhoneNum.getText().toString());
            }
        });*/
        mEdtVerification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBtnNext.setEnabled(TextUtils.isEmpty(mEdtVerification.getText().toString().trim()));

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });



        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.verifySmsCode(mEdtPhoneNum.getText().toString(), mEdtVerification.getText().toString());
            }
        });


        mTvVCLogin = view.findViewById(R.id.auth_register_tv_code_login);
        mTvVCLogin.setOnClickListener(v -> {
            MvpFragmentManager.addOrShowFragment(getFragmentManager(), VerificationLoginFragment.class,this,android.R.id.content);
        });
        mTvPsdLogin = view.findViewById(R.id.auth_register_tv_psd_login);

        mTvPsdLogin.setOnClickListener( v->{
            MvpFragmentManager.addOrShowFragment(getFragmentManager(), PasswordLoginFragment.class,this,android.R.id.content);
        });



    }

    @Override
    public RegisterContract.IRegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }





    @Override
    public void onSmsCodeResult(String msg, boolean success) {
        if(success){
            showToast("获取验证码成功");
        }else{
            showToast("获取验证码失败" + msg);
        }
    }

    @Override
    public void onVerifySmsCodeResult(String msg, boolean success) {
        if(success){
            showToast("验证码成功");
            MvpFragmentManager.addOrShowFragment(getFragmentManager(), SetPsdFragment.class,this,android.R.id.content);
        }else{
            showToast("验证码失败" + msg);
        }
    }
}
