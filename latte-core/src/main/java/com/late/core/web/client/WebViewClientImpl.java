package com.late.core.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.late.core.app.ConfigType;
import com.late.core.app.Latte;
import com.late.core.ui.loader.LatteLoader;
import com.late.core.util.log.LatteLogger;
import com.late.core.web.IPageLoadListener;
import com.late.core.web.WebFragment;
import com.late.core.web.route.Router;


public class WebViewClientImpl extends WebViewClient {
    private final WebFragment FRAGMENT;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = Latte.getHander();

    public void setPageLoadListener(IPageLoadListener listener){
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

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading();
            }
        }, 1000);

    }
}
