package com.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.late.core.app.AccountManager;
import com.late.core.app.IUserChecker;
import com.late.core.fragments.LatteFragment;
import com.late.core.ui.launcher.ILauncherListener;
import com.late.core.ui.launcher.OnLauncherFinishTag;
import com.late.core.ui.launcher.ScrollLauncherTag;
import com.late.core.util.storage.LattePreference;
import com.late.core.util.timer.BaseTimerTask;
import com.late.core.util.timer.ITimerListener;
import com.latte.ec.R;

import java.text.MessageFormat;
import java.util.Timer;

/**
 * Created by Administrator on 2018\11\6 0006.
 */

public class LauncherFragment extends LatteFragment implements ITimerListener{

    AppCompatTextView mTvTimer = null;
    private Timer mTimer = null;
    private int mCount = 5;

    private ILauncherListener mILauncherListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    //初始化定时任务
    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initTimer();
        mTvTimer = $(R.id.tv_launcher_timer);
        //按钮监听
        mTvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimer != null){
                    mTimer.cancel();
                    mTimer = null;
                    checkIsShowScroll();
                }
            }
        });

    }

    //判断是否显示滑动启动页
    private void checkIsShowScroll(){
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
            start(new LauncherScrollFragment(), SINGLETASK);
        }else {
            //检查用户是否登录了APP
            AccountManager.checkAccount(new IUserChecker() {
                //已登录
                @Override
                public void onSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }
                //未登录
                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
    }

    //Timer具体执行内容，倒计时及计时结束后的跳转
    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null){
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0){
                        if (mTimer != null){
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}
