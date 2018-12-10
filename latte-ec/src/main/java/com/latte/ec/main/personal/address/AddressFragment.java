package com.latte.ec.main.personal.address;

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

import java.util.List;

public class AddressFragment extends LatteFragment implements ISuccess{
    private RecyclerView mRecyclerView;
    private AddressAdapter mAdapter;

    @Override
    public Object setLayout() {
        return R.layout.fragment_address;
    }



    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRecyclerView = $(R.id.rv_address);

        RestClient.Builder()
                .url("address.php")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data = new AddressDataConverter().setJsonData(response).convert();
        mAdapter = new AddressAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }
}
