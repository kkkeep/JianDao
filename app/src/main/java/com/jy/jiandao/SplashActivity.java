package com.jy.jiandao;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jy.jiandao.home.HomeActivity;
import com.mr.k.libmvp.base.BaseMvpActivity;
import com.mr.k.libmvp.manager.MvpManager;

public class SplashActivity extends AppCompatActivity {



    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MvpManager.isAgreementForSplash()){ // 如果用户之前同意过协议

           showContent();

        }else{
            //

            showAgreementPop();
        }



    }



    private void showContent(){
        // 第一次 启动延时5s
        mHandler.postDelayed(mLaunchTask,5000);

        if(MvpManager.isFirstLaunch()){ // 判断是否第一次启动

            showGuidePage();
        }
    }

    // 显示用户同意协议 pop
    private void showAgreementPop(){

    }

    // 显示引导页，viewpager

    private void showGuidePage(){

    }







    private Runnable mLaunchTask = new Runnable() {
        @Override
        public void run() {

            startHomeActivity();
        }
    };


    private void startHomeActivity(){

        Intent intent = new Intent(this, HomeActivity.class);

        startActivity(intent);
    }
}
