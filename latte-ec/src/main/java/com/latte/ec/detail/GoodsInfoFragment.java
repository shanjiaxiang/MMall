package com.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.late.core.fragments.LatteFragment;
import com.latte.ec.R;

public class GoodsInfoFragment extends LatteFragment{

    public static final String ARG_GOODS_DESC = "goods_desc";
    private JSONObject mData = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        final String goodsData;
        if (args !=null) {
            goodsData = args.getString(ARG_GOODS_DESC);
            mData = JSON.parseObject(goodsData);
        }
    }

    public static GoodsInfoFragment create(String goodsInfo){
        final Bundle args = new Bundle();
        args.putString(ARG_GOODS_DESC, goodsInfo);
        final GoodsInfoFragment fragment = new GoodsInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_goods_info;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final String name = mData.getString("name");
        final String desc = mData.getString("description");
        final double price = mData.getDouble("price");

        final AppCompatTextView goodInfoTitle = $(R.id.tv_goods_info_title);
        final AppCompatTextView goodInfoDesc = $(R.id.tv_goods_info_desc);
        final AppCompatTextView goodInfoPrice = $(R.id.tv_goods_info_price);

        goodInfoTitle.setText(name);
        goodInfoDesc.setText(desc);
        goodInfoPrice.setText(String.valueOf(price));
    }
}
