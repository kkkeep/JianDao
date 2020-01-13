package com.jy.jiandao.test;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jy.jiandao.R;
import com.mr.k.banner.KBanner;

import java.util.ArrayList;
import java.util.List;

/*
 * created by Cherry on 2020-01-13
 **/
public class BannerActivity extends AppCompatActivity {
    private KBanner mKBanner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_banner_activity);

        mKBanner  = findViewById(R.id.banner);


        List<Banner> banners = new ArrayList<>();

        banners.add(new Banner("title1", R.drawable.ic_auth_qq));
        banners.add(new Banner("title2", R.drawable.ic_auth_wechat));
        banners.add(new Banner("title3", R.drawable.ic_auth_sina));
        banners.add(new Banner("title4", R.drawable.ic_auth_logo));
        banners.add(new Banner("title5", R.drawable.ic_edit_clean));
        banners.add(new Banner("title6", R.drawable.ic_auth_close));
        banners.add(new Banner("title7", R.drawable.ic_mvp_eyes_open));


        mKBanner.setData(banners);

        mKBanner.setAdapter(new KBanner.KBannerAdapter<Banner>() {
            @Override
            public void fillBannerItemData(KBanner banner, ImageView imageView, Banner data, int position) {
                imageView.setImageResource(data.url);

            }
        });

    }



    public class Banner {
        String title;
        int url;

        public Banner(String title, int url) {
            this.title = title;
            this.url = url;
        }
    }
}
