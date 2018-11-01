package com.latte.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

/**
 * Author: 傅令杰
 * Date: 2018/6/11
 */

public class GlideImageLoader extends ImageLoader {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(path)
                .apply(OPTIONS)
                .into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        return new AppCompatImageView(context);
    }
}
