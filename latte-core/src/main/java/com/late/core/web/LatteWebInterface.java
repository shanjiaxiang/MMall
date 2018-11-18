package com.late.core.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.late.core.web.event.Event;
import com.late.core.web.event.EventManager;

public class LatteWebInterface {
    private final WebFragment FRAGMENT;


    public LatteWebInterface(WebFragment fragment) {
        FRAGMENT = fragment;
    }

    static LatteWebInterface create(WebFragment fragment) {
        return new LatteWebInterface(fragment);
    }

    @JavascriptInterface
    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getIntance().createEvent(action);
        if (event != null) {
            event.setAction(action);
            event.setFragment(FRAGMENT);
            event.setContext(FRAGMENT.getContext());
            event.setUrl(FRAGMENT.getUrl());
            return event.execute(params);
        }
        return null;
    }

}
