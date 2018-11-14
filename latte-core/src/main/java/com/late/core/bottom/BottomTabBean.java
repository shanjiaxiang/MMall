package com.late.core.bottom;

/**
 * Created by Administrator on 2018\11\14 0014.
 */

public final class BottomTabBean {

    private final CharSequence ICON;
    private final CharSequence TITLE;


    public BottomTabBean(CharSequence icon, CharSequence title) {
        ICON = icon;
        TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }



}
