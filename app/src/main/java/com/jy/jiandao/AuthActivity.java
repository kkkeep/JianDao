package com.jy.jiandao;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jy.jiandao.auth.register.RegisterFragment;
import com.mr.k.libmvp.base.BaseActivity;
import com.mr.k.libmvp.manager.MvpFragmentManager;

/*
 * created by Cherry on 2019-12-27
 **/
public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_auth);

        MvpFragmentManager.addOrShowFragment(getSupportFragmentManager(), RegisterFragment.class, android.R.id.content );
    }
}
