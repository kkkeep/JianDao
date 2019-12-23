package com.jy.jiandao;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
 * created by Cherry on 2019-12-23
 **/
public class LambdaActivity extends AppCompatActivity {


    public Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FunctionInterface functionInterface = new FunctionInterface() {
            @Override
            public int add(int a, int b) {
                return a + b;
            }
        };


        int c = functionInterface.add(1, 2);


        FunctionInterface functionInterface1= ( m, n) -> {
            return m + n;
        };




        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("onclick");
            }
        });
        mButton.setOnClickListener((View v) -> System.out.println("onclick"));

    }


}
