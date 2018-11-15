package com.late.core.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.late.core.R;

public class ImageHolder implements Holder<String> {

    private AppCompatImageView mImageView = null;


    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        Glide.with(context)
                .load(data)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .dontAnimate()
//                        .centerCrop()
                .into(mImageView);
    }
}
