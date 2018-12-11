package com.latte.ec.main.personal.order;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

public class OrderListClickListener extends SimpleClickListener {

    private final OrderListFramgent FRAGMENT;

    public OrderListClickListener(OrderListFramgent fragment) {
        FRAGMENT = fragment;
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        FRAGMENT.getSupportDelegate().start(new OrderCommendFragment());
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
