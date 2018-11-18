package com.late.core.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.late.core.fragments.LatteFragment;
import com.late.core.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public abstract class WebFragment extends LatteFragment implements IWebViewInitializer{

    private WebView mWebView = null;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String mUrl = null;
    private boolean mIsWebViewAvailable = false;

    private LatteFragment mTopFragment = null;

    public WebFragment() {
    }

    public abstract IWebViewInitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mUrl = args.getString(RouteKeys.URL.name());
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        } else {
            final IWebViewInitializer initializer = setInitializer();
            if (initializer != null) {
                final WeakReference<WebView> webViewWeakReference =
                        new WeakReference<>(new WebView(getContext()), WEB_VIEW_QUEUE);
                mWebView = webViewWeakReference.get();
                mWebView = initializer.initWebView(mWebView);
                mWebView.setWebViewClient(initializer.initWebViewClient());
                mWebView.setWebChromeClient(initializer.initWebChromeClient());
                mWebView.addJavascriptInterface(LatteWebInterface.create(this), "latte");
                mIsWebViewAvailable = true;
            } else {
                throw new NullPointerException("WebView Initializer is null!");
            }
        }
    }

    public void setTopFragment(LatteFragment fragment){
        mTopFragment = fragment;
    }

    public LatteFragment getTopFragment(){
        if (mTopFragment == null){
            mTopFragment = this;
        }
        return mTopFragment;
    }


    public WebView getWebView() {
        if (mWebView == null) {
            throw new NullPointerException("WebView is null");
        }
        return mIsWebViewAvailable ? mWebView : null;
    }

    public String getUrl() {
        if (mUrl == null) {
            throw new NullPointerException("Url is null");
        }
        return mUrl;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}































