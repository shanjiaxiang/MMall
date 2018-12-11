package com.latte.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.late.core.fragments.LatteFragment;
import com.late.core.util.callback.CallbackManager;
import com.late.core.util.callback.CallbackType;
import com.latte.ec.R;
import com.latte.ec.main.personal.list.ListAdapter;
import com.latte.ec.main.personal.list.ListBean;
import com.latte.ec.main.personal.list.ListItemType;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends LatteFragment {
    RecyclerView mRecyclerView;

    @Override
    public Object setLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRecyclerView = $(R.id.rv_settings);
        final ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWITCH)
                .setId(1)
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked){
                            Toast.makeText(getContext(), "打开推送", Toast.LENGTH_SHORT).show();
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_OPEN_PUSH).executeCallback(null);
                        }else {
                            Toast.makeText(getContext(), "关闭推送", Toast.LENGTH_SHORT).show();
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_STOP_PUSH).executeCallback(null);
                        }
                    }
                })
                .setText("消息推送")
                .build();

        final ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setFragment(new InfoFragment())
                .setText("关于")
                .build();
        final List<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(about);

        //设置RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        ListAdapter mAdapter = new ListAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SettingsClickListener(this));
    }
}
