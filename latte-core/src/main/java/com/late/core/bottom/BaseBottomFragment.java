package com.late.core.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.late.core.R;
import com.late.core.fragments.LatteFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2018\11\14 0014.
 */

public abstract class BaseBottomFragment extends LatteFragment implements View.OnClickListener{
    private final ArrayList <BottomItemFragment> ITEM_FRAGMENT = new ArrayList <>();
    private final ArrayList <BottomTabBean> ITEM_BEAN = new ArrayList <>();
    private final LinkedHashMap <BottomTabBean, BottomItemFragment> ITEMS = new LinkedHashMap <>();
    private int mCurrentFragment = 0;
    private int mIndexFragment = 0;
    private int mClickedColor = Color.RED;
    LinearLayoutCompat mBottomBar = null;


    public abstract LinkedHashMap <BottomTabBean, BottomItemFragment> setItems(ItemBuilder builder);

    public abstract int setIndexFragment();

    @ColorInt
    public abstract int setClickedColor();

    @Override
    public Object setLayout() {
        return R.layout.fragment_button;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIndexFragment = setIndexFragment();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap <BottomTabBean, BottomItemFragment> items = setItems(builder);
        ITEMS.putAll(items);

        for (Map.Entry <BottomTabBean, BottomItemFragment> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomItemFragment value = item.getValue();
            ITEM_BEAN.add(key);
            ITEM_FRAGMENT.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mBottomBar = $(R.id.bottom_bar);
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_text_layout, mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            //设置每个item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean = ITEM_BEAN.get(i);
            //初始化数据
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            if (i == mIndexFragment){
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }
        final ISupportFragment[] fragmentArray = ITEM_FRAGMENT.toArray(new ISupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_fragment_container, mIndexFragment, fragmentArray);
    }

    private void resetColor(){
        final int count = mBottomBar.getChildCount();
        for(int i=0; i< count; i++){
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemIcon.setTextColor(Color.GRAY);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View view) {
        final int tag = (int) view.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) view;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemIcon.setTextColor(mClickedColor);
        itemTitle.setTextColor(mClickedColor);

        getSupportDelegate().showHideFragment(ITEM_FRAGMENT.get(tag), ITEM_FRAGMENT.get(mCurrentFragment));
        mCurrentFragment = tag;
    }
}
