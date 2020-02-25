package com.jy.jiandao;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * created by Cherry on 2019-12-26
 **/
public interface AppConstant {

    String BASE_URL = "https://www.seetao.com";
    //String BASE_URL = "http://test.seetaoism.com";

    String PLATFORM_ANDROID = "android";
    String LANG = "zh";


    public interface Url {

        String GET_VERIFICATION_CODE = "/api/sms/sendsms";
        String USER_REGISTER = "/api/user/register";
        String CHECK_VERIFICATION_CODE = "/api/sms/checksmscode";
        String PASSWORD_LOGIN = "/api/user/login";
        String COLUMN_LIST = "/api/column/columnmanagelist";
        String GET_NEWS = "/app/v_1_3/article/recommendlist";
        String GET_VIDEO_NEWS = "/app/v_1_3/article/videolist"; // 视频列表
        String GET_TOPIC_NEWS = "/app/v_1_3/article/speciallist"; // 视频列表
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

        String VIDEO_NEWS_START = RECOMMOND_NEWS_START_;
        String VIDEO_NEWS_NUMBER = RECOMMOND_NEWS_NUMBER;
        String VIDEO_NEWS_POINT_TIME = RECOMMOND_NEWS_POINT_TIME;




        String TOPIC_NEWS_START = RECOMMOND_NEWS_START_;
        String TOPIC_NEWS_NUMBER = RECOMMOND_NEWS_NUMBER;
        String TOPIC_NEWS_POINT_TIME = RECOMMOND_NEWS_POINT_TIME;
    }

    interface IntentKey {
        String PHONE_NUMBER = "phone";
    }



}
