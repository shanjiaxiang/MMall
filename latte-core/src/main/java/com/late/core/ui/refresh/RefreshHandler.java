package com.late.core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.late.core.app.Latte;
import com.late.core.net.RestClient;
import com.late.core.net.callback.ISuccess;
import com.late.core.ui.recycler.DataConverter;
import com.late.core.ui.recycler.MultipleRecyclerAdapter;

/**
 * Created by Administrator on 2018\11\15 0015.
 */

public class RefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter;
    private final DataConverter CONVERTER;


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




    public static RefreshHandler create(SwipeRefreshLayout refresh_layout,
                                        RecyclerView recyclerView,
                                        DataConverter converter,
                                        PagingBean bean){
        return new RefreshHandler(refresh_layout, recyclerView, converter, bean);
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

    public void firstPage(String url){
        BEAN.setmDelayed(1000);
        RestClient.Builder()
                .url(url)
                .loader(Latte.getApplicationContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setmTotal(object.getInteger("total"))
                                .setmPageSize(object.getInteger("page_size"));
                        //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
