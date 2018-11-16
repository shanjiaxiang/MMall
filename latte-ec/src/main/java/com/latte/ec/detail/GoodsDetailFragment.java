package com.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.late.core.fragments.LatteFragment;
import com.latte.ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by Administrator on 2018\11\16 0016.
 */

public class GoodsDetailFragment extends LatteFragment {

    public static GoodsDetailFragment create(){
        return new GoodsDetailFragment();
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
