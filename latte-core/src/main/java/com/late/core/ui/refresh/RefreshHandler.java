package com.late.core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;

import com.late.core.app.Latte;

/**
 * Created by Administrator on 2018\11\15 0015.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {
    private final SwipeRefreshLayout REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout refresh_layout) {
        REFRESH_LAYOUT = refresh_layout;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    private void refresh(){
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHander().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }


    @Override
    public void onRefresh() {
        refresh();
    }
}
