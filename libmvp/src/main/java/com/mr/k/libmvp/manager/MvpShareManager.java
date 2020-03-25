package com.mr.k.libmvp.manager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.jy.umeng.share.LinkData;
import com.jy.umeng.share.ShareUtils;
import com.mr.k.libmvp.Utils.PermissionUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;

public class MvpShareManager {




    public static void init(Context context){

        ShareUtils.init(context);

    }

    /**
     * 分享超链接 到指定平台，不带面板
     */
    public static void shareLinkDirectly(FragmentActivity activity, LinkData data, UMShareListener listener, SHARE_MEDIA platform){

        if(platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.QZONE){

            new PermissionUtils(activity).checkPermission(activity, new PermissionUtils.OnPermissionCallBack() {
                @Override
                public void onAllMustAccept() {
                    shareLinkDirectlyInner(activity,data,listener,platform);

                }

                @Override
                public void shouldShowRationale(PermissionUtils.PermissionCall call) {

                }

                @Override
                public void shouldShowPermissionSetting() {

                }

                @Override
                public void onDenied() {
                    listener.onError(platform,new RuntimeException(data.permisssonMsg));

                }
            },new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},null);
        }else{
            shareLinkDirectlyInner(activity,data,listener,platform);
        }
    }

    /**
     * 分享超链接 代面板
     */
    private static void shareLinkByPanel(FragmentActivity activity, LinkData data,UMShareListener listener,SHARE_MEDIA ... platforms){

        boolean need = false;
        for(SHARE_MEDIA platform : platforms){

            if(platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.QZONE){
                need = true;
                break;
            }

        }

        if(need){

            new PermissionUtils(activity).checkPermission(activity, new PermissionUtils.OnPermissionCallBack() {
                @Override
                public void onAllMustAccept() {
                    shareLinkByPanelInner(activity,data,listener,platforms);

                }

                @Override
                public void shouldShowRationale(PermissionUtils.PermissionCall call) {

                }

                @Override
                public void shouldShowPermissionSetting() {

                }

                @Override
                public void onDenied() {
                    listener.onError(SHARE_MEDIA.QQ,new RuntimeException(data.permisssonMsg));

                }
            },new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},null);
        }else{
            shareLinkByPanelInner(activity,data,listener,platforms);
        }
    }




    private static void shareLinkDirectlyInner(Activity activity, LinkData data, UMShareListener listener,SHARE_MEDIA platform){

        UMWeb web = new UMWeb(data.url);

        if(!TextUtils.isEmpty(data.title)){
            web.setTitle(data.title);//标题
        }


        if(data.thumb != null ){
            UMImage image = null;
            if( data.thumb instanceof String){
                image = new UMImage(activity, data.thumb.toString());
            }else if(data.thumb instanceof Integer){
                image = new UMImage(activity, (Integer) data.thumb);
            }else if(data.thumb instanceof File){
                image = new UMImage(activity, (File) data.thumb);
            }else if(data .thumb instanceof Bitmap){
                image = new UMImage(activity, (Bitmap) data.thumb);
            }else if(data.thumb instanceof byte []){
                image = new UMImage(activity,(byte []) data.thumb);
            }

            if(image != null){
                web.setThumb(image);  //缩略图
            }
        }

        if(!TextUtils.isEmpty(data.description)){
            web.setDescription(data.description);//描述
        }

        new ShareAction(activity).withMedia(web).setPlatform(platform).share();

    }

    private static void shareLinkByPanelInner(Activity activity, LinkData data,UMShareListener listener,SHARE_MEDIA ... platforms){

        UMWeb web = new UMWeb(data.url);

        if(!TextUtils.isEmpty(data.title)){
            web.setTitle(data.title);//标题
        }


        if(data.thumb != null ){
            UMImage image = null;
            if( data.thumb instanceof String){
                image = new UMImage(activity, data.thumb.toString());
            }else if(data.thumb instanceof Integer){
                image = new UMImage(activity, (Integer) data.thumb);
            }else if(data.thumb instanceof File){
                image = new UMImage(activity, (File) data.thumb);
            }else if(data .thumb instanceof Bitmap){
                image = new UMImage(activity, (Bitmap) data.thumb);
            }else if(data.thumb instanceof byte []){
                image = new UMImage(activity,(byte []) data.thumb);
            }

            if(image != null){
                web.setThumb(image);  //缩略图
            }
        }

        if(!TextUtils.isEmpty(data.description)){
            web.setDescription(data.description);//描述
        }

        new ShareAction(activity).withMedia(web).setDisplayList(platforms).setCallback(listener).open();

    }

}
