package com.mr.k.libmvp.manager;

import com.mr.k.libmvp.Utils.DataFileCacheUtils;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.IUser;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MvpUserManager {

    public static volatile IUser mUser;

    private static Future<IUser> future;

    private static final String USER_CACHE_FILE = "user_cache_file";


    private static ReentrantLock mLock = new ReentrantLock();
    // 登录，注册 时调用

    public static void login(IUser user) {

        if (SystemFacade.isMainThread()) {
            throw new IllegalThreadStateException("MvpUserManager.saveUser(IUser user) 必须调用在子线程");
        }

        mUser = user;

        File file = SystemFacade.getExternalCacheDir(MvpManager.mContext, USER_CACHE_FILE);

        if (file != null && user != null) {
            DataFileCacheUtils.saveEncryptedDataToFile(file, user);
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


    public static <U extends IUser> U getUserFromSdard(Class<U> aClass) {
        if (SystemFacade.isMainThread()) {
            throw new IllegalThreadStateException("MvpUserManager.getUserFromSdard() 必须调用在子线程");
        }


        File file = SystemFacade.getExternalCacheDir(MvpManager.mContext, USER_CACHE_FILE);

        if (file != null) {
            return DataFileCacheUtils.getencryptedDataFromFile(file, aClass);
        }

        return null;
    }


    static <U extends IUser> void init(Class<U> aClass) {

      ExecutorService executorService =   Executors.newSingleThreadExecutor();

      future = executorService.submit(new Callable<IUser>() {
          @Override
          public IUser call() throws Exception {
              try{
                  mUser = getUserFromSdard(aClass);
                  return mUser;
              }finally {
                  executorService.shutdown();
              }

          }
      });


    }


    public  static  <T extends IUser> T getUser() {

        try{
            if(mUser == null && future != null){
                mUser = future.get();
                future = null;
            }
            return (T) mUser;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
        }
        return null;

    }

    public  static String getToke() {

        try {
            if(mUser == null){
                mUser = getUser();
                if(mUser == null){
                    return null;
                }
            }
            String toke = mUser.getTokenValue();
            if (toke == null) {
                loginOut();
            }

            return toke;
        }finally {

        }

    }


    public static boolean isLoginIn(){
        return getToke() != null;
    }


}
