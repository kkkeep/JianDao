package com.jy.jiandao.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.User;
import com.jy.jiandao.data.repository.BaseRepository;
import com.jy.jiandao.home.HomeActivity;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.Utils.Logger;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.BaseActivity;
import com.mr.k.libmvp.base.BaseMvpActivity;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.ParamsMap;
import com.mr.k.libmvp.exception.ResultException;
import com.mr.k.libmvp.manager.MvpFragmentManager;
import com.mr.k.libmvp.manager.MvpManager;
import com.mr.k.libmvp.manager.MvpUserManager;

import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {


    private Handler mHandler = new Handler();

    private ViewPager mGuildPager;

    private TextView mBtnJump;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MvpManager.isAgreementForSplash()) {
            showContent();
        } else {

            getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    showAgreementPop();
                }
            });

        }


    }


    private void showContent() {


        if (MvpManager.isFirstLaunch()) { // 判断是否第一次启动
            showGuidePage();
        } else {
            // 第一次 启动延时5s
            mHandler.postDelayed(mLaunchTask, 5000);

            getUserInfo();
            showAd();


        }
    }

    // 显示用户同意协议 pop
    private void showAgreementPop() {

        AgreementPopView agreementPopView = new AgreementPopView(this);

        agreementPopView.setOnClickListener(new AgreementPopView.OnClickListener() {
            @Override
            public void onAgree(boolean agree) {
                if (agree) {
                    MvpManager.agreeFroSpalsh();
                    showContent();
                } else {
                    finish();
                }
            }

            @Override
            public void onTextClick(int position) {
                Intent intent = new Intent(SplashActivity.this, SplashProtocolPolicyActivity.class);
                intent.putExtra("from", position);
                startActivity(intent);
            }
        });

        agreementPopView.showCenter(getWindow().getDecorView());
    }


    private void showAd() {

        // 如果有广告，那么判断是否有广告，
        // 如果有广告，把广告加载到内存里面，
        // 移除倒计时5s
        // 开始在广告页面做倒计时


    }
    // 显示引导页，viewpager

    private void showGuidePage() {

        setContentView(R.layout.activity_splash);

        mGuildPager = findViewById(R.id.splash_viewpager);

        mBtnJump = findViewById(R.id.splash_btn_jump);


        mGuildPager.setAdapter(new GuildPageAdpater());



        findViewById(R.id.splash_btn_jump).setOnClickListener(view ->{
            startHomeActivity();
        });


    }


    private Runnable mLaunchTask = new Runnable() {
        @Override
        public void run() {

            startHomeActivity();
        }
    };


    private void startHomeActivity() {

        Intent intent = new Intent(this, HomeActivity.class);

        startActivity(intent);

        finish();
    }


    private class GuildPageAdpater extends PagerAdapter {

        private int drawableIds[] = new int[]{R.drawable.splash_guild1, R.drawable.splash_guild2, R.drawable.splash_guild3};

        @Override
        public int getCount() {
            return drawableIds.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View page = LayoutInflater.from(container.getContext()).inflate(R.layout.item_splash_guild_page, container, false);


            page.setBackgroundResource(drawableIds[position]);

            if (position == drawableIds.length - 1) {

                Button tryNow = page.findViewById(R.id.splash_guild_item_btn_try_now);
                tryNow.setVisibility(View.VISIBLE);
                tryNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startHomeActivity();
                    }
                });

            } else {
                page.findViewById(R.id.splash_guild_item_btn_try_now).setVisibility(View.GONE);
            }

            container.addView(page);

            return page;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            container.removeView((View) object);
        }
    }

    private void getUserInfo() {

        if (!MvpUserManager.isLoginIn()) {
            return;

        }

        ParamsMap paramsMap = new ParamsMap(ParamsUtils.getCommonParams(), AppConstant.Url.GETUSER);

        new BaseRepository().get(this, user -> {

            User localUser = MvpUserManager.getUser();

            localUser.setUserInfo(user.getUserInfo());

            MvpUserManager.login(localUser);


        }, paramsMap, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                Logger.d("Get UserInfo success");

            }

            @Override
            public void onFail(ResultException e) {
                Logger.d("Get UserInfo fail msg = %s", e.getMessage());

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mLaunchTask);
    }
}
