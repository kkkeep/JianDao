package com.jy.jiandao.data.ok;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.HttpResult;
import com.jy.jiandao.data.entity.User;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.jy.jiandao.AppConstant.Url.CHECK_VERIFICATION_CODE;
import static com.jy.jiandao.AppConstant.Url.GET_VERIFICATION_CODE;
import static com.jy.jiandao.AppConstant.Url.USER_REGISTER;

/*
 * created by Cherry on 2019-12-26
 **/
public interface ApiService {

    @POST
    Observable<HttpResult<String>> login(@FieldMap Map<String,String> params);
    @POST(GET_VERIFICATION_CODE)
    @FormUrlEncoded
    Observable<HttpResult<String>> getVerificationCode(@FieldMap Map<String,String> params);


    @POST(CHECK_VERIFICATION_CODE)
    @FormUrlEncoded
    Observable<HttpResult<String>> getCheckVerificationCode(@FieldMap Map<String,String> params);

    @POST(USER_REGISTER)
    @FormUrlEncoded
    Observable<HttpResult<User>> register(@FieldMap Map<String,String> params);

}
