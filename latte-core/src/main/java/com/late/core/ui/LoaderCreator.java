package com.late.core.ui;

import android.content.Context;
import android.util.Log;

import com.late.core.app.Latte;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Created by Administrator on 2018\11\2 0002.
 */

public final class LoaderCreator {

    //存储样式名称字符串及对应的Indicator
    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap <>();

    //创建AVLoadingIndicatorView
    static AVLoadingIndicatorView create(String type, Context context){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null){
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type, indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    //通过样式名称字符串获取其indicator
    private static Indicator getIndicator(String name){
        if (name == null || name.isEmpty())
            return null;
        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")){
            //通过反射获得报名
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            //组装indicator
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);

        //查找样式对应类
        try {
            final Class <?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
