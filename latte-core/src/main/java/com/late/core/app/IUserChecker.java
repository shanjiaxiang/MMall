package com.late.core.app;

/**
 * 用户是否登录的回调接口
 * Created by Administrator on 2018\11\8 0008.
 */

public interface IUserChecker {
    void onSignIn();
    void onNotSignIn();
}
