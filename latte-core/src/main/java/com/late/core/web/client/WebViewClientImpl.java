package com.late.core.web.client;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.late.core.util.log.LatteLogger;
import com.late.core.web.WebFragment;
import com.late.core.web.route.Router;

public class WebViewClientImpl extends WebViewClient {
    private final WebFragment FRAGMENT;


    public WebViewClientImpl(WebFragment fragment) {
        FRAGMENT = fragment;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading", url);
        return Router.getInstance().handleWebUrl(FRAGMENT, url);
    }
}
