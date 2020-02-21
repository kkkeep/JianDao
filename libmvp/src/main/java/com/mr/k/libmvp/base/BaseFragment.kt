package com.mr.k.libmvp.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.NO_ID
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.ContentFrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.mr.k.libmvp.R
import com.mr.k.libmvp.Utils.Logger

import com.mr.k.libmvp.manager.MvpFragmentManager
import com.mr.k.libmvp.widget.LoadingView
import com.trello.rxlifecycle2.components.support.RxFragment

/*
 * created by Cherry on 2019-12-20
 **/
abstract class BaseFragment : RxFragment(), BaseLoading {


    override var mLoadingView: LoadingView? = null;


    private lateinit var mHostActivity: Activity

    abstract val layoutId: Int

    open val isAddBackStack: Boolean
        get() = true

    open val action: Action
        get() = Action.Hide


    override fun onAttach(activity: Context) {
        super.onAttach(activity)

        mHostActivity = activity as Activity
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        doOnCreate(inflater, container, savedInstanceState)

        val view: View

        val rootView = inflater.inflate(layoutId, container, false)


        val parentName = rootView.javaClass.simpleName
        if (parentName == FrameLayout::class.java.simpleName ||
            parentName == RelativeLayout::class.java.simpleName ||
            parentName == ConstraintLayout::class.java.simpleName ||
            parentName == ContentFrameLayout::class.java.simpleName
        ) {

            view = rootView
        } else {
            val frameLayout = FrameLayout(context!!)

            frameLayout.layoutParams = rootView.layoutParams

            frameLayout.addView(rootView)

            view = frameLayout

        }

        if (view.id == View.NO_ID) {
            view.id = 0x10000000
        }

        return view

    }


    fun <V : View> findViewById(@IdRes id: Int) : V{
        return view!!.findViewById<V>(id)
    }

    fun getRootViewId() : Int{
         view?.run {
            return this.id
        }
        return android.R.id.content
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
        loadData()
    }


    @JvmOverloads
    protected fun <V : View>bindView(@IdRes id: Int,listener: View.OnClickListener? = null) : V{

        val view = view!!.findViewById<V>(id);

        listener?.apply {
            view.setOnClickListener(this);
        }
        return view

    }
    protected open fun doOnCreate(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
    }

    protected open fun loadData() {}

    protected open fun initView(view: View, savedInstanceState: Bundle?) {}


    protected fun back(){
        activity?.onBackPressed()
    }
    protected fun showToast(@StringRes id: Int) {
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show()
    }

    protected fun showToast(content: String) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()

    }

    // 显示一个fragment 时，对上一个fragment 做的处理
    enum class Action {
        Remove, Detach, Hide,NONE
    }

/*
    override fun getDefaultRootVieId(): Int {
        return view!!.id
    }*/

    override fun <T : View> getViewById(id: Int): T? {

        var v: T? = view!!.findViewById(id)

        if (v == null) {
            v = mHostActivity.findViewById<T>(id);
        }
        return v

    }



    open fun getEnter() = R.anim.common_page_right_in
    open fun getExit() = R.anim.common_page_left_out
    open fun popExit() = R.anim.common_page_right_out
    open fun popEnter() = R.anim.common_page_left_in




}
