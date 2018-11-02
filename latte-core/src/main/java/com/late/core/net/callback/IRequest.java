package com.late.core.net.callback;

/**
 * 用于请求开始和结束的回调，例如：加载圈的显示
 * Created by Administrator on 2018\11\2 0002.
 */

public interface IRequest {
    void onRequestStart();
    void onRequestEnd();
}
