package com.late.core.net.callback;

import android.os.Handler;

import com.late.core.app.ConfigType;
import com.late.core.app.Latte;
import com.late.core.ui.loader.LatteLoader;
import com.late.core.ui.loader.LoaderStyle;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018\11\2 0002.
 */

public class RequestCallbacks implements Callback<String>{
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();


    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error, LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = style;
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            if (call.isExecuted()){
                if (SUCCESS != null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        }else {
            if (ERROR != null){
                ERROR.onError(response.code(), response.message());
            }
        }
        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }

        if (LOADER_STYLE != null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            }, (int)Latte.getConfigurations().get(ConfigType.LOADER_DELAY));
        }
    }

    @Override
    public void onFailure(Call <String> call, Throwable t) {
        if (FAILURE != null){
            FAILURE.onFailure();
        }

        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }
    }
}
