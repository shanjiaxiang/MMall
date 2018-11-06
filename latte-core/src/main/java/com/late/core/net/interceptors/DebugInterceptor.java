package com.late.core.net.interceptors;

import android.support.annotation.RawRes;
import android.util.Log;

import com.late.core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018\11\6 0006.
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debug_url, int debug_raw_id) {
        DEBUG_URL = debug_url;
        DEBUG_RAW_ID = debug_raw_id;
    }

    //模拟请求返回内容
    private Response getResponse(Chain chain, String json){
        Log.d("show", "返回自定义内容...");
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    //指定json文件作为返回内容
    private Response debugResponse(Chain chain, @RawRes int rawId){
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);
    }

    //进行拦截
    @Override
    public Response intercept(Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        Log.d("show", "进入拦截器....");
        if (url.contains(DEBUG_URL)){
            Log.d("show", "进行拦截....");
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        //不进行拦截时执行原请求操作
        return chain.proceed(chain.request());
    }
}
