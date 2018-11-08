package com.late.core.app;

import com.late.core.util.storage.LattePreference;

/**
 * Created by Administrator on 2018\11\8 0008.
 */

public class AccountManager {

    private enum SignTag{
        SIGN_TAG
    }

    //设置用户登录状态
    public static void setSignState(boolean state){
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    //获取用户登录状态
    public static boolean isSignIn(){
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }


    //判断是否已登录
    public static void checkAccount(IUserChecker checker){
        if (isSignIn()){
            checker.onSignIn();
        }else {
            checker.onNotSignIn();
        }
    }


}
