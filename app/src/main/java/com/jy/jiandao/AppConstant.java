package com.jy.jiandao;

/*
 * created by Cherry on 2019-12-26
 **/
public interface AppConstant {

    String BASE_URL = "https://www.seetao.com";

    String PLATFORM_ANDROID = "android";
    String LANG = "zh";



    public interface Url{

        String GET_VERIFICATION_CODE = "/api/sms/sendsms";
        String USER_REGISTER = "/api/user/register";
        String CHECK_VERIFICATION_CODE = "/api/sms/checksmscode";
        String PASSWORD_LOGIN = "/api/user/login";
        String COLUMN_LIST = "/api/column/columnmanagelist";
        String GET_NEWS = "/app/v_1_3/article/recommendlist";
    }


    public interface RequestKey{

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

    }

    interface IntentKey{
        String PHONE_NUMBER = "phone";
    }


}
