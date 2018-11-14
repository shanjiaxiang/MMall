package com.late.core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;

import com.late.core.app.Latte;

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener{
    private final SwipeRefreshLayout REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout fresh_layout) {
        REFRESH_LAYOUT = fresh_layout;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    private void refresh(){
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        },2000);
    }


    @Override
    public void onRefresh() {
        refresh();
    }
}
