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

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;
import com.late.core.app.Latte;
import com.late.core.bottom.BottomItemFragment;
import com.late.core.fragments.LatteFragment;
import com.late.core.net.RestClient;
import com.late.core.net.callback.ISuccess;
import com.late.core.ui.recycler.MultipleItemEntity;
import com.late.core.util.log.LatteLogger;
import com.latte.ec.R;
import com.latte.ec.pay.FastPay;
import com.latte.ec.pay.IAliPayResultListener;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by Administrator on 2018\11\19 0019.
 */

public class ShopCartFragment extends BottomItemFragment implements ISuccess,ICartItemListener,View.OnClickListener,IAliPayResultListener{
    private RecyclerView mRecyclerView = null;
    private ShopCartAdapter adapter = null;
    private IconTextView iconSelectedAll = null;
    private AppCompatTextView tvRemoveSelectedItem = null;
    private AppCompatTextView tvClearAllSelectedItem = null;
    private ViewStubCompat mViewStub = null;
    private AppCompatTextView mTvTotalPrice = null;
    private AppCompatTextView mTvPay = null;



    //购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount = 0;
    private double mTotalPrice = 0.0;

    private void bindViewId() {
        mRecyclerView = $(R.id.rv_shop_cart);
        iconSelectedAll = $(R.id.icon_shop_cart_select_all);
        tvRemoveSelectedItem = $(R.id.tv_top_shop_cart_remove_selected);
        tvClearAllSelectedItem = $(R.id.tv_top_shop_cart_clear);
        mViewStub = $(R.id.stub_no_item);
        mTvTotalPrice = $(R.id.tv_shop_cart_total);
        mTvPay = $(R.id.tv_shop_cart_pay);
    }

    private void setClick() {
        iconSelectedAll.setOnClickListener(this);
        tvRemoveSelectedItem.setOnClickListener(this);
        tvClearAllSelectedItem.setOnClickListener(this);
        mTvPay.setOnClickListener(this);
    }

    //创建订单，注意，和支付是没有关系的
    private void createOrder(){
        final String orderUrl = "";
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        orderParams.put("userid", "");
        orderParams.put("amount", 0.01);
        orderParams.put("comment", "测试支付");
        orderParams.put("type",1);
        orderParams.put("ordertype", 0);
        orderParams.put("isanonymous", true);
        orderParams.put("followeduser", 0);
        RestClient.Builder()
                .url(orderUrl)
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体的支付
                        final int orderId = JSON.parseObject(response).getInteger("result");
                        FastPay.create(ShopCartFragment.this)
                                .setPayResultListener(ShopCartFragment.this)
                                .setOrderId(orderId)
                                .beginPayDialog();
                    }
                })
                .build()
                .post();


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
        adapter.setCartItemListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        mTotalPrice = adapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
        checkItemCount();
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price = adapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));
    }


    //按钮点击事件
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.icon_shop_cart_select_all){

            selectAll();
        }else if (i == R.id.tv_top_shop_cart_remove_selected){
            removeSelected();
        }else if (i==R.id.tv_top_shop_cart_clear){
            clearAll();
        }else if (i == R.id.tv_shop_cart_pay){
            FastPay.create(this)
                    .beginPayDialog();
//            createOrder();
        }

    }



    private void selectAll(){
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

    private void removeSelected(){
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

    private void clearAll(){
        adapter.getData().clear();
        adapter.notifyDataSetChanged();
        checkItemCount();
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }


}
