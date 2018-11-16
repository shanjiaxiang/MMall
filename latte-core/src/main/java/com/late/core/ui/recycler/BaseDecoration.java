package com.late.core.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * Created by Administrator on 2018\11\16 0016.
 */

public class BaseDecoration extends DividerItemDecoration {

    public BaseDecoration(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookUpImpl(color, size));
    }

    public static BaseDecoration create(@ColorInt int color, int size) {
        return new BaseDecoration(color, size);
    }


}
