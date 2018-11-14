package com.late.core.wechat.templates;

import com.late.core.activities.ProxyActivity;
import com.late.core.fragments.LatteFragment;
import com.late.core.wechat.BaseWXActivity;
import com.late.core.wechat.BaseWXEntryActivity;
import com.late.core.wechat.LatteWeChat;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * Created by Administrator on 2018\11\13 0013.
 */

public class WXEntryTemplate extends BaseWXEntryActivity{

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
