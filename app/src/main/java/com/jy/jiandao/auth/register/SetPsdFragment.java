package com.jy.jiandao.auth.register;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jy.jiandao.R;
import com.jy.jiandao.auth.login.PasswordLoginFragment;
import com.jy.jiandao.auth.login.VerificationLoginFragment;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.manager.MvpFragmentManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * created by Cherry on 2019-12-30
 **/
public class SetPsdFragment extends BaseMvpFragment {
    private TextView mTvVCLogin;
    private TextView mTvPsdLogin;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_set_password;
    }
    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

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
}
