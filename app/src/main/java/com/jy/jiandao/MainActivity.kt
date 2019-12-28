package com.jy.jiandao

import android.os.Bundle
import android.view.ViewGroup
import com.mr.k.libmvp.base.BaseActivity

class MainActivity : BaseActivity() {
    lateinit  var rootView : ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.layout_register_content)



      /* showLoading.setOnClickListener {

        }*/


    }


}
