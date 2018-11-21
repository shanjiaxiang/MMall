package com.latte.ec.main;

import android.graphics.Color;

import com.late.core.bottom.BaseBottomFragment;
import com.late.core.bottom.BottomItemFragment;
import com.late.core.bottom.BottomTabBean;
import com.late.core.bottom.ItemBuilder;
import com.latte.ec.main.cart.ShopCartFragment;
import com.latte.ec.main.discover.DiscoverFragment;
import com.latte.ec.main.index.IndexFragment;
import com.latte.ec.main.personal.PersonalFragment;
import com.latte.ec.main.sort.SortFragment;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2018\11\14 0014.
 */

public class EcBottomFragment extends BaseBottomFragment {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemFragment> setItems(ItemBuilder builder) {
        LinkedHashMap<BottomTabBean, BottomItemFragment> items = new LinkedHashMap <>();
        items.put(new BottomTabBean("{fa-home}","主页"), new IndexFragment());
        items.put(new BottomTabBean("{fa-sort}","分类"), new SortFragment());
        items.put(new BottomTabBean("{fa-compass}","发现"), new DiscoverFragment());
        items.put(new BottomTabBean("{fa-shopping-cart}","购物车"), new ShopCartFragment());
        items.put(new BottomTabBean("{fa-user}","我的"), new PersonalFragment());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexFragment() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
