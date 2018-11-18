package com.late.core.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.late.core.fragments.LatteFragment;
import com.late.core.web.WebFragment;
import com.late.core.web.WebFragmentImpl;

public class Router {
    public Router() {
    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    public final boolean handleWebUrl(WebFragment fragment, String url) {
        //如果是电话协议
        if (url.contains("tel:")) {
            callPhone(fragment.getContext(), url);
            return true;
        }
        final LatteFragment topFragment = fragment.getTopFragment();
        final WebFragmentImpl webFragment = WebFragmentImpl.create(url);
        topFragment.start(webFragment);
        return true;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("webview url is null");
        }
    }

    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public void loadPage(WebFragment fragment, String url){
        loadPage(fragment.getWebView(), url);
    }

    private void callPhone(Context context, String uri) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);
    }


}
