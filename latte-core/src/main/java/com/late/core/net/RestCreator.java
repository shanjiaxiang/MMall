package com.late.core.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.late.core.app.ConfigType;
import com.late.core.app.Latte;
import com.late.core.net.rx.RxRestService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.internal.functions.ObjectHelper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2018\11\2 0002.
 */

public class RestCreator {

    private static final class ParamsHolder{
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap <> ();
    }

    public static WeakHashMap<String, Object> getParams(){
        return ParamsHolder.PARAMS;
    }


    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class RetrofitHolder{
        private static final String BASE_URL = (String) Latte.getConfigurations()
                .get(ConfigType.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static final class OKHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();

        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfiguration(ConfigType.INTERCEPTOR);

        private static OkHttpClient.Builder addInterceptor(){
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()){

                for (Interceptor interceptor: INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            } else if (INTERCEPTORS == null){
                Log.d("Interceptor", "INTERCEPTORS为null");
//                Toast.makeText(Latte.getApplication(), "INTERCEPTORS为null", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("Interceptor", "INTERCEPTORS为empty");
//                Toast.makeText(Latte.getApplication(), "INTERCEPTORS为empty", Toast.LENGTH_SHORT).show();
            }
            return BUILDER;
        }
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE = RetrofitHolder
                .RETROFIT_CLIENT.create(RestService.class);
    }

    private static final class RxRestServiceHolder{
        private static final RxRestService REST_SERVICE = RetrofitHolder
                .RETROFIT_CLIENT.create(RxRestService.class);
    }

    public static RxRestService getRxRestService(){
        return RxRestServiceHolder.REST_SERVICE;
    }
}
