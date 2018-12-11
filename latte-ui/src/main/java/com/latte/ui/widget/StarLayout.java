package com.latte.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.iconify.widget.IconTextView;
import com.latte.ui.R;

import java.util.ArrayList;

public class StarLayout extends LinearLayoutCompat implements View.OnClickListener {

    private static final String ICON_UN_SELECT = "{fa-star-o}";
    private static final String ICON_SELECTED = "{fa-star}";
    private static final int STAR_TOTAL_COUNT = 5;
    private static final ArrayList<IconTextView> STARS = new ArrayList<>();

    private int starCount = 0;


    public StarLayout(Context context) {
        super(context);
    }

    public StarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStarIcon();
    }

    public StarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initStarIcon() {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final IconTextView star = new IconTextView(getContext());
            star.setGravity(Gravity.CENTER);
            final LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            star.setLayoutParams(lp);
            star.setText(ICON_UN_SELECT);
            star.setTag(R.id.star_count, i);
            star.setTag(R.id.star_is_select, false);
            star.setOnClickListener(this::onClick);
            STARS.add(star);
            this.addView(star);
        }
    }


    @Override
    public void onClick(View view) {
        IconTextView star = (IconTextView) view;
        starCount = (int) star.getTag(R.id.star_count);
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            IconTextView temp = STARS.get(i);
            if (i <= starCount) {
                temp.setTag(R.id.star_is_select, true);
                temp.setTextColor(Color.RED);
            } else {
                temp.setTag(R.id.star_is_select, false);
                temp.setTextColor(Color.GRAY);
            }
        }
    }

    public int getStarCount() {
        return starCount;
    }

}
