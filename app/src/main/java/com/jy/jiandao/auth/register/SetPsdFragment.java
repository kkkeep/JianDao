package com.jy.jiandao.auth.register;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.R;
import com.jy.jiandao.auth.BaseAuthFragment;
import com.jy.jiandao.auth.login.PasswordLoginFragment;
import com.jy.jiandao.auth.login.VerificationLoginFragment;
import com.jy.jiandao.data.entity.User;
import com.mr.k.libmvp.Utils.Logger;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.base.IBaseMvpPresenter;
import com.mr.k.libmvp.manager.MvpFragmentManager;
import com.mr.k.libmvp.widget.EditCleanButton;
import com.mr.k.libmvp.widget.EditTogglePasswordButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * created by Cherry on 2019-12-30
 **/
public class SetPsdFragment extends BaseAuthFragment<RegisterContract.IRegisterSetPresenter> implements View.OnClickListener, RegisterContract.IRegisterSetView {

    private static final String TAG = "SetPsdFragment";

    private EditTogglePasswordButton mTogglePasswordButton;
    private EditTogglePasswordButton mToggleConfirmPasswordButton;

    private EditCleanButton mEditPasswordCleanButton;
    private EditCleanButton mEditConfirmPasswordCleanButton;

    private EditText mEdtPassword;
    private EditText mEdtConfirmPassword;

    private Button mBtnRegister;

    private TextView mTvUserAgreement;






    @Override
    public int getLayoutId() {

        return R.layout.fragment_set_password;
    }
    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        mTogglePasswordButton = bindView(R.id.auth_register_set_iv_toggle_psd);
        mToggleConfirmPasswordButton = bindView(R.id.auth_register_set_iv_toggle_confirm_psd);
        mEdtPassword = bindView(R.id.auth_register_set_edt_password);
        mEdtConfirmPassword = bindView(R.id.auth_register_set_edt_confirm_password);
        mEditPasswordCleanButton = bindView(R.id.auth_register_set_iv_clean_psd);
        mEditConfirmPasswordCleanButton = bindView(R.id.auth_register_set_iv_clean_confirm_psd);
        mBtnRegister = bindView(R.id.auth_register_set_btn_register,this);
        bindView(R.id.auth_register_set_tv_verification_code_login,this);
        bindView(R.id.auth_register_set_tv_password_login,this);
        mTvUserAgreement = bindView(R.id.auth_register_set_tv_user_agreement );

        mTogglePasswordButton.bindEditText(mEdtPassword);
        mEditPasswordCleanButton.bindEditText(mEdtPassword);
        mToggleConfirmPasswordButton.bindEditText(mEdtConfirmPassword);
        mEditConfirmPasswordCleanButton.bindEditText(mEdtConfirmPassword);

        mEdtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnRegister.setEnabled(!TextUtils.isEmpty(mEdtConfirmPassword.getText().toString().trim()));
            }
        });


        setUserAgreement();


    }

    private void setUserAgreement(){
        String content = getString(R.string.text_auth_register_set_user_agreement);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(content);

        stringBuilder.setSpan(new ClickableSpan(){

            @Override
            public void onClick(@NonNull View widget) {
                showToast("用户协议");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);// 设置可点击文字的颜色
                ds.setUnderlineText(false);// 取消下划线

            }
        }, 9, 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        stringBuilder.setSpan(new ClickableSpan(){
            @Override
            public void onClick(@NonNull View widget) {
                showToast("隐私声明");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);// 设置可点击文字的颜色
                ds.setUnderlineText(false); // 取消下划线

            }
        }, 14, 18, Spanned.SPAN_INCLUSIVE_INCLUSIVE);



        mTvUserAgreement.setText(stringBuilder);
        // 不设置这个，将不可点击
        mTvUserAgreement.setMovementMethod(LinkMovementMethod.getInstance());
    }



    @Override
    public RegisterContract.IRegisterSetPresenter createPresenter() {
        return new RegisterSetPresenter();
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.auth_register_set_btn_register:{
              // todo  需要判断两次密码是否一致

                Bundle bundle = getArguments();
                String phoneNum = null;
                if(bundle != null){
                     phoneNum = bundle.getString(AppConstant.IntentKey.PHONE_NUMBER);
                }
                if(TextUtils.isEmpty(phoneNum)){
                    throw new NullPointerException("跳转到设置页面需要传入手机号");
                }

                mPresenter.register(phoneNum , mEdtPassword.getText().toString().trim(),mEdtConfirmPassword.getText().toString().trim());

                break;
            }
            case R.id.auth_register_set_tv_verification_code_login:{
                MvpFragmentManager.addOrShowFragment(getFragmentManager(), VerificationLoginFragment.class,this,android.R.id.content);
                break;
            }
            case R.id.auth_register_set_tv_password_login:{
                MvpFragmentManager.addOrShowFragment(getFragmentManager(), PasswordLoginFragment.class,this,android.R.id.content);
                break;
            }



        }
    }

    @Override
    public void onRegisterResult(User user, String msg) {
        if(user != null){
            Logger.d("%s user = %s",TAG,user);
        }else{
            Logger.d("%s error = %s",msg);
        }
    }
}
