package com.latte.ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.late.core.fragments.LatteFragment;
import com.latte.ec.R;
import com.latte.ec.main.personal.list.ListAdapter;
import com.latte.ec.main.personal.list.ListBean;
import com.latte.ec.main.personal.list.ListItemType;
import com.latte.ec.main.personal.settings.NameFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\11\22 0022.
 */

public class UserProfileFragment extends LatteFragment {

    private Toolbar mToolbar = null;
    private RecyclerView mRecyclerView = null;
    private ListAdapter mAdapter = null;



    private void bindViewId(){
        mToolbar = $(R.id.toolbar_user_profile);
        mRecyclerView = $(R.id.rv_user_profile);
    }


    @Override
    public Object setLayout() {
        return R.layout.fragment_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        bindViewId();


        final ListBean image = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_AVATAR)
                .setId(1)
                .setImageUrl("http://i9.qhimg.com/t017d891ca365ef60b5.jpg")
                .build();

        final ListBean name = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("姓名")
                .setValue("未设置姓名")
                .setFragment(new NameFragment())
                .build();

        final ListBean gender = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("性别")
                .setValue("未设置性别")
                .build();

        final ListBean birth = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("生日")
                .setValue("未设置生日")
                .build();
        final List<ListBean> data = new ArrayList<>();
        data.add(image);
        data.add(name);
        data.add(gender);
        data.add(birth);

        //设置RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter  = new ListAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }
}
