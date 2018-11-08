package com.latte.example;

import android.app.Application;

import com.diabin.fastec.example.R;
import com.facebook.stetho.Stetho;
import com.late.core.app.Latte;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.late.core.net.interceptors.DebugInterceptor;
import com.latte.ec.database.DatabaseManager;
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
        initStetho();
        DatabaseManager.getInstance().init(this);

    }

    // 初始化调试工具
    private void initStetho(){
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );
    }

}
