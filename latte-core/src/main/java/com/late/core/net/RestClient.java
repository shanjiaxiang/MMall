package com.late.core.net;

import android.content.Context;

import com.late.core.net.callback.IError;
import com.late.core.net.callback.IFailure;
import com.late.core.net.callback.IRequest;
import com.late.core.net.callback.ISuccess;
import com.late.core.net.callback.RequestCallbacks;
import com.late.core.ui.LatteLoader;
import com.late.core.ui.LoaderStyle;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018\11\2 0002.
 */

@SuppressWarnings("FieldCanBeLocal")
public class RestClient {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;

    RestClient(String url,
               WeakHashMap <String, Object> params,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body,
               LoaderStyle style,
               Context context) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.LOADER_STYLE = style;
        this.CONTEXT = context;
    }

    public static RestClientBuilder Builder(){
        return new RestClientBuilder();
    }

    private void request(HttpMethod method){
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null){
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null){
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        } else {
            LatteLoader.showLoading(CONTEXT, LoaderStyle.BallBeatIndicator);
        }

        switch (method){
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            default:
                break;
        }

        if (call != null){
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback(){
        return new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE);
    }

    public final void get(){
        request(HttpMethod.GET);
    }
    public final void put(){
        request(HttpMethod.PUT);
    }
    public final void post(){
        request(HttpMethod.POST);
    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }

}
