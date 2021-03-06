package com.mr.k.libmvp.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ParamsMap extends HashMap<String,String> {

    private String url;


    public ParamsMap(String url) {
        this.url = url;

    }

    public ParamsMap(@NonNull Map<? extends String, ? extends String> m) {
        super(m);
    }

    public ParamsMap(@NonNull Map<? extends String, ? extends String> m, String url) {
        super(m);
        this.url = url;
    }

    public ParamsMap() {
    }

    public String getUrl(){
        return url;
    }
}
