package com.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by Administrator on 2018\10\31 0031.
 */

public enum EcIcons implements Icon {
    icon_scan('\ue6b8'),
    icon_ali_pay('\ue6b8');

    private char charactor;

    EcIcons(char charactor) {
        this.charactor = charactor;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return charactor;
    }
}