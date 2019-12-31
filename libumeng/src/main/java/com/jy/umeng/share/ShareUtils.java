package com.jy.umeng.share;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.bean.PlatformName;

/*
 * created by Cherry on 2019-11-27
 **/
public class ShareUtils {


    public static void init(Context context){

        UMConfigure.setLogEnabled(true);
        //平台表单名称
        PlatformName.SINA="新浪微博";
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */

        UMConfigure.init(context,"5da416070cafb26975000f3f","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");



        //第一个参数表示 AppID ，第二：AppSecret
        PlatformConfig.setWeixin("wxc18a6ee8aede4929", "cc1459aa71cc4c3ea24232d62df6231d"); //

        //第一个参数表示 APP ID ，第二：APP KEY
        PlatformConfig.setQQZone("1109759123", "a3x6lGx32PYMrmK3");

        // 第一个参数表示 APP ID ，第二：App Secret，第三： 回调 地址
        PlatformConfig.setSinaWeibo("110298133","afb59986db07d0b9b0886e9980d28d1c","https://www.seetao.com");

    }
}
