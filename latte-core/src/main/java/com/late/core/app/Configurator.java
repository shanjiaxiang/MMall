package com.late.core.app;

import android.util.Log;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by Administrator on 2018\10\31 0031.
 */

public class Configurator {

    //存储配置项
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap <>();
    //存储Icon描述字符串
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList <>();

    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList <>();


    //构造方法初始化，未配置完成
    private Configurator(){
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    //获取单例
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    //获得配置列表
    final HashMap<Object, Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }

    //静态内部类单例
    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    //配置完成
    public void configure(){
        initIcons();
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    //设置API
    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }

    //添加ICON
    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }

    //添加拦截器
    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    //添加多个拦截器ArrayList
    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    //添加dialog显示时间
    public final Configurator withDelayTime(int delayTime){
        LATTE_CONFIGS.put(ConfigType.LOADER_DELAY, delayTime);
        return this;
    }


    //初始化Iconify
    private void initIcons(){
        if (ICONS.size() > 0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i=1; i<ICONS.size(); i++){
                initializer.with(ICONS.get(i));
            }
        }
    }



    //检查是否配置完成
    private void checkConfiguration(){
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady){
            throw new RuntimeException("Configuration is not ready, call configure");
        }
    }

    //获取某一项配置值
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key.name());
    }

}
