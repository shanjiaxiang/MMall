package com.late.core.app;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

/**
 * Created by Administrator on 2018\10\31 0031.
 */

public final class Latte {

    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<Object, Object> getConfigurations(){
        return Configurator.getInstance().getLatteConfigs();
    }

    public static <T> T getConfiguration(Enum<ConfigType> key){
        return (T) getConfigurations().get(key);
    }

    public static Context getApplication(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT);
    }

    public static Context getApplicationContext(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT);
    }

    public static Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    public static Handler getHander(){
        return getConfiguration(ConfigType.HANDLER);
    }

}
