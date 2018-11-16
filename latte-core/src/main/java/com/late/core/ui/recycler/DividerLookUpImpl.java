package com.late.core.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
 * Created by Administrator on 2018\11\16 0016.
 */

public class DividerLookUpImpl implements DividerItemDecoration.DividerLookup {
    private final int COLOR;
    private final int SIZE;

    public DividerLookUpImpl(int color, int size) {
        this.COLOR = color;
        this.SIZE = size;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder()
                .color(COLOR)
                .size(SIZE)
                .build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder()
                .color(COLOR)
                .size(SIZE)
                .build();
    }
}
