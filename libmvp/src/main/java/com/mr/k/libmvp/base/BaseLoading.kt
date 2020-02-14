package com.mr.k.libmvp.base

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.mr.k.libmvp.widget.LoadingView


/*
 * created by Cherry on 2019-12-25
**/
interface BaseLoading{

    var mLoadingView : LoadingView?

    // 如果不传id ,默认把loading 页挂载到 android.R.id.content 这个发 frame layout 上
    // 并让loading 背景透明
     fun showPopLoadingView(@IdRes parentId : Int = getDefaultRootVieId()) {
        showPopLoadingView(getParentForLoading(parentId))
    }


    // 如果不传id ,默认把loading 页挂载到 android.R.id.content 这个发 frame layout 上
    // 并让loading 背景透明
    fun showPopLoadingView(viewGroup: ViewGroup) {
        showLoadingView(LoadingView.MODE_TRANSPARENT_BACKGROUND,viewGroup)
    }


    // 如果不传id ,默认把loading 页挂载到 android.R.id.content 这个发frame layout 上,并让
    // loading 背景白色，不透明
    fun showFullLoadingView(@IdRes parentId : Int = getDefaultRootVieId()) {
        showFullLoadingView(getParentForLoading(parentId))
    }

    // 如果不传id ,默认把loading 页挂载到 android.R.id.content 这个发frame layout 上,并让
    // loading 背景白色，不透明
     fun showFullLoadingView(viewGroup: ViewGroup) {
        showLoadingView(LoadingView.MODE_WHITE_BACKGROUND,viewGroup)
    }

     fun closeLoadingView() {
        mLoadingView?.run {
            if(this.isShown){
                this.close()
            }
        }
    }
    // 显示错误页面，错误消息和错误icon 都是默认的
    fun showErrorLoadingView() {
        mLoadingView?.run {
            this.onError()
        }
    }

    // 显示错误页面，错误消息和错误icon 都是默认的
     fun showErrorLoadingView(retryListener: LoadingView.OnRetryListener) {
        mLoadingView?.run {
            this.onError(retryListener)
        }
    }
    // 显示错误页面,用指定的错误消息
    fun showErrorLoadingView(msg : String,retryListener: LoadingView.OnRetryListener){
        mLoadingView?.run {
            onError(msg,retryListener)
        }
    }

    // 显示错误页面,用指定的错误消息,和制定的错误图标
    fun showErrorLoadingView(msg : String ,@DrawableRes errorIconId : Int,retryListener: LoadingView.OnRetryListener){
        mLoadingView?.run {
            onError(msg,errorIconId,retryListener)
        }
    }

    fun <T : View> getViewById(id : Int) : T?

    fun getDefaultRootVieId() : Int{
        if(this is Activity){
            return android.R.id.content
        }else{
            val fragment = this as Fragment
            return fragment.view!!.id;
        }
    }

    //
   open fun getParentForLoading(@IdRes viewId : Int = android.R.id.content) : ViewGroup {

        return getViewById(viewId)!!
    }

    // 真正的把 loading page 显示到我们屏幕上
    private fun showLoadingView(@LoadingView.Mode mode : Int, parent : ViewGroup){
        mLoadingView = LoadingView.inject(parent);
        mLoadingView!!.showMode(mode)

    }
}