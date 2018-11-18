package com.late.core.web.chromeclient;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebChomeClientImpl extends WebChromeClient{

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);

    }
}
