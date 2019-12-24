package com.jy.jiandao

import android.os.Bundle
import com.mr.k.libmvp.base.BaseActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        showLoading.setOnClickListener {

            //showPopLoadingView()
           //// showFullLoadingView()


        }
    }


}
