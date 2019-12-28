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
    }


    public interface RequestKey{

         String TIMESTAMP = "timestamp";
         String NONCE = "nonce";
         String LANG = "lang";
         String FROM = "from";
         String SIGNATURE = "signature";

         String AUTH_REGISTER_MOBILE = "mobile";
         String AUTH_REGISTER_TYPE = "type";
    }


}
