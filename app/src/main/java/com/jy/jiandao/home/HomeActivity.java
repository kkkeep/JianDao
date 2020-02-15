package com.jy.jiandao.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jy.jiandao.R;
import com.jy.jiandao.data.repository.NewsPageRepository;
import com.jy.jiandao.home.recommend.RecommendFragment;
import com.jy.jiandao.home.recommend.page.NewsPageAdapter;
import com.mr.k.libmvp.base.BaseActivity;
import com.mr.k.libmvp.manager.MvpFragmentManager;
import com.mr.k.libmvp.widget.bottomtab.BottomTabLayout;

/*
 * created by Cherry on 2020-01-06
 **/
public class HomeActivity extends BaseActivity {

    private BottomTabLayout bottomTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomTabLayout = findViewById(R.id.main_bottom_tab_layout);


        bottomTabLayout.setOnTabSelectListener(new BottomTabLayout.OnTabSelectListener() {
            @Override
            public void select(int id) {
                switch (id){
                    case R.id.mvp_tab_layout_tab1:{
                        MvpFragmentManager.addOrShowFragment(getSupportFragmentManager(), RecommendFragment.class, null, R.id.home_fragment_container);
                        break;
                    }
                }

            }
        });


        bottomTabLayout.select(R.id.mvp_tab_layout_tab1);








    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NewsPageRepository.destroy();
    }
}
