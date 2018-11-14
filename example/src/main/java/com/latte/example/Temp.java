package com.latte.example;

import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivityDelegate;

/**
 * Created by Administrator on 2018\11\2 0002.
 */

public abstract class Temp extends AppCompatActivity implements ISupportActivity{
    public abstract View setRootView();
    private final SupportActivityDelegate DELEGATE = new SupportActivityDelegate(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DELEGATE.onCreate(savedInstanceState);

        initContainer(savedInstanceState);


    }

    private void initContainer(@Nullable Bundle savedInstanceState){
        //最底层的Layout
        final FrameLayout rootLayout = new FrameLayout(this);
        //给Layout绑定id
        rootLayout.setId(R.id.delegate_container);
        //设置Layout
        setContentView(rootLayout);
        if (savedInstanceState == null){
            //给指定id的Layout加载根Fragment
            DELEGATE.loadRootFragment(R.id.delegate_container, (ISupportFragment) setRootView());
        }
    }

    @Override
    protected void onDestroy() {
        DELEGATE.onDestroy();
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
