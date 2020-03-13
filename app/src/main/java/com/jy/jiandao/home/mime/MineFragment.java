package com.jy.jiandao.home.mime;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.jy.jiandao.R;
import com.mr.k.libmvp.Utils.PermissionUtils;
import com.mr.k.libmvp.base.BaseMvpFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.raphets.roundimageview.RoundImageView;

import java.io.File;

public class MineFragment extends BaseMvpFragment<MineContract.IMinePresenter> implements MineContract.IMineView {



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
                checkCameraPermisss();
            }

            @Override
            public void onGallery() {

            }
        });
        headPicPopView.show(mRoundImageView);

    }




    private void checkCameraPermisss(){
        PermissionUtils permissionUtils = new PermissionUtils(this);

        String permiss [] = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};

        permissionUtils.checkPermission(getActivity(), new PermissionUtils.OnPermissionCallBack() {
            @Override
            public void onAllMustAccept() {

                openCamera();

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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"fileImg.jpg")));
        startActivityForResult(intent,101); // 101: 相机的返回码参数（随便
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public MineContract.IMinePresenter createPresenter() {
        return null;
    }

    @Override
    public void onLoginOutSuccess() {

    }

    @Override
    public void onLoginOutFail() {

    }

    @Override
    public void onUpLoadHeadPicSuccess() {

    }

    @Override
    public void onUpLoadHeadPicFail() {

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
