package com.mr.k.libmvp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Toast
import androidx.annotation.IdRes

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.mr.k.libmvp.R

import com.mr.k.libmvp.manager.MvpFragmentManager
import com.mr.k.libmvp.widget.LoadingView
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/*
 * created by Cherry on 2019-12-20
 **/
open class BaseActivity : RxAppCompatActivity() {





    protected fun showToast(@StringRes id: Int) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
    }

    protected fun showToast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()

    }


    // 如果不传id ,默认把loading 页挂载到 android.R.id.content 这个发 frame layout 上
    // 并让loading 背景透明
    protected fun showPopLoadingView(@IdRes parentId : Int = android.R.id.content) {
        showPopLoadingView(getParentForLoading(parentId))
    }
    // 如果不传id ,默认把loading 页挂载到 android.R.id.content 这个发 frame layout 上
    // 并让loading 背景透明
    protected fun showPopLoadingView(viewGroup: ViewGroup) {
        showLoadingView(LoadingView.MODE_TRANSPARENT_BACKGROUND,viewGroup)
    }


    // 如果不传id ,默认把loading 页挂载到 android.R.id.content 这个发frame layout 上,并让
    // loading 背景白色，不透明
    protected fun showFullLoadingView(@IdRes parentId : Int = android.R.id.content) {
        showFullLoadingView(getParentForLoading(parentId))
    }

    // 如果不传id ,默认把loading 页挂载到 android.R.id.content 这个发frame layout 上,并让
    // loading 背景白色，不透明
    protected fun showFullLoadingView(viewGroup: ViewGroup) {
        showLoadingView(LoadingView.MODE_WHITE_BACKGROUND,viewGroup)
    }






    protected fun closeLoadingView() {
    }

    protected fun showErrorLoadingView() {

    }


    //
    private fun getParentForLoading(@IdRes viewId : Int = android.R.id.content) : ViewGroup{
        return findViewById(viewId);
    }

    // 真正的把 loading page 显示到我们屏幕上
    private fun showLoadingView(@LoadingView.Mode mode : Int,parent : ViewGroup){
        val loadingView = LayoutInflater.from(this).inflate(R.layout.mvp_layout_loading, parent, false) as LoadingView;
        loadingView.showMode(mode)
        parent.addView(loadingView)
    }


}
