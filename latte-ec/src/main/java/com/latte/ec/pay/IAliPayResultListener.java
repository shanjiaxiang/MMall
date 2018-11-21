package com.latte.ec.pay;

/**
 * Created by Administrator on 2018\11\21 0021.
 */

public interface IAliPayResultListener {
    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
