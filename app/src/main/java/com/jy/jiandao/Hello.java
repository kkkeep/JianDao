package com.jy.jiandao;

import java.util.HashMap;
import java.util.Map;

/*
 * created by Cherry on 2019-12-20
 **/
public class Hello {


    public static Map<String, Map<String,String>> depsMap = new HashMap();

    public static void main(String args []){

        Map<String,String > retrofitMap = new HashMap<>();
        retrofitMap.put("retrofit2", "com.squareup.retrofit2:retrofit:$versions.retrofit");
        retrofitMap.put("retrofit_converter_gson", "com.squareup.retrofit2:converter-gson:$versions.retrofit");
        retrofitMap.put("retrofit2_adapter_rxjava", "com.squareup.retrofit2:adapter-rxjava2:$versions.retrofit");


        Map<String,String> rxJavaMap = new HashMap<>();

        rxJavaMap.put("rxjava", "io.reactivex.rxjava2:rxjava:$versions.rxjava");
        rxJavaMap.put("rxAndroid", "io.reactivex.rxjava2:rxandroid:$versions.rxAndroid");
        rxJavaMap.put("rxPermissions", "com.github.tbruyelle:rxpermissions:$versions.rxpermissions");



        depsMap.put("retrofitX", retrofitMap);
        depsMap.put("rxjavaX", rxJavaMap);




        imp();



    }


    public static void imp(){

        System.out.println(depsMap.get("retrofitX").get("retrofit2"));
        System.out.println(depsMap.get("retrofitX").get("retrofit_converter_gson"));
        System.out.println(depsMap.get("retrofitX").get("retrofit2_adapter_rxjava"));
    }
}
