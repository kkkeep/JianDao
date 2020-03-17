package com.jy.jiandao.data.repository;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.User;
import com.jy.jiandao.data.ok.JDDataService;
import com.jy.jiandao.home.mime.MineContract;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.ParamsMap;
import com.mr.k.libmvp.manager.MvpUserManager;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MineRepository extends BaseRepository implements MineContract.IMineRepository {



    @Override
    public void loginOut(LifecycleProvider provider, ParamsMap paramsMap, IBaseCallBack<String> callBack) {

    }

    @Override
    public void uploadHeadPic(LifecycleProvider provider, ParamsMap paramsMap, IBaseCallBack<User> callBack) {



        File file = new File(paramsMap.get(AppConstant.RequestKey.MINE_FILE));
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileData = MultipartBody.Part.createFormData("file", file.getName(), body);


        HashMap<String,RequestBody> hashMap = new HashMap();

        for(Map.Entry<String,String> entry : paramsMap.entrySet()){

            if(!entry.getKey().equals(AppConstant.RequestKey.MINE_FILE)){

                RequestBody requestBody =  RequestBody.create(MediaType.parse("multipart/form-data"),entry.getValue());
                hashMap.put(entry.getKey(),requestBody);
            }
        }


        observer(provider,JDDataService.getApiService().uploadHead(hashMap,fileData),this::getConvertObservable,callBack);;



    }
}
