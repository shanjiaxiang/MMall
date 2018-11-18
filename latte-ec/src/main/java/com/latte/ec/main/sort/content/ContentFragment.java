package com.latte.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.late.core.fragments.LatteFragment;
import com.latte.ec.R;

/**
 * Created by Administrator on 2018\11\16 0016.
 */

public class ContentFragment extends LatteFragment {

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;

    public static ContentFragment getInstance(int contentId){
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null){
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_list_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
