package com.latte.ec.launcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.late.core.fragments.LatteFragment;
import com.late.core.ui.launcher.ScrollLauncherTag;
import com.late.core.util.storage.LattePreference;
import com.late.core.util.timer.BaseTimerTask;
import com.late.core.util.timer.ITimerListener;
import com.latte.ec.R;

import java.text.MessageFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018\11\6 0006.
 */

public class LauncherFragment extends LatteFragment implements ITimerListener{

    AppCompatTextView mTvTimer = null;
    private Timer mTimer = null;
    private int mCount = 5;

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

        }
    }


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
