package com.jy.jiandao.auth.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jy.jiandao.R;
import com.jy.jiandao.auth.BaseAuthFragment;
import com.jy.jiandao.auth.login.PasswordLoginFragment;
import com.jy.jiandao.auth.login.VerificationLoginFragment;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.manager.MvpFragmentManager;
import com.mr.k.libmvp.widget.EditTogglePasswordButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * created by Cherry on 2019-12-30
 **/
public class SetPsdFragment extends BaseAuthFragment implements View.OnClickListener {
    private TextView mTvVCLogin;
    private TextView mTvPsdLogin;

    private EditTogglePasswordButton mTogglePasswordButton;
    private EditText mEdtPassword;
    private Button mBtnRegister;


    @Override
    public int getLayoutId() {

        return R.layout.fragment_set_password;
    }
    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        mTogglePasswordButton = bindView(R.id.auth_register_set_iv_toggle_psd);

        mEdtPassword = bindView(R.id.auth_register_set_edt_password);

        mTogglePasswordButton.bindEditText(mEdtPassword);

        mBtnRegister = bindView(R.id.auth_register_set_btn_register,this);
        mBtnRegister.setEnabled(true);

        mTvVCLogin = view.findViewById(R.id.auth_register_set_tv_verification_code_login);
        mTvPsdLogin = view.findViewById(R.id.auth_register_set_tv_password_login);

        mTvVCLogin.setOnClickListener(v -> {
            MvpFragmentManager.addOrShowFragment(getFragmentManager(), VerificationLoginFragment.class,this,android.R.id.content);
        });
        mTvPsdLogin.setOnClickListener( v->{
            MvpFragmentManager.addOrShowFragment(getFragmentManager(), PasswordLoginFragment.class,this,android.R.id.content);
        });


    }
    @Override
    public IBaseMvpPresenter createPresenter() {
        return null;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.auth_register_set_btn_register:{
                showToast("jhhhhh");
            }
        }
    }
}
