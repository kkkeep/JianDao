package com.jy.jiandao.data.ok;

import com.jy.jiandao.data.HttpResult;
import com.jy.jiandao.data.entity.ColumnData;
import com.jy.jiandao.data.entity.RecommendPageData;
import com.jy.jiandao.data.entity.TopicPageData;
import com.jy.jiandao.data.entity.User;
import com.jy.jiandao.data.entity.VideoPageData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

import static com.jy.jiandao.AppConstant.Url.CHECK_VERIFICATION_CODE;
import static com.jy.jiandao.AppConstant.Url.COLUMN_LIST;
import static com.jy.jiandao.AppConstant.Url.GET_NEWS;
import static com.jy.jiandao.AppConstant.Url.GET_TOPIC_NEWS;
import static com.jy.jiandao.AppConstant.Url.GET_VERIFICATION_CODE;
import static com.jy.jiandao.AppConstant.Url.GET_VIDEO_NEWS;
import static com.jy.jiandao.AppConstant.Url.PASSWORD_LOGIN;
import static com.jy.jiandao.AppConstant.Url.USER_REGISTER;

/*
 * created by Cherry on 2019-12-26
 **/


public interface ApiService {

    @POST(PASSWORD_LOGIN)
    @FormUrlEncoded
    Observable<HttpResult<User>> login(@FieldMap Map<String,String> params);


    @POST(GET_VERIFICATION_CODE)
    @FormUrlEncoded
    Observable<HttpResult<String>> getVerificationCode(@FieldMap Map<String,String> params);


    @POST(CHECK_VERIFICATION_CODE)
    @FormUrlEncoded
    Observable<HttpResult<String>> getCheckVerificationCode(@FieldMap Map<String,String> params);

    @POST(USER_REGISTER)
    @FormUrlEncoded
    Observable<HttpResult<User>> register(@FieldMap Map<String,String> params);



    @GET(COLUMN_LIST)
    Observable<HttpResult<ColumnData>> getColumnList(@QueryMap Map<String,String> params);


    @GET(GET_NEWS)
    Observable<HttpResult<RecommendPageData>> getNews(@QueryMap Map<String,String> params);



    @GET(GET_VIDEO_NEWS)
    Observable<HttpResult<VideoPageData>> getVideoNews(@QueryMap Map<String,String> params);


    @GET(GET_TOPIC_NEWS)
    Observable<HttpResult<TopicPageData>> getTopicNews(@QueryMap Map<String,String> params);



    @GET()
    Observable<String>  get(@Url String url, @QueryMap Map<String,String> params);

    @GET()
    Observable<String> get(@Url String url,@HeaderMap Map<String,String> headers, @QueryMap Map<String,String> params);

    @POST()
    @FormUrlEncoded
    Observable<String> post(@Url String url,@FieldMap Map<String,String> params);

    @POST()
    @FormUrlEncoded
    Observable<String> post(@Url String url, @HeaderMap Map<String,String> headers,@FieldMap Map<String,String> params);

}
