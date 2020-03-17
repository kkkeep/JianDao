package com.jy.jiandao.home.mime;

import android.service.autofill.UserData;

import com.jy.jiandao.data.entity.User;
import com.mr.k.libmvp.base.IBaseCallBack;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;
import com.mr.k.libmvp.base.ParamsMap;
import com.trello.rxlifecycle2.LifecycleProvider;

public interface MineContract {



    interface IMineView extends IBaseMvpView<IMinePresenter>{


        void onLoginOutSuccess();
        void onLoginOutFail();

        void onUpLoadHeadPicSuccess(User user);

        void onUpLoadHeadPicFail(String msg);


    }


    interface IMinePresenter extends IBaseMvpPresenter<IMineView>{


        void loginOut();


        void uploadHeadPic(String filePath);



    }


    interface IMineRepository {
        void loginOut(LifecycleProvider provider, ParamsMap paramsMap, IBaseCallBack<String> callBack);


        void uploadHeadPic(LifecycleProvider provider, ParamsMap paramsMap,IBaseCallBack<User> callBack);
    }
}
