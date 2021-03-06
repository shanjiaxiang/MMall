package com.latte.ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.late.core.app.Latte;
import com.late.core.net.RestClient;
import com.late.core.net.callback.ISuccess;
import com.late.core.ui.recycler.MultipleFields;
import com.late.core.ui.recycler.MultipleItemEntity;
import com.late.core.ui.recycler.MultipleRecyclerAdapter;
import com.late.core.ui.recycler.MultipleViewHolder;
import com.latte.ec.R;

import java.util.ArrayList;
import java.util.List;

public class ShopCartAdapter extends MultipleRecyclerAdapter {

    private boolean mIsSelectedAll = false;
    private ICartItemListener mCartItemListener = null;

    private double mTotalPrice = 0.0;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        //初始化总价
        for (MultipleItemEntity entity : data) {
            final double price = entity.getField(ShopCartItemFields.PRICE);
            final int count = entity.getField(ShopCartItemFields.COUNT);
            final double total = price * count;
            mTotalPrice = mTotalPrice + total;
        }
        //添加购物车item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    public double getTotalPrice(){
        return mTotalPrice;
    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        mIsSelectedAll = isSelectedAll;
    }

    public void setCartItemListener(ICartItemListener listener) {
        this.mCartItemListener = listener;
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
//        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                //先取出所有值
                final int id = entity.getField(MultipleFields.ID);
                final String thumb = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(ShopCartItemFields.TITLE);
                final String desc = entity.getField(ShopCartItemFields.DESC);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                final double price = entity.getField(ShopCartItemFields.PRICE);

//                final int position = entity.getField(ShopCartItemFields.POSITION);
//                Log.d("shopcart:", "item position:" + position);
//                Log.d("shopcart:", "item title:" + title);


                //取出所有控件
                final AppCompatImageView imgThumb = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView isSelectedIcon = holder.getView(R.id.icon_item_shop_cart);

                //赋值
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));

                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imgThumb);

                //在左侧勾选渲染前改变全选与否状态
                entity.setField(ShopCartItemFields.IS_SELECTED, mIsSelectedAll);

                final boolean selected = entity.getField(ShopCartItemFields.IS_SELECTED);
                //根据数据状态显示左侧勾选
                if (selected) {
                    isSelectedIcon.setTextColor(
                            ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                } else {
                    isSelectedIcon.setTextColor(Color.GRAY);
                }
                //添加左侧勾选点击事件
                isSelectedIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final boolean currentSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                        if (currentSelected) {
                            isSelectedIcon.setTextColor(Color.GRAY);
                            entity.setField(ShopCartItemFields.IS_SELECTED, false);
                        } else {
                            isSelectedIcon.setTextColor(
                                    ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                            entity.setField(ShopCartItemFields.IS_SELECTED, true);
                        }
                    }
                });

                //添加加减事件
                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentCount = entity.getField(ShopCartItemFields.COUNT);
                        if (Integer.parseInt(tvCount.getText().toString()) > 1) {
                            RestClient.Builder()
                                    .url("shop_cart_count.php")
                                    .loader(mContext)
                                    .params("count", currentCount)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            --countNum;
                                            tvCount.setText(String.valueOf(countNum));

                                            if (mCartItemListener != null){
                                                mTotalPrice = mTotalPrice - price;
                                                final double itemTotal = price * countNum;
                                                mCartItemListener.onItemClick(itemTotal);
                                            }

                                        }
                                    })
                                    .build()
                                    .post();
                        }
//
                    }
                });
                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentCount = entity.getField(ShopCartItemFields.COUNT);
                        RestClient.Builder()
                                .url("shop_cart_count.php")
                                .loader(mContext)
                                .params("count", currentCount)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        int countNum = Integer.parseInt(tvCount.getText().toString());
                                        ++countNum;
                                        tvCount.setText(String.valueOf(countNum));

                                        if (mCartItemListener != null){
                                            mTotalPrice = mTotalPrice + price;
                                            final double itemTotal = price * countNum;
                                            mCartItemListener.onItemClick(itemTotal);
                                        }
                                    }
                                })
                                .build()
                                .post();
                    }
//
                });

                break;
            default:
                break;
        }


    }
}
