package com.mr.k.libmvp.Utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class DataFileCacheUtils {



    //

    /**
     * 把 json 转出指定的对象并返回
     * @param tClass 需要转的对象的class
     * @param jsonStr json 串
     */

    public static <T> T convertToDataFromJson(Class<T> tClass, String jsonStr) {

        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }

        Gson gson = new Gson();

        return gson.fromJson(jsonStr, tClass);
    }





    /**
     * 把 json 转出指定的对象List<T> 这种类型并返回，比如 List<Person>
     * @param tClass ：List里面泛型的class,比如 Person.class
     * @param jsonStr ： json 串
     */

    public static <T> List<T> convertToListFromJson(Class<T> tClass, String jsonStr) {

        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }


        Gson gson = new Gson();


        ParameterizedTypeImpl parameterizedType = new ParameterizedTypeImpl(List.class,new Type[]{tClass});

        return gson.fromJson(jsonStr, parameterizedType);
    }


    /**
     *  Map<String,Map<String,List<String>>>
     *
     *          ParameterizedTypeImpl listType = new ParameterizedTypeImpl(List.class,new Type[]{String.class}); // List<String>
     *
     *         ParameterizedTypeImpl mapInnerType = new ParameterizedTypeImpl(Map.class,new Type[]{String.class,listType}); // Map<String,List<String>
     *
     *         ParameterizedTypeImpl parameterizedType = new ParameterizedTypeImpl(Map.class,new Type[]{String.class,mapInnerType}); Map<String,Map<String,List<String>>
     *
     */

    //


    ///

    public static <K,V> Map<K,V> convertToMapFromJson(Class<K> keyClass, Class<V> vclass, String jsonStr) {


        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }

        Gson gson = new Gson();

        ParameterizedTypeImpl parameterizedType = new ParameterizedTypeImpl(Map.class,new Type[]{keyClass,vclass});


        return gson.fromJson(jsonStr, parameterizedType);
    }






    // 把一个对象转出json
    public static String convertJsonFromData(Object data){

        if(data == null) return null;

        Gson gson = new Gson();

        return gson.toJson(data);

    }


    public static class ParameterizedTypeImpl implements ParameterizedType{


        // List<Column>

        private final Class raw; // List.class
        private final Type[] args; // {Column.class}


        public ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args;
        }

        @NonNull
        @Override
        public Type[] getActualTypeArguments() {
            return args;// { Column.class}
        }

        @NonNull
        @Override
        public Type getRawType() {
            return raw; // List.class
        }

        @Nullable
        @Override
        public Type getOwnerType() {
            return null;
        }
    }






}
