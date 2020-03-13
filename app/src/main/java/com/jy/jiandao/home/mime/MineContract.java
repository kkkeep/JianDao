package com.jy.jiandao.home.mime;

import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.base.IBaseMvpView;

public interface MineContract {



    interface IMineView extends IBaseMvpView<IMinePresenter>{


        void onLoginOutSuccess();
        void onLoginOutFail();

        void onUpLoadHeadPicSuccess();

        void onUpLoadHeadPicFail();


    }


    interface IMinePresenter extends IBaseMvpPresenter<IMineView>{


        void loginOut();


        void uploadHeadPic();



    }
}
