package com.mr.k.libmvp.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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

    private static final String ACTION_USER_LOGIN = "com.mr.k.libmvp.manager.user.login";
    private static final String ACTION_USER_LOGOUT = "com.mr.k.libmvp.manager.user.logout";

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


        // 发一个登录广播



        sentBroadcast(ACTION_USER_LOGIN);

    }


    // 退出登录
    public synchronized static void loginOut() {

        mUser = null;
        File file = SystemFacade.getExternalCacheDir(MvpManager.mContext, USER_CACHE_FILE);
        if (file.exists()) {
            file.delete();
        }

        sentBroadcast(ACTION_USER_LOGOUT);

        // 发一个登出广播
    }





    private static void sentBroadcast(String action){
       LocalBroadcastManager broadcastManager =  LocalBroadcastManager.getInstance(MvpManager.mContext);
       broadcastManager.sendBroadcast(new Intent(action));
    }



    public static UserStateChangeListener addUserStateChangeListener(@NonNull  UserStateChangeListener listener){

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(ACTION_USER_LOGOUT);
        intentFilter.addAction(ACTION_USER_LOGIN);

        LocalBroadcastManager.getInstance(MvpManager.mContext).registerReceiver(listener,intentFilter);
        return listener;
    }

    public static void removeUserStateChangeListener(@NonNull  UserStateChangeListener listener){

        LocalBroadcastManager.getInstance(MvpManager.mContext).unregisterReceiver(listener);
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


    public synchronized static boolean isLoginIn(){
        return getToke() != null;
    }



    public static class UserStateChangeListener extends BroadcastReceiver {

       public void onLogin(IUser user){

        }
       public void onLogout(){

        }

        @Override
        final public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()){

                case ACTION_USER_LOGIN:{
                    onLogin(mUser);
                    break;
                }
                case ACTION_USER_LOGOUT:{
                    onLogout();
                    break;
                }
            }

        }
    }




}
