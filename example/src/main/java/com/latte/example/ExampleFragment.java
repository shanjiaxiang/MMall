package com.latte.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.late.core.fragments.LatteFragment;
import com.late.core.net.RestClient;
import com.late.core.net.callback.IError;
import com.late.core.net.callback.IFailure;
import com.late.core.net.callback.IRequest;
import com.late.core.net.callback.ISuccess;
import com.late.core.ui.loader.LoaderStyle;

/**
 * Created by Administrator on 2018\11\1 0001.
 */

public class ExampleFragment extends LatteFragment {


    @Override
    public Object setLayout() {
        return R.layout.fragment_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
//        testRestClientBuild();
    }

//    public void testRestClientBuild(){
//        RestClient.Builder()
//                .url("http://127.0.0.1/index")
//                .loader( LoaderStyle.BallBeatIndicator, getContext())
//                .onRequest(new IRequest() {
//                    @Override
//                    public void onRequestStart() {
//
//                    }
//
//                    @Override
//                    public void onRequestEnd() {
//
//                    }
//                })
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .failure(new IFailure() {
//                    @Override
//                    public void onFailure() {
//
//                    }
//                })
//                .error(new IError() {
//                    @Override
//                    public void onError(int code, String msg) {
//
//                    }
//                })
//                .build()
//                .get();
//    }



}
