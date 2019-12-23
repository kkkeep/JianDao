package com.mr.k.libmvp.manager;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.mr.k.libmvp.base.BaseFragment;

import java.lang.ref.SoftReference;

/*
 * created by Cherry on 2019-11-26
 **/
public class MvpFragmentManager {


    private static SoftReference<BaseFragment> mPreFragmentReference = new SoftReference<>(null);

    public static BaseFragment addOrShowFragment(FragmentManager fragmentManager, Class<? extends BaseFragment> aClass, int containerId, Bundle args){

        return addOrShowFragment(fragmentManager, aClass, containerId, null,args);
    }

    /**
     * 1：
     * add --> remove
     * attach --> detach
     * show --> hide
     * replace (add + remove)
     * <p>
     * 2：
     * 如果一个fragment已经 被add 了， 那就不需要再次被 add（如果被隐藏了就显示）
     * 如果一个fragment 没有被add，那么就add，同时根据实际情况是否隐藏上一个fragment
     * <p>
     * 3：
     * 进出场动画
     * 4：
     * 回退栈
     */

    public static BaseFragment addOrShowFragment(FragmentManager fragmentManager, Class<? extends BaseFragment> aClass, int containerId,String tag, Bundle args) {

        if(TextUtils.isEmpty(tag)){
            tag = getFragmentTag(aClass);
        }

        BaseFragment baseFragment = null;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        try {
            // 查找fragment 时候能复用

            Fragment fragment = fragmentManager.findFragmentByTag(tag);

            if (fragment == null) {

                baseFragment = aClass.newInstance(); // new  一个 fragment 实例

                baseFragment.setArguments(args); // 设置参数

                fragmentTransaction.add(containerId, baseFragment, tag); // add 一个 fragment 并且 打一个 tag,方便下一次查找

                handLastFragment(fragmentManager, fragmentTransaction, baseFragment);// 隐藏之前的，否则有可能有重叠现象

                if (baseFragment.isAddBackStack()) { // 是否加入回退栈
                    fragmentTransaction.addToBackStack(tag); // 加入回退栈，记住加入回退栈的是事物本身，而不是不fragment 加入到了回退栈
                }
                fragmentTransaction.commit();

                mPreFragmentReference = new SoftReference<>(baseFragment);


            } else {
                // fragment 能被复用，就不需要 new 一个 新的
                baseFragment = (BaseFragment) fragment;

                int count = fragmentManager.getBackStackEntryCount();
                FragmentManager.BackStackEntry stackEntry = null;
                for (int i = 0; i < count; i++) {
                    stackEntry = fragmentManager.getBackStackEntryAt(i);
                    if (stackEntry.getName().equals(tag)) {
                        baseFragment.setArguments(args);
                        fragmentManager.popBackStackImmediate(tag, 0);
                        return baseFragment;
                    }
                }
                if (count > 0) { // 清空回退栈
                    fragmentManager.popBackStackImmediate(fragmentManager.getBackStackEntryAt(0).getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {

                    if (baseFragment.isDetached()) {
                        fragmentTransaction.attach(baseFragment);
                    } else if (baseFragment.isHidden()) {
                        fragmentTransaction.show(baseFragment);
                    }

                    handLastFragment(fragmentManager, fragmentTransaction, baseFragment);
                    baseFragment.setArguments(args);
                    fragmentTransaction.commit();

                    mPreFragmentReference = new SoftReference<>(baseFragment);
                }

                //}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return baseFragment;
    }


    private static void handLastFragment(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, BaseFragment fragment) {

        if (mPreFragmentReference != null && mPreFragmentReference.get() != null) {
            BaseFragment f = mPreFragmentReference.get();

            if(fragmentManager.findFragmentByTag(getFragmentTag(f.getClass())) != f){
                return;
            }
            if (fragment.getAction() == BaseFragment.Action.Hide) {
                if (!f.isHidden()) {
                    fragmentTransaction.hide(f);
                }
            } else if (fragment.getAction() == BaseFragment.Action.Remove) {
                if (f.isAdded()) {
                    fragmentTransaction.remove(f);
                }
            } else if (fragment.getAction() == BaseFragment.Action.Detach) {
                if (!f.isDetached()) {
                    fragmentTransaction.detach(f);
                }
            }
        }

    }


    private static String getFragmentTag(Class< ? extends BaseFragment> aClass){
        return aClass.getName();
    }
}
