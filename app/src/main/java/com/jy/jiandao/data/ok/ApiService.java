package com.jy.jiandao.data.ok;

import com.jy.jiandao.data.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.POST;

/*
 * created by Cherry on 2019-12-26
 **/
public interface ApiService {

    @POST
    Observable<HttpResult<String>> login(@FieldMap Map<String,String> params);


}
