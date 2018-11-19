package com.latte.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.late.core.app.Latte;
import com.late.core.bottom.BottomItemFragment;
import com.late.core.net.RestClient;
import com.late.core.net.callback.ISuccess;
import com.late.core.ui.recycler.MultipleItemEntity;
import com.latte.ec.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\11\19 0019.
 */

public class ShopCartFragment extends BottomItemFragment implements ISuccess {
    private RecyclerView mRecyclerView = null;
    private ShopCartAdapter adapter = null;
    private IconTextView iconSelectedAll = null;
    private AppCompatTextView tvRemoveSelectedItem = null;

    //购物车数量标记
    private int mCurrentDeleteItemPosition = 0;
    private int mTotalShopCartItem;


    private void bindViewId() {
        mRecyclerView = $(R.id.rv_shop_cart);
        iconSelectedAll = $(R.id.icon_shop_cart_select_all);
        tvRemoveSelectedItem = $(R.id.tv_top_shop_cart_remove_selected);
    }

    private void setClick() {
        iconSelectedAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int tag = (int) iconSelectedAll.getTag();
                if (tag == 0) {

                    iconSelectedAll.setTextColor(
                            ContextCompat.getColor(getContext(), R.color.app_main));
                    iconSelectedAll.setTag(1);
                    adapter.setIsSelectedAll(true);
                    //更新RecyclerView的显示状态
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                } else {
                    iconSelectedAll.setTextColor(Color.GRAY);
                    iconSelectedAll.setTag(0);
                    adapter.setIsSelectedAll(false);
                    //更新RecyclerView的显示状态
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                }
            }
        });
        tvRemoveSelectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<MultipleItemEntity> data = adapter.getData();
                //要删除的数据
                final List<MultipleItemEntity> deleteItemList = new ArrayList<>();
                for (MultipleItemEntity entity : data) {
                    final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                    if (isSelected) {
                        deleteItemList.add(entity);
                    }
                }

                for (MultipleItemEntity entity : deleteItemList) {

                }
            }
        });
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        bindViewId();
        setClick();
        iconSelectedAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.Builder()
                .loader(getContext())
                .url("shop_cart.php")
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> dataList =
                new ShopCartDataConverter().setJsonData(response).convert();

        adapter = new ShopCartAdapter(dataList);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }
}
