package com.jy.jiandao.home.mime;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.data.entity.User;
import com.jy.jiandao.data.repository.MineRepository;
import com.jy.jiandao.utils.ParamsUtils;
import com.mr.k.libmvp.base.BasePresenter;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.ParamsMap;
import com.mr.k.libmvp.exception.ResultException;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MinePresenter extends BasePresenter<MineContract.IMineView> implements MineContract.IMinePresenter {

    private MineContract.IMineRepository mineRepository;


    public MinePresenter() {

        mineRepository = new MineRepository();
    }

    @Override
    public void loginOut() {

    }

    @Override
    public void uploadHeadPic(String filePath) {


        ParamsMap paramsMap = new ParamsMap();

        paramsMap.put(AppConstant.RequestKey.MINE_FILE,filePath);

        paramsMap.putAll(ParamsUtils.getCommonParams());



        mineRepository.uploadHeadPic(getProvider(), paramsMap, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                if(mView != null){
                    mView.onUpLoadHeadPicSuccess(data);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onUpLoadHeadPicFail(e.getMessage());
                }
            }
        });

    }
}
