package com.mr.k.libmvp.manager;

import com.mr.k.libmvp.Utils.DataFileCacheUtils;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.IUser;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MvpUserManager {

    public static IUser mUser;

    private static final String USER_CACHE_FILE = "user_cache_file";


    private static Lock mLock = new ReentrantLock();
    // 登录，注册 时调用

    public static void login(IUser user) {
        if (SystemFacade.isMainThread()) {
            throw new IllegalThreadStateException("MvpUserManager.saveUser(IUser user) 必须调用在子线程");
        }

        mUser = user;

        File file = SystemFacade.getExternalCacheDir(MvpManager.mContext, USER_CACHE_FILE);

        if (file != null && user != null) {
            DataFileCacheUtils.saveDataToFile(file, user);
        }


    }


    // 退出登录
    public static void loginOut() {

        mUser = null;

        new Thread(new Runnable() {
            @Override
            public void run() {
                
                File file = SystemFacade.getExternalCacheDir(MvpManager.mContext, USER_CACHE_FILE);
                if (file.exists()) {
                    file.delete();
                }
            }
        }).start();

    }


    public static IUser getUserFromSdard() {
        if (SystemFacade.isMainThread()) {
            throw new IllegalThreadStateException("MvpUserManager.getUserFromSdard() 必须调用在子线程");
        }


        File file = SystemFacade.getExternalCacheDir(MvpManager.mContext, USER_CACHE_FILE);

        if (file != null) {
            return DataFileCacheUtils.getDataFromFile(file, IUser.class);
        }

        return null;
    }


    static void init() {
        mLock.lock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mUser = getUserFromSdard();
                mLock.unlock();
            }

        }).start();


    }


    public  static IUser getUser() {

        try{
            mLock.lock();
            return mUser;
        }finally {
            mLock.unlock();
        }

    }

    public  static String getToke() {
        mLock.lock();
        String toke = mUser.getTokenValue();
        if (toke == null) {
            loginOut();
        }
        mLock.unlock();

        return toke;
    }


}
