package com.jy.jiandao.data.ok.converter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/*
 * created by Cherry on 2020-01-10
 **/
public class MyGsonResponseBodyConverter <T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private ParameterizedType parameterizedType;
    private Type type;

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, Type type) {
        this.gson = gson;
        this.adapter = adapter;
        if(type instanceof ParameterizedType){
            this.parameterizedType = (ParameterizedType) type;
        }else {
            this.type = type;
        }

    }

    @Override public T convert(ResponseBody value) throws IOException {




        MediaType mediaType = value.contentType();
        Charset charset = null;
        if (mediaType != null) {
             charset = mediaType.charset(); // 得到服务器返回的json 串的字符编码
        }
        if (charset == null) {
            charset = StandardCharsets.UTF_8; // 如果服务器没有返回字符串编码，那么就用默认的编码
        }
        String json = new String(value.bytes(),charset);

        if(type != null && type == String.class &&  parameterizedType == null ){

            return (T)json;
        }

        try {
            JSONObject object = new JSONObject(json);
            Type [] types = parameterizedType.getActualTypeArguments();
            Type resultType = types[0]; // 由于我们httpresult 这个对象只有一个泛型参数，所以取第一个
            if(resultType == String.class){
                value = ResponseBody.create(mediaType,json); // 不需要从json 串里面删除 data
            }else{
                if(!object.isNull("code")){
                    int code = object.getInt("code");
                    if(code != 1) { // 如果服务器告诉请求失败
                        if(!object.isNull("data")){
                            String data  = object.getString("data");
                            if(TextUtils.isEmpty(data)){
                                object.remove("data");
                            }
                        }
                    }
                }
                value = ResponseBody.create(mediaType,object.toString()); // 不需要从json 串里面删除 data
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            return result;
        } finally {
            value.close();
        }
    }
}
