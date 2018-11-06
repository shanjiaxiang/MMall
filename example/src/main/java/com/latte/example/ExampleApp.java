package com.latte.example;

import android.app.Application;

import com.diabin.fastec.example.R;
import com.late.core.app.Latte;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.late.core.net.interceptors.DebugInterceptor;
import com.latte.ec.icon.FontEcModule;

/**
 * Created by Administrator on 2018\10\31 0031.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1/")
                .withDelayTime(1000)
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();
    }
}
