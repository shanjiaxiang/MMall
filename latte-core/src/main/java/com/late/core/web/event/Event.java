package com.late.core.web.event;

import android.content.Context;

import com.late.core.fragments.LatteFragment;

public abstract class Event implements IEvent {

    private Context mContext = null;
    private String mAction = null;
    private LatteFragment mFragment = null;
    private String mUrl = null;

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

    public LatteFragment getFragment() {
        return mFragment;
    }

    public void setFragment(LatteFragment mFragment) {
        this.mFragment = mFragment;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
