package com.late.core.web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.late.core.web.chromeclient.WebChomeClientImpl;
import com.late.core.web.client.WebViewClientImpl;
import com.late.core.web.route.RouteKeys;
import com.late.core.web.route.Router;

public class WebFragmentImpl extends WebFragment {

    private IPageLoadListener mIPageLoadListener = null;

    public void setPageLoadListener(IPageLoadListener listener){
        this.mIPageLoadListener = listener;
    }

    public static WebFragmentImpl create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebFragmentImpl fragment = new WebFragmentImpl();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if (getUrl() != null) {
            //用原生的方式模拟Web跳转并进行页面加载
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        client.setPageLoadListener(mIPageLoadListener);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChomeClientImpl();
    }
}
