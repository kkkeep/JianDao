package com.mr.k.libmvp.base;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ParamsMap extends HashMap<String,String> {

    private String url;


    public ParamsMap(String url) {
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
