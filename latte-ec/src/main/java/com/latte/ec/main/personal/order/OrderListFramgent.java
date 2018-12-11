package com.latte.ec.main.personal.order;

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
import com.latte.ec.main.personal.PersonalFragment;

import java.util.List;

/**
 * Created by Administrator on 2018\11\22 0022.
 */

public class OrderListFramgent extends LatteFragment {

    private String mType = null;
    private RecyclerView mRecyclerView = null;

    private void bindViewId(){
        mRecyclerView = $(R.id.rv_order_list);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mType = args.getString(PersonalFragment.ORDER_TYPE);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        bindViewId();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.Builder()
                .url("order_list.php")
                .loader(getContext())
                .params("type",mType)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final List<MultipleItemEntity> data = new OrderListDataConverter().setJsonData(response).convert();
                        OrderListAdapter adapter = new OrderListAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.addOnItemTouchListener(new OrderListClickListener(OrderListFramgent.this));
                    }
                })
                .build()
                .get();
    }
}
