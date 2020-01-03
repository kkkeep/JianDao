package com.jy.jiandao.auth.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jy.jiandao.R;
import com.jy.jiandao.auth.register.RegisterFragment;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.manager.MvpFragmentManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * created by Cherry on 2019-12-30
 **/
public class VerificationLoginFragment extends BaseMvpFragment {

    private TextView mTvPsdLogin;
    private TextView mTvRegister;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_verification_code_login;
    }

    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        mTvPsdLogin = view.findViewById(R.id.auth_login_password_login);
        mTvRegister = view.findViewById(R.id.auth_login_tv_register_now);

        mTvPsdLogin.setOnClickListener(v -> {
            MvpFragmentManager.addOrShowFragment(getFragmentManager(), PasswordLoginFragment.class,this,android.R.id.content);
        });
        mTvRegister.setOnClickListener( v->{
            MvpFragmentManager.addOrShowFragment(getFragmentManager(), RegisterFragment.class,this,android.R.id.content);
        });
    }

    @Override
    public IBaseMvpPresenter createPresenter() {
        return null;
    }


    @Override
    public int getEnter() {
        return 0;
    }

    @Override
    public boolean isAddBackStack() {
        return false;
    }
}
