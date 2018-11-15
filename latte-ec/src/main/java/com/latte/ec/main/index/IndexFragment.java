package com.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.late.core.bottom.BottomItemFragment;
import com.late.core.ui.refresh.RefreshHandler;
import com.latte.ec.R;

/**
 * Created by Administrator on 2018\11\14 0014.
 */

public class IndexFragment extends BottomItemFragment {
    RecyclerView mRecycleView = null;
    SwipeRefreshLayout mRefreshLayout = null;
    Toolbar mToolbar = null;
    IconTextView mScanIcon = null;
    AppCompatEditText mSearchView = null;
    IconTextView mMessageIcon = null;

    private RefreshHandler mRefreshHandler = null;




    private void initRefreshLayout(){
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        viewBindId();
        mRefreshHandler = new RefreshHandler(mRefreshLayout);

    }

    private void viewBindId(){
        mRecycleView = $(R.id.rv_index);
        mRefreshLayout = $(R.id.srl_index);
        mToolbar = $(R.id.tb_index);
        mScanIcon = $(R.id.icon_index_scan);
        mSearchView = $(R.id.et_search_view);
        mMessageIcon = $(R.id.icon_index_message);
    }
}
