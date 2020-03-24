package com.jy.jiandao.splash;

import android.os.Bundle;

import com.jy.jiandao.R;
import com.mr.k.libmvp.base.BaseActivity;
import com.mr.k.libmvp.manager.MvpFragmentManager;

import org.jetbrains.annotations.Nullable;

/*
 * created by Cherry on 2019-12-20
 **/
public class SplashProtocolPolicyActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_protocol_policy);


        int from = getIntent().getIntExtra("from", 1);

        if (from == 1) {
            MvpFragmentManager.addOrShowFragment(getSupportFragmentManager(), SplashUserProtocolFragment.class, null, android.R.id.content);
        } else {
            MvpFragmentManager.addOrShowFragment(getSupportFragmentManager(), SplashPrivacyPolicyFragment.class, null, android.R.id.content);
        }


    }

}
