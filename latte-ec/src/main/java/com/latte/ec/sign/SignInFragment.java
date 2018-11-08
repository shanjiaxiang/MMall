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

import com.joanzapata.iconify.widget.IconTextView;
import com.late.core.fragments.LatteFragment;
import com.late.core.net.RestClient;
import com.late.core.net.callback.IError;
import com.late.core.net.callback.IFailure;
import com.late.core.net.callback.ISuccess;
import com.late.core.util.log.LatteLogger;
import com.latte.ec.R;

/**
 * Created by Administrator on 2018\11\7 0007.
 */

public class SignInFragment extends LatteFragment {

    private TextInputEditText mMail = null;
    private TextInputEditText mPassword = null;
    private AppCompatButton mSignIn = null;
    private AppCompatTextView mLinkSignUp = null;
    private IconTextView mWechatSignUp = null;

    private ISignListener mISignListener = null;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mISignListener = (ISignListener) activity;
        }
    }

    private boolean checkForm() {
        final String email = mMail.getText().toString();
        final String password = mPassword.getText().toString();
        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mMail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mMail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请至少输入6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }


    private void findMyView() {
        mMail = $(R.id.edit_sign_in_mail);
        mPassword = $(R.id.edit_sign_in_password);
        mSignIn = $(R.id.btn_sign_in);
        mLinkSignUp = $(R.id.tv_to_sign_up);
        mWechatSignUp = $(R.id.icon_sign_in_wechat);
    }


    public void onMyClick() {
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm()){
                    LatteLogger.d("USER_PROFILE", "start request ...");
                    RestClient.Builder()
                            .url("http://mock.fulingjie.com/mock/data/user_profile.json")
                            .params("email", mMail.getText().toString())
                            .params("password", mPassword.getText().toString())
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    Log.d("show", "onSuccess");
                                    LatteLogger.json("USER_PROFILE", response);
                                    //将返回的数据写入数据库
                                    SignHandler.onSignIn(response, mISignListener);
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
        mLinkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(new SignUpFragment(), SINGLETASK);
            }
        });
        mWechatSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        findMyView();
        onMyClick();
    }
}
