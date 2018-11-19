package com.late.core.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.late.core.fragments.LatteFragment;
import com.late.core.web.WebFragment;

public abstract class Event implements IEvent {

    private Context mContext = null;
    private String mAction = null;
    private WebFragment mFragment = null;
    private String mUrl = null;
    private WebView mWebView = null;

    public WebView getWebView(){
        return mFragment.getWebView();
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String mAction) {
        this.mAction = mAction;
    }

    public WebFragment getFragment() {
        return mFragment;
    }

    public void setFragment(WebFragment mFragment) {
        this.mFragment = mFragment;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
