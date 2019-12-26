package com.jy.jiandao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.jy.jiandao.data.ok.ApiService
import com.jy.jiandao.data.ok.JDDataService
import com.mr.k.libmvp.base.BaseActivity
import com.mr.k.libmvp.manager.MvpFragmentManager
import com.mr.k.libmvp.oknet.DataService
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    lateinit  var rootView : ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)



       showLoading.setOnClickListener {

        }


    }


}
