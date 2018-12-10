package com.latte.ec.main.personal;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.late.core.fragments.LatteFragment;
import com.latte.ec.main.personal.list.ListBean;

public class PersonalClickListener extends SimpleClickListener {
    private final LatteFragment FRAGMENT;

    public PersonalClickListener(LatteFragment fragment) {
        this.FRAGMENT = fragment;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id =  bean.getmId();
        switch (id){
            case 2:
                FRAGMENT.getLatteParentFragment().getSupportDelegate().start(bean.getmFragment());
                break;
        }

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
