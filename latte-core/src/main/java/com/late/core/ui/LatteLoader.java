package com.late.core.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.late.core.R;
import com.late.core.app.Latte;
import com.late.core.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\11\2 0002.
 */

public class LatteLoader {
    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList <>();
    private static final String DEFAULT_LOADER_STYLE = LoaderStyle.BallSpinFadeLoaderIndicator.name();

    public static void showLoading(Context context, Enum<LoaderStyle> type){
        showLoading(context, type.name());
    }

    public static void showLoading(Context context, String type){

        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);

        Log.d("show", "type:" + type);

        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null){
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE ;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context){
        showLoading(context, DEFAULT_LOADER_STYLE);
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog : LOADERS){
            if (dialog != null){
                if (dialog.isShowing()){
                    dialog.cancel();
                }
            }
        }
    }

}
