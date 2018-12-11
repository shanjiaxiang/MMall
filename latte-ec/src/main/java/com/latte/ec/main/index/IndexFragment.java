package com.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.late.core.bottom.BottomItemFragment;
import com.late.core.ui.recycler.BaseDecoration;
import com.late.core.ui.refresh.PagingBean;
import com.late.core.ui.refresh.RefreshHandler;
import com.late.core.util.callback.CallbackManager;
import com.late.core.util.callback.CallbackType;
import com.late.core.util.callback.IGlobalCallback;
import com.latte.ec.R;
import com.latte.ec.main.EcBottomFragment;

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

    @Override
    public Object setLayout() {
        return R.layout.fragment_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        viewBindId();
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecycleView, new IndexDataConverter());

        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
                    @Override
                    public void executeCallback(String args) {
                        Toast.makeText(getContext(), args, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("index.php", getContext());
    }

    private void initRefreshLayout(){
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }
    private void initRecyclerView(){
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.addItemDecoration(BaseDecoration.create(
                ContextCompat.getColor(getContext(), R.color.app_background), 5));
        final EcBottomFragment ecBottomFragment = getLatteParentFragment();

        mRecycleView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomFragment));
//        mRecycleView.set
    }



    private void viewBindId(){
        mRecycleView = $(R.id.rv_index);
        mRefreshLayout = $(R.id.srl_index);
        mToolbar = $(R.id.tb_index);
        mScanIcon = $(R.id.icon_index_scan);
        mSearchView = $(R.id.et_search_view);
        mMessageIcon = $(R.id.icon_index_message);

        mScanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanWithCheck(getLatteParentFragment());
            }
        });
    }
}
