package com.late.core.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Created by Administrator on 2018\11\6 0006.
 */

public abstract class BaseInterceptor implements Interceptor {

    //获取get请求参数
    protected LinkedHashMap<String, String> getUrlParameters(Chain chain){
        final HttpUrl url = chain.request().url();
        int size = url.querySize();
        final LinkedHashMap<String, String> params = new LinkedHashMap <>();
        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return params;
    }

    //获取get请求某key对应value
    protected String getUrlParameters(Chain chain, String key){
        final HttpUrl url = chain.request().url();
        return url.queryParameter(key);
    }

    //获取Post请求参数
    protected LinkedHashMap<String, String> getBodyParameters(Chain chain){
        final FormBody formBody = (FormBody) chain.request().body();
        int size = formBody.size();
        final LinkedHashMap<String, String> params = new LinkedHashMap <>();
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i), formBody.value(i));
        }
        return params;
    }
    //获取Post请求某key对应value
    protected String getBodyParameters(Chain chain, String key){
        return getBodyParameters(chain).get(key);
    }


}
