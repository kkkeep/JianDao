package com.jy.jiandao.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jy.jiandao.R;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/*
 * created by Cherry on 2019-12-31
 **/
public abstract class BaseAuthFragment<P extends IBaseMvpPresenter> extends BaseMvpFragment<P>{

    private ImageView mIvClose;
    private ImageView mIvWeChatLogin;
    private ImageView mIvQQLogin;
    private ImageView mIvSinaLogin;


    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIvClose = bindView(R.id.auth_iv_close, mOnClickListener);
        mIvWeChatLogin = bindView(R.id.auth_iv_wechat_login, mOnClickListener);
        mIvQQLogin = bindView(R.id.auth_iv_qq_login, mOnClickListener);
        mIvSinaLogin = bindView(R.id.auth_iv_sina_login, mOnClickListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };


    private View.OnClickListener mOnClickListener = (v) ->{

        switch (v.getId()){
            case R.id.auth_iv_close:{
                back();
                break;
            }
            case R.id.auth_iv_wechat_login:{
                UMShareAPI umShareAPI = UMShareAPI.get(getActivity());
                umShareAPI.getPlatformInfo(getActivity(),SHARE_MEDIA.QQ,umAuthListener);
                break;
            }
            case R.id.auth_iv_qq_login:{
                UMShareAPI umShareAPI = UMShareAPI.get(getActivity());
                umShareAPI.getPlatformInfo(getActivity(),SHARE_MEDIA.WEIXIN,umAuthListener);
                break;
            }

            case R.id.auth_iv_sina_login:{
                UMShareAPI umShareAPI = UMShareAPI.get(getActivity());
                umShareAPI.getPlatformInfo(getActivity(),SHARE_MEDIA.SINA,umAuthListener);
                break;
            }
        }
    };



}
