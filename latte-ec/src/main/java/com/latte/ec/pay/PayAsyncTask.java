package com.latte.ec.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;
import com.late.core.ui.loader.LatteLoader;

/**
 * Created by Administrator on 2018\11\21 0021.
 */

public class PayAsyncTask extends AsyncTask<String, Void, String> {

    private final Activity ACTIVITY;
    private final IAliPayResultListener LISTENER;

    //订单支付成功
    private static final String ALI_PAY_STATUS_SUCCESS = "9000";
    //订单处理中
    private static final String ALI_PAY_STATUS_PAYING = "8000";
    //支付失败
    private static final String ALI_PAY_STATUS_FAIL = "4000";
    //用户中途取消
    private static final String ALI_PAY_STATUS_CANCEL = "6001";
    //网络错误
    private static final String ALI_PAY_STATUS_CONNECT_ERROR = "6002";

    public PayAsyncTask(Activity activity, IAliPayResultListener listener) {
        ACTIVITY = activity;
        LISTENER = listener;
    }


    @Override
    protected String doInBackground(String... params) {
        final String alPaySign = params[0];
        final PayTask payTask = new PayTask(ACTIVITY);
        return payTask.pay(alPaySign, true);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        LatteLoader.stopLoading();

        final PayResult payResult = new PayResult(result);
        //支付宝返回此次支付结果和价签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
        final String resultInfo = payResult.getResult();
        final String resultStatus = payResult.getResultStatus();

        switch (resultStatus){
            case ALI_PAY_STATUS_SUCCESS:
                if (LISTENER != null){
                    LISTENER.onPaySuccess();
                }
                break;
            case ALI_PAY_STATUS_PAYING:
                if (LISTENER != null){
                    LISTENER.onPaying();
                }
                break;
            case ALI_PAY_STATUS_FAIL:
                if (LISTENER != null){
                    LISTENER.onPayFail();
                }
                break;
            case ALI_PAY_STATUS_CANCEL:
                if (LISTENER != null){
                    LISTENER.onPayCancel();
                }
                break;
            case ALI_PAY_STATUS_CONNECT_ERROR:
                if (LISTENER != null){
                    LISTENER.onPayConnectError();
                }
                break;
            default:

                break;

        }


    }
}
