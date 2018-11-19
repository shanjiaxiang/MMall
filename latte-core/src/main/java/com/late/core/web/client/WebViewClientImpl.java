package com.late.core.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.late.core.app.ConfigType;
import com.late.core.app.Latte;
import com.late.core.ui.loader.LatteLoader;
import com.late.core.util.log.LatteLogger;
import com.late.core.util.storage.LattePreference;
import com.late.core.web.IPageLoadListener;
import com.late.core.web.WebFragment;
import com.late.core.web.route.Router;


public class WebViewClientImpl extends WebViewClient {
    private final WebFragment FRAGMENT;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = Latte.getHander();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebFragment fragment) {
        FRAGMENT = fragment;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading", url);
        return Router.getInstance().handleWebUrl(FRAGMENT, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    //同步cookie,获取浏览器cookie
    private void syncCookie() {
        final CookieManager manager = CookieManager.getInstance();
        //注意此处的Cookie和API请求的Cookie是不一样的，这个在网页不可见
        final String webHost = Latte.getConfiguration(ConfigType.WEB_HOST);
        if (webHost != null) {
            if (manager.hasCookies()){
                final String cookieStr = manager.getCookie(webHost);
                if (cookieStr != null && !cookieStr.equals("")) {
                    LattePreference.addCustomAppProfile("cookie", cookieStr);
                }
            }
        }
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        syncCookie();
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading();
            }
        }, 1000);

    }
}
