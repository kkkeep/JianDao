package com.mr.k.libmvp.manager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.IntDef;

import com.mr.k.libmvp.Utils.SpUtils;
import com.mr.k.libmvp.base.IUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MvpManager {


    public static final int REQUEST_FIRST_LOAD = 0x200; // 第一次加载请求
    public static final int REQUEST_REFRESH_LOAD = 0x300; // 刷新请求
    public static final int REQUEST_LOAD_MORE_LOAD = 0x400; // 加载更多请求


    public static final int RESPONSE_FROM_MEMORY = 0X100; // 数据从内存返回
    public static final int RESPONSE_FROM_SDCARD = 0X200; // 数据从sdcard 返回，
    public static final int RESPONSE_FROM_SERVER = 0X300; // 数据从服务器返回


    public static Context mContext;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({REQUEST_FIRST_LOAD, REQUEST_REFRESH_LOAD, REQUEST_LOAD_MORE_LOAD})
    public @interface RequestType {
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RESPONSE_FROM_MEMORY, RESPONSE_FROM_SDCARD, RESPONSE_FROM_SERVER})
    public @interface ResponseType {
    }


    public static <U extends IUser> void initMvp(Context context,Class<U> uClass){
        mContext = context.getApplicationContext();

        MvpUserManager.init(uClass);
    }


    public static boolean isFirstLaunch(){

        boolean init =  SpUtils.getBoolean("init"); // 返回false 没有初始值，表示第一启动

        if(!init){
            SpUtils.save("init",true);
            return true; // 第一次启动
        }else{
            return false; // 不是第一次启动
        }

    }


    public static boolean isAgreementForSplash() {
        return SpUtils.getBoolean("agreement"); // 返回false 没有初始值，表示没有同意协议,true 表示之前用户点击过同意

    }

    public static void agreeFroSpalsh(){
        SpUtils.save("agreement",true);
    }


}
