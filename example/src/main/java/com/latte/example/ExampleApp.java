package com.latte.example;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.late.core.app.Latte;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.late.core.net.interceptors.DebugInterceptor;
import com.late.core.net.rx.AddCookieInterceptor;
import com.latte.example.event.TestEvent;
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
                .withApiHost("http://mock.fulingjie.com/mock/api/")
                .withDelayTime(1000)
                .withWebHost("https://www.baidu.com/")
                //添加Cookie同步拦截器
                .withInterceptor(new AddCookieInterceptor())
//                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .withWeChatAppId("")
                .withWeChatSecret("")
                .withJavascriptInterface("latte")
                .withWebEvent("test", new TestEvent())
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
