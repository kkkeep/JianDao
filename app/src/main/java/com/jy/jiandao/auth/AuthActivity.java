package com.jy.jiandao.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jy.jiandao.JDApplication;
import com.jy.jiandao.auth.login.PasswordLoginFragment;
import com.jy.jiandao.auth.login.VerificationLoginFragment;
import com.jy.jiandao.auth.register.RegisterFragment;
import com.jy.jiandao.home.HomeActivity;
import com.mr.k.libmvp.base.BaseActivity;
import com.mr.k.libmvp.manager.MvpFragmentManager;
import com.mr.k.libmvp.manager.MvpUserManager;

/*
 * created by Cherry on 2019-12-27
 **/
public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_auth);

        if(MvpUserManager.isLoginIn()){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        MvpFragmentManager.addOrShowFragment(getSupportFragmentManager(), PasswordLoginFragment.class,null, android.R.id.content );

        //



       /* String cotent = "abcden年后123.。，";


        String e =  EncryptUtils.decrypt(cotent,EncryptUtils.key,EncryptUtils.iv);

        System.out.println("e = " + e   );
        String d = EncryptUtils.decrypt(e,EncryptUtils.key,EncryptUtils.iv);

        System.out.println("e = " + e  + " ----" + "d = " + d);
*/

    }


    public static void open(){
        Intent intent = new Intent(JDApplication.mContext,AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        JDApplication.mContext.startActivity(intent);


    }
}
