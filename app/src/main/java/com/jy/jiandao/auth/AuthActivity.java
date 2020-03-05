package com.jy.jiandao.auth;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jy.jiandao.auth.login.PasswordLoginFragment;
import com.jy.jiandao.auth.login.VerificationLoginFragment;
import com.jy.jiandao.auth.register.RegisterFragment;
import com.mr.k.libmvp.base.BaseActivity;
import com.mr.k.libmvp.manager.EncryptUtils;
import com.mr.k.libmvp.manager.MvpFragmentManager;

/*
 * created by Cherry on 2019-12-27
 **/
public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_auth);

        MvpFragmentManager.addOrShowFragment(getSupportFragmentManager(), PasswordLoginFragment.class,null, android.R.id.content );



        String cotent = "abcden年后123.。，";


        String e =  EncryptUtils.decrypt(cotent,EncryptUtils.key,EncryptUtils.iv);

        System.out.println("e = " + e   );
        String d = EncryptUtils.decrypt(e,EncryptUtils.key,EncryptUtils.iv);

        System.out.println("e = " + e  + " ----" + "d = " + d);


    }
}
