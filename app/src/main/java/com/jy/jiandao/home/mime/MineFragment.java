package com.jy.jiandao.home.mime;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.core.content.FileProvider;

import com.jy.jiandao.GlideApp;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.User;
import com.mr.k.libmvp.Utils.PermissionUtils;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.BaseMvpFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.raphets.roundimageview.RoundImageView;

import java.io.File;
import java.io.FileNotFoundException;

public class MineFragment extends BaseMvpFragment<MineContract.IMinePresenter> implements MineContract.IMineView {



    private static final String USER_HEAD_IMG_PATH = "head";
    private static final String USER_HEAD_IMG_NAME = "head.jpg";

    private static final int CAMERA = 0X100;
    private static final int GALLERY = 0X101;

    private RoundImageView mRoundImageView;

    private Button mLoginInOut;



    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {

        mRoundImageView = findViewById(R.id.home_mine_heade_pic);

        mLoginInOut = findViewById(R.id.home_mine_login_in_out);


        mRoundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopView();
            }
        });
    }


    private void showPopView(){

        HeadPicPopView headPicPopView = new HeadPicPopView(getContext());

        headPicPopView.setOnClickListener(new HeadPicPopView.OnClickListener() {
            @Override
            public void onCamera() {
                checkCameraPermisss(CAMERA);
            }

            @Override
            public void onGallery() {

                checkCameraPermisss(GALLERY);


            }
        });
        headPicPopView.show(mRoundImageView);

    }




    private void checkCameraPermisss(int type){
        PermissionUtils permissionUtils = new PermissionUtils(this);

        String permiss [] = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};

        permissionUtils.checkPermission(getActivity(), new PermissionUtils.OnPermissionCallBack() {
            @Override
            public void onAllMustAccept() {

                if(type == GALLERY){
                    openGallery();
                }else {
                    openCamera();
                }

            }

            @Override
            public void shouldShowRationale(PermissionUtils.PermissionCall call) {

            }


            @Override
            public void shouldShowPermissionSetting() {

            }

            @Override
            public void onDenied() {
                showToast("需要相关权限才能操作");
            }
        },permiss, null);
    }


    private void openCamera(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //跳转到 ACTION_IMAGE_CAPTURE
        //判断内存卡是否可用，可用的话就进行存储
        //putExtra：取值，Uri.fromFile：传一个拍照所得到的文件，fileImg.jpg：文件名

        File path = SystemFacade.getExternalCacheDir(getContext(),USER_HEAD_IMG_PATH);

        if(!path.exists()){
            path.mkdir();
        }

        File file =  new File(path,USER_HEAD_IMG_NAME);
        Uri uri = null;

        if(SystemFacade.hasN()){
            uri = FileProvider.getUriForFile(getContext(),getContext().getPackageName() + ".userhead.HeadProvider",file);
        }else{
            uri = Uri.fromFile(file);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);

        startActivityForResult(intent,CAMERA);
    }

    private void openGallery(){
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        picture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        picture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(picture, GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_CANCELED){

            if(requestCode == CAMERA){



                File file =  SystemFacade.getExternalCacheDir(getContext(),USER_HEAD_IMG_PATH + File.separator + USER_HEAD_IMG_NAME);

                if(file != null && file .exists()){
                    //GlideApp.with(mRoundImageView).load(file).into(mRoundImageView);

                    uploadFile(file.getAbsolutePath());
                }



            }else if(requestCode == GALLERY){

                Uri uri = data.getData();

                if(uri != null){
                   // GlideApp.with(mRoundImageView).load(uri).into(mRoundImageView);
                    String filepPath = null;
                    if(uri.getScheme().equals("content")){
                        String [] keys = {MediaStore.MediaColumns.DATA};

                        ContentResolver contentResolver = getContext().getContentResolver();

                        Cursor cursor = contentResolver.query(uri,keys,null,null,null);


                        try {
                            if(cursor != null){
                                cursor.moveToFirst();

                                filepPath = cursor.getString(cursor.getColumnIndex(keys[0]));
                            }
                        }finally {
                            cursor.close();
                        }




                    }else {
                        filepPath = uri.getPath();
                    }
                    if(filepPath == null){
                        showToast("获取相册图片失败");
                        return;
                    }

                    GlideApp.with(mRoundImageView).load(filepPath).into(mRoundImageView);
                    uploadFile(filepPath);
                }
            }
        }
    }






    private void uploadFile(String file){

        mPresenter.uploadHeadPic(file);
    }



    @Override
    public MineContract.IMinePresenter createPresenter() {
        return new MinePresenter();
    }

    @Override
    public void onLoginOutSuccess() {

    }

    @Override
    public void onLoginOutFail() {

    }

    @Override
    public void onUpLoadHeadPicSuccess(User user) {

        GlideApp.with(mRoundImageView).load(user.getUserInfo().getHeadUrl()).into(mRoundImageView);
    }

    @Override
    public void onUpLoadHeadPicFail(String msg) {
        showToast(msg);

    }


    @Override
    public boolean isNeedAnimation() {
        return false;
    }

    @Override
    public boolean isAddBackStack() {
        return false;
    }


}
