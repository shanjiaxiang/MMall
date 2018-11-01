package com.latte.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.late.core.activities.ProxyActivity;
import com.late.core.app.Latte;
import com.diabin.fastec.example.R;
import com.late.core.fragments.LatteFragment;

public class ExampleActivity extends ProxyActivity {


    @Override
    public LatteFragment setRootDelegate() {
        return null;
    }


}
