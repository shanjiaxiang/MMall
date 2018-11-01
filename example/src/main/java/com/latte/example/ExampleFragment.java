package com.latte.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.diabin.fastec.example.R;
import com.late.core.fragments.LatteFragment;

/**
 * Created by Administrator on 2018\11\1 0001.
 */

public class ExampleFragment extends LatteFragment {


    @Override
    public Object setLayout() {
        return R.layout.fragment_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
