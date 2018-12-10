package com.latte.ec.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.late.core.ui.recycler.DataConverter;
import com.late.core.ui.recycler.MultipleFields;
import com.late.core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public class AddressDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = array.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");
            final String phone = data.getString("phone");
            final String address = data.getString("address");
            final Boolean isDefault = data.getBoolean("default");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(AddressItemType.ADDRESS_ITEM_TYPE)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.NAME, name)
                    .setField(MultipleFields.TAG, isDefault)
                    .setField(AddressFields.ADDRESS,address)
                    .setField(AddressFields.PHONE, phone)
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
