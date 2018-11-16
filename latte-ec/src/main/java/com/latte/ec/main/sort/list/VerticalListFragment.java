package com.latte.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.late.core.fragments.LatteFragment;
import com.late.core.net.RestClient;
import com.late.core.net.callback.ISuccess;
import com.late.core.ui.recycler.MultipleItemEntity;
import com.latte.ec.R;
import com.latte.ec.main.sort.SortFragment;

import java.util.List;

/**
 * Created by Administrator on 2018\11\16 0016.
 */

public class VerticalListFragment extends LatteFragment {

    private RecyclerView mRecyclerView = null;

    private void bindViewId(){
        mRecyclerView = $(R.id.rv_vertical_menu_list);
        initRecyclerView();
    }

    private void initRecyclerView(){
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        //屏蔽动画效果
        mRecyclerView.setAnimation(null);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        bindViewId();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.Builder()
                .url("sort_list.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final List<MultipleItemEntity> data =  new VerticalListDataConverter().setJsonData(response).convert();
                        final SortFragment fragment = (SortFragment) getParentFragment();
                        final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data, fragment);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }
}
