package com.late.core.util.timer;

import java.util.TimerTask;

/**
 * Created by Administrator on 2018\11\6 0006.
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener mITimerListener) {
        this.mITimerListener = mITimerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null){
            mITimerListener.onTimer();
        }
    }
}
