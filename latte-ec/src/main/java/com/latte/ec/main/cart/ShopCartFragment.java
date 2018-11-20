package com.latte.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
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
    private AppCompatTextView tvClearAllSelectedItem = null;
    private ViewStubCompat mViewStub = null;

    //购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount = 0;

    private void bindViewId() {
        mRecyclerView = $(R.id.rv_shop_cart);
        iconSelectedAll = $(R.id.icon_shop_cart_select_all);
        tvRemoveSelectedItem = $(R.id.tv_top_shop_cart_remove_selected);
        tvClearAllSelectedItem = $(R.id.tv_top_shop_cart_clear);
        mViewStub = $(R.id.stub_no_item);
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
                final List <MultipleItemEntity> data = adapter.getData();
                //要删除的数据
                final List <MultipleItemEntity> deleteItemList = new ArrayList <>();
                for (MultipleItemEntity entity : data) {
                    final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                    if (isSelected) {
                        deleteItemList.add(entity);
                    }
                }
                Log.d("shopcart", "deleteItemList size:" + deleteItemList.size());
                for (MultipleItemEntity entity : deleteItemList) {
                    int removePosition;
                    final int entityPosition = entity.getField(ShopCartItemFields.POSITION);
                    if (entityPosition > mCurrentCount - 1) {
                        removePosition = entityPosition - (mTotalCount - mCurrentCount);
                    } else {
                        removePosition = entityPosition;
                    }

                    if (removePosition <= adapter.getItemCount()) {
                        adapter.remove(removePosition);
                        mCurrentCount = adapter.getItemCount();
                        adapter.notifyItemRangeChanged(removePosition, adapter.getItemCount());
                    }
                    Log.d("shopcart", "removePosition:" + removePosition);
                    Log.d("shopcart", "mTotalCount:" + mTotalCount);
                    Log.d("shopcart", "mCurrentCount:" + mCurrentCount);

                }
                checkItemCount();
            }
        });
        tvClearAllSelectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.getData().clear();
                adapter.notifyDataSetChanged();
                checkItemCount();
            }
        });
    }

    private void checkItemCount(){
        final int count = adapter.getItemCount();
        if (count == 0){
            final View stubView = mViewStub.inflate();
            final AppCompatTextView tvToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "跳转到商品详情页", Toast.LENGTH_SHORT).show();
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        }else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
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
        final ArrayList <MultipleItemEntity> dataList =
                new ShopCartDataConverter().setJsonData(response).convert();

        adapter = new ShopCartAdapter(dataList);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        checkItemCount();
    }
}
