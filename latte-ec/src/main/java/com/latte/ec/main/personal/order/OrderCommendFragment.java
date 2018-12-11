package com.latte.ec.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.late.core.app.Latte;
import com.late.core.fragments.LatteFragment;
import com.late.core.util.callback.CallbackManager;
import com.late.core.util.callback.CallbackType;
import com.late.core.util.callback.IGlobalCallback;
import com.late.core.util.log.LatteLogger;
import com.latte.ec.R;
import com.latte.ui.widget.AutoPhotoLayout;
import com.latte.ui.widget.StarLayout;

public class OrderCommendFragment extends LatteFragment {

    private StarLayout mStarLayout;
    private AutoPhotoLayout mAutoPhotoLayout;



    @Override
    public Object setLayout() {
        return R.layout.fragment_order_comment;
    }

    private void setClick(){
        $(R.id.top_tv_comment_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "stars:"+ mStarLayout.getStarCount(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mStarLayout = $(R.id.custom_star_layout);
        mAutoPhotoLayout = $(R.id.custom_auto_photo_layout);
        mAutoPhotoLayout.setFragment(this);
        setClick();

        CallbackManager.getInstance().addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>(){
            @Override
            public void executeCallback(Uri args) {
                mAutoPhotoLayout.onCropTarget(args);
            }
        });
    }
}
