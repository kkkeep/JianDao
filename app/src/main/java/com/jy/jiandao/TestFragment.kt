package com.jy.jiandao

import android.os.Bundle
import android.view.View

import com.mr.k.libmvp.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.android.synthetic.main.fragment_test.showLoading

/*
 * created by Cherry on 2019-12-25
 **/
class TestFragment : BaseFragment() {


    override val layoutId: Int
        get() = R.layout.fragment_test


    override fun initView(view: View, savedInstanceState: Bundle?) {

        showLoading.setOnClickListener {

            showFullLoadingView()
            // showFullLoadingView()

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
}
