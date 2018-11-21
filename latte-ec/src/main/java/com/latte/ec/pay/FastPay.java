package com.latte.ec.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.late.core.app.ConfigType;
import com.late.core.app.Latte;
import com.late.core.fragments.LatteFragment;
import com.late.core.net.RestClient;
import com.late.core.net.callback.ISuccess;
import com.late.core.ui.loader.LatteLoader;
import com.late.core.util.log.LatteLogger;
import com.late.core.wechat.LatteWeChat;
import com.latte.ec.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

public class FastPay implements View.OnClickListener{

    //设置支付回调监听
    private IAliPayResultListener mAliPayListener = null;
    private Activity mActivity = null;

    private AlertDialog mDialog = null;
    private int mOrderId = -1;

    public FastPay(LatteFragment fragment) {
        this.mActivity = fragment.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(fragment.getContext()).create();
    }

    public static FastPay create(LatteFragment fragment) {
        return new FastPay(fragment);
    }

    //显示支付选项页面
    public void beginPayDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);
            window.findViewById(R.id.btn_dialog_pay_alipay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);

        }
    }

    //设置订单id
    public FastPay setOrderId(int orderId){
        this.mOrderId = orderId;
        return this;
    }

    //设置支付结果监听
    public FastPay setPayResultListener(IAliPayResultListener listener){
        mAliPayListener = listener;
        return this;
    }

    //进行支付宝支付
    public final void aliPay(int orderId){
        //签名串，通过请求服务器获取
        final String singUrl = "你的服务端支付地址" + orderId;
        //获取签名字符串
        RestClient.Builder()
                .url(singUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //支付签名字符串
                        final String paySign = JSON.parseObject(response).getString("result");
                        //必须是异步的调用客户端支付接口
                        final PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity, mAliPayListener);
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySign);
                    }
                })
                .build()
                .post();
    }

    //微信支付
    private final void weChatPay(int orderId){
        LatteLoader.stopLoading();
        final String weChatPrePayUrl = "";
        LatteLogger.d("WX_PAY", weChatPrePayUrl);

        final IWXAPI iwxapi = LatteWeChat.getInstance().getWXAPI();
        final String appId = Latte.getConfiguration(ConfigType.WECHAT_APP_ID);
        iwxapi.registerApp(appId);

        RestClient.Builder()
                .url(weChatPrePayUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject result = JSON.parseObject(response).getJSONObject("result");
                        //
                        final String prepayId = result.getString("prepayid");
                        final String partnerId = result.getString("partnerid");
                        final String packageValue = result.getString("package");
                        final String timeStamp = result.getString("timestamp");
                        final String nonceStr = result.getString("noncestr");
                        final String paySign = result.getString("sign");

                        final PayReq payReq = new PayReq();
                        payReq.appId  = appId;
                        payReq.prepayId  = prepayId;
                        payReq.packageValue  = packageValue;
                        payReq.timeStamp  = timeStamp;
                        payReq.nonceStr  = nonceStr;
                        payReq.sign  = paySign;

                        iwxapi.sendReq(payReq);
                    }
                })
                .build()
                .post();
    }

    //支付选线该页面点击时间监听
    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if (id == R.id.btn_dialog_pay_alipay){
//            aliPay(mOrderId);
            mDialog.cancel();

        }else if (id == R.id.btn_dialog_pay_wechat){
            mDialog.cancel();

        }else if (id == R.id.btn_dialog_pay_cancel){
            mDialog.cancel();

        }
    }
}














