package com.jy.jiandao.auth.login;

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
import com.jy.jiandao.auth.register.RegisterFragment;
import com.jy.jiandao.data.entity.User;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.manager.MvpFragmentManager;
import com.mr.k.libmvp.widget.EditCleanButton;
import com.mr.k.libmvp.widget.EditTogglePasswordButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * created by Cherry on 2019-12-30
 **/
public class PasswordLoginFragment extends BaseAuthFragment<PasswordLoginContract.IPasswordLoginPresenter> implements PasswordLoginContract.IPasswordLoginView, View.OnClickListener {



    private EditText mEdtPhoneNum;
    private EditText mEdtPassword;

    private EditCleanButton mEdtCleanPhoneNum;
    private EditCleanButton mEdtCleanPassword;
    private EditTogglePasswordButton mEdtTogglePassword;
    private TextView mTvForgetPassword;

    private Button mBtnLogin;




    @Override
    public int getLayoutId() {
        return R.layout.fragment_password_login;
    }

    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {

        bindView(R.id.auth_password_login_tv_code_login,this);
        bindView(R.id.auth_password_login_tv_register_now,this);

        mEdtPhoneNum = bindView(R.id.auth_password_login_edt_phone_num, this);
        mEdtPassword = bindView(R.id.auth_password_login_edt_password, this);
        mEdtCleanPhoneNum = bindView(R.id.auth_password_login_iv_clean_phone_num);
        mEdtCleanPassword = bindView(R.id.auth_password_login_iv_clean_password);

        mEdtTogglePassword = bindView(R.id.auth_password_login_iv_toggle);

        mTvForgetPassword = bindView(R.id.auth_password_login_tv_forget_password, this);
        mBtnLogin = bindView(R.id.auth_password_login_btn_login, this);



        mEdtCleanPhoneNum.bindEditText(mEdtPhoneNum);
        mEdtCleanPassword.bindEditText(mEdtPassword);
        mEdtTogglePassword.bindEditText(mEdtPassword);

        mEdtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mBtnLogin.setEnabled(!TextUtils.isEmpty(mEdtPassword.getText().toString().trim()));

            }
        });





    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.auth_password_login_tv_code_login:{
                MvpFragmentManager.addOrShowFragment(getFragmentManager(), VerificationLoginFragment.class,this,android.R.id.content);
                break;
            }
            case R.id.auth_password_login_tv_register_now:{
                MvpFragmentManager.addOrShowFragment(getFragmentManager(), RegisterFragment.class,this,android.R.id.content);
                break;
            }

            case R.id.auth_password_login_btn_login:{
                String phoneNum = mEdtPhoneNum.getText().toString().trim();
                if(!SystemFacade.isValidPhoneNumber(phoneNum)){
                    showToast(R.string.error_invalid_phone_num);
                    return;
                }
                String password  = mEdtPassword.getText().toString().trim();
                if(TextUtils.isEmpty(password)){
                    showToast(R.string.error_invalid_null_password);
                    return;
                }
                mPresenter.login(phoneNum,password);

              //  getActivity().finish();
                break;
            }

        }
    }

    @Override
    public void onLoginResult(User user, String msg) {
        if(user != null){
            showToast("登录成功");
        }else{
            showToast( msg);
        }
    }

    @Override
    public PasswordLoginContract.IPasswordLoginPresenter createPresenter() {
        return new PasswordLoginPresenter();
    }
}
