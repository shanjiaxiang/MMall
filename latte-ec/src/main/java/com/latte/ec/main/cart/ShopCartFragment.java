package com.latte.ec.main.cart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.late.core.bottom.BottomItemFragment;
import com.late.core.net.RestClient;
import com.late.core.net.callback.ISuccess;
import com.latte.ec.R;

/**
 * Created by Administrator on 2018\11\19 0019.
 */

public class ShopCartFragment extends BottomItemFragment implements ISuccess{

    @Override
    public Object setLayout() {
        return R.layout.fragment_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

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
        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
    }
}
