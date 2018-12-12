package com.latte.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.late.core.fragments.LatteFragment;
import com.late.core.ui.recycler.MultipleFields;
import com.late.core.ui.recycler.MultipleItemEntity;
import com.late.core.util.log.LatteLogger;
import com.latte.ec.detail.GoodsDetailFragment;

/**
 * Created by Administrator on 2018\11\16 0016.
 */

public class IndexItemClickListener extends SimpleClickListener {

    private final LatteFragment FRAGMENT;

    public IndexItemClickListener(LatteFragment fragment) {
        this.FRAGMENT = fragment;
    }

    public static IndexItemClickListener create(LatteFragment fragment){
        return new IndexItemClickListener(fragment);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) adapter.getData().get(position);
        final int goodsId = entity.getField(MultipleFields.ID);
        final GoodsDetailFragment fragment = GoodsDetailFragment.create(goodsId);
        FRAGMENT.start(fragment);
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
