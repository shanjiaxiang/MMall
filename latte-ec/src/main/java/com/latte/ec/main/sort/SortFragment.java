package com.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.late.core.bottom.BottomItemFragment;
import com.latte.ec.R;
import com.latte.ec.main.sort.content.ContentFragment;
import com.latte.ec.main.sort.list.VerticalListFragment;

/**
 * Created by Administrator on 2018\11\14 0014.
 */

public class SortFragment extends BottomItemFragment {

    @Override
    public Object setLayout() {
        return R.layout.fragment_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListFragment listFragment = new VerticalListFragment();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container, listFragment);
        //设置右侧第一个分类显示，默认显示分类1
        getSupportDelegate().loadRootFragment(R.id.vertical_content_container, ContentFragment.getInstance(1));



    }
}
