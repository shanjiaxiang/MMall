package com.late.core.wechat;

import android.app.Activity;

import com.late.core.app.ConfigType;
import com.late.core.app.Latte;
import com.late.core.wechat.callbacks.IWechatSignInCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2018\11\13 0013.
 */

public class LatteWeChat {
    static final String APP_ID = Latte.getConfiguration(ConfigType.WECHAT_APP_ID);
    static final String APP_SECRET = Latte.getConfiguration(ConfigType.WECHAT_APP_CECRET);
    private final IWXAPI WXAPI;

    private IWechatSignInCallback mSignInCallback = null;


    private static final class Holder{
        private static final LatteWeChat INSTANCE = new LatteWeChat();
    }

    public static LatteWeChat getInstance(){
        return Holder.INSTANCE;
    }

    private LatteWeChat(){
        final Activity activity = Latte.getConfiguration(ConfigType.ACTIVITY_WECHAT);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI(){
        return WXAPI;
    }

    public final void signIn(){
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }


    public IWechatSignInCallback getSignInCallback() {
        return mSignInCallback;
    }

    public LatteWeChat onSignInCallback(IWechatSignInCallback mSignInCallback) {
        this.mSignInCallback = mSignInCallback;
        return this;
    }
}
