package com.jy.jiandao;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mr.k.libmvp.widget.bottomtab.BottomTabLayout;

/*
 * created by Cherry on 2020-01-06
 **/
public class MainActivity extends AppCompatActivity {

    private BottomTabLayout bottomTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomTabLayout = findViewById(R.id.main_bottom_tab_layout);


        bottomTabLayout.setOnTabSelectListener(new BottomTabLayout.OnTabSelectListener() {
            @Override
            public void select(int position) {
                Toast.makeText(MainActivity.this, "position = " + position, 1).show();
            }
        });


        bottomTabLayout.select(1);

    }
}
