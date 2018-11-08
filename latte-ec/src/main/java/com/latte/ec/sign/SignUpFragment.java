package com.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.late.core.fragments.LatteFragment;
import com.late.core.net.RestClient;
import com.late.core.net.callback.IError;
import com.late.core.net.callback.IFailure;
import com.late.core.net.callback.ISuccess;
import com.late.core.util.log.LatteLogger;
import com.latte.ec.R;

import java.util.regex.Pattern;

public class SignUpFragment extends LatteFragment{

    private TextInputEditText mName = null;
    private TextInputEditText mMail = null;
    private TextInputEditText mPhone = null;
    private TextInputEditText mPassword = null;
    private TextInputEditText mRePassword = null;
    private AppCompatButton mSignUp = null;
    private AppCompatTextView mLinkSignIn = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mISignListener = (ISignListener) activity;
        }
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String email = mMail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();

        boolean isPass = true;
        if (name.isEmpty()) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mMail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mMail.setError(null);
        }

        //方便测试
        if (phone.isEmpty() || phone.length() < 2) {
            mPhone.setError("错误的手机号码");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请至少输入6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || !rePassword.equals(password)) {
            mRePassword.setError("两次输入密码不一致");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        findMyView();
        onMyClick();

    }

    private void findMyView() {
        mName = $(R.id.edit_sign_up_name);
        mMail = $(R.id.edit_sign_up_mail);
        mPhone = $(R.id.edit_sign_up_phone);
        mPassword = $(R.id.edit_sign_up_password);
        mRePassword = $(R.id.edit_sign_up_password2);
        mSignUp = $(R.id.btn_sign_up);
        mLinkSignIn = $(R.id.tv_already_sign_up);
    }


    public void onMyClick() {
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm()){
                    LatteLogger.d("USER_PROFILE", "start request ...");
                    RestClient.Builder()
                            .url("http://mock.fulingjie.com/mock/data/user_profile.json")
                            .params("name", mName.getText().toString())
                            .params("email", mMail.getText().toString())
                            .params("phone", mPhone.getText().toString())
                            .params("password", mPassword.getText().toString())
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    Log.d("show", "onSuccess");
                                    LatteLogger.json("USER_PROFILE", response);
                                    //将返回的数据写入数据库
                                    SignHandler.onSignUp(response, mISignListener);
                                }
                            })
                            .failure(new IFailure() {
                                @Override
                                public void onFailure() {
                                    Log.d("show", "onFailure");
                                    LatteLogger.d("USER_PROFILE", "onFailure");
                                }
                            })
                            .error(new IError() {
                                @Override
                                public void onError(int code, String msg) {
                                    Log.d("show", "onError");
                                    LatteLogger.d("USER_PROFILE", "onError");
                                }
                            })
                            .loader(getContext())
                            .build()
                            .get();
                }
            }
        });
        mLinkSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(new SignInFragment(), SINGLETASK);
            }
        });
    }
}
