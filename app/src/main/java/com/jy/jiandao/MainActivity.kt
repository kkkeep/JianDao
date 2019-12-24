package com.jy.jiandao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mr.k.libmvp.base.BaseActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    lateinit  var rootView : ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

           // rootView = LayoutInflater.from(this).inflate(R.layout.activity_main,null) as ViewGroup;
        setContentView(R.layout.activity_main)


        showLoading.setOnClickListener {

            showPopLoadingView()
           // showFullLoadingView()


        }


        showLoading.postDelayed({
            //closeLoadingView()
            showErrorLoadingView()

            showLoading.postDelayed({
                showPopLoadingView()

                showLoading.postDelayed({
                    closeLoadingView()
                },2000)
            },2000)
        },5000)
    }


}
