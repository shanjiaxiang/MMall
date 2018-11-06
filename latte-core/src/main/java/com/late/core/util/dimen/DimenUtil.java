package com.late.core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.late.core.app.Latte;

/**
 * Created by Administrator on 2018\11\2 0002.
 */

public class DimenUtil {

    public static int getScreenWidth(){
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

}
