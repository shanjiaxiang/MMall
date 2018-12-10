package com.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.late.core.bottom.BottomItemFragment;
import com.latte.ec.R;
import com.latte.ec.main.personal.address.AddressFragment;
import com.latte.ec.main.personal.list.ListAdapter;
import com.latte.ec.main.personal.list.ListBean;
import com.latte.ec.main.personal.list.ListItemType;
import com.latte.ec.main.personal.order.OrderListFramgent;
import com.latte.ec.main.personal.profile.UserProfileFragment;
import com.latte.ec.main.personal.settings.SettingFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018\11\21 0021.
 */

public class PersonalFragment extends BottomItemFragment implements View.OnClickListener{
    private RecyclerView mRecyclerView = null;
    private ListAdapter mAdapter = null;
    private Bundle mArgs = null;

    private AppCompatTextView allOrderList = null;
    private CircleImageView userAvatar = null;

    public static final String ORDER_TYPE = "ORDER_TYPE";

    private void bindViewId(){
        mRecyclerView = $(R.id.rv_personal_setting);
        allOrderList = $(R.id.tv_all_order);
        userAvatar = $(R.id.img_user_avatar);
        allOrderList.setOnClickListener(this);
        userAvatar.setOnClickListener(this);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_personal;
    }

    private void startOrderListByType(){
        final OrderListFramgent framgent = new OrderListFramgent();
        framgent.setArguments(mArgs);
        getLatteParentFragment().getSupportDelegate().start(framgent);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        bindViewId();
        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setFragment(new AddressFragment())
                .setText("收货地址")
                .build();

        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setFragment(new SettingFragment())
                .setText("系统设置")
                .build();
        final List<ListBean> data = new ArrayList <>();
        data.add(address);
        data.add(system);

        //设置RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ListAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new PersonalClickListener(this));

    }


    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if (id == R.id.tv_all_order){
            mArgs.putString(ORDER_TYPE, "all");
            startOrderListByType();
        } else if (id == R.id.img_user_avatar){
            getLatteParentFragment().getSupportDelegate().start(new UserProfileFragment());
        }
    }
}
