package com.latte.ec.detail;

import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;

import static android.animation.ObjectAnimator.ofFloat;

public class ScaleUpAnimator extends BaseViewAnimator {

    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                ofFloat(target, "scaleX", 0.8f, 1f, 1.4f, 1.2f, 1),
                ofFloat(target, "scaleY", 0.8f, 1f, 1.4f, 1.2f, 1)
        );
    }
}
