package com.mr.k.libmvp;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MvpManager {


    public static final int REQUEST_FIRST_LOAD = 0x200; // 第一次加载请求
    public static final int REQUEST_REFRESH_LOAD = 0x300; // 刷新请求
    public static final int REQUEST_LOAD_MORE_LOAD = 0x400; // 加载更多请求


    public static final int RESPONSE_FROM_MEMORY = 0X100; // 数据从内存返回
    public static final int RESPONSE_FROM_SDCARD = 0X200; // 数据从sdcard 返回，
    public static final int RESPONSE_FROM_SERVER = 0X300; // 数据从服务器返回

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({REQUEST_FIRST_LOAD, REQUEST_REFRESH_LOAD, REQUEST_LOAD_MORE_LOAD})
    public @interface RequestType {
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RESPONSE_FROM_MEMORY, RESPONSE_FROM_SDCARD, RESPONSE_FROM_SERVER})
    public @interface ResponseType {
    }
}
