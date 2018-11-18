package com.latte.ec.main.discover;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.late.core.bottom.BottomItemFragment;
import com.late.core.fragments.LatteFragment;
import com.late.core.web.WebFragmentImpl;
import com.latte.ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class DiscoverFragment extends BottomItemFragment {


    @Override
    public Object setLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebFragmentImpl fragment = WebFragmentImpl.create("index.html");
        fragment.setTopFragment(this.getLatteParentFragment());
        getSupportDelegate().loadRootFragment(R.id.web_discovery_container, fragment);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
