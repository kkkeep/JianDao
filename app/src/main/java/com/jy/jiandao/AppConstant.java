package com.jy.jiandao;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * created by Cherry on 2019-12-26
 **/
public interface AppConstant {

    String BASE_URL = "https://www.seetao.com";

    String PLATFORM_ANDROID = "android";
    String LANG = "zh";


    public interface Url {

        String GET_VERIFICATION_CODE = "/api/sms/sendsms";
        String USER_REGISTER = "/api/user/register";
        String CHECK_VERIFICATION_CODE = "/api/sms/checksmscode";
        String PASSWORD_LOGIN = "/api/user/login";
        String COLUMN_LIST = "/api/column/columnmanagelist";
        String GET_NEWS = "/app/v_1_3/article/recommendlist";
    }


    public interface RequestKey {

        String TIMESTAMP = "timestamp";
        String NONCE = "nonce";
        String LANG = "lang";
        String FROM = "from";
        String SIGNATURE = "signature";

        String AUTH_REGISTER_MOBILE = "mobile";
        String AUTH_REGISTER_TYPE = "type";
        String VERIFICATION_CODE = "sms_code";
        String AUTH_REGISTER_PASSWORD = "password";
        String AUTH_REGISTER_CONFIRM_PASSWORD = "affirm_password";
        String PASSWORD_LOGIN_USER_NAME = "username";
        String PASSWORD_LOGIN_PASSWORD = AUTH_REGISTER_PASSWORD;


        String RECOMMOND_NEWS_COLUMN_ID = "id";
        String RECOMMOND_NEWS_START_ = "start";
        String RECOMMOND_NEWS_NUMBER = "number";
        String RECOMMOND_NEWS_POINT_TIME = "point_time";


    }

    interface IntentKey {
        String PHONE_NUMBER = "phone";
    }


    int REQUEST_FIRST_LOAD = 0x200; // 第一次加载请求
    int REQUEST_REFRESH_LOAD = 0x300; // 刷新请求
    int REQUEST_LOAD_MORE_LOAD = 0x400; // 加载更多请求



    int RESPONSE_FROM_MEMORY= 0X100; // 数据从内存返回
    int RESPONSE_FROM_SDCARD= 0X200; // 数据从sdcard 返回，
    int RESPONSE_FROM_SERVER = 0X300; // 数据从服务器返回

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({REQUEST_FIRST_LOAD, REQUEST_REFRESH_LOAD, REQUEST_LOAD_MORE_LOAD})
     @interface RequestType {
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RESPONSE_FROM_MEMORY, RESPONSE_FROM_SDCARD, RESPONSE_FROM_SERVER})
    @interface ResponseType{}


}
