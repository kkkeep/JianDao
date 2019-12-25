package com.mr.k.libmvp.base

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
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

     fun showErrorLoadingView() {
        mLoadingView?.run {
            this.onError()
        }
    }

    fun <T : View> getViewById(id : Int) : T?

    fun getDefaultRootVieId() : Int

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