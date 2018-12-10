package com.latte.ec.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.late.core.net.RestClient;
import com.late.core.net.callback.ISuccess;
import com.late.core.ui.recycler.MultipleFields;
import com.late.core.ui.recycler.MultipleItemEntity;
import com.late.core.ui.recycler.MultipleRecyclerAdapter;
import com.late.core.ui.recycler.MultipleViewHolder;
import com.latte.ec.R;

import java.util.List;

public class AddressAdapter extends MultipleRecyclerAdapter {

    protected AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ADDRESS_ITEM_TYPE, R.layout.item_address);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case AddressItemType.ADDRESS_ITEM_TYPE:
                final int id = entity.getField(MultipleFields.ID);
                final String name = entity.getField(MultipleFields.NAME);
                final boolean isDefault = entity.getField(MultipleFields.TAG);
                final String address = entity.getField(AddressFields.ADDRESS);
                final String phone = entity.getField(AddressFields.PHONE);

                final AppCompatTextView nameText = holder.getView(R.id.tv_address_name);
                final AppCompatTextView phoneText = holder.getView(R.id.tv_address_phone);
                final AppCompatTextView addressText = holder.getView(R.id.tv_address_address);
                final AppCompatTextView deleteTextView = holder.getView(R.id.tv_address_delete);

                nameText.setText(name);
                phoneText.setText(phone);
                addressText.setText(address);


                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RestClient.Builder()
                                .url("address.php")
                                .params("id", id)
                                .loader(view.getContext())
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        remove(holder.getLayoutPosition());
                                    }
                                })
                                .build()
                                .post();
                    }
                });
                break;
            default:
                break;
        }


    }
}
