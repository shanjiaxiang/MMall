package com.late.core.ui.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.late.core.app.Latte;
import com.late.core.net.RestClient;
import com.late.core.net.callback.IError;
import com.late.core.net.callback.ISuccess;
import com.late.core.ui.recycler.DataConverter;
import com.late.core.ui.recycler.MultipleRecyclerAdapter;
import com.late.core.util.log.LatteLogger;

/**
 * Created by Administrator on 2018\11\15 0015.
 */

public class RefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter;
    private final DataConverter CONVERTER;
    private Context mContext;


    public RefreshHandler(SwipeRefreshLayout refresh_layout,
                          RecyclerView recyclerView,
                          DataConverter converter,
                          PagingBean bean) {
        REFRESH_LAYOUT = refresh_layout;
        REFRESH_LAYOUT.setOnRefreshListener(this);

        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView, DataConverter converter) {
        return new RefreshHandler(swipeRefreshLayout, recyclerView, converter, new PagingBean());
    }

    public static RefreshHandler create(SwipeRefreshLayout refresh_layout,
                                        RecyclerView recyclerView,
                                        DataConverter converter,
                                        PagingBean bean) {
        return new RefreshHandler(refresh_layout, recyclerView, converter, bean);
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHander().postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行网络请求等操作

                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }

    public void firstPage(String url, Context context) {
        mContext = context;
        BEAN.setDelayed(1000);
        RestClient.Builder()
                .url(url)
                .loader(context)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                        LatteLogger.d("loadmore", "total:"+object.getInteger("total")
                                +"page_size:"+object.getInteger("page_size")+"index:"+BEAN.getPageIndex()
                        +"currentCount:"+ BEAN.getCurrentCount());
                    }
                })
                .build()
                .get();
    }

    private void paging(final String url) {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();

        if (mAdapter.getData().size() < pageSize || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
            LatteLogger.d("loadmore", "loadMoreEnd");
        } else {
            Latte.getHander().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient.Builder()
                            .url(url + index)
                            .loader(mContext)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    LatteLogger.json("loadmore", response);
                                    CONVERTER.clearData();
                                    mAdapter.addData(CONVERTER.setJsonData(response).convert());
                                    BEAN.setCurrentCount(mAdapter.getData().size());
                                    mAdapter.loadMoreComplete();
                                    BEAN.addIndex();
                                }
                            })
                            .error(new IError() {
                                @Override
                                public void onError(int code, String msg) {
                                    LatteLogger.d("loadmore", "loading error");
                                }
                            })
                            .build()
                            .get();
                }
            }, 1000);
        }
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        paging("refresh.php?index=");
    }
}