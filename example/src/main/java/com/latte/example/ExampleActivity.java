package com.latte.example;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.late.core.activities.ProxyActivity;
import com.late.core.app.Latte;
import com.diabin.fastec.example.R;
import com.late.core.fragments.LatteFragment;
import com.latte.ec.launcher.LauncherFragment;


public class ExampleActivity extends ProxyActivity {


    @Override
    public LatteFragment setRootDelegate() {
        return new LauncherFragment();
    }


}
